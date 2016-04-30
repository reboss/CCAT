package ccat_model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author John
 */
public class AnswerModel {

    private Connection connection;
    
    public AnswerModel() throws SQLException{
        connection = DriverManager.getConnection("jdbc:sqlite:CCAT.db");
    }
    
    public void saveAnswers(ArrayList<String> answers){
        
    }
    
    public void retrieveAnswers(){
        
    }
}
