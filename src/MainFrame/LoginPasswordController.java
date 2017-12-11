/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFrame;

import Database.Student;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Database.DatabaseApi;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.PasswordField;

/**
 * FXML Controller class
 *
 * @author yunyi.ZHANG
 */
public class LoginPasswordController implements Initializable {

    DatabaseApi api = new DatabaseApi();

    @FXML
    private Button login_btn;

    @FXML
    private Button loginFace_btn;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label invalid_label;

    String selectedUsername;
    String selectedPassword;

    /**
     * Initializes the controller class.
     */
    @FXML
    //Click "Another Person" button--> set the info to default --> 
    //msg box asking for ID --> user input ID, search database --> display info of student with this ID
    private void handleLoginAction(ActionEvent event) throws IOException {
        selectedUsername = getUsername();
        selectedPassword = getPassword();
        //api.insertLoginVisit("zoe", "1234");//测试用账户
        boolean flag = api.loginInput(selectedUsername, selectedPassword);
        
        //boolean flag = true;//test用
        if (flag) {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("/MainFrame/Face.fxml"));
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(home_page_scene);
            app_stage.show();

        } else {
            username.clear();
            password.clear();
            invalid_label.setText("Wrong username or password."); //need a loop to re-enter
        }
    }

    @FXML
    private void handleFaceLoginAction(ActionEvent event) throws IOException{
        Parent faceLogin = FXMLLoader.load(getClass().getResource("/MainFrame/LoginFace.fxml"));
        Scene face_login = new Scene(faceLogin);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide(); //optional
        app_stage.setScene(face_login);
        app_stage.show();
 }

    public String getUsername() {
        System.out.println(username.getText());
        return username.getText();
    }

    public String getPassword() {
        System.out.println(password.getText());
        return password.getText();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   

}
