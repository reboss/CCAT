/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccat_view;

import java.io.IOException;
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
import javafx.util.Duration;

/**
 *
 * @author Elliott
 */
public class CCAT_View extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image("/medicalIcon.png"));
        primaryStage.setTitle("Critical Care Audit Tool - CONFIDENTIAL");
        primaryStage.setScene(scene);
        primaryStage.show();
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
                Logger.getLogger(CCAT_View.class.getName()).log(Level.SEVERE, null, ex);
            }
            primaryStage.close();
        });
    }
    
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
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
