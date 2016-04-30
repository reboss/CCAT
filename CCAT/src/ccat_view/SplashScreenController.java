/**
 * Copyright (C) John Mulvany-Robbins, Elliott Sobek, Zac Batog.
 * Github profiles:
 * John Mulvany-Robbins (https://github.com/reboss),
 * Elliott Sobek (https://github.com/ElliottSobek),
 * Zac Batog (https://github.com/batogz) 
 */

package ccat_view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Elliott
 */
public class SplashScreenController implements Initializable {

    @FXML
    private ImageView imageView;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image img = new Image("/stethoscopeAndComputerNew.jpg");
        imageView.setImage(img);
    }

}
