//AddFormController.java
//
//Purpose: This is the controller class for AddForm page - where a user can add
//         courses that abide by form criteria.
//
//
//Author: Oleh Vytvitskyy  | oleh.vytvitskyy@gmail.com
//
//Date Created : 20-Apr-2018
//Date Modified: 28-Apr-2018

package ov;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddFormController implements Initializable {
    
    // member variables
    private Stage stage;
    private CourseInventoryModel model;
    @FXML
    private TextField textId;
    @FXML
    private TextField textTitle;
    @FXML
    private TextField textCredit;
    @FXML
    private ChoiceBox<String> comboCategory;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    
    // add button validates user input and adds course into course ArrayList
    @FXML
    private void handleButtonAdd(ActionEvent event) {
        
        Alert alert = new Alert(AlertType.ERROR);
        
        // validate ID
        String id = textId.getText().toUpperCase();
        if (model.validateId(id) == false){
            alert.setContentText("ID must be 4 letters + 5 digits");
            alert.show(); return;
        }
        
        // validate title
        String title = textTitle.getText();
        if (title == null || title.length() == 0){
            alert.setContentText("Title cannot be empty.");
            alert.show(); return;
        }
        
        // validate credit
        int credit = 0;
        try{
            credit = Integer.parseInt(textCredit.getText());
        }catch (NumberFormatException e){
            alert.setContentText("Credit must be numeric.");
            alert.show(); return;
        }
        if (credit <= 0){
            alert.setContentText("Credit must be greater than 0.");
            alert.show(); return;
        }
        
        // validate category
        String cat = comboCategory.getValue();
        if (cat == null){
            alert.setContentText("Credit must be greater than 0");
            alert.show(); return;
        }
        
        //add course and close the window
        model.addCourse(id, title, credit, cat);
        stage.close();

        if(stage != null)
            stage.close();
    }

    
    @FXML
    private void handleButtonCancel(ActionEvent event) {
        if(stage != null)
            stage.close();
    }
    
    // populating choicebox with courses category selection choicebox
    void setChoiceBox() { comboCategory.getItems().setAll(this.model.getCategories());} 
    
    public void setStage(Stage stage)                { this.stage = stage; }
    public void setModel(CourseInventoryModel model) { this.model = model; }
    
}
