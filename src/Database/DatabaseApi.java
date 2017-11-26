/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

/**
 *
 * @author zoe
 */
public class DatabaseApi {
    ConnectMethod con=null;
    public DatabaseApi(){
         con= new ConnectMethod("jdbc:derby:Test1");
    
}
    public void createDateBase(){
        con.createDateBase();
    }
    public void getcon() {
        try{
        con.getCon();}        
        catch(ClassNotFoundException e){
        System.out.println("ClassNotFound Exception" + e);}
    }
    public void createStudentTable(){
        con.createTable("CREATE TABLE Student ( primaryID INT not null primary key GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),andrewNumID INT,andrewStringID VARCHAR(50),Firstname VARCHAR(50),Lastname VARCHAR(50),gender VARCHAR(50),nationality VARCHAR(50),program VARCHAR(50), status INT)");

    }
    public void displayAllStudent(){
        con.displayStudent("select * from Student");
    }
    public Student findStudent(int andrewNumID){
    Student student =con.getStudent("select * from Student where andrewNumID= "+andrewNumID);
    return student;
    
}
//int andrewNumID ,String andrewStringID,String firstname,String lastname, String gender, String nationality,Str
    public void insertRowStudent(Student student){
        con.insertRowStudent(student.getAndrewNumID(), student.getAndrewStringID(), student.getFirstname(), student.getLastname(),student.getGender(),student.getNationality(), student.getNationality(),student.getStatus());
    }
    
    public void updateStudent(Student student){
        con.updateStudent(student.getAndrewNumID(),student.getAndrewStringID(), student.getFirstname(), student.getLastname(),student.getGender(),student.getNationality(), student.getNationality());
    }
    public void refreshStudent(){
    con.refreshTable("Student","CREATE TABLE Student ( primaryID INT not null primary key GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),andrewNumID INT,andrewStringID VARCHAR(50),Firstname VARCHAR(50),Lastname VARCHAR(50),gender VARCHAR(50),nationality VARCHAR(50),program VARCHAR(50), status INT)");
    con.loadtxtStudent("student.csv");
    }
    public void loadtxtStudent(){
   con.loadtxtStudent("student.csv");}
    public void createVisitTable(){
        con.createTable("CREATE TABLE Visit ( primaryID INT not null primary key GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),andrewNumID INT,reason INT,visitTime TIMESTAMP)");

    }
    public void refreshVisit(){
    con.refreshTable("Visit","CREATE TABLE Visit ( primaryID INT not null primary key GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),andrewNumID INT,reason INT,visitTime TIMESTAMP)");
    
    }
    public void insertRowVisit(int andrewNumID,int reason){
        con.insertRowVisit( andrewNumID,reason);
    }
    public void displayAllVisit(){
    con.displayVisit("select * from Visit");
    
    }
    
    

}