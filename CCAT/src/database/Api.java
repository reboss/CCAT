package database;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Elliott
 */
public class Api {

    private final Connection con;

    /**
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Api() throws ClassNotFoundException, SQLException {
        this.con = getConnection();
    }

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:ccat.db");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    /**
     *
     * @param user
     * @param pass
     * @return
     */
    public boolean isValidCredentials(String user, String pass) {
        return false;
    }

    /**
     *
     * @param user
     * @return
     */
    public boolean isAdmin(String user) {
        if (isUserInDb(user)) {
            try {
                PreparedStatement statement
                        = con.prepareStatement("SELECT users.admin FROM users WHERE users.username = '" + user + "'");
                ResultSet result = statement.executeQuery();
                result.next();  // <- Needs testing, may not need.
                int adminStatus = Integer.parseInt(result.getString("users.admin"));
                return adminStatus == 1;
            } catch (SQLException | NumberFormatException e) {
                System.err.println(e);
            }
        } else {
            // User not in database
        }
        return false;
    }

    /**
     *
     * @param user
     * @return
     */
    public boolean isUserInDb(String user) {
        try {
            PreparedStatement statement
                    = con.prepareStatement("SELECT users.username FROM users WHERE users.username = '" + user + "'");
            ResultSet result = statement.executeQuery();
            result.next(); // <- Needs testing, may not need.
            String s = result.getString("users.username");
            return s == null;
        } catch (Exception e) {
            System.err.println(e);
        }
        return false;
    }

    /**
     *
     * @param user
     * @param password
     * @param access
     */
    public void addUser(String user, String password, String access) {
        String pass = MD5(password);
        try {
            PreparedStatement posted = con.prepareStatement("INSERT INTO "
                    + "users (username, password, admin) VALUES "
                    + "('" + user + "', '" + pass + "', '" + access + "')");
            posted.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     *
     * @param user
     * @param password
     * @param admin
     */
    public void updateUser(String user, String password, String admin) {
        if (isUserInDb(user)) {
            Map<String, List<String>> creds = getUsersCredentials(user);
            String pass = "", access = "";
            if (password.equals("")) {
                pass = creds.get(user).get(0);
            }
            if (admin.equals("")) {
                access = creds.get(user).get(1);
            }

            try {
                PreparedStatement posted = con.prepareStatement(
                        "UPDATE users SET users.password = '" + pass + "', users.admin = '"
                        + access + "' WHERE users.username = '" + user + "'");
                posted.executeUpdate();
            } catch (Exception e) {
                System.err.println(e);
            }
        } else {
            // User not in database
        }
    }

    /**
     *
     * @param user
     */
    public void deleteUser(String user) {
        if (isUserInDb(user)) {
            try {
                PreparedStatement statement
                        = con.prepareStatement("DELETE FROM users WHERE users.username = '" + user + "'");
                statement.executeUpdate();
            } catch (Exception e) {
                System.err.println(e);
            }
        } else {
            // No user in database
        }
    }

    /**
     *
     * @return @throws SQLException
     */
    public List<String> readDB() throws SQLException {
        List<String> list = null;
        try (PreparedStatement statement = con.prepareStatement("SELECT * FROM users");
                ResultSet result = statement.executeQuery()) {
            list = new ArrayList<>();
            while (result.next()) {
                list.add(result.getString("fName"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return list;

    }

    /**
     *
     * @param user
     * @return
     */
    private Map<String, List<String>> getUsersCredentials(String user) {
        if (isUserInDb(user)) {
            try {
                PreparedStatement statement
                        = con.prepareStatement("SELECT users.username, users.password");
                ResultSet result = statement.executeQuery();
                Map<String, List<String>> map = new HashMap<>();
                String username;
                while (result.next()) {
                    List<String> passAndType = new ArrayList<>();
                    username = result.getString("users.UName");
                    passAndType.add(result.getString("users.UPW"));
                    passAndType.add(result.getString("users.UType"));
                    map.put(username, passAndType);
                }
                return map;
            } catch (Exception e) {
                System.err.println(e);
            }
        } else {
            // User not in database
        }
        return null;
    }

    /**
     *
     * @param pass
     * @return
     */
    private String MD5(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(pass.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
