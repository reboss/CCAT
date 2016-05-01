package ccat_model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.ArrayList;



/**
 *
 * @author John
 */
public class AnswerModel {

    private Connection connection;
    
    public AnswerModel() throws SQLException{
        connection = DriverManager.getConnection("jdbc:sqlite:CCAT.db");

        connection.setAutoCommit(false);
    }
    
    public void saveAnswers(List<String> answers, int userID) throws SQLException{
        
        Statement stmt = connection.createStatement();
        int user = userID;
        Date date = new Date();
        String qid = "(SELECT id FROM questions WHERE question = ";
        String sql =    "INSERT INTO answers ()"+
                        "VALUES ()";


    }
    
    public void saveAnswers(ArrayList<String> answers){


        
    }
    
    public void retrieveAnswers(){
        
    }
}
