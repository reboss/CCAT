/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccat_model;

/**
 *
 * @author JRebo_000
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
