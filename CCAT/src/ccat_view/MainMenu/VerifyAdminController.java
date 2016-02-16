/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccat_view.MainMenu;

import ccat_model.Account;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Elliott
 */
public class VerifyAdminController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private PasswordField pass;
    @FXML
    private Button back;
    @FXML
    private Label errMsg;

    private Account account;
    private MainMenuController mMC;

    @FXML
    private void onBack(ActionEvent event) {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onLogin(ActionEvent event) throws FileNotFoundException, IOException {
        if (account.validate(username.getText(), pass.getText())) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ccat_view/MainMenu.fxml"));
            this.mMC.setAccess();
            Stage primaryStage = (Stage) back.getScene().getWindow();
            primaryStage.close();
        } else {
            username.clear();
            pass.clear();
            errMsg.setText("Username or Password incorrect");
            errMsg.setTextFill(Color.RED);
        }
    }

    public final void setMMC(MainMenuController mMC) {
        this.mMC = mMC;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.username.setText("sobeke");
        this.pass.setText("1234");
        try {
            account = new Account();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VerifyAdminController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VerifyAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
