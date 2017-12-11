/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFrame;

import Database.DatabaseApi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author kk
 */
public class LoginFaceController {

    @FXML
    private ImageView loginView;
    @FXML
    private Button loginPassword;
    @FXML
    private Text t;
    @FXML
    private Button login_btn;

    private boolean cameraActive;
    private static int cameraId;
    private static boolean loginStatus;
    private ScheduledExecutorService timer;

    private VideoCapture capture;
//    private CascadeClassifier faceCascade;
    private FaceRecognition rec;
    DatabaseApi api = new DatabaseApi();
    String url = "jdbc:derby://localhost:1527/Student";
    String username = "user1";
    String password = "abcd";
    String query = "SELECT * FROM Staff";

    public void initialize() {
        capture = new VideoCapture();
        cameraActive = false;
//      faceCascade = new CascadeClassifier();
        rec = new FaceRecognition();
        cameraId = 0;
        loginStatus = false;

    }

    @FXML
    private void startDetect(MouseEvent event) {
        rec.trainModel();
        startCamera();
    }

    @FXML
    private void startCamera() {

        loginView.setPreserveRatio(true);
        if (!cameraActive) {

            capture.open(cameraId);

            if (capture.isOpened()) {
                cameraActive = true;

                Runnable frameGrabber = () -> {
                    Mat frame = grabFrame();
                    Image imageToShow = Utils.mat2Image(frame);
                    updateImageView(loginView, imageToShow);
                };

                timer = Executors.newSingleThreadScheduledExecutor();
                timer.scheduleAtFixedRate(frameGrabber, 0, 100, TimeUnit.MILLISECONDS);

            } else {
                System.err.println("Impossible to open the camera connection...");
            }
        } else {
            cameraActive = false;
            // stop timer
            stopAcquisition();
        }
    }

    /**
     * If face login failed, try to login with password
     */
    @FXML
    private void loginWithPassword(ActionEvent event) throws IOException {
        stopAcquisition();
        Parent loginPassword = FXMLLoader.load(getClass().getResource("/MainFrame/LoginPassword.fxml"));
        Scene login_password = new Scene(loginPassword);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(login_password);
        window.show();
    }

    /**
     * Grabs present frame of the ImageView.
     *
     * @return
     */
    private Mat grabFrame() {
        Mat frame = new Mat();

        if (capture.isOpened()) {
            try {
                capture.read(frame);
                if (!frame.empty()) {
                    ArrayList<Integer> id = rec.loginDetect(frame);
                    boolean login = api.loginFace(id);
                    login_btn.setDisable(false);
                    //如果成功识别
                    if (login) {                       
                        stopAcquisition();
                        //Give a login successfully message
                        t.setText("Congratulations! Admin!");
                        loginStatus=true;                      
                        
                    }
                }
                // to the main window

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.err.println("Exception during the image elaboration: " + e);
            }
        }
        return frame;
    }

    /**
     * Updates the image in the ImageView.
     *
     * @param view
     * @param image
     */
    private static void updateImageView(ImageView view, Image image) {
        Utils.onFXThread(view.imageProperty(), image);
    }

    private void stopAcquisition() {
        if (timer != null && !timer.isShutdown()) {
            try {
                timer.shutdown();
                timer.awaitTermination(30, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                System.err.println("Exception in stopping the frame capture, trying to release the camera now..." + e);
            }
        }

        if (capture.isOpened()) {
            capture.release();
        }
    }

    @FXML
    private void handleLoginAction(ActionEvent event) throws IOException{
            if(loginStatus){
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("/MainFrame/Face.fxml"));
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(home_page_scene);
            app_stage.show();
            }else{
                t.setText("Unable to Login...");
            }
 }
}
