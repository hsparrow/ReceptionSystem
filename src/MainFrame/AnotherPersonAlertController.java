/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFrame;

import Database.DatabaseApi;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import Database.Student;
import javafx.scene.image.Image;

/**
 * FXML Controller class
 *
 * @author yunyi.ZHANG
 */
public class AnotherPersonAlertController implements Initializable {

    DatabaseApi api = new DatabaseApi();

    @FXML
    private Label label_wrongDetetion;

    @FXML
    private Label label_ID;

    @FXML
    private TextField ID;

    @FXML
    private Button OK_btn;

    @FXML
    private Button cancel_btn;

    @FXML
    private Label invalid_label;

    static int selectedID = 0;

    @FXML
    //Click "Another Person" button--> set the info to default --> 
    //msg box asking for ID --> user input ID, search database --> display info of student with this ID
    private void handleButtonAction(ActionEvent event) throws IOException {

        if (isValidCredentials()) {
//
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/MainFrame/Face.fxml"));
            Parent root = loader.load();
            Controller controller = loader.getController();

            selectedID = getId();
            Student student = api.findStudent(selectedID);

            String name = student.getFirstname() + " " + student.getLastname();
            String studentID = String.valueOf(student.getAndrewNumID());
            String andrewID = student.getAndrewStringID();
            String program = student.getProgram();
            String gender = student.getGender();
            String nationality = student.getNationality();
            String lastTimeVisited = String.valueOf(student.getLasttime());
            int timesOfVisit = student.getVisittime();
            Image profile_image = Utils.getProfileImage(selectedID);
            controller.setInfo(name, studentID, andrewID, program, gender, nationality, lastTimeVisited, timesOfVisit, profile_image);

            Stage stage = (Stage) OK_btn.getScene().getWindow();
            stage.close();

            Scene home_page_scene = new Scene(root);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            app_stage.setScene(home_page_scene);
            app_stage.show();
//          app_stage.hide(); //optional

        } else {
            ID.clear();
            invalid_label.setText("This ID does not exist."); //need a loop to re-enter
        }
    }

    // Cancel Button not done
    @FXML
    private void handleCancelAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancel_btn.getScene().getWindow();
        stage.close();
    }

    private boolean isValidCredentials() {

        boolean let_in = false;

        //case student:
        selectedID = getId();

        try {
            if (!api.findStudent(selectedID).equals(null)) {
                let_in = true;
            } else {
                let_in = false;
            }
        } catch (Exception e) {
            let_in = false;
        }

//        try (Connection c = DriverManager.getConnection(url, username, password);
//                PreparedStatement pStmt1 = c.prepareStatement("SELECT ID FROM MEMBERINFO")) {
//            System.out.println("connect successfully");
//
//            ResultSet rs = pStmt1.executeQuery();
//            while (rs.next()) {
//                if (rs.getString("ID") != null && rs.getString("ID").equals(ID.getText())) {
//                    String ID = rs.getString("ID");
//                    System.out.println("ID = " + ID);
//                    let_in = true;
//                }
//            }
//            System.out.println("Opened database successfully");
//
//            rs.close();
//
//        } catch (Exception e) {
//            //    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//            e.printStackTrace();
//            System.exit(0);
//        }
        System.out.println("isValidCredentials operation done successfully");
        System.out.println(let_in);
        return let_in;

    }

    public int getId() {
        int id = -1;
        try {
            id = Integer.parseInt(ID.getText());
            System.out.println(Integer.parseInt(ID.getText()));
        } catch (NumberFormatException e) {
            ID.clear();
            invalid_label.setText("This ID does not exist.");
        }
        return id;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
