/**
 * Copyright (C) John Robbins, Elliott Sobek, Zac Batog.
 * Github profiles:
 * John Robbins (https://github.com/reboss),
 * Elliott Sobek (https://github.com/ElliottSobek),
 * Zac Batog (https://github.com/batogz) 
 */

package ccat_view.MainMenu;

import ccat_model.Answer;
import ccat_model.AnswerModel;
import ccat_model.Header;
import ccat_model.QuestionModel;
import ccat_model.Question;
import ccat_model.TableRow;
import ccat_model.UserModel;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
    @FXML
    private AnchorPane chartAnchor;
    @FXML
    private RadioButton day;
    @FXML
    private RadioButton week;
    @FXML
    private RadioButton month;
    @FXML
    private RadioButton quarter;
    
    private ToggleGroup dateFilterChoice;   
    private final int BRADEN_SCALE_MAX = 24;
    private final int QUESTION_NUM = 44;
    private QuestionModel template;
    private AnswerModel answerModel;
    private List<Tab> tabs;
    private List<VBox> tabContentList;
    private List<TableRow> rows;
    private List<Answer> answersToBeSaved;

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
        System.out.println("Chart Anchor Width 2: " + this.chartAnchor.getWidth());
        System.out.println("Chart Anchor Height 2: " + this.chartAnchor.getHeight());
        System.exit(0);
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void onAdminTasks(ActionEvent event) throws IOException, SQLException {
        
        answerModel = new AnswerModel();
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
        answerModel.loadAnswers();
        
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
    private void populateTabs() throws SQLException {

        List<Header> headers = template.loadQuestions();
        rows = new ArrayList<>();
        int row = 0;
        int part = 0;
        for (Header header : headers) {

            if (header.getParentId() != part) {
                row = 0;
            }
            part = header.getParentId() - 1;

            if (header.getText().compareTo(" ") == 0) {
                continue;
            }

            TableRow headerRow = new TableRow(header, tabs.get(part), 800.0, true);
            headerRow.setColor("#336699");

            AnchorPane headerAnchor = new AnchorPane();
            headerAnchor.getChildren().add(headerRow);
            this.tabContentList.get(part).getChildren().add(row, headerAnchor);

            row++;
            for (Question question : header.getChildren()) {

                TableRow questionRow = new TableRow(question, tabs.get(part), 800.0, false);
                questionRow.setToggles();
                if ((row - header.getId()) % 2 == 0) {
                    questionRow.setColor("#d6e0f5");
                }
                AnchorPane questionAnchor = new AnchorPane();
                questionAnchor.getChildren().add(questionRow);
                this.tabContentList.get(part).getChildren().add(row, questionAnchor);
                rows.add(questionRow);

                row++;

            }

        }
    }

    /**
     *
     * @return @throws java.sql.SQLException
     */
    @FXML
    private Boolean onSubmit(ActionEvent event) throws SQLException {
        answersToBeSaved = new ArrayList<>();
        answerModel = new AnswerModel();

        for (int i = 0; i < QUESTION_NUM; i++) {
            TableRow row = rows.get(i);

            if (!row.isValid()) {
                row.setTabError();
                return false;
            } else if (row.isNotYes() && !row.getAnswer().getText().isEmpty()) {
                answersToBeSaved.add(row.getAnswer());
            } else {
                this.setErrorsOff();
            }
        }
        answerModel.saveAnswers(answersToBeSaved, null);
        this.resetRows();

        return null;
    }

    /**
     * 
     */
    public void setToggles() {
        for (TableRow row : rows) {
            row.testToggleGroup();
        }
    }

    /**
     * 
     */
    public void setErrorsOff() {
        for (TableRow row : rows) {
            if (row.isValid()) {
                row.setTabErrorOff();
            } else {
                row.setTabError();
            }
        }
    }

    /**
     *
     */
    public final void setAccess() {
        admin.setDisable(false);
    }
    
    /**
     * 
     */
    private void resetRows() {
        for (int i = 0; i < QUESTION_NUM; i++) {
            TableRow row = rows.get(i);
            row.reset();
            row.clearNotes();
        }
    }
    
    /**
     * 
     */
    private void initializeAuditChart() throws ClassNotFoundException, SQLException {
        NumberAxis xAxis = new NumberAxis(0.0, 24.0, 1.0);
        NumberAxis yAxis = new NumberAxis(0.0, 100.0, 5.0);
        xAxis.setLabel("Hour");
        yAxis.setLabel("%");
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Audit Averages for Current day");
        chart.setPrefSize(520.0,338.0); // Bug with parent width & height
//        chart.setPrefSize(this.chartAnchor.getWidth(),this.chartAnchor.getHeight());
        System.out.println("Chart Anchor Width: " + this.chartAnchor.getWidth());
        System.out.println("Chart Anchor Height: " + this.chartAnchor.getHeight());
        this.chartAnchor.getChildren().add(chart);
        
        this.dateFilterChoice = new ToggleGroup();
        this.day.setToggleGroup(dateFilterChoice);
        this.week.setToggleGroup(dateFilterChoice);
        this.month.setToggleGroup(dateFilterChoice);
        this.quarter.setToggleGroup(dateFilterChoice);
        
        UserModel userModel = new UserModel();
        
        this.day.setOnAction((ActionEvent e) -> {
            chart.setTitle("Audit Averages for Current day");
            xAxis.setLabel("Hour");
            xAxis.setLowerBound(0.0);
            xAxis.setUpperBound(24.0);
            xAxis.setTickUnit(1.0);
            // chart.getData().add(userModel.getSeries("day"));
        });
        this.week.setOnAction((ActionEvent e) -> {
            chart.setTitle("Audit Averages for Current Week");
            xAxis.setLabel("Day of Current Week");
            xAxis.setLowerBound(1.0);
            xAxis.setUpperBound(7.0);
            xAxis.setTickUnit(1.0);
            // chart.getData().add(userModel.getSeries("week"));
        });
        this.month.setOnAction((ActionEvent e) -> {
            chart.setTitle("Audit Averages by Month");
            xAxis.setLabel("Month of Current Year");
            xAxis.setLowerBound(1.0);
            xAxis.setUpperBound(12.0);
            xAxis.setTickUnit(1.0);
            // chart.getData().add(userModel.getSeries("month"));
        });
        this.quarter.setOnAction((ActionEvent e) -> {
            chart.setTitle("Audit Averages by Business Quarter");
            xAxis.setLabel("Quarter of Current Year");
            xAxis.setLowerBound(1.0);
            xAxis.setUpperBound(4.0);
            xAxis.setTickUnit(1.0);
            // chart.getData().add(userModel.getSeries("quarter"));
        });        
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
            this.template = new QuestionModel();
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
            populateTabs();
        } catch (SQLException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            initializeAuditChart();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
