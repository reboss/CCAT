/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccat_model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author JRebo_000
 */
public class QuestionLoader {
 
    
    private final Map<String, Map<String, List<String>>> content;
    private Map<String, List<String>> orderedSubheaders;
    
    public QuestionLoader(){
        content = new HashMap<>();
    }
    
    public void loadQuestions(){}
    

    public Map<String, Map<String, List<String>>> getContent()
    {return Collections.unmodifiableMap(content);}
    
    public void traverseMap(){
        for (String header : orderedSubheaders.keySet()){
            System.out.println(header);
            for (String subheader : orderedSubheaders.get(header)){
                System.out.println(subheader);
                for (String field : content.get(header).get(subheader))
                    System.out.println(field);
            }
        }
    }
    
    public Map<String, List<String>> getOrderedSubheaders(){
        return orderedSubheaders;
    }
    
    public List<String> getHeaders(){
        List<String> headers = new ArrayList<>();
        for (String key : content.keySet()){
            headers.add(key);
        }
        return headers;
    }
}
