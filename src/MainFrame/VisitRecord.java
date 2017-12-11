/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFrame;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author yunyi.ZHANG
 */
public class VisitRecord {

    final private IntegerProperty andrewNumID = new SimpleIntegerProperty();
    final private StringProperty andrewStringID = new SimpleStringProperty();
    final private StringProperty Firstname = new SimpleStringProperty();
    final private StringProperty Lastname = new SimpleStringProperty();
    final private StringProperty gender = new SimpleStringProperty();
    final private StringProperty nationality = new SimpleStringProperty();
    final private StringProperty program = new SimpleStringProperty();
    final private IntegerProperty status = new SimpleIntegerProperty();
    final private StringProperty visitTime = new SimpleStringProperty();
    final private StringProperty visitReason = new SimpleStringProperty();

//    private int primaryID;
//    private int andrewNumID;
//    private String andrewStringID;
//    private String Firstname;
//    private String Lastname;
//    private String gender;
//    private String nationality;
//    private String program;
//    private int status;
//    private Timestamp visitTime;
//    private String visitReason;
    
    
    
    
        /**
         * Constructor of this class.
         */
    public VisitRecord() {
        this.Firstname.setValue("");
        this.Lastname.setValue("");
        this.andrewNumID.setValue(-1);
        this.andrewStringID.setValue("");
//        this.primaryID = student.getPrimaryID();
        this.gender.setValue("");
        this.nationality.setValue("");
        this.program.setValue("");
        this.status.setValue(-1);
        this.visitTime.setValue("");
        this.visitReason.setValue("");
    }

           /**
         * Constructor of this class with parameters.
         * @param andrewNumID 7-digit ID of this member.
         * @param andrewStringID andrewID of this member.
         * @param Firstname first name of this member.
         * @param Lastname last name of this member.
         * @param gender gender of this member.
         * @param nationality nationality of this member.
         * @param program program of this member.
         * @param status status of this member: 0 means student, 1 means staff.
         * @param visitTime visit time of this member.
         * @param visitReason visit reason of this member.
         */
    public VisitRecord(int andrewNumID, String andrewStringID, String Firstname, String Lastname, String gender, String nationality, String program, int status, String visitTime, String visitReason) {
        this.Firstname.setValue(Firstname);
        this.Lastname.setValue(Lastname);
        this.andrewNumID.setValue(andrewNumID);
        this.andrewStringID.setValue(andrewStringID);
//        this.primaryID = student.getPrimaryID();
        this.gender.setValue(gender);
        this.nationality.setValue(nationality);
        this.program.setValue(program);
        this.status.setValue(status);
        this.visitTime.setValue(visitTime);
        this.visitReason.setValue(visitReason);

    }

//    public int getPrimaryID() {
//        return primaryID;
//    }
//
//    public void setPrimaryID(int primaryID) {
//        this.primaryID = primaryID;
//    }
    public IntegerProperty andrewNumIDProperty() {
        return andrewNumID;
    }

    public StringProperty andrewStringIDProperty() {
        return andrewStringID;
    }

    public StringProperty FirstnameProperty() {
        return Firstname;
    }

    public StringProperty LastnameProperty() {
        return Lastname;
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public StringProperty nationalityProperty() {
        return nationality;
    }

    public StringProperty programProperty() {
        return program;
    }

    public IntegerProperty statusProperty() {
        return status;
    }

    public StringProperty visitTimeProperty() {
        return visitTime;
    }

    public StringProperty visitReasonProperty() {
        return visitReason;
    }

    /**
     * Sets this andrewNumID.
     *
     * @param andrewNumID Sets this andrewNumID.
     */
    public void setAndrewNumID(Integer andrewNumID) {
        this.andrewNumID.set(andrewNumID);
    }

    /**
     * Sets this andrewStringID.
     *
     * @param andrewStringID Sets this andrewStringID.
     */
    public void setAndrewStringID(String andrewStringID) {
        this.andrewStringID.set(andrewStringID);
    }

    /**
     * Sets this first name.
     *
     * @param firstName Sets this first name.
     */
    public void setFirstname(String firstName) {
        this.Firstname.set(firstName);
    }

    /**
     * Sets this last name.
     *
     * @param lastName Sets this last name.
     */
    public void setLastname(String lastName) {
        this.Lastname.set(lastName);
    }

    /**
     * Sets this gender.
     *
     * @param gender Sets this gender.
     */
    public void setGender(String gender) {
        this.gender.set(gender);
    }

    /**
     * Sets this nationality.
     *
     * @param nationality Sets this nationality.
     */
    public void setNationality(String nationality) {
        this.nationality.set(nationality);
    }

    /**
     * Sets this program.
     *
     * @param program Sets this program.
     */
    public void setProgram(String program) {
        this.program.set(program);
    }

    /**
     * Sets this status.
     *
     * @param status Sets this status.
     */
    public void setStatus(Integer status) {
        this.status.set(status);
    }

    /**
     * Sets this visitTime.
     *
     * @param visitTime Sets this visitTime.
     */
    public void setVisitTime(String visitTime) {
        this.visitTime.set(visitTime);
    }

    /**
     * Sets this visitReason.
     *
     * @param visitReason Sets this visitReason.
     */
    public void setVisitReason(String visitReason) {
        this.visitReason.set(visitReason);
    }

            /**
         * Gets first name.
         * @return Returns this first name.
         */
	public String getFirstname() {
		return Firstname.get();
	}

    public Integer getAndrewNumID() {
        return andrewNumID.get();
    }

    public String getAndrewStringID() {
        return andrewStringID.get();
    }

    public String getLastname() {
        return Lastname.get();
    }

    public String getGender() {
        return gender.get();
    }

    public String getNationality() {
        return nationality.get();
    }

    public String getProgram() {
        return program.get();
    }

    public Integer getStatus() {
        return status.get();
    }

    public String getVisitTime() {
        return visitTime.get();
    }

    public String getVisitReason() {
        return visitReason.get();
    }
}
