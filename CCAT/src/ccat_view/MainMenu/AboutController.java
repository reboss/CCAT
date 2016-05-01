/**
 * Copyright (C) John Robbins, Elliott Sobek, Zac Batog.
 * Github profiles:
 * John Robbins (https://github.com/reboss),
 * Elliott Sobek (https://github.com/ElliottSobek),
 * Zac Batog (https://github.com/batogz) 
 */

package ccat_view.MainMenu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Elliott
 */
public class AboutController implements Initializable {

    @FXML
    private TextArea info;

    @FXML
    private Button close;

    /**
     * 
     * @param event 
     */
    @FXML
    private void onClose(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        info.setFont(Font.font(18.0));
        info.setText("The Critical Care Audit Tool (CCAT) is an evidenced-based, self-educating audit tool "
                + "for Registered Nurses of the ICU and CCU units at the Grey Nuns Hospital to increase "
                + "their knowledge in documentation while increasing their patient care competencies, care "
                + "planning, and professional practice. \n\nAim: To meet the needs of high acuity patients, "
                + "accreditation standards, best practice standards, and continuing competency requirements, "
                + "all while meeting the greater goal of improving quality of care and subsequently, patient "
                + "safety.");
    }

}
