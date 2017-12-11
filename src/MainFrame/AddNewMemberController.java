package MainFrame;

import Database.DatabaseApi;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import Database.Student;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author KanBaru
 */
public class AddNewMemberController implements Initializable {

    @FXML
    private TextField idText;
    @FXML
    private TextField newAndrewIdText;
    @FXML
    private TextField newFirstNameText;
    @FXML
    private TextField newLastNameText;

    @FXML
    private TextField newNationalityText;

    @FXML
    private Button closeButton;
    @FXML
    private Button confirmButton;

    @FXML
    private ChoiceBox<String> chooseIdentity;// = new ChoiceBox<>(FXCollections.observableArrayList("Student", "Staff"));
    @FXML
    private ChoiceBox<String> chooseProgram;
    @FXML
    private ChoiceBox<String> chooseGender;

    @FXML
    private ImageView photoView;
    @FXML
    private Button capture_button;
    @FXML
    private Label hidedLabel;
    int ifStudent;

//    private ImageView photoView;
    // int currentId;
    DatabaseApi api = new DatabaseApi();

    static int currentID;
    private static Image image;
    boolean isProfileEmpty = true;
    private int newAndrewNumID;
    private String identity;
    private String newAndrewStringID;
    private String newFirstName;
    private String newLastName;
    private String newGender;
    private String newProgram;
    private String newNationality;

    // confirm传ID给capture图片(只有insert到数据库)
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        isProfileEmpty = true;
        currentID = 0;
        newAndrewNumID = 0;
        ifStudent=0;
        identity = "";
        newAndrewStringID = "";
        newFirstName = "";
        newLastName = "";
        newGender = "";
        newProgram = "";
        newNationality = "";

        chooseIdentity.getItems().addAll("Student", "Staff");
        chooseIdentity.getSelectionModel().selectFirst();

        chooseProgram.getItems().addAll("MISM", "MSIT", "MSPPM");
        chooseProgram.getSelectionModel().selectFirst();
        chooseGender.getItems().addAll("Male", "Female", "Other");
        chooseGender.getSelectionModel().selectFirst();
        confirmButton.setDisable(true);
    }

    /**
     * add new member
     */
    @FXML
    public void addMember() throws IOException {

        currentID = 0;
        // 学生和faculty新加一样吗
        // insert失败会怎么样

        //confirm需要有判断和提示，还有exception
//        confirmButton.setDisable(checkEmpty());
//        if (chooseIdentity.getValue().equals("Staff")) {
//            chooseProgram.getItems().clear();
//            chooseProgram.getItems().addAll("Professor", "Faculty");
//            chooseProgram.getSelectionModel().selectFirst();
//
//        } else {
//            chooseProgram.getItems().clear();
//            chooseProgram.getItems().addAll("MISM", "MSIT", "MSPPM");
//            chooseProgram.getSelectionModel().selectFirst();
//        }
        
        try {
            newAndrewNumID = Integer.parseInt(idText.getText().trim());
            identity = chooseIdentity.getValue();
            newAndrewStringID = newAndrewIdText.getText().trim();
            newFirstName = newFirstNameText.getText().trim();
            newLastName = newLastNameText.getText().trim();
            newGender = chooseGender.getValue();
            newProgram = chooseProgram.getValue();
            newNationality = newNationalityText.getText().trim();
            
            if(identity.equals("Student")){
                ifStudent=0;
            }else{
                ifStudent=1;
            }
            
            // 存staff
       //     api.insertRowStudent(new Student(newId, newAndrewStringID, newFirstName, newLastName, newGender, newNationality, newProgram, 0));

            if (api.insertRowStudent(new Student(newAndrewNumID, newAndrewStringID, newFirstName, newLastName, newGender, newNationality, newProgram, ifStudent))) {
                System.out.println("Insert successfully.");
                //存最新一次的photo到文件夹，名字为id.jpg
                Utils.saveImage("src/Resources/profileImage/", String.valueOf(newAndrewNumID), image);
                currentID = newAndrewNumID;
                showTrainingAlert();
                Stage stage = (Stage) confirmButton.getScene().getWindow();
                stage.close();
            } else {
              //  System.out.print(api.insertRowStudent(new Student(newId, newAndrewStringID, newFirstName, newLastName, newGender, newNationality, newProgram, 0)));
              hidedLabel.setText("AndrewID has already existed!");
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * 弹出capture picture的界面
     *
     * @param event
     * @throws IOException
     */
    public void showTrainingAlert() throws IOException {
        Parent train_parent = FXMLLoader.load(getClass().getResource("/MainFrame/CaptureTraining.fxml"));
        Scene train_scene = new Scene(train_parent);
        Stage window = new Stage();
        window.setScene(train_scene);
        window.showAndWait();
    }

    /**
     * Sets profile image from the present frame.
     * @param event
     */
    @FXML
    public void setAddMemberProfile(ActionEvent event) {
        Controller c = new Controller();
        image = c.getPresentImage();
        Utils.updateImageView(photoView, image);
        if(photoView != null) {
            isProfileEmpty = false;
        }
//        isProfileEmpty = false;
        checkFull();
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        // Changes the controller detail info status, true for keep set up 
//        Controller.status = true;
    }

    @FXML
    private void checkFull() {
//        boolean empty = isProfileEmpty || idText.getText().isEmpty() || chooseIdentity.getValue().isEmpty() || newAndrewIdText.getText().isEmpty() || newFirstNameText.getText().isEmpty() || newLastNameText.getText().isEmpty() || chooseGender.getValue().isEmpty() || chooseProgram.getValue().isEmpty() || newNationalityText.getText().isEmpty();
        confirmButton.setDisable(checkEmpty());
    }

//    @FXML
//    private void checkFull() {
//        boolean empty = newFirstNameText.getText().isEmpty() || newLastNameText.getText().isEmpty() || newAndrewIdText.getText().isEmpty() || newProgramText.getText().isEmpty() || newGenderText.getText().isEmpty() || newNationalityText.getText().isEmpty();
//        confirmButton.setDisable(empty);
//    }
    @FXML
    private void changeIdentity() {
        confirmButton.setDisable(checkEmpty());
        if (chooseIdentity.getValue().equals("Staff")) {
            chooseProgram.getItems().clear();
            chooseProgram.getItems().addAll("Professor", "Faculty");
            chooseProgram.getSelectionModel().selectFirst();

        } else {
            chooseProgram.getItems().clear();
            chooseProgram.getItems().addAll("MISM", "MSIT", "MSPPM");
            chooseProgram.getSelectionModel().selectFirst();
        }
    }

    private boolean checkEmpty() {
        return isProfileEmpty || idText.getText().isEmpty()
                || chooseIdentity.getValue().isEmpty() || newAndrewIdText.getText().isEmpty()
                || newFirstNameText.getText().isEmpty() || newLastNameText.getText().isEmpty()
                || //                chooseGender.getValue().isEmpty() || chooseProgram.getValue().isEmpty() || 
                newNationalityText.getText().isEmpty();
    }

}
