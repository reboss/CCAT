package ccat_model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author John
 */
public class FileLoader {

    private final Scanner fileLoader;
    private final Map<String, Map<String, List<String>>> content;
    private Map<String, List<String>> orderedSubheaders;

    /**
     *
     * @param file
     * @throws FileNotFoundException
     */
    public FileLoader(FileReader file) throws FileNotFoundException {
        fileLoader = new Scanner(file);
        content = new HashMap<>();
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
     */
    public void loadTemplate() {
        String header = "", subHeader = "", temp;
        Map<String, List<String>> sections = new HashMap<>();
        List<String> fields = new ArrayList<>();
        orderedSubheaders = new HashMap<>();

        while (fileLoader.hasNextLine()) {
            temp = fileLoader.nextLine();

            if (temp.isEmpty()) {
                //System.out.println(subHeader);
                //sections.put(subHeader, fields); 
                //fields = new ArrayList<>();
            } else if (temp.charAt(0) == '[') {
                if (!header.isEmpty()) {
                    sections.put(subHeader, fields);
                    content.put(header, sections);
                }
                sections = new HashMap<>();
                fields = new ArrayList<>();
                header = temp.split("\\[")[1].split("\\]")[0];
                orderedSubheaders.put(header, new ArrayList<>());

            } else if (temp.charAt(0) == '-') {

                //TODO: make sure the last read in subheader is put to the map properly
                sections.put(subHeader, fields);
                fields = new ArrayList<>();
                orderedSubheaders.get(header).add(subHeader);
                subHeader = temp.split("-")[1];

            } else {
                fields.add(temp.substring(2));
            }

        }
        content.put(header, sections);
    }

    /**
     * 
     * @return 
     */
    public Map<String, List<String>> getOrderedSubheaders() {
        return Collections.unmodifiableMap(orderedSubheaders);
    }

    //TODO: make headers return in the same order as they are in questions.txt
    
    /**
     * 
     * @return 
     */
    public List<String> getHeaders() {
        List<String> headers = new ArrayList<>();
        for (String key : content.keySet()) {
            headers.add(key);
            //System.out.println(key);
        }
        return headers;
    }
}
