

package ccat_model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author John
 */
public class Header extends CCATQA{
    
    private final List<Question> questions;
    
    Header(String objectText, Integer objectId, Integer parentId) {
        super(objectText, objectId, parentId);
        questions = new ArrayList<>();
    }   
    
    /**
     *
     * @return
     */
    public List<Question> getChildren(){
        return questions;
    }
    
    /**
     *
     * @param question
     */
    public void addChild(Question question){
        questions.add(question);
    }
    
    
    
}
