/**
 * Copyright (C) John Robbins, Elliott Sobek, Zac Batog.
 * Github profiles:
 * John Robbins (https://github.com/reboss),
 * Elliott Sobek (https://github.com/ElliottSobek),
 * Zac Batog (https://github.com/batogz) 
 */

package database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Elliott
 */
public class SQLiteSeeder {

    /**
     * 
     * @param args
     * @throws FileNotFoundException
     * @throws SQLException 
     */
    public static void main(String args[]) throws FileNotFoundException, SQLException {
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

            while (questionsReader.hasNextLine()) {
                String line = questionsReader.nextLine();

                System.out.println(line);
                if (line.isEmpty()) {
                } else if (line.charAt(0) == '[') {
                    partId++;
                } else if (line.charAt(0) == '-') {

                    sql = "INSERT INTO headers (header, part) "
                            + "VALUES ('" + line.split("-")[1] + "', " + partId + ");";
                    stmt.executeUpdate(sql);
                    headerId++;
                } else {
                    sql = "INSERT INTO questions (question, hid) "
                            + "VALUES ('" + line.split(" ", 2)[1] + "', " + headerId + ");";
                    stmt.executeUpdate(sql);
                }
            }

            stmt.close();
            conn.commit();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

}
