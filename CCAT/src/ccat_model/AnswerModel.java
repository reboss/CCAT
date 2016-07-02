/**
 * Copyright (C) John Robbins, Elliott Sobek, Zac Batog.
 * Github profiles:
 * John Robbins (https://github.com/reboss),
 * Elliott Sobek (https://github.com/ElliottSobek),
 * Zac Batog (https://github.com/batogz) 
 */

package ccat_model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author John
 */
public class AnswerModel {

    private final Connection connection;
    private final float TOTAL_QUESTIONS = 44;
    
    /**
     *
     * @throws SQLException
     */
    public AnswerModel() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:CCAT.db");
        connection.setAutoCommit(true);
    }

    /**
     *
     * @param answers HashMap of { question_id : answer }
     * @param username
     * @throws SQLException
     */
    public void saveAnswers(List<Answer> answers, String username) throws SQLException {
        float score = ((TOTAL_QUESTIONS - answers.size()) / TOTAL_QUESTIONS) * 100;
        System.out.println("The Score is " + score);
        try (Statement stmt = connection.createStatement()) {
            String sql =
                    "INSERT INTO audits "
                    + "(created, name, score) "
                    + "VALUES (DATE('now'), " + username + ", " + score + ")";
            
            System.out.println("The audits table query was : \n\t" + sql);
            System.out.println("The insertion into audits table returned, " + stmt.executeUpdate(sql));
            
            int auditId;
            sql = "SELECT LAST_INSERT_ROWID() AS id;";
            ResultSet id = stmt.executeQuery(sql);
            id.next();
            auditId = id.getInt("id");
            System.out.println("The result set of last insert id is " + id);
            System.out.println("The last inserted id was " + auditId);
            
            if (score != 100.0){
                
                sql =
                        "INSERT INTO answers"
                        + "(answer, created, aid, qid) "
                        + "VALUES ('"
                        + answers.get(0).getText() + "', DATE('now'), " + auditId + ", "
                        + answers.get(0).getParentId() + ")";
                
                
                for (int i = 1; i < answers.size(); i++){
                    
                    sql +=
                            ", ('" + answers.get(i).getText() + "', " + auditId + ", "
                            + answers.get(i).getParentId() + ")";
                }
                
                System.out.println("The query was : \n\t" + sql);
                System.out.println("The result of the above query was " + stmt.executeUpdate(sql));
            }
            stmt.close();
        }
        connection.close();
        
    }
    
    /**
     *
     * @return 
     * @throws java.sql.SQLException
     */

    public List<Audit> loadAnswers() throws SQLException {
        
        List<Audit> audits = new ArrayList<>();
        Statement stmt = connection.createStatement();
        Statement stmt2 = connection.createStatement();
        ResultSet result1;
        ResultSet result2;
        String sql = "SELECT * FROM audits ORDER BY created DESC";
        result1 = stmt.executeQuery(sql);
        
        while(result1.next()){
            
            int id = result1.getInt("id");
            Date date = result1.getDate("created");
            String name = result1.getString("name");
            double score = result1.getDouble("score");
            Audit audit = new Audit(name, id, score, 0);
            
            
            sql =   "SELECT * FROM answers, questions WHERE aid = " + 
                    id + " AND questions.id = answers.qid";
            result2 = stmt2.executeQuery(sql);
            
            // add selection and braden score to database
            while (result2.next()){
                
                String question = result2.getString("question");
                String answer = result2.getString("answer");
                audit.addChild(new Answer(answer, question, id));
                
            }
        }
        
        return audits;

    }
}
