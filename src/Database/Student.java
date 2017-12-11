/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Timestamp;

/**
 *
 * @author zoe
 */
public class Student {
// primaryID INT,andrewNumID INT,andrewStringID VARCHAR(50),Firstname VARCHAR(50),Lastname VARCHAR(50),gender VARCHAR(50),nationality VARCHAR(50),program VARCHAR(50))
private int primaryID;
private int andrewNumID;
private String andrewStringID;
private String Firstname;
private String Lastname;
private String gender;
private String nationality;
private String program;
private int status;
private Timestamp lasttime;
private int visittime;

    public Student(int primaryID, int andrewNumID, String andrewStringID, String Firstname, String Lastname, String gender, String nationality, String program,int status) {
        this.primaryID = primaryID;
        this.andrewNumID = andrewNumID;
        this.andrewStringID = andrewStringID;
        this.Firstname = Firstname;
        this.Lastname = Lastname;
        this.gender = gender;
        this.nationality = nationality;
        this.program = program;
        this.status=status;
    }
    
    public Student(int andrewNumID, String andrewStringID, String Firstname, String Lastname, String gender, String nationality, String program,int status) {

        this.andrewNumID = andrewNumID;
        this.andrewStringID = andrewStringID;
        this.Firstname = Firstname;
        this.Lastname = Lastname;
        this.gender = gender;
        this.nationality = nationality;
        this.program = program;
        this.status=status;
    }
    
    public Student(int primaryID,int andrewNumID, String andrewStringID, String Firstname, String Lastname, String gender, String nationality, String program,int status,Timestamp lasttime,int visittime) {
this.primaryID=primaryID;
        this.andrewNumID = andrewNumID;
        this.andrewStringID = andrewStringID;
        this.Firstname = Firstname;
        this.Lastname = Lastname;
        this.gender = gender;
        this.nationality = nationality;
        this.program = program;
        this.status=status;
        this.lasttime=lasttime;
        this.visittime=visittime;
    }

    public Timestamp getLasttime() {
        return lasttime;
    }

    public void setLasttime(Timestamp lasttime) {
        this.lasttime = lasttime;
    }

    public int getVisittime() {
        return visittime;
    }

    public void setVisittime(int visittime) {
        this.visittime = visittime;
    }

    public Student() {
    }

   

    public int getPrimaryID() {
        return primaryID;
    }

    public int getAndrewNumID() {
        return andrewNumID;
    }

    public String getAndrewStringID() {
        return andrewStringID;
    }

    public String getFirstname() {
        return Firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public String getGender() {
        return gender;
    }

    public String getNationality() {
        return nationality;
    }

    public String getProgram() {
        return program;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
