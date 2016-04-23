/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccat_view.MainMenu;

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
import ccat_model.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
//import files.*;

//TODO: add onClickListeners to radioButton groups to update score in real time
//      as well as check for errors in case someone tries to submit an incomplete 
//      form

//TODO: error logging and reporting option

/**
 * FXML Controller class
 *
 * @author Elliott
 */
public class MainMenuController implements Initializable {

    @FXML
    private VBox partAScroller;
    @FXML
    private VBox partBScroller;
    @FXML
    private VBox partCScroller;
    @FXML
    private Tab partA;
    @FXML
    private Tab partB;
    @FXML
    private Tab partC;
    @FXML
    private Tab admin;

    private FileLoader template;
    private List<Tab> tabs;
    private List<VBox> scrollers;
    private Map<ToggleGroup, String> answers;
    private Map<String, Boolean> questionsAnswerCheck;

    public MainMenuController() {
    }

    @FXML
    private void onLogout(ActionEvent event) {
        admin.setDisable(true);
    }

    @FXML
    private void onExit(ActionEvent event) {
        System.exit(1);
    }

    @FXML
    private void onAdminTasks(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("VerifyAdmin.fxml"));
        Parent par = loader.load();
        Scene scene = new Scene(par);
        Stage stage = new Stage();
        stage.setTitle("CCAT - Login");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/medicalIcon.png"));
        VerifyAdminController verifyAdminController = loader.getController();
        verifyAdminController.setMMC(this);
        stage.show();
    }

    @FXML
    private void onAbout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("About.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("CCAT");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/medicalIcon.png"));
        stage.show();
    }

    //TODO: make partB tab also scrollable
    @FXML
    private void populateTabs(){
        
        questionsAnswerCheck = new HashMap<>();
        Map<String, Map<String, List<String>>> content = template.getContent();
        answers = new HashMap<>();
        //template.traverseMap(); // for testing
//        template.getHeaders();
        int i = 0;
        for (String header : content.keySet()){
            
            scrollers.get(i).setAlignment(Pos.CENTER); 
            for (String subheader : template.getOrderedSubheaders().get(header)){
                
                
                FlowPane sectionBox = new FlowPane(); 
                sectionBox.setStyle("-fx-background-color: #336699");
                Label subHeaderLabel = new Label(subheader);
                subHeaderLabel.setTextFill(Color.web("#FFFFFF"));
                subHeaderLabel.setFont(Font.font("Verdana", 15));
                sectionBox.getChildren().add(subHeaderLabel);
                sectionBox.setPrefWidth(600.0);

                scrollers.get(i).getChildren().add(sectionBox);
                
                List<String> list = content.get(header).get(subheader);
                int row = 0;
                for (String question : list) {
                    
                    
                    AnchorPane anchor2 = new AnchorPane();
                    
                    Label label = new Label(question);
                    label.setPrefWidth(575.0);
                    label.setStyle("-fx-font-weight: bold");
                    questionsAnswerCheck.put(question, false);
                    
                    AnchorPane.setBottomAnchor(label, 0.0);
                    AnchorPane.setTopAnchor(label, 0.0);
                    AnchorPane.setLeftAnchor(label, 0.0);
                    AnchorPane.setRightAnchor(label, 0.0);
                    
                    ToggleGroup group = new ToggleGroup();
                    RadioButton yes = new RadioButton("yes");
                    RadioButton no = new RadioButton("no");
                    RadioButton na = new RadioButton("n/a");
                    yes.setPrefWidth(40.0);
                    no.setPrefWidth(40.0);
                    na.setPrefWidth(40.0);
                    yes.setToggleGroup(group);
                    no.setToggleGroup(group);
                    na.setToggleGroup(group);
                    
                    TextArea area = new TextArea();
                    area.setPrefSize(0.0, 0.0);
                    area.setWrapText(true);
                    area.setVisible(false);
                    
                    Label noteLabel = new Label("");
                    noteLabel.setPrefSize(0.0, 0.0);
                    noteLabel.setVisible(false);
                    
                    FlowPane flow = new FlowPane();

                    
                    
                    no.setOnAction((ActionEvent event) -> {
                        if (!area.getText().trim().isEmpty())
                            questionsAnswerCheck.put(question, true);
                        area.setPrefSize(700.0, 65.0);
                        area.setVisible(true);
                        noteLabel.setPrefSize(90.0, 10.0);
                        noteLabel.setVisible(true);
                        area.positionCaret(1);
                    });
                    na.setOnAction((ActionEvent event) -> {
                        if (!area.getText().trim().isEmpty())
                            questionsAnswerCheck.put(question, true);
                        area.setPrefSize(700.0, 65.0);
                        area.setVisible(true);
                        noteLabel.setPrefSize(90.0, 10.0);
                        noteLabel.setVisible(true);
                    });
                    yes.setOnAction((ActionEvent event) -> {
                        questionsAnswerCheck.put(question, true);
                        area.setPrefSize(0.0, 0.0);
                        area.setVisible(false);
                        noteLabel.setPrefSize(0.0, 0.0);
                        noteLabel.setVisible(false);
                        flow.resize(800.0, 10.0);
                    });
                    
                    answers.put(group, question);
                    //TODO:  add ToggleGroup to a list so input can be accessed later
                    
                    flow.setVgap(10.0);
                    flow.setHgap(10.0);
                    flow.setPrefWrapLength(800.0);
                    flow.getChildren().addAll(label, yes, no, na, area);
                    if (row % 2 == 1){
                        flow.setStyle("-fx-background-color: #dbe4f0;");
                    }
         

                    anchor2.getChildren().add(flow);
                    scrollers.get(i).getChildren().addAll(anchor2);
                    row ++;
                }
            }
            i++;
        }
    }

    @FXML
    public Boolean saveFile() {
        for (String key : questionsAnswerCheck.keySet()){
            if (!questionsAnswerCheck.get(key)){
                System.out.println(key);
                return false;
            }
        }
        return true;
    }
    
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
            this.template = new FileLoader(new FileReader("questions.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        scrollers = new ArrayList<>();
        tabs = new ArrayList<>();
        scrollers.add(partAScroller);
        scrollers.add(partBScroller);
        scrollers.add(partCScroller);
        tabs.add(partA);
        tabs.add(partB);
        tabs.add(partC);
        template.loadTemplate();
        for (VBox box : scrollers) {
            AnchorPane.setBottomAnchor(box, 0.0);
            AnchorPane.setTopAnchor(box, 0.0);
            AnchorPane.setLeftAnchor(box, 0.0);
            AnchorPane.setRightAnchor(box, 0.0);
        }

        populateTabs();
        
    }

}
