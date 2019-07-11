//CourseInventoryModel.Java
//
//Purpose: This is the model class for course inventory, handles multiple
//         validation, setters/getters, hashmap.
//
//Author: Oleh Vytvitskyy  | oleh.vytvitskyy@gmail.com
//
//Date Created : 02-Apr-2018
//Date Modified: 28-Apr-2018

package ov;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javafx.collections.FXCollections;


public class CourseInventoryModel {
    
    // member variables
    private final Set<String> categories;
    private ArrayList<Course> courses;
    private Course selectedCourse = null;
    String newCourseId;
    
    // constructor
    public CourseInventoryModel(){
        courses = new ArrayList<Course>();
        categories = new HashSet<>();
    }
    
    
    //parse .dat file
    public void readCourseFile(File file){
        //validate param
        if(file == null)
            return;
        
        ArrayList<String> lines = new ArrayList<>();
        String line;
        
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            //copy all lines from file to memory
            while ((line = reader.readLine()) != null){
                lines.add(line);
            }
        }catch(IOException e){
            System.out.println("[ERROR] Failed to Read file");
        }
        
        //process parsing every line
        for(int i = 0; i < lines.size(); i++){
            line = lines.get(i);
            
            //split line
            String[] tokens = line.split(";");
            
            //assign tokens to course object
            if(tokens.length == 4){
                //trim then set token values to course
                String id = tokens[0].trim();
                String title = tokens[1].trim();
                int credit = Integer.valueOf(tokens[2].trim());
                String cat = tokens[3].trim();
                categories.add(cat);
                
                Course c = new Course(id, title, credit, cat);
                System.out.println(c.getId());
                //put Course to ArrayList
                courses.add(c);
            } //END OF IF
        } //END OF FOR
    }  //END OF METHOD
    
    
    //save .dat file
    public void saveCourseFile(File file){       
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            for (int i = 0; i < courses.size(); i++){
                Course course = courses.get(i);
                writer.write(course.getId() + " ; " + course.getTitle() + " ; " +
                             course.getCredit() + " ; " + course.getCategory());    
                writer.newLine();          
            }
            writer.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        } 
    }
    
    public ArrayList<Course> findCoursesById (String id)
    {                        
        // make all characters uppercase
        String keyword = id.toUpperCase();
        ArrayList<Course> list = new ArrayList<Course>();
        
        //find all courses containing the keyword
        for (int i = 0; i< this.courses.size(); ++i){
            Course c = courses.get(i);
            System.out.println("findcoursebyid");
            if (c.getId().contains(keyword)){
                list.add(c);
            }
        }
        
        //validation
        if (id == null || id.isEmpty()){
            return this.courses;              //return empty array
        }
        return list;  
    }
    
    // removing course method
    public void removeCourse(Course c){
        //remove selected course(index - c) from ArrayList courses
        courses.remove(c);
    }
    
    // updating a course, selected by ID
    public void updateCourse(String id, String title, int credit, String cat){
        Course course = getCourseById(id);
        if (course != null)
            course.set(id, title, credit, cat);
    }
    
    // selecting a course by it's ID
    private Course getCourseById(String id){
        for (int i = 0; i < courses.size(); ++i){
            if (id.equals(courses.get(i).getId()))
                return courses.get(i);
        }
        return null;
    }
    
    // adding a course to courses ArrayList (to index courses.size() +1)
    public void addCourse(String id, String title, int credit, String cat){
        Course course = new Course(id, title, credit, cat);
        courses.add(course);
        setNewCourseId(id);
    }
    
    // method to validate course ID is in correct format (using regex)
    public boolean validateId(String id){
        //regex: 4 letters followed by 5 numbers
        final String PATTERN = "^[A-Za-z]{4}[0-9]{5}$";
        if (id != null && id.matches(PATTERN))
            return true;
        else
            return false;
    }
    
    
    
    Set<String> getCategories()              { return categories; }
    int getCourseIndex(String id)            { return courses.indexOf(id); }
    void setNewCourseId(String newCourseId)  { this.newCourseId = newCourseId; }
    String getNewCourseId()                  { return newCourseId; }
    public int getCourseCount()              { return courses.size(); }
    public ArrayList<Course> getCourses()    { return courses; } 
    public Course getSelectedCourse()        { return selectedCourse; }
    public int getSelectedCourseIndex()      { return this.courses.indexOf(selectedCourse); }
    public void setSelectedCourse(Course c)  { this.selectedCourse = c; }  
}
