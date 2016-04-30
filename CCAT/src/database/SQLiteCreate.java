

package database;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author John
 */
public class SQLiteCreate {

    private static Connection c;
    private static Statement stmt;
    
    /**
     * 
     * @param args 
     */
    public static void main(String args[]) {
        {
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:C:\\Program Files (x86)\\CCAT-files\\CCAT.db");
                System.out.println("Opened database successfully");


          stmt = c.createStatement();
          String sql =  "CREATE TABLE headers ("+
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                        "header TEXT,"+
                        "part SMALLINT);";

         stmt.executeUpdate(sql);
         sql =          "CREATE TABLE questions (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                        "question TEXT NOT NULL, " +
                        "hid INTEGER NOT NULL,"+
                        "FOREIGN KEY (hid) REFERENCES headers(id) "+
                        "ON DELETE CASCADE ON UPDATE CASCADE);";

         stmt.executeUpdate(sql);
         sql =          "CREATE TABLE users ( " +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                        "fName TEXT," +
                        "lName TEXT," +
                        "username TEXT," +
                        "password TEXT," +
                        "admin BIT);" ;

         stmt.executeUpdate(sql);
         sql =          "CREATE TABLE answers (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                        "answer TEXT NOT NULL," +
                        "created DATE,"+
                        "uid INTEGER NOT NULL," +
                        "qid INTEGER NOT NULL," +
                        "FOREIGN KEY (qid) REFERENCES questions(id) " +
                        "ON DELETE CASCADE ON UPDATE CASCADE," +
                        "FOREIGN KEY (uid) REFERENCES users(id) " +
                        "ON DELETE CASCADE ON UPDATE CASCADE);";

                    stmt.executeUpdate(sql);
                    sql = "CREATE TABLE scores ("
                            + "dailyScore FLOAT(5, 2),"
                            + "weeklyScore FLOAT(5, 2),"
                            + "monthlyScore FLOAT(5,2),"
                            + "quarterlyScore FLOAT(5, 2));";

                    stmt.executeUpdate(sql);
                    stmt.close();
                    c.close();
                } catch (ClassNotFoundException | SQLException e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(1);
                }
                System.out.println("All tables created successfully");
            }
    }

}
