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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author John
 */
public class QuestionLoader {

    private final Map<String, Map<String, List<String>>> content;
    private final Map<String, List<String>> orderedSubheaders;
    private final Connection dbConnection;
    private final List<Integer> questionIds;
    private final List<Header> headers;
    
    /**
     *
     * @throws java.sql.SQLException
     */
    public QuestionLoader() throws SQLException {
        content = new HashMap<>();
        questionIds = new ArrayList<>();
        orderedSubheaders = new HashMap<>();
        dbConnection = DriverManager.getConnection("jdbc:sqlite:CCAT.db");
        dbConnection.setAutoCommit(false);
        headers = new ArrayList<>();

    }

    /**
     *
     * @return 
     * @throws java.sql.SQLException
     */
    public List<Header> loadQuestions() throws SQLException {

        Statement stmt1 = dbConnection.createStatement();
        Statement stmt2 = dbConnection.createStatement();
        String sql = "SELECT id, part, header FROM headers";
        ResultSet headerQueryResults = stmt1.executeQuery(sql);

        while (headerQueryResults.next()) {

            Integer part = headerQueryResults.getInt("part");
            String headerText = headerQueryResults.getString("header");
            Integer id = headerQueryResults.getInt("id");
            
            Header header = new Header(headerText, id, part);

            sql = "SELECT id, question, hid FROM questions WHERE hid = " + headerQueryResults.getInt("id");
            ResultSet questionsQueryResults = stmt2.executeQuery(sql);

            while (questionsQueryResults.next()) {
                
                id = questionsQueryResults.getInt("id");
                String questionText = questionsQueryResults.getString("question");
                
                Question question = new Question(questionText, id, null);
                header.addChild(question);
                
            }
            headers.add(header);
        }
        
        return headers;
    }

    /**
     *
     * @return
     */
    public Map<String, Map<String, List<String>>> getContent() {
        return Collections.unmodifiableMap(content);
    }
    
    public List<Integer> getIds(){return questionIds;}

    /**
     *
     */
    public void traverse() {
        for (Header header : headers){
            System.out.println(header);
            for(Question question : header.getChildren()){
                System.out.println(question);
            }
        }
    }

    /**
     *
     * @return
     */
    public Map<String, List<String>> getOrderedSubheaders() {
        return Collections.unmodifiableMap(orderedSubheaders);
    }

    /**
     *
     * @return
     */
    public List<String> getHeaders() {
        List<String> headers = new ArrayList<>();
        for (String key : content.keySet()) {
            headers.add(key);
        }
        return headers;
    }
}
