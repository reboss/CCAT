/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccat_model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JRebo_000
 */
public class Audit extends CCATQA{
    
    private final double score;
    private final int bradenScore;
    private final List<Answer> answers;
    
    public Audit(String username, Integer id, double score, Integer bradenScore) {
        super(username, id, null);
        answers = new ArrayList<>();
        this.score = score;
        this.bradenScore = bradenScore;
    }
    
    public List<Answer> getChildren(){
        return answers;
    }
    
    public void addChild(Answer child){
        answers.add(child);
    }
    
    public double getScore(){
        return score;
    }
    
    public int getBradenScore(){
        return bradenScore;
    }
    
}
