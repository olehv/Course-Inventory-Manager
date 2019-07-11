//CourseInventory_OlehVytvitskyy.java
//
//Purpose: Main class which contains start method to declare stage, FXMLLLoader,
//         title, and stage.show();
//         *main method emitted because it's not necessary* 
//
//Author: Oleh Vytvitskyy  | oleh.vytvitskyy@gmail.com
//
//Date Created : 02-Apr-2018
//Date Modified: 28-Apr-2018

package ov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class CourseInventory_OlehVytvitskyy extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainForm.fxml"));
        Parent root = (Parent)loader.load();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        //Assign stage to the controller
        MainFormController controller = (MainFormController)loader.getController();
        controller.setStage(stage);
        
        //show window
        stage.setTitle("Course Inventory Application");
        stage.show();
    } 
}
