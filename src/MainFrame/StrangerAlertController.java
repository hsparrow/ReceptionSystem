/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFrame;

import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yunyi.ZHANG
 */
public class StrangerAlertController implements Initializable {

    @FXML
    private Label Stranger_label;

    @FXML
    private Button Yes_btn;

    @FXML
    private Button No_btn;

    @FXML
    private void handleButtonActionYes(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Face.fxml"));
        Parent root = loader.load();
        //set the information to default
        Controller controller = loader.getController();
        
        // Stranger ID?
        Image stranger =  Utils.getProfileImage(0);
        controller.setInfo("Stranger", "N/A","N/A", "N/A", "N/A", "N/A","N/A", -1,stranger);

        Scene home_page_scene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide(); //optional
        app_stage.setScene(home_page_scene);
        app_stage.show();
        
            

    }




    @FXML
    private void handleButtonActionNo(ActionEvent event) throws IOException {
        System.out.println("Not a stranger");
//        Parent home_page_parent = FXMLLoader.load(getClass().getResource("Face.fxml"));
//        Scene home_page_scene = new Scene(home_page_parent);
//        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        app_stage.hide(); //optional
//        app_stage.setScene(home_page_scene);
//        app_stage.show();
        
                Stage stage = (Stage) No_btn.getScene().getWindow();
            stage.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
