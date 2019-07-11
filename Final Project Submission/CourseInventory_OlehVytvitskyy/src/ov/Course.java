package ov;

public class Course {
    String id;
    String title;
    int credit;
    String category;
    
    
    //ctor
    
    public Course(){
        this("","",0,"NONE");
        
    }
    
    public Course(String id, String title, int credit, String cat){
        this.id = id;
        this.title = title;
        this.credit = credit;
        this.category = cat;
    }
    
    public void set(String id, String title, int credit, String cat){
        this.id = id;
        this.title = title;
        this.credit = credit;
        this.category = cat;
    }
    
    //public String toString()
    
    @Override
    public boolean equals(Object o){
    
            if (o instanceof Course){
                Course course = (Course)o; //downcast
                return id.equals(course.getId());
                //return true;
            }else {
                return false;
            }
            //return true;
    }
    
    
    

    //getters/setters
    public String getId()                               { return id; }
    public void setId(String id)                        { this.id = id; }
    public String getTitle()                            { return title; }
    public void setTitle(String title)                  { this.title = title; }
    public int getCredit()                              { return credit; }
    public void setCredit(int credit)                   { this.credit = credit; }
    public String getCategory()                         { return category;  }
    public void setCategory(String category)            { this.category = category; }
       
}
