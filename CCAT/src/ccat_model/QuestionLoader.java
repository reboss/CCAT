/**
 * Copyright (C) John Mulvany-Robbins, Elliott Sobek, Zac Batog.
 * Github profiles:
 * John Mulvany-Robbins (https://github.com/reboss),
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
    private Map<String, List<String>> orderedSubheaders;
    private Connection dbConnection;

    /**
     * 
     */
    public QuestionLoader() throws SQLException {
        content = new HashMap<>();
        orderedSubheaders = new HashMap<>();
        dbConnection = DriverManager.getConnection("jdbc:sqlite:CCAT.db");
        dbConnection.setAutoCommit(false);
        
        
    }

    /**
     * 
     */
    public void loadQuestions() throws SQLException {
        
        Statement stmt1 = dbConnection.createStatement();
        Statement stmt2 = dbConnection.createStatement();
        String sql = "SELECT id, part, header FROM headers";
        ResultSet headerQueryResults = stmt1.executeQuery(sql);
        
        
        while (headerQueryResults.next()){
            
            int part = headerQueryResults.getInt("part");
            String header = headerQueryResults.getString("header");
            
            Map<String, List<String>> questions = new HashMap<>();
            List<String> headers = new ArrayList<>();
            
            if (content.get("Part "+part) == null){
                
                content.put("Part "+ part, questions);
                orderedSubheaders.put("Part "+part, headers);
                
            }
            
            if (content.get("Part "+part).get(header) == null){
                
                content.get("Part "+part).put(header, new ArrayList<>());
                orderedSubheaders.get("Part "+part).add(header);
                
            }
            
            sql = "SELECT question, hid FROM questions WHERE hid = " + headerQueryResults.getInt("id");
            ResultSet questionsQueryResults = stmt2.executeQuery(sql);
            
            while (questionsQueryResults.next()){              
                
                String question = questionsQueryResults.getString("question");                
                content.get("Part "+part).get(header).add(question);
                
                
            }

        }
        //this.traverseMap();
    }

    /**
     * 
     * @return 
     */
    public Map<String, Map<String, List<String>>> getContent() {
        return Collections.unmodifiableMap(content);
    }

    /**
     * 
     */
    public void traverseMap() {
        for (String header : orderedSubheaders.keySet()) {
            System.out.println(header);
            for (String subheader : orderedSubheaders.get(header)) {
                System.out.println(subheader);
                for (String field : content.get(header).get(subheader)) {
                    System.out.println(field);
                }
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
