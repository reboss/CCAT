/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccat_view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
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
        FadeTransition ft = new FadeTransition(Duration.millis(4000), root);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
        ft.setOnFinished((ActionEvent event) -> {
            primaryStage.close();
            try {
                displayLoginScene(primaryStage);
            } catch (IOException ex) {
                Logger.getLogger(CCAT_View.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        
//        Parent root = FXMLLoader.load(getClass().getResource("MainMenu/MainMenu.fxml"));
//        Scene scene = new Scene(root);
        //primaryStage.setFullScreen(true);
        primaryStage.setTitle("CCAT");

        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/medicalIcon.png"));
        primaryStage.show();
        ft.setOnFinished((ActionEvent event) -> {
            primaryStage.close();
            try {
                displayLoginScene(primaryStage);
            } catch (IOException ex) {
                Logger.getLogger(CCAT_View.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        
//        Parent root = FXMLLoader.load(getClass().getResource("MainMenu/MainMenu.fxml"));
//        Scene scene = new Scene(root);
        primaryStage.setTitle("CCAT");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void displayLoginScene(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu/MainMenu.fxml"));
        Scene scene = new Scene(root);
        //stage.setMaximized(true);
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
