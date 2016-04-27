package ccat_model;

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

    /**
     * 
     */
    public QuestionLoader() {
        content = new HashMap<>();
    }

    /**
     * 
     */
    public void loadQuestions() {
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
