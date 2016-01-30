/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class FileLoader {
    
    private final Scanner fileLoader;
    private final Map<String, Map<String, List<String>>> content;
    
    public FileLoader(FileReader file) throws FileNotFoundException{
        fileLoader = new Scanner(file);
        content = new HashMap<>();
    }
    
    public Map<String, Map<String, List<String>>> getContent(){return Collections.unmodifiableMap(content);}
    
    public void traverseMap(){
        for (String header : content.keySet()){
            System.out.println(header);
            for (String subheader : content.get(header).keySet()){
                System.out.println(subheader);
                for (String field : content.get(header).get(subheader)) {
                    System.out.println(field);
                }
            }
        }
    }
    
    public void loadTemplate(){
        String header = "", subHeader = "", temp;
        Map<String, List<String>> sections = new HashMap<>();
        List<String> fields = new ArrayList<>();
        
        while (fileLoader.hasNextLine()){
            temp = fileLoader.nextLine();
            
            if (temp.isEmpty()) {}
            else if  (temp.charAt(0) == '['){
                if (!header.isEmpty()) {
                    content.put(header, sections);
                }
                sections = new HashMap<>();
                header = temp.split("\\[")[1].split("\\]")[0];
                subHeader = fileLoader.nextLine().split("-")[1];
            }
            else if (temp.charAt(0) == '-'){
                sections.put(subHeader, fields);
                fields = new ArrayList<>();
                subHeader = temp.split("-")[1];
            }
            
            else {
                fields.add(temp);
            }
            
        }
        
    }
    public static void main(String[] args){
        
        
    }
}
