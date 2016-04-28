package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

}
