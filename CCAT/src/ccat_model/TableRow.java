/**
 * Copyright (C) John Robbins, Elliott Sobek, Zac Batog.
 * Github profiles:
 * John Robbins (https://github.com/reboss),
 * Elliott Sobek (https://github.com/ElliottSobek),
 * Zac Batog (https://github.com/batogz) 
 */

package ccat_model;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

/**
 *
 * @author John
 */
public class TableRow extends FlowPane {

    private final ToggleGroup answerToggle;
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
    private final Answer answer;
    private final Label questionLabel;
    private final CCATQA object;
    private final Tab parentTab;

    /**
     *
     * @param object
     * @param parentTab
     * @param width
     * @param isHeader
     */
    public TableRow(CCATQA object, Tab parentTab, double width, Boolean isHeader) {

        this.answer = new Answer(null, null, object.getId());
        this.object = object;
        this.questionLabel = new Label(object.getText());
        this.parentTab = parentTab;
        this.answerToggle = new ToggleGroup();
        this.yes = new RadioButton("");
        this.no = new RadioButton("");
        this.na = new RadioButton("");

        this.notes = new TextArea();
        this.isChecked = false;
        this.isNotHeader = false;

        this.notesLabel = new Label("Notes:");
        notesLabel.setPrefWidth(width);
        this.yesLabel = new Label("yes");
        yesLabel.setPrefWidth(width * 0.06);
        this.noLabel = new Label("no");

        noLabel.setPrefWidth(width * 0.06);
        this.naLabel = new Label("n/a");
        naLabel.setPrefWidth(width * 0.06);

        notes.setPrefWidth(width * 0.95);
        notes.setPrefHeight(65.0);

        this.setPrefWidth(width);
        this.setPrefHeight(20.0);

        this.questionLabel.setPrefWidth(width * 0.82);
        this.questionLabel.setStyle(null);
        this.getChildren().add(this.questionLabel);

        yes.setPrefWidth(width * 0.06);
        no.setPrefWidth(width * 0.06);
        na.setPrefWidth(width * 0.06);

        yes.setToggleGroup(answerToggle);
        no.setToggleGroup(answerToggle);
        na.setToggleGroup(answerToggle);

        if (isHeader) {

            this.questionLabel.setTextFill(Color.web("#FFFFFF"));
            this.questionLabel.setStyle("-fx-font-weight: bold; -fx-font : 16px \"Verdana\";");
            yesLabel.setTextFill(Color.web("#FFFFFF"));
            noLabel.setTextFill(Color.web("#FFFFFF"));
            naLabel.setTextFill(Color.web("#FFFFFF"));
            this.getChildren().addAll(yesLabel, noLabel, naLabel);
        } else {
            this.setPadding(new Insets(5, 0, 5, 0));
        }

    }

    /**
     *
     */
    public void testToggleGroup() {
        answerToggle.selectToggle(yes);
    }

    /**
     *
     * @return
     */
    public Boolean isNotYes() {
        return answerToggle.getSelectedToggle() != yes;
    }

    /**
     *
     * @return answer to question as well as notes if applicable
     */
    public Answer getAnswer() {

        if (answerToggle.getSelectedToggle() == yes) {
            return null;
        } else if (answerToggle.getSelectedToggle() == no) {
            answer.setSelectedToggleValue("no");
        } else {
            answer.setSelectedToggleValue("n/a");
        }
        answer.setText(notes.getText());
        System.out.println(answer.getText());
        return answer;

    }

    /**
     * set toggle groups also confirms that this is a question and not a header
     *
     */
    public void setToggles() {

        this.isNotHeader = true;

        yes.setOnAction((ActionEvent event) -> {

            isChecked = true;
            if (this.getChildren().contains(notes)) {
                this.getChildren().remove(notesLabel);
                this.getChildren().remove(notes);
            }

        });

        no.setOnAction((ActionEvent event) -> {

            isChecked = true;
            if (!this.getChildren().contains(notes)) {
                this.getChildren().add(notesLabel);
                this.getChildren().add(notes);
            }

        });

        na.setOnAction((ActionEvent event) -> {

            isChecked = true;
            if (!this.getChildren().contains(notes)) {
                this.getChildren().add(notesLabel);
                this.getChildren().add(notes);
            }

        });

        this.getChildren().addAll(yes, no, na);
    }

    /**
     * sets row width as well as scales objects within row
     *
     * @param width
     */
    public void setRowWidth(double width) {

        this.setPrefWidth(width);
        questionLabel.setPrefWidth(width * 0.75);
        yes.setPrefWidth(width * 0.05);
        no.setPrefWidth(width * 0.05);
        na.setPrefWidth(width * 0.05);

    }

    /**
     *
     */
    public void setTabError() {
        parentTab.setStyle("-fx-border-color:red; -fx-border-width: 1px;");
        this.notes.setStyle("-fx-border-color:red; -fx-border-width: 1px; ");
    }

    /**
     *
     */
    public void setTabErrorOff() {
        parentTab.setStyle("-fx-border-color:#eeeeee; -fx-border-width: 1px;");
        this.notes.setStyle("-fx-border-color:#DDDDDD; -fx-border-width: 1px;");
    }

    /**
     *
     * @return
     */
    public CCATQA getObject() {
        return object;
    }

    /**
     *
     * @return tab that question is located in
     */
    public Tab getParentTab() {
        return parentTab;
    }

    /**
     *
     * Checks if the ToggleGroup has been checked off with yes, no or n/a Checks
     * that the notes field is not empty on a no or n/a call Checks that it is
     * actually a question and not a header
     *
     * @return
     */
    public Boolean isValid() {

        if (answerToggle.getSelectedToggle() == no
                || answerToggle.getSelectedToggle() == na) {

            return (isChecked && !notes.getText().isEmpty()) || !isNotHeader;
        }
        return isChecked || !isNotHeader;
    }

    /**
     *
     * @param color
     */
    public void setColor(String color) {
        this.setStyle("-fx-background-color: " + color + ";");
    }
    
    /**
     * 
     */
    public void reset(){
        this.answerToggle.getSelectedToggle().setSelected(false);
    }

    /**
     * 
     */
    public void clearNotes() {
        this.notes.setText("");
    }

}
