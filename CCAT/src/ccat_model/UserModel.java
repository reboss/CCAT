/**
 * Copyright (C) John Robbins, Elliott Sobek, Zac Batog.
 * Github profiles:
 * John Robbins (https://github.com/reboss),
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
public class UserModel {

    private final Connection con;
    private final String salt;

    /**
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public UserModel() throws ClassNotFoundException, SQLException {
        this.con = getConnection();
        this.salt = "M5@aG9:[2cY0";
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
                        = con.prepareStatement("SELECT password FROM users "
                                + "WHERE username = '" + user + "'");
                ResultSet result = statement.executeQuery();
                result.next();
                return result.getString("password").equals(MD5(pass + this.salt));
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
                        = con.prepareStatement("SELECT users.admin FROM users "
                                + "WHERE users.username = '" + user + "'");
                ResultSet result = statement.executeQuery();
                result.next();
                int adminStatus = result.getInt("users.admin");
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
     * @param fName
     * @param lName
     * @param password
     * @param access
     */
    public void addUser(String fName, String lName, String password, int access) {
        // Make auto username, createusername handles for isUserInDb thus no checking needed
        String username = createUserName(fName, lName);
        String pass = MD5(password + this.salt);
        try {
            PreparedStatement posted = con.prepareStatement("INSERT INTO "
                    + "users (fName, lName, username, password, admin) VALUES "
                    + "('" + fName + "', '" + lName + "', '" + username + "', '"
                    + pass + "', " + access + ")");
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
    public void updateUser(String user, String password, int admin) {
        if (isUserInDb(user)) {
            Map<String, List<String>> creds = getUsersCredentials(user);
            String pass, access = "";
            if (password.equals("")) {
                pass = MD5(creds.get(user).get(0) + this.salt);
            } else {
                pass = MD5(password + this.salt);
            }

            try {
                PreparedStatement posted = con.prepareStatement(
                        "UPDATE users SET users.password = '" + pass + "', users.admin = "
                        + access + " WHERE users.username = '" + user + "'");
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
     * @param user
     * @return
     */
    private boolean isUserInDb(String user) {
        try {
            PreparedStatement statement
                    = con.prepareStatement("SELECT username FROM users "
                            + "WHERE username = '" + user + "'");
            ResultSet result = statement.executeQuery();
            result.next();
            String s = result.getString("username");
            return !(s == null);
        } catch (Exception e) {
            System.err.println(e);
        }
        return false;
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
                    username = result.getString("users.username");
                    passAndType.add(result.getString("users.password"));
                    passAndType.add(result.getString("users.admin"));
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
