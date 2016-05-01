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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
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
 * @author John, Elliott, Zac
 */
public class MainMenuController implements Initializable {

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
    private Map<ToggleGroup, String> answers;
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

        notesOnNoOrNa = new HashMap<>();
        questionsAnswerCheck = new HashMap<>();
        Map<String, Map<String, List<String>>> content = template.getContent();
        answers = new HashMap<>();

        int i = 0;
        for (String header : content.keySet()) {

            this.tabContentList.get(i).setAlignment(Pos.CENTER);
            int row = 0;
            for (String subheader : template.getOrderedSubheaders().get(header)) {

                TableRow sectionBox = new TableRow(subheader, tabs.get(i), (float) 800.0, true);
                sectionBox.setStyle("Verdana", 15, "#FFFFFF");
                
                if (subheader.compareTo(" ") == 0 || subheader.isEmpty()) {
                    sectionBox.setColor("#eeeeee");
                } else {
                    sectionBox.setColor("#336699");
                }


                this.tabContentList.get(i).getChildren().add(row, sectionBox);
                row++;

                List<String> list = content.get(header).get(subheader);

                for (String question : list) {

                    

//                    Label displayedQuestion = new Label(question);
//                    displayedQuestion.setPrefWidth(575.0);
//                    questionsAnswerCheck.put(question, false);

//                    ToggleGroup group = new ToggleGroup();
//                    RadioButton yes = new RadioButton("yes");
//                    RadioButton no = new RadioButton("no");
//                    RadioButton na = new RadioButton("n/a");
//                    yes.setPrefWidth(40.0);
//                    no.setPrefWidth(40.0);
//                    na.setPrefWidth(40.0);
//                    yes.setToggleGroup(group);
//                    no.setToggleGroup(group);
//                    na.setToggleGroup(group);
//
//                    TextArea area = new TextArea();
//                    area.setPrefSize(0.0, 0.0);
//                    area.setWrapText(true);
//                    area.setVisible(false);
//
//                    Label noteLabel = new Label("");
//                    noteLabel.setPrefSize(0.0, 0.0);
//                    noteLabel.setVisible(false);
//
//                    FlowPane flow = new FlowPane();
//
//                    //TODO: remove duplication of no and na ActionListener's
//                    no.setOnAction((ActionEvent event) -> {
//
//                        questionsAnswerCheck.put(question, true);
//                        notesOnNoOrNa.put(question, area);
//                        area.setPrefSize(700.0, 65.0);
//                        area.setVisible(true);
//                        noteLabel.setPrefSize(90.0, 10.0);
//                        noteLabel.setVisible(true);
//                        area.positionCaret(1);
//
//                    });
//                    na.setOnAction((ActionEvent event) -> {
//
//                        questionsAnswerCheck.put(question, true);
//                        notesOnNoOrNa.put(question, area);
//                        area.setPrefSize(700.0, 65.0);
//                        area.setVisible(true);
//                        noteLabel.setPrefSize(90.0, 10.0);
//                        noteLabel.setVisible(true);
//
//                    });
//                    yes.setOnAction((ActionEvent event) -> {
//
//                        questionsAnswerCheck.put(question, true);
//                        area.setPrefSize(0.0, 0.0);
//                        area.setVisible(false);
//                        noteLabel.setPrefSize(0.0, 0.0);
//                        noteLabel.setVisible(false);
//                        flow.resize(800.0, 10.0);
//
//                    });
//
//                    answers.put(group, question);
//                    //TODO:  add ToggleGroup to a list so input can be accessed later
//
//                    if (question.contains("Braden Scale")) {
//                        ComboBox<String> score = new ComboBox<>();
//                        Label spacer = new Label();
//                        spacer.setPrefWidth(280.0);
//                        for (int num = 6; num < BRADEN_SCALE_MAX; num++) {
//                            Integer option = num;
//                            score.getItems().add(option.toString());
//                        }
//                        displayedQuestion.setPrefWidth(215.0);
//                        flow.getChildren().addAll(displayedQuestion, score, spacer, yes, no, na, area);
//
//                    } else {
//                        flow.getChildren().addAll(displayedQuestion, yes, no, na, area);
//                    }
//                    flow.setVgap(10.0);
//                    flow.setHgap(10.0);
//                    flow.setPrefWrapLength(800.0);
                    
                    AnchorPane anchor = new AnchorPane();
                    anchor.setPrefHeight(20.0);
                    TableRow flow = new TableRow(question, tabs.get(i), 800.0, false);
                    flow.setToggles();
                    
                    if (row % 2 == 1) {
                        flow.setColor("#dbe4f0");
                    }
                    
                    anchor.getChildren().add(flow);
                    this.tabContentList.get(i).getChildren().add(row, anchor);
                    row++;
                }
            }
            i++;
        }
    }

    /**
     * 
     * @return 
     */
    @FXML
    public Boolean saveFile() throws SQLException {

        List<String> answersToBeSaved = new ArrayList<>();
        answerModel = new AnswerModel();
        
        for (String key : notesOnNoOrNa.keySet()) {
            
            if (notesOnNoOrNa.get(key).getText().isEmpty()) {
                
                notesOnNoOrNa.get(key).setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                notesOnNoOrNa.get(key).parentProperty();
                return false;
                
            } else {
                
                answersToBeSaved.add(notesOnNoOrNa.get(key).getText());
                
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
    }

}
