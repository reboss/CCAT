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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author John
 */
public class AnswerModel {

    private final Connection connection;

    /**
     *
     * @throws SQLException
     */
    public AnswerModel() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:CCAT.db");
        connection.setAutoCommit(false);
    }

    /**
     *
     * @param answers HashMap of { question_id : answer }
     * @param userID
     * @throws SQLException
     */
    public void saveAnswers(HashMap<Integer, String> answers, int userID) throws SQLException {

        Statement stmt = connection.createStatement();
        int user = userID;
        Date date = new Date();
        String qid = "(SELECT id FROM questions WHERE question = ";
        String sql = "INSERT INTO answers ()"
                + "VALUES ()";

    }

    /**
     *
     * @param answers
     */
    public void saveAnswers(ArrayList<String> answers) {

    }

    /**
     *
     */
    public void retrieveAnswers() {

    }
}
