
package ccat_model;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author John
 */
public class TableRow extends FlowPane{
    
    private ToggleGroup answer;
    private Boolean isChecked;
    private Boolean isNotHeader;
    private RadioButton yes;
    private RadioButton no;
    private RadioButton na;
    private TextArea notes;
    private final Label question;
    private final Tab parentTab;
    
    /**
     *
     * @param question
     * @param parentTab
     * @param width
     */
    public TableRow(String question, Tab parentTab, float width){
        
        this.question = new Label(question);
        this.parentTab = parentTab;
        
        
        this.setPrefWidth(width);
        
        this.question.setPrefWidth(width * 0.75);
        this.getChildren().add(this.question);
        
        yes.setPrefWidth(width*0.05);
        no.setPrefWidth(width*0.05);
        na.setPrefWidth(width*0.05);
        
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
        this.yes = new RadioButton();
        this.no = new RadioButton();
        this.na = new RadioButton();
        
        yes.setToggleGroup(answer);
        no.setToggleGroup(answer);
        na.setToggleGroup(answer);
        
        
        yes.setOnAction((ActionEvent event) -> {
            
            isChecked = true;
            if(this.getChildren().contains(notes)){
                this.getChildren().remove(notes);
            }
            
        });
        
        no.setOnAction((ActionEvent event) -> {
            
            isChecked = true;
            if(!this.getChildren().contains(notes)){
                this.getChildren().add(notes);
            }
            
        });
        
        na.setOnAction((ActionEvent event) -> {
            
            isChecked = true;
            if(!this.getChildren().contains(notes)){
                this.getChildren().add(notes);
            }
            
        });
        
        this.getChildren().addAll(yes, no, na);
    }
    
    /**
     * sets row width as well as scales objects within row
     * @param width
     */
    public void setRowWidth(float width){ 
        
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
        return isChecked || isNotHeader;
    }
    
    /**
     *
     * @param color
     */
    public void setColor(String color){
        this.setStyle("-fx-background-color: " + color + ";");
    }
    
    
}
