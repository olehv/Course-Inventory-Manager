//MainFormController.java
//
//Purpose: This is it.. where most of the magic happens, read file, save,
//         data manipulation button handlers, etc.
//
//Author: Oleh Vytvitskyy  | oleh.vytvitskyy@gmail.com
//
//Date Created : 02-Apr-2018
//Date Modified: 28-Apr-2018

package ov;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainFormController implements Initializable {
    
    //member vars
    private Stage stage; //rememeber its stage reference here
    private CourseInventoryModel model;    //model part of MVC
    @FXML
    private ListView<Course> listviewCourse;
    @FXML
    private TextField textTitle;
    @FXML
    private TextField textCredit;
    @FXML
    private ComboBox<String> comboCategory;
    public File file;
    @FXML
    private Pane paneEdit;
    @FXML
    private ChoiceBox<String> sortCategory;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonCancel;
    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonSearch;
    @FXML
    private MenuItem menuOpen;
    @FXML
    private MenuItem menuSave;
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        paneEdit.setDisable(true);
        // Create model object
        model = new CourseInventoryModel();
      
        //customize listview cell to display IDs only
        listviewCourse.setCellFactory(list -> {
            // Declare anonymouse inner class extended Listcell and override updateItem()
            ListCell<Course> cell = new ListCell<Course>()
            {
                @Override
                public void updateItem(Course course, boolean empty)
                {
                    super.updateItem(course, empty);
                    if (empty || course == null){
                        this.setText(null);
                        this.setGraphic(null);
                    }else {
                        this.setText(course.getId());
                    }}
                };
            return cell;
            });  
        
        //Register changeListener for listviewCourse, track the changes
        listviewCourse.getSelectionModel()
                      .selectedItemProperty()
                      .addListener((ov, oldv, newv)-> {
                          showCourseInfo(newv);
                      });
        
        // Disable all buttons initially because they serve no purpose before upload
        buttonDelete.setDisable(true);
        buttonAdd.setDisable(true);
        buttonSave.setDisable(true);
        buttonCancel.setDisable(true);
        buttonEdit.setDisable(true);
        buttonSearch.setDisable(true);
        // Enable open menu prior to file being loaded - in order to be loaded
        menuOpen.setDisable(false);
        // Disable save button (menuItem) because no item is loaded yet
        menuSave.setDisable(true);
        
    } //END INITIALIZE

    
    
    // method to show course info (used to display in paneEdit)
    private void showCourseInfo (Course course){
        if (course == null) return;
        
        textTitle.setText(course.getTitle());
        textCredit.setText(String.valueOf(course.getCredit()));

        comboCategory.getItems().setAll(model.getCategories());
        comboCategory.getSelectionModel().select(course.getCategory());
    }

    

    @FXML
    private void handleMenuAbout(ActionEvent event) throws Exception {
        //load scene graph from xml
        FXMLLoader fxmlLoader = new FXMLLoader(
                                    getClass().getResource("AboutForm.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        
        //create and show about window as modal
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("About");
        stage.setScene(scene);
        stage.show();
        
        // remember the stage, so can close it later
        AboutFormController ctrlAbout = fxmlLoader.getController();
        ctrlAbout.setStage(stage);
    }

    @FXML
    private void handleMenuOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(stage);
        fileChooser.setTitle("Open Course List File...");
        //open the file
        model.readCourseFile(file);
        
        // Display course to listview
        if(model.getCourseCount() > 0){
            sortCategory.getItems().setAll(model.getCategories());
            sortCategory.getItems().add(0, "ALL CATEGORIES");
            sortCategory.getSelectionModel().selectFirst();
            //populate courses
            populateCourses(null);
            // file is now loaded, buttons available
            buttonDelete.setDisable(false);
            buttonAdd.setDisable(false);
            buttonSave.setDisable(false);
            buttonCancel.setDisable(false);
            buttonEdit.setDisable(false);
            buttonSearch.setDisable(false);
            // not allowed to open another file once one is loaded
            menuOpen.setDisable(true);
            // save menu becomes available because file is now available for save
            menuSave.setDisable(false);
        }else{
            //no data
        }    
    }
    
    // method used to populate courses into listview (left side list on main page)
    private void populateCourses(String cat){
        listviewCourse.setItems(FXCollections.observableList(model.getCourses()));
    }

    @FXML
    private void handleMenuSaveAs(ActionEvent event) {
        model.saveCourseFile(file);
        Alert alert = new Alert(AlertType.INFORMATION,
                      "Courses File Saved");
        alert.show();
    }

    // method to handle program close, confirming with user if they're sure
    @FXML
    private void handleMenuExit(ActionEvent event) { 
            Alert alert = new Alert(AlertType.CONFIRMATION,
                          "Do you really want to quit?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK)
                Platform.exit();
            else
                stage.close(); //ignore close event 
    }

    
    //remember stage reference
    public void setStage(Stage stage){
        this.stage = stage;
        
        stage.setOnCloseRequest(e -> {
            
            Alert alert = new Alert(AlertType.CONFIRMATION,
                          "Do you really want to quit?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK)
                Platform.exit();
            else
                e.consume(); //ignore close event
        });
    }        

    @FXML
    private void handleButtonDelete(ActionEvent event) {
         // get slected course from listview
         Course course = listviewCourse.getSelectionModel().getSelectedItem();
         if (course == null)
             return;
         
         //confirm deletion using alert
         Alert alert = new Alert(AlertType.CONFIRMATION,
                                 "Do you want to delete " + course.getId() + "?");
         Optional<ButtonType> result = alert.showAndWait();
         if(result.get() == ButtonType.OK)
         {
             //delete the course and save to the file
             model.removeCourse(course);
             model.saveCourseFile(file);
             
             //reload course list
             listviewCourse.setItems(FXCollections.observableList(model.getCourses()));
             listviewCourse.getSelectionModel().clearSelection();
         }  
    }

    @FXML
    private void handleButtonAdd(ActionEvent event) throws Exception{
        // load cene graph from fxml
        FXMLLoader fxmlLoader = new FXMLLoader(
                                    getClass().getResource("AddForm.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        
        // create and show about window as modal
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add New Course");
        stage.setScene(scene);
        
        // remember the stage, so can close it later
        AddFormController ctrlAdd = fxmlLoader.getController();
        ctrlAdd.setStage(stage);
        ctrlAdd.setModel(model);
        ctrlAdd.setChoiceBox();
        
        // wait user response within this block
        stage.showAndWait();
        
        // process the user response here
        String id = model.getNewCourseId();
        int index = model.getCourseIndex(id);
        if (id == null) return;
        
        //save the file
        model.saveCourseFile(file);
        
        //focus on the new item
        listviewCourse.setItems(FXCollections.observableList(model.getCourses()));
        listviewCourse.getSelectionModel().select(index);
        listviewCourse.getFocusModel().focus(index);
        listviewCourse.scrollTo(index);
    }

    @FXML
    private void handleButtonSave(ActionEvent event) {
        // get selected course from listview
        Course c = listviewCourse.getSelectionModel().getSelectedItem();
        if (c == null)
            return;
        
        // confirm saving
        Alert alert1 = new Alert(AlertType.CONFIRMATION,
                                "Do you want to save the changes?");
        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() != ButtonType.OK)
            return;
        
        // alert for validation
        Alert alert2 = new Alert(AlertType.ERROR);
        
        // validate title
        String title = textTitle.getText();
        if (title == null || title.length() == 0){
            alert2.setContentText("Title cannot be empty.");
            alert2.show();
            return;    
        }
        
        // Valdate credit
        int credit;
        try {
            credit = Integer.parseInt(textCredit.getText());
        }catch (NumberFormatException e){
            alert2.setContentText("Credit must be an integer.");
            alert2.show(); return;
        }
        if (credit < 0){
            alert2.setContentText("Credit must be greater than 0.");
            alert2.show();
            return;
        }
        
        // Validate category
        String cat = comboCategory.getValue();
        if (cat == null){
            alert2.setContentText("Category must be selected.");
            alert2.show();
            return;
        }
        
        // update the course
        model.updateCourse(c.getId(), title, credit, cat);
        model.saveCourseFile(file);
        paneEdit.setDisable(true);
    }

    @FXML
    private void handleButtonCancel(ActionEvent event) {
        // re-select the course
        Course c = listviewCourse.getSelectionModel().getSelectedItem();
        listviewCourse.getSelectionModel().select(c);
        //enable/disable controls
        paneEdit.setDisable(true);
    }

    // mehtod allows user to edit course info when edit button is selected
    @FXML
    private void handleButtonEdit(ActionEvent event) {
        paneEdit.setDisable(false);
    }

    @FXML
    private void handleButtonSearch(ActionEvent event) throws IOException
    {
        // Create search form stage and open it
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchForm.fxml"));
        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Search Courses");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        
        //Assign stage to the controller
        SearchFormController controller = loader.getController();
        controller.setModel(model);
        controller.setStage(stage);
        controller.updateTableCourse();
        
        //show modal window
        stage.showAndWait();
        
        // update listview with user selection
        Course c = model.getSelectedCourse();
        showCourseInfo(c);
        int index = model.getSelectedCourseIndex();
        
        
        if (c != null){  //selected
            //programatically select an item in the listview
            listviewCourse.getSelectionModel().select(c);
            //make it focused
            listviewCourse.getFocusModel().focus(index);
            listviewCourse.scrollTo(index);
        }else{  //no selection
            listviewCourse.getSelectionModel().clearSelection();
        }    
    }//END OF handleButtonSearch
 
}//END of MainFormController Class
