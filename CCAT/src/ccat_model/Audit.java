/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccat_model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author JRebo_000
 */
public class Audit extends CCATQA{
    
    private String date;
    private FlowPane rowLayout;
    private final double score;
    private final int bradenScore;
    private final List<Answer> answers;
    
    public Audit(String username, Integer id, double score, Integer bradenScore, String date) {
        super(username, id, null);
        answers = new ArrayList<>();
        this.score = score;
        this.bradenScore = bradenScore;
        this.date = date;
        this.rowLayout = new FlowPane();
        this.rowLayout.setStyle("-fx-font-weight: bold; -fx-font : 16px \"Verdana\"; -fx-background-color : #d6e0f5;");
        Label usernameLabel = new Label(username);
        Label scoreLabel = new Label(""+score+"       ");
        Label dateLabel = new Label(date);
        rowLayout.getChildren().addAll(usernameLabel, scoreLabel, dateLabel);
        Button downArrow = new Button("\u25BC");
        rowLayout.getChildren().add(downArrow);
        
        downArrow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayChildren();
            }
        });
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
    
    public String getDateCreated(){
        return date;
    }
    
    public void displayChildren(){
        for (int i = 0; i < answers.size(); i++){
            rowLayout.getChildren().add(answers.get(i).getRowLayout());
        }
    }
    
    public void hideChildren(){
        for (int i = 0; i < answers.size(); i++){
            rowLayout.getChildren().remove(answers.get(i).getRowLayout());
        }
    }
    
    public FlowPane getRowLayout(){
        return rowLayout;
    }
}
