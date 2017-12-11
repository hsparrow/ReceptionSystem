/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import static java.lang.System.in;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author zoe
 */
public class DatabaseApi {

    ConnectMethod con = null;

    public DatabaseApi() {
        con = new ConnectMethod("jdbc:derby:Test1");

    }

    public void createDateBase() {
        con.createDateBase();
    }

    public void getcon() {
        try {
            con.getCon();
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFound Exception" + e);
        }
    }

    public void createStudentTable() {
        con.createTable("CREATE TABLE Student ( primaryID INT not null primary key GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),andrewNumID INT,andrewStringID VARCHAR(50),Firstname VARCHAR(50),Lastname VARCHAR(50),gender VARCHAR(50),nationality VARCHAR(50),program VARCHAR(50), status INT)");

    }

    public void displayAllStudent() {
        con.displayStudent("select * from Student");
    }

    public Student findStudent(int andrewNumID) {
        System.out.println("select * from Student where andrewNumID= " + andrewNumID);
        Student student = con.getStudent("select * from Student where andrewNumID= " + andrewNumID);
        return student;

    }
    


    public int findFirstStudent(ArrayList<Integer> studentID) {
        for (int i : studentID) {
            if (findStudent(i) != null) {
                return (i);
            }
        }
        return (0);
    }

    public Student getStudentDetailed(int andrewNumID) {
        String url = "select student.* ,max(visit.visitTime) as lastesttime ,count(visit.visitTime) as visitnumber from Student left join visit on visit.andrewNumID = student.andrewNumID where student.andrewNumID  = " + andrewNumID + " group by Student.primaryID,Student.andrewNumID,Student.andrewStringID,Student.Firstname,Student.Lastname,Student.gender,Student.nationality,Student.program,Student.status";
        Student student = con.getStudentDetailed(url);
        return student;

    }
    //select student.*,max(visit.visitTime),count(visit.visitTime) from Student left join visit on student.andrewNumID=visit.andrewNumID group by Student.primaryID)
//int andrewNumID ,String andrewStringID,String firstname,String lastname, String gender, String nationality,Str

    public boolean insertRowStudent(Student student) {
        boolean flag = con.insertRowStudent(student.getAndrewNumID(), student.getAndrewStringID(), student.getFirstname(), student.getLastname(), student.getGender(), student.getNationality(), student.getProgram(), student.getStatus());
        return flag;
    }

    public boolean updateStudent(Student student) {
        boolean flag = con.updateStudent(student.getAndrewNumID(), student.getAndrewStringID(), student.getFirstname(), student.getLastname(), student.getGender(), student.getNationality(), student.getProgram());
        return flag;
    }

    public void refreshStudent() {
        con.refreshTable("Student", "CREATE TABLE Student ( primaryID INT not null primary key GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),andrewNumID INT,andrewStringID VARCHAR(50),Firstname VARCHAR(50),Lastname VARCHAR(50),gender VARCHAR(50),nationality VARCHAR(50),program VARCHAR(50), status INT)");
        con.loadtxtStudent("student.csv");
    }

    public void loadtxtStudent() {
        con.loadtxtStudent("student.csv");
    }

    public void createVisitTable() {
        con.createTable("CREATE TABLE Visit ( primaryID INT not null primary key GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),andrewNumID INT,reason INT,visitTime TIMESTAMP)");

    }

    public void refreshVisit() {
        con.refreshTable("Visit", "CREATE TABLE Visit ( primaryID INT not null primary key GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),andrewNumID INT,reason INT,visitTime TIMESTAMP)");

    }

    public void refreshLogin() {
        con.refreshTable("login", "CREATE TABLE Login ( primaryID INT not null primary key GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),admin VARCHAR(50),password VARCHAR(50))");

    }

    public void insertRowVisit(int andrewNumID, int reason) {
        con.insertRowVisit(andrewNumID, reason);
    }

    public void displayAllVisit() {
        con.displayVisit("select * from Visit");

    }

    public ResultSet getResultSet(String query) {
        return (con.getResultSet(query));

    }

    public void createLoginTable() {
        con.createTable("CREATE TABLE Login ( primaryID INT not null primary key GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),admin VARCHAR(50),password VARCHAR(50))");

    }

    public void insertLoginVisit(String admin, String password) {
        con.insertRowLogin(admin, password);
    }

    public void displayAllLogin() {
        con.displayLogin("select * from login");

    }

    public boolean loginFace(ArrayList<Integer> id) {
        for (Integer i : id) {

            boolean flag = con.loginCheck("select * from login where admin = '" + Integer.toString(i) + "'");
            if (flag == true) {
                return true;
            }

        }

        return false;

    }

    public boolean loginInput(String id, String password) {
        boolean flag = con.loginCheck("select * from login where admin = '" + id + "'" + " and password= '" + password + "'");
        return (flag);

    }

//     public 
}
