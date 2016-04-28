package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
     * @return
     * @throws ClassNotFoundException
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
    public String isAdmin(String user) {
        return null;
    }
    
    /**
     * 
     * @param user
     * @return 
     */
    public boolean isUserInDb(String user) {
        return false;
    }
    
    /**
     * 
     * @param user
     * @param pass
     * @param access 
     */
    public void addUser(String user, String pass, String access) {
        
    }
    
    /**
     * 
     * @param user
     * @param pass
     * @param access 
     */
    public void updateUser(String user, String pass, String access) {
        
    }
    
    /**
     * 
     * @param user 
     */
    public void deleteUser(String user) {
        
    }

    /**
     * 
     * @return
     * @throws SQLException 
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
     * @return 
     */
    private Map<String, List<String>> getUsersCredentials() {
        return null;
    }
    
    /**
     * 
     * @param pass
     * @return 
     */
    private String MD5(String pass) {
        return null;
    }

}
