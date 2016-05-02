/**
 * Copyright (C) John Robbins, Elliott Sobek, Zac Batog.
 * Github profiles:
 * John Robbins (https://github.com/reboss),
 * Elliott Sobek (https://github.com/ElliottSobek),
 * Zac Batog (https://github.com/batogz) 
 */

package ccat_view.MainMenu;

import ccat_model.AnswerModel;
import ccat_model.QuestionLoader;
import ccat_model.TableRow;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Pos;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

//import files.*;

//TODO: add onClickListeners to radioButton groups to update score in real time
//      as well as check for errors in case someone tries to submit an incomplete 
//      form
//TODO: error logging and reporting option
/**
 * FXML Controller class
 *
 * @author John, Elliott, Zac
 */
public class MainMenuController implements Initializable {

    @FXML
    private TabPane tabPane;
    @FXML
    private VBox auditBrowserContent;
    @FXML
    private VBox partAContent;
    @FXML
    private VBox partBContent;
    @FXML
    private VBox partCContent;
    @FXML
    private Tab partA;
    @FXML
    private Tab partB;
    @FXML
    private Tab partC;
    @FXML
    private Tab admin;

    private final int BRADEN_SCALE_MAX = 24;
    private QuestionLoader template;
    private AnswerModel answerModel;
    private List<Tab> tabs;
    private List<VBox> tabContentList;
    private Map<String, Boolean> questionsAnswerCheck;
    private Map<String, TextArea> notesOnNoOrNa;
    private List<TableRow> rows;

    /**
     *
     */
    public MainMenuController() {
    }

    /**
     *
     * @param event
     */
    @FXML
    private void onLogout(ActionEvent event) {
        admin.setDisable(true);
    }

    /**
     *
     * @param event
     */
    @FXML
    private void onExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void onAdminTasks(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("VerifyAdmin.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("CCAT - Login");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/medicalIcon.png"));
        VerifyAdminController verifyAdminController = loader.getController();
        verifyAdminController.setMMC(this);
        stage.show();
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void onAbout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("About.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("CCAT - About");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/medicalIcon.png"));
        stage.show();
    }

    /**
     *
     */
    @FXML
    private void populateTabs() {

        rows = new ArrayList<>();
        notesOnNoOrNa = new HashMap<>();
        questionsAnswerCheck = new HashMap<>();
        Map<String, Map<String, List<String>>> content = template.getContent();

        int i = 0;
        for (String header : content.keySet()) {
            

            int row = 0;
            for (String subheader : template.getOrderedSubheaders().get(header)) {
                
             
                Label subheaderLabel = new Label(subheader);
                subheaderLabel.setFont(Font.font("Verdana", 15));
                TableRow subheaderRow = new TableRow(subheaderLabel, tabs.get(i), 800.0, true);
                

                if (subheader.compareTo(" ") == 0 || subheader.isEmpty()) {
                    continue;
                } else {
                    subheaderRow.setColor("#336699");
                }

                AnchorPane headerAnchor = new AnchorPane();
                headerAnchor.getChildren().add(subheaderRow);
                this.tabContentList.get(i).getChildren().add(row, headerAnchor);

                row++;

                List<String> list = content.get(header).get(subheader);
                rows.add(subheaderRow);

                for (String question : list) {

           
                    Label questionLabel = new Label(question);
                    TableRow questionRow = new TableRow(questionLabel, tabs.get(i), 800.0, false);
                    questionRow.setToggles();
                    

                    if (row % 2 == 1) {

                        questionRow.setColor("#dbe4f0");
                    }

                    AnchorPane questionAnchor = new AnchorPane();
                    questionAnchor.getChildren().add(questionRow);
                    this.tabContentList.get(i).getChildren().add(row, questionAnchor);
                    
                    rows.add(questionRow);
                    row++;
                }
            }
            HBox submitRow = new HBox();
            Button submit = new Button("Submit");
          
            submit.setOnAction((ActionEvent event) ->{
            try {
                if (this.saveFile()){
                    
                }
                else{
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
            submitRow.getChildren().add(submit);
            this.tabContentList.get(i).getChildren().add(submitRow);
            i++;
        }
        
    }

    /**
     *
     * @return
     * @throws java.sql.SQLException
     */
    @FXML
    public Boolean saveFile() throws SQLException {

        List<String> answersToBeSaved = new ArrayList<>();
        answerModel = new AnswerModel();
        
        for (TableRow row : rows) {
        

            if (!row.isValid()){ 
                row.setTabError();
                return false;
            } else {
                row.setTabErrorOff();  

            }

            answerModel.saveAnswers(answersToBeSaved, 1);
        }

        for (String key : questionsAnswerCheck.keySet()) {

            if (!questionsAnswerCheck.get(key)) {

                System.out.println(key);
                return false;

            }
        }
        return true;
    }

    /**
     *
     */
    public final void setAccess() {
        admin.setDisable(false);
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.template = new QuestionLoader();
        } catch (SQLException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabContentList = new ArrayList<>();
        tabs = new ArrayList<>();
        tabContentList.add(partAContent);
        tabContentList.add(partBContent);
        tabContentList.add(partCContent);
        tabs.add(partA);
        tabs.add(partB);
        tabs.add(partC);

        try {
            template.loadQuestions();
        } catch (SQLException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        populateTabs();
        
        
//        tabPane.getScene().widthProperty().addListener(new ChangeListener<Number>() {
//            @Override 
//            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
//                tabPane.setPrefWidth((double) newSceneWidth);
//                for (TableRow row : rows){
//                    row.setRowWidth((double) newSceneWidth);
//                }
//            }
//
//        });
    }

}
