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
        dbConnection = null;
        
        dbConnection = DriverManager.getConnection("jdbc:sqlite:CCAT.db");
        dbConnection.setAutoCommit(false);
        
        
    }

    /**
     * 
     */
    public void loadQuestions() throws SQLException {
        
        Statement stmt = null;
        stmt = dbConnection.createStatement();
        String sql = "SELECT id, part, header FROM headers";
        ResultSet headerQueryResults = stmt.executeQuery(sql);
        
        while (headerQueryResults.next()){
            
            int part = headerQueryResults.getInt("part");
            String header = headerQueryResults.getString("header");
            
            if (content.get(part) == null){
                content.put("Part "+ part, new HashMap<>());
                orderedSubheaders.put("Part "+part, new ArrayList<>());
            }
            
            sql = "SELECT question, hid, FROM questions WHERE hid = " + headerQueryResults.getInt("id");
            ResultSet questionsQueryResults = stmt.executeQuery(sql);
            
            while (questionsQueryResults.next()){
                
                if (content.get(part).get(header) == null){
                    content.get(part).put(header, new ArrayList<>());
                    orderedSubheaders.get(part).add(header);
                }
                
                String question = questionsQueryResults.getString("question");                
                content.get(part).get(header).add(question);
                
            }
            
            

        }
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
