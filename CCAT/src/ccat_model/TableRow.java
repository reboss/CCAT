
package ccat_model;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author John
 */
public class TableRow extends FlowPane{
    
    private final ToggleGroup answer;
    private Boolean isChecked;
    private Boolean isNotHeader;
    private final RadioButton yes;
    private final RadioButton no;
    private final RadioButton na;
    private final TextArea notes;
    private final Label notesLabel;
    private final Label yesLabel;
    private final Label noLabel;
    private final Label naLabel;
    private Label question; 
    private final Tab parentTab;
    
    /**
     *
     * @param question
     * @param parentTab
     * @param width
     * @param isHeader
     */
    public TableRow(Label question, Tab parentTab, double width, Boolean isHeader){
        
        this.question = question;
        this.parentTab = parentTab;
        this.answer = new ToggleGroup();
        this.yes = new RadioButton("");
        this.no = new RadioButton("");
        this.na = new RadioButton("");
        this.notes = new TextArea();
        
        this.isChecked = false;
        this.isNotHeader = false;
        
        this.notesLabel = new Label("Notes:");
        notesLabel.setPrefWidth(width);
        this.yesLabel = new Label("yes");
        yesLabel.setPrefWidth(width*0.06);
        this.noLabel = new Label("no");
        noLabel.setPrefWidth(width*0.06);
        this.naLabel = new Label("n/a");
        naLabel.setPrefWidth(width*0.06);
        
        notes.setPrefWidth(width * 0.95);
        notes.setPrefHeight(65.0);
     
        this.setPrefWidth(width);
        this.setPrefHeight(20.0);
        
        this.question.setPrefWidth(width * 0.82);
        this.getChildren().add(this.question);
        
        yes.setPrefWidth(width*0.06);
        no.setPrefWidth(width*0.06);
        na.setPrefWidth(width*0.06);
        
        yes.setToggleGroup(answer);
        no.setToggleGroup(answer);
        na.setToggleGroup(answer);
        
        if (isHeader){
//            this.question.setFont(Font.font("Verdana", 15));
            this.question.setTextFill(Color.web("#FFFFFF"));
            this.question.setStyle("-fx-font-weight: bold");
            yesLabel.setTextFill(Color.web("#FFFFFF"));
            noLabel.setTextFill(Color.web("#FFFFFF"));
            naLabel.setTextFill(Color.web("#FFFFFF"));
            this.getChildren().addAll(yesLabel, noLabel, naLabel);
        }
        
    }
    
    /**
     *
     * @return answer to question as well as notes if applicable
     */
    public String getAnswer(){
        
        if (answer.getSelectedToggle() == yes){
            return "yes";
        }
        else if (answer.getSelectedToggle() == no){
            return "no - " + notes.getText();
        }
        else {
            return "n/a - " + notes.getText();
        }
        
    }
    
    /**
     * set toggle groups
     * also confirms that this is a question and not a header
     * 
     */
    public void setToggles(){
        
        this.isNotHeader = true;
             
        
        yes.setOnAction((ActionEvent event) -> {
            
            isChecked = true;
            if(this.getChildren().contains(notes)){
                this.getChildren().remove(notesLabel);
                this.getChildren().remove(notes);
            }
            
        });
        
        no.setOnAction((ActionEvent event) -> {
            
            isChecked = true;
            if(!this.getChildren().contains(notes)){
                this.getChildren().add(notesLabel);
                this.getChildren().add(notes);
            }
            
        });
        
        na.setOnAction((ActionEvent event) -> {
            
            isChecked = true;
            if(!this.getChildren().contains(notes)){
                this.getChildren().add(notesLabel);
                this.getChildren().add(notes);
            }
            
        });
        
        this.getChildren().addAll(yes, no, na);
    }
    
    /**
     * sets row width as well as scales objects within row
     * @param width
     */
    public void setRowWidth(double width){ 
        
        this.setPrefWidth(width); 
        question.setPrefWidth(width*0.75);
        yes.setPrefWidth(width*0.05);
        no.setPrefWidth(width*0.05);
        na.setPrefWidth(width*0.05);
        
    }
    
    /**
     * 
     * @return tab that question is located in
     */
    public Tab getParentTab(){ return parentTab; }
    
    
    public void setTabError(){
        
        parentTab.setStyle("-fx-border-color:red; -fx-border-width: 1px;");
        this.notes.setStyle("-fx-border-color:red; -fx-border-width: 1px; ");
    }
    
    
    public void setTabErrorOff() {
        
        parentTab.setStyle("-fx-border-color:#eeeeee; -fx-border-width: 1px;");
        this.notes.setStyle("-fx-border-color:#DDDDDD; -fx-border-width: 1px;");
        
    }
    
    public String getQuestion(){
        return question.getText();
    }
    /**
     * 
     * Checks if the ToggleGroup has been checked off with yes, no or n/a
     * Checks that the notes field is not empty on a no or n/a call
     * Checks that it is actually a question and not a header
     * 
     * @return
     */
    public Boolean isValid(){
        
        if  (answer.getSelectedToggle() == no || 
             answer.getSelectedToggle() == na){
            
            return (isChecked && !notes.getText().isEmpty()) || !isNotHeader ;
        }
        return isChecked || !isNotHeader;
    }
    
    /**
     *
     * @param color
     */
    public void setColor(String color){
        this.setStyle("-fx-background-color: " + color + ";");
    }  
    
}
