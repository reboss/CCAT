package ccat_model;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author John
 */
public class AnswerModel {

    private Connection connection;
    
    public AnswerModel() throws SQLException{
        connection = DBConnection.getInstance();
    }
    
    public void saveAnswers(){
        
    }
    
    public void retrieveAnswers(){
        
    }
}
