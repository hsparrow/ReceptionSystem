package MainFrame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Database.DatabaseApi;
import Database.Student;

/**
 * FXML Controller class
 *
 * @author KanBaru
 */
public class AddNewMemberController implements Initializable {
 DatabaseApi api=new DatabaseApi();
    @FXML
    private TextField idText;
    @FXML
    private TextField newAndrewIdText;
    @FXML
    private TextField newFirstNameText;
    @FXML
    private TextField newLastNameText;
//    @FXML
//    private TextField newGenderText;
    @FXML
    private TextField newNationalityText;
//    @FXML
//    private TextField newProgramText;
    @FXML
    private Button closeButton;
    @FXML
    ChoiceBox<String> chooseIdentity;// = new ChoiceBox<>(FXCollections.observableArrayList("Student", "Staff"));
    @FXML
    ChoiceBox<String> chooseProgram;
    @FXML
    ChoiceBox<String> chooseGender;

    /**
     * add new member
     */
    @FXML
    public void addMember() {
        //int selectedID = getId(); //等着航哥OPENCV的接口
        //confirm需要有判断和提示，还有exception
        try{
        int newId = Integer.parseInt(idText.getText());
        String identity = chooseIdentity.getValue();
        String newAndrewID = newAndrewIdText.getText();
        String newFirstName = newFirstNameText.getText();
        String newLastName = newLastNameText.getText();
        String newGender = chooseGender.getValue();
        String newProgram = chooseProgram.getValue();
        String newNationality = newNationalityText.getText();
        api.insertRowStudent(new Student(newId,newAndrewID,newFirstName,newLastName,newGender,newNationality,newProgram,0));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
               
    }

    /**
     * 新增成员的时候直接提取照片
     */
    public void capturePicture() {

    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        chooseIdentity.getItems().addAll("Student");
        chooseIdentity.getItems().addAll("Staff");
        chooseProgram.getItems().addAll("MISM");
        chooseProgram.getItems().addAll("MSIT");
        chooseProgram.getItems().addAll("MSPPM");
        chooseGender.getItems().addAll("Male");
        chooseGender.getItems().addAll("Female");
        chooseGender.getItems().addAll("Other");
    }

    private int getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
