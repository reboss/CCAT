/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author JRebo_000
 */

    
import java.sql.*;

public class SQLiteCreate
{
  public static void main (String args[]){
  {
    Connection c = null;
    Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:CCAT.db");
      System.out.println("Opened database successfully");

      stmt = c.createStatement();
      String sql =  "CREATE TABLE headers ("+
                    "id INT PRIMARY KEY NOT NULL,"+
                    "header TEXT,"+
                    "part SMALLINT);";
      
     stmt.executeUpdate(sql);
     sql =          "CREATE TABLE questions (" +
                    "id INT PRIMARY KEY NOT NULL," +
                    "question TEXT NOT NULL, " +
                    "hid INT NOT NULL,"+
                    "FOREIGN KEY (hid) REFERENCES headers(id) "+
                    "ON DELETE CASCADE ON UPDATE CASCADE);";
     
     stmt.executeUpdate(sql);
     sql =          "CREATE TABLE users ( " +
                    "id INT PRIMARY KEY NOT NULL," +
                    "fName TEXT," +
                    "lName TEXT," +
                    "username TEXT," +
                    "password TEXT," +
                    "admin BIT);" ;
     
     stmt.executeUpdate(sql);
     sql =          "CREATE TABLE answers (" +
                    "id INT PRIMARY KEY NOT NULL," +
                    "answer TEXT NOT NULL," +
                    "created DATE,"+
                    "uid INT NOT NULL," +
                    "qid INT NOT NULL," +
                    "FOREIGN KEY (qid) REFERENCES questions(id) " +
                    "ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (uid) REFERENCES users(id) " +
                    "ON DELETE CASCADE ON UPDATE CASCADE);";
     
     stmt.executeUpdate(sql);
     sql =          "CREATE TABLE scores (" +
                    "dailyScore FLOAT(5, 2)," +
                    "weeklyScore FLOAT(5, 2),"+
                    "monthlyScore FLOAT(5,2),"+
                    "quarterlyScore FLOAT(5, 2));"; 
      
      
      stmt.executeUpdate(sql);
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Table created successfully");
    }
  }
}

