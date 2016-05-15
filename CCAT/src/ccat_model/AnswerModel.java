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
import java.util.List;

/**
 *
 * @author John
 */
public class AnswerModel {

    private final Connection connection;
    private final int TOTAL_QUESTIONS = 44;
    
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
                
        float score = (TOTAL_QUESTIONS - answers.size()) / TOTAL_QUESTIONS * 100;
        System.out.println("The Score is "+score);
        try (Statement stmt = connection.createStatement()) {
            String sql =
                    "INSERT INTO audits "
                    + "(created, name, score) "
                    + "VALUES (DATE('now'), " + username + ", " + score
                    + ")";
            
            System.out.println("The audits table query was : \n\t"+ sql);
            System.out.println("The insertion into audits table returned, "+stmt.executeUpdate(sql));
            
            int auditId;
            sql = "SELECT LAST_INSERT_ROWID() AS id;";
            ResultSet id = stmt.executeQuery(sql);
            id.next();
            auditId = id.getInt("id");
            System.out.println("The result set of last insert id is "+id);
            System.out.println("The last inserted id was "+auditId);
            
            if (score != 100.0){
                
                sql =
                        "INSERT INTO answers"
                        + "(answer, aid, qid) "
                        + "VALUES ('"
                        + answers.get(0).getText()+"',"+ auditId + ", "
                        + answers.get(0).getParentId() + ")";
                
                
                for (int i = 1; i < answers.size(); i++){
                    
                    sql +=
                            ", ('" + answers.get(i).getText() + "',"+ auditId +", "
                            + answers.get(i).getParentId() + ")";
                }
                
                System.out.println("The query was : \n\t"+sql);
                System.out.println("The result of the above query was "+stmt.executeUpdate(sql));
            }
            stmt.close();
        }
        connection.close();
        
    }
    
    /**
     *
     */
    public void loadAnswers() {

    }
}
