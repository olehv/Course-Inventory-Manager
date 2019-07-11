//AboutFormController.java
//
//Purpose: This class is the controller for AboutForm page which shows 'About'
//         info regarding the application (version & copyright).
//
//Author: Oleh Vytvitskyy  | oleh.vytvitskyy@gmail.com
//
//Date Created : 20-Apr-2018
//Date Modified: 28-Apr-2018

package ov;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class AboutFormController implements Initializable {

    // member variables
    private Stage stage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    // Setting stage to close when close button selected
    @FXML
    private void handleButtonClose(ActionEvent event) {
        if(stage != null){
            stage.close();
        }
    }
   
    public void setStage(Stage stage)            { this.stage = stage; }
}
