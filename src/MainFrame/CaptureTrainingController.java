/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFrame;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author kk
 */
public class CaptureTrainingController implements Initializable {

    private static final int NUM_OF_TRAINING_IMAGES = 50;
//    Controller controller = new Controller();
    FaceRecognition rec = Controller.getRecognizer();

    @FXML
    private Label statusLabel;
    @FXML
    private Button startTrainButton;
    @FXML
    private ImageView capture;

    static int captureCounter;

    @FXML
    private void startCaptureTraining() throws IOException {
        statusLabel.setText("Capturing Image");

        VideoCapture cap = Controller.getCapture();
        Mat face = captureTraining(cap, captureCounter);
        Image currentCapture = Utils.mat2Image(face);
        Utils.updateImageView(capture, currentCapture);
        if(capture != null) {
            captureCounter++;
        }
        
        if(captureCounter == 11) {
            showCompleteAlert();
            captureCounter = 0;
        }

//        for (int i = 1; i <= NUM_OF_TRAINING_IMAGES; i++) {
//            try {
//                Thread.sleep(100);
//            } catch(InterruptedException e) {
//                System.out.println("number of training photo: " + i);
//            }
//            
//            cap = Controller.getCapture();
//            rec.captureTraining(cap, i);
//        }
//        showCompleteAlert();
        
    }


    // when capture, only one person is allowed in front of the camera
    public Mat captureTraining(VideoCapture cap, int counter) {
        System.out.println("training grab:" + counter);
        Mat frame = new Mat();
        Mat face = new Mat();
        
        if (cap.isOpened()) {
            try {
                cap.read(frame);
                if (!frame.empty()) {
                    HashMap<Integer, Mat> map = rec.detectAndRecognize(frame);
                    Map.Entry<Integer, Mat> entry = map.entrySet().iterator().next();
                    face = entry.getValue();
                    if (face == null) {
                        captureTraining(cap, counter);
                    }
                    System.out.println("grab not empty");
                    Image img = Utils.mat2Image(face);
                    Utils.updateImageView(capture, img);
                    saveTrainingImage(face, AddNewMemberController.currentID, counter);
                    System.out.println("The id of the face: " + AddNewMemberController.currentID);
                }
            } catch (Exception e) {
                System.err.println("Exception during the image elaboration: " + e);
            }
        }
        return face;
    }

    public void saveTrainingImage(Mat image, int ID, int imageCounter) {
        String tmpPath = "";
        String imageName = "";
        tmpPath = "src/Resources/TrainingImage/" + Integer.toString(ID);
        imageName = Integer.toString(imageCounter);

        Utils.export(tmpPath, image, imageName);
        System.out.println("save training ok");
    }

    private void showCompleteAlert() throws IOException {
        Parent complete_parent = FXMLLoader.load(getClass().getResource("/MainFrame/TrainingComplete.fxml"));
        Scene complete_scene = new Scene(complete_parent);
        Stage window = new Stage();
        window.setScene(complete_scene);
        window.showAndWait();
        Stage stage = (Stage) startTrainButton.getScene().getWindow();
        stage.close();
        
        rec.trainModel();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        captureCounter = 1;
    }

}
