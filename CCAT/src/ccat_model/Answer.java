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
public class Answer extends CCATQA{
    
    private String answer;
    
    public Answer(String objectText, Integer objectId, Integer parentId) {
        super(objectText, objectId, parentId);
        answer = "";
    }
    
    public void setSelectedToggleValue(String answer){
        this.answer = answer; 
    }
    
    public String getSelectedToggle(){
        return answer;
    }
    
}
