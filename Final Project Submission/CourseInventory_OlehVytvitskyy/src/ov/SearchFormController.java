//SearchFormController.java
//
//Purpose: This class is to handle all search functionality when a user loads
//         a course, and wants to search through course with keyword.
//
//Author: Oleh Vytvitskyy  | oleh.vytvitskyy@gmail.com
//
//Date Created : 20-Apr-2018
//Date Modified: 28-Apr-2018

package ov;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SearchFormController implements Initializable {

    // member vars
    private CourseInventoryModel model;
    private Stage stage;
    @FXML
    private TableView<Course> tableCourse;
    @FXML
    private TableColumn<Course, String> colId;
    @FXML
    private TableColumn<Course, String> colTitle;
    @FXML
    private TableColumn<Course, Integer> colCredit;
    @FXML
    private TableColumn<Course, String> colCategory;
    @FXML
    private TextField textSearch;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Customize tableview
        colId.setCellValueFactory(new PropertyValueFactory<Course, String>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Course, String>("title"));
        colCredit.setCellValueFactory(new PropertyValueFactory<Course, Integer>("credit"));
        colCategory.setCellValueFactory(new PropertyValueFactory<Course, String>("category"));
        
    }    

    @FXML
    private void handleSearch(ActionEvent event) 
    {
        tableCourse.setItems(FXCollections.observableList(model.findCoursesById(textSearch.getText()))); 
        System.out.println(tableCourse.getItems());      
    }

    @FXML
    private void handleSelect(ActionEvent event) 
    {
        //remember selected course
        model.setSelectedCourse(tableCourse.getSelectionModel().getSelectedItem());

        //close window (if selected is not null)
        if (stage != null){
            stage.close();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        model.setSelectedCourse(null);  //no selection
        stage.close();
    }
    
    
    
    public void setStage(Stage stage)                { this.stage = stage; }
    public void setModel(CourseInventoryModel model) { this.model = model; }
    void updateTableCourse(){
        tableCourse.setItems(FXCollections.observableList(model.getCourses()));
    }
    
}
