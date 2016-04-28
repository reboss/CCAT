package ccat_view;

import database.Api;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author John, Elliott, Zac
 */
public class CCAT extends Application {

    /**
     *
     * @param primaryStage
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        displaySplashScreen(primaryStage);
    }

    /**
     *
     * @param stage
     * @throws IOException
     * @throws InterruptedException
     */
    private void displaySplashScreen(Stage stage) throws IOException, InterruptedException {
        Parent root = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("/medicalIcon.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        DoubleProperty opacity = root.opacityProperty();
        Timeline ft = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                new KeyFrame(new Duration(4000), new KeyValue(opacity, 1.0))
        );
        ft.play();
        ft.setOnFinished((ActionEvent event) -> {
            try {
                displayLoginScene();
            } catch (IOException ex) {
                Logger.getLogger(CCAT.class.getName()).log(Level.SEVERE, null, ex);
            }
            stage.close();
        });
    }

    /**
     *
     * @throws IOException
     */
    private void displayLoginScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu/MainMenu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/medicalIcon.png"));
        stage.setTitle("Critical Care Audit Tool - CONFIDENTIAL");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param args
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Api api = new Api();
        
        List<String> list;
        list = api.readDB();
        for (String s : list) {
            System.out.println(s);
        }
        System.exit(0);
//        launch(args);
    }

}
