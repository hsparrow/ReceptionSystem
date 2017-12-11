package MainFrame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Database.DatabaseApi;
import Database.Student;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;

/**
 *
 * @author KanBaru
 */
public class EditInfoController implements Initializable {

    DatabaseApi api = new DatabaseApi();
    @FXML
    private TextField newAndrewIdText;
    @FXML
    private TextField newFirstNameText;
    @FXML
    private TextField newLastNameText;
    @FXML
    ChoiceBox<String> chooseGender;
    @FXML
    private TextField newNationalityText;
    @FXML
    ChoiceBox<String> chooseProgram;
    @FXML
    private Button closeButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Label currentIdLabel;
    @FXML
    private Label hidedLabel;
    @FXML
    private ImageView profileView;
    int currentId;

    /**
     * get current identity
     * @param currentId
     */
    public int getIdentity(int currentId) {
        Student currentStudent = api.getStudentDetailed(currentId);
        return currentStudent.getStatus();
    }

    /**
     * action for edit information button
     */
    public void editInfo() {
        //case student:
        if (currentId != 0) {
            String newAndrewID = newAndrewIdText.getText();
            String newFirstName = newFirstNameText.getText();
            String newLastName = newLastNameText.getText();
            String newGender = chooseGender.getValue();
            String newNationality = newNationalityText.getText();
            String newProgram = chooseProgram.getValue();

            if (api.updateStudent(new Student(currentId, newAndrewID, newFirstName, newLastName, newGender, newNationality, newProgram, getIdentity(currentId)))) {
//       
                Stage stage = (Stage) closeButton.getScene().getWindow();
                stage.close();
//                Controller.status = true;
            } else {
                hidedLabel.setText("edit failed!");
            }
        }else{
            hidedLabel.setText("Failed! Current ID is 0!");
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Controller.status = true;
    }

//    /**
//     * 为了不报错，等航哥OPENCV出来的接口
//     */
//    public int getCurrentId() {
//        return 2323131;
//    }
    @FXML
    private void checkFull() {
        //boolean empty = newFirstNameText.getText().isEmpty() | newLastNameText.getText().isEmpty() | newAndrewIdText.getText().isEmpty() | chooseProgram.getValue().isEmpty() | chooseGender.getValue().isEmpty() | newNationalityText.getText().isEmpty();
        confirmButton.setDisable(checkEmpty());
    }

    private boolean checkEmpty() {
        return newFirstNameText.getText().isEmpty()
                | newLastNameText.getText().isEmpty()
                | newAndrewIdText.getText().isEmpty()
                //    | chooseProgram.getValue().isEmpty()| chooseGender.getValue().isEmpty()
                | newNationalityText.getText().isEmpty();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(Controller.id!=0){
            currentId=Controller.id;
        }else{
            currentId=Controller.currentID;
        }
        currentIdLabel.setText(Integer.toString(currentId));
        confirmButton.setDisable(true);
        if (getIdentity(currentId) == 0) {
            chooseProgram.getItems().addAll("MISM", "MSIT", "MSPPM");
            chooseProgram.getSelectionModel().selectFirst();
        } else {
            chooseProgram.getItems().addAll("Professor", "Faculty");
        }

//    chooseProgram.setItems(FXCollections.observableArrayList("MISM", "MSIT", "MSPPM", new Separator(), "Professor", "Faculty"));
        chooseGender.getItems().addAll("Male", "Female", "Other");
        chooseGender.getSelectionModel().selectFirst();
        profileView.setImage(Utils.getProfileImage(currentId));

        Controller.status = false;
    }
}
