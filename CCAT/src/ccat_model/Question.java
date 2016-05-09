/**
 * Copyright (C) John Robbins, Elliott Sobek, Zac Batog.
 * Github profiles:
 * John Robbins (https://github.com/reboss),
 * Elliott Sobek (https://github.com/ElliottSobek),
 * Zac Batog (https://github.com/batogz) 
 */

package ccat_model;

/**
 *
 * @author John
 */
public class Question extends CCATQA{
    
    private String answer;
    
    public Question(String objectText, Integer objectId, Integer parentId) {
        super(objectText, objectId, parentId);
        answer = "";
    }
    
    public void setAnswer(String answer){
        this.answer = answer;
    }
    
    public String getAnswer(){
        return this.answer;
    }
}
