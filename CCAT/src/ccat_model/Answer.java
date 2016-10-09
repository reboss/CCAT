/**
 * Copyright (C) John Robbins, Elliott Sobek, Zac Batog.
 * Github profiles:
 * John Robbins (https://github.com/reboss),
 * Elliott Sobek (https://github.com/ElliottSobek),
 * Zac Batog (https://github.com/batogz) 
 */

package ccat_model;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author John
 */
public class Answer extends CCATQA {

    private final String answer;
    private String selection;
    private final String question;
    private FlowPane rowLayout;
    private static final double WIDTH = 800;
    
    public Answer(String answer, String question, Integer parentId) {
        super(answer, null, parentId);
        this.question = question;
        this.answer = answer;
        rowLayout = new FlowPane();
        rowLayout.setStyle("-fx-background-color : #FFFFFF");
        rowLayout.setPrefWidth(WIDTH);
        Label questionLabel = new Label(this.question + ":");
        questionLabel.setPrefWidth(WIDTH * 0.5);
        Label answerLabel = new Label(this.answer);
        rowLayout.getChildren().addAll(questionLabel, answerLabel);
    }

    /**
     * 
     * @param answer 
     */
    public void setSelectedToggleValue(String selection) {
        this.selection = selection;
    }

    /**
     * 
     * @return 
     */
    public String getSelectedToggle() {
        return answer;
    }

    public String getQuestion(){
        return this.question;
    }
    
    @Override
    public String getType(){
        return "answer";
    }
    
    public FlowPane getRowLayout(){
        return rowLayout;
    }
    
    
}
