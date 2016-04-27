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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;


public class SQLiteSeeder
{
  public static void main( String args[] ) throws FileNotFoundException
  {
    Connection conn = null;
    Statement stmt = null;
    FileReader questionsTxtFile = new FileReader("questions.txt");
    Scanner questionsReader = new Scanner(questionsTxtFile);
    
    try {
      Class.forName("org.sqlite.JDBC");
      conn = DriverManager.getConnection("jdbc:sqlite:CCAT.db");
      conn.setAutoCommit(false);
      System.out.println("Opened database successfully");
      int partId = 0;
      int headerId = 0;
      String sql = "";
      stmt = conn.createStatement();
      
      while(questionsReader.hasNextLine()){
          String line = questionsReader.nextLine();
          
          System.out.println(line);
          if (line.isEmpty()){}
          
          else if (line.charAt(0) == '['){
              partId++;
          }
          
          else if (line.charAt(0) == '-'){
              
              sql =   "INSERT INTO headers (header, part) " +
                    "VALUES ('" + line.split("-")[1] + "', " + partId + ");"; 
              stmt.executeUpdate(sql);
              headerId++;
          }
          
          else{
              sql =   "INSERT INTO questions (question, hid) " +
                    "VALUES ('" + line + "', " + headerId + ");"; 
              stmt.executeUpdate(sql);
          }
      }
      
      
      stmt.close();
      conn.commit();
      conn.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Records created successfully");
  }
  
  
}


