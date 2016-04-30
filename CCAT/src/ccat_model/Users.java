/**
 * Copyright (C) John Mulvany-Robbins, Elliott Sobek, Zac Batog.
 * Github profiles:
 * John Mulvany-Robbins (https://github.com/reboss),
 * Elliott Sobek (https://github.com/ElliottSobek),
 * Zac Batog (https://github.com/batogz) 
 */

package ccat_model;

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
public class Users {

    private final Connection con;

    /**
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Users() throws ClassNotFoundException, SQLException {
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
        if (isUserInDb(user)) {
            try {
                PreparedStatement statement
                        = con.prepareStatement("SELECT users.password FROM users WHERE users.username = '" + user + "'");
                ResultSet result = statement.executeQuery();
                result.next(); // <- Needs testing, may not need.
                return result.getString("users.password").equals(pass);
            } catch (Exception e) {
                System.err.println(e);
            }
            return false;
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
     * @param fName
     * @param lName
     * @param password
     * @param access
     */
    public void addUser(String fName, String lName, String password, String access) {
        // Make auto username, createusername handles for isUserInDb thus no checking needed
        String username = createUserName(fName, lName);
        String pass = MD5(password);
        try {
            PreparedStatement posted = con.prepareStatement("INSERT INTO "
                    + "users (fName, lName,username, password, admin) VALUES "
                    + "('" + fName + "', '" + lName + "', '" + username + "', '"
                    + pass + "', '" + access + "')");
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
     * @throws java.sql.SQLException
     */
    public void deleteOutOfDateRecords() throws SQLException {
        try {
            PreparedStatement statement
                    = con.prepareStatement("DELETE FROM answers WHERE "
                            + "answers.created < NOW() - INTERVAL 14 DAY");
            statement.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     *
     * @param fName
     * @param lName
     * @return
     */
    private String createUserName(String fName, String lName) {
        String username = lName + fName.charAt(0);
        int i = 0;
        while (isUserInDb(username)) {
            // If username does not have number on end add number
            if (!Character.isDigit(username.charAt(username.length() - 1))) {
                i++;
                username += Integer.toString(i);
            } else { // Replace number on end
                i++;
                username = username.replace(Integer.toString(i - 1), Integer.toString(i));
            }
        }
        return username;
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
