package MainFrame;

import Database.DatabaseApi;
import Database.Student;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * con
 *
 * @author KanBaru
 */
public class Controller {

    @FXML
    private Button camera_btn;
    @FXML
    private ImageView currentFrame;

    @FXML
    private Text memberName;

    @FXML
    private Text andrewID;

    @FXML
    private Text studentID;

    @FXML
    private Text program;

    @FXML
    private Text gender;

    @FXML
    private Text nationality;

    @FXML
    private ImageView profile_image;

    @FXML
    private Text lastTimeVisited;

    @FXML
    private Text timesOfVisit;

    @FXML
    private TextField anotherPersonID;

    @FXML
    private Label label_ID;
    @FXML
    private Label label_wrongDetetion;
    @FXML
    private Label invalid_label;
    @FXML
    private Button cancel_btn;
    @FXML
    private Button ConfirmAnother_btn;

    private boolean cameraActive;
    private static int cameraId;
    private ScheduledExecutorService timer;

    private static VideoCapture capture;
    private static FaceRecognition rec;

    private static int counter;
    /**
     * Current recognized andrewNumID.
     */
    static int recID;
    /**
     * Current andrewNumID for setting the detail info.
     */
    static int currentID;
    // Changes the controller detail info status, true for keep change the detail info, false for retain the current info. 
    static boolean status;

    DatabaseApi api = new DatabaseApi();

// the id get from textfield in anotherperson page
    private int anotherPersonPageID;

    public void initialize() {
        capture = new VideoCapture();
        cameraActive = false;
        rec = new FaceRecognition();
//        camera_btn.setDisable(false);
        cameraId = 0;
        counter = 0;
        recID = 0;
        currentID = 0;
        status = true;
    }

    /**
     * 修改信息时的弹窗
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void showEditInfoAlert(ActionEvent event) throws IOException {

        Parent edit_parent = FXMLLoader.load(getClass().getResource("/MainFrame/EditInformation.fxml"));
        status = false;
        Scene edit_scene = new Scene(edit_parent);
        Stage window = new Stage();
        window.setScene(edit_scene);
        window.showAndWait();

    }

    /**
     * Add new member
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void showAddNewMemberAlert(ActionEvent event) throws IOException {
        //stopAcquisition();
        status = false;
        Parent add_parent = FXMLLoader.load(getClass().getResource("/MainFrame/AddNewMember.fxml"));
        Scene add_scene = new Scene(add_parent);
        Stage window = new Stage();
        window.setScene(add_scene);
        window.showAndWait();
    }

    /**
     * 点击reason按钮弹出窗口
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void showReasonAlert(ActionEvent event) throws IOException {
        Parent reason_parent = FXMLLoader.load(getClass().getResource("/MainFrame/ReasonForVisit.fxml"));
        status = false;
        Scene reason_scene = new Scene(reason_parent);
        Stage window = new Stage();
        window.setScene(reason_scene);
        window.showAndWait();
    }

    /**
     * call security
     *
     * @param e
     * @throws IOException
     */
    @FXML
    public void callSecurity(ActionEvent e) throws IOException {
        Parent call = FXMLLoader.load(getClass().getResource("/MainFrame/CallSecurity.fxml"));
        Scene call_scene = new Scene(call);
        Stage call_window = new Stage();
        call_window.setScene(call_scene);
        call_window.show();
    }

    @FXML
    protected void startCamera(ActionEvent event) {
        // preserve image ratio
        currentFrame.setPreserveRatio(true);

        if (!cameraActive) {

            capture.open(cameraId);

            if (capture.isOpened()) {
                cameraActive = true;

                rec.trainModel();

                Runnable frameGrabber = () -> {
                    // effectively grab and process a single frame
                    Mat frame = grabFrame();
                    Image imageToShow = Utils.mat2Image(frame);
                    Utils.updateImageView(currentFrame, imageToShow);

//                    recID = rec.getTmpLabelList().get(0);
                    recID = api.findFirstStudent(rec.getTmpLabelList());
                    int count = 0;
                    if (recID == 0) {
                        count++;
                        if (count > 10) {
                            count = 0;
                            //弹出个alert
                            //if 点了yes
                            // 弹出添加新人
                            // if no
                            // 关闭这个页面继续识别
                        }
                    } else {
                        if (status) {
                            currentID = recID;
                            System.out.println("currentID" + currentID);

                            Platform.runLater(() -> {
                                if (currentID > 0) {
                                    System.out.println("the id is " + currentID);
                                    setDetailInfo(currentID);
                                    setProfileImage(currentID);
                                }
                            });
                        }

                    }

//                    Platform.runLater(() -> {
//                        if(currentID != 0) {
//                            setProfileImage(currentID);
//                        }
//                    });
                };

                timer = Executors.newSingleThreadScheduledExecutor();
                timer.scheduleAtFixedRate(frameGrabber, 0, 30, TimeUnit.MILLISECONDS);

                camera_btn.setText("Stop Camera");
            } else {
                System.err.println("Impossible to open the camera connection...");
            }
        } else {
            cameraActive = false;
            camera_btn.setText("Start Camera");

            // stop timer
            stopAcquisition();
        }
    }

    /**
     * Gets present detected face image.
     *
     * @return Returns present image of this current frame.
     */
    public Image getPresentImage() {

        Mat frame;
        frame = grabFrame();

        Image presentImage = Utils.mat2Image(frame);

        return presentImage;
    }

    /**
     * Grabs present frame of the ImageView.
     *
     * @return
     */
    private Mat grabFrame() {
        Mat frame = new Mat();
        ArrayList<Integer> id = new ArrayList<>();

        if (capture.isOpened()) {
            try {
                // read current frame
                capture.read(frame);

                if (!frame.empty()) {
//                    System.out.println("start");
//                    frame = rec.detectAndRecognize(frame);
                    HashMap<Integer, Mat> map = rec.detectAndRecognize(frame);
                    Map.Entry<Integer, Mat> entry = map.entrySet().iterator().next();
                    int recognizedID = entry.getKey();
                    System.out.println("recognized ID: " + recognizedID);
                    if (recognizedID != -1) {
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                System.out.print("chuck");
//                                setDetailInfo(recognizedID);
//                                setProfileImage(recognizedID);
//                            }
//                        });
                        setDetailInfo(recognizedID);
                        setProfileImage(recognizedID);
                        currentID = recognizedID;
                    }

//                    System.out.println("idddd" + recID);
//                    setDetailInfo(recID);
//                    if(counter < 5) {
//                        rec.detect(frame);
//                        System.out.println("detect");
//                        counter++;
//                    } else{
//                        detectAndRecognize(frame);
//                        frame = rec.detectAndRecognize(frame);
//                        id = rec.getTmpLabelList();
//                        System.out.println(id.get(0));
                    // search id.get(0) info in database then set text
                    // setDetailInfo();
//                        counter = 0;
//                    }
                }
            } catch (Exception e) {
                System.err.println("Exception during the image elaboration: " + e);
            }
        }
        return frame;
    }

//    public void setInfo(String name, String studentID, String andrewID, String program, String gender, String nationality) {
//        this.memberName.setText(name);
//        this.studentID.setText(studentID);
//        this.andrewID.setText(andrewID);
//        this.program.setText(program);
//        this.gender.setText(gender);
//        this.nationality.setText(nationality);
////        this.profile_image.setImage(profile);
//    }
    private void setProfileImage(int id) {
        Image profile = Utils.getProfileImage(id);
        this.profile_image.setImage(profile);
    }

    private void setDetailInfo(int id) {
        Student recStudent = new Student();
        String name = "";
        // 如果id不存在或有错应该返回一个null
        recStudent = api.getStudentDetailed(id);

        if (recStudent != null) {
            name = recStudent.getFirstname().concat(" ").concat(recStudent.getLastname());
            System.out.println(name);
            System.out.println(this.memberName.getText());
            this.memberName.setText(name);
            this.studentID.setText(Integer.toString(recStudent.getAndrewNumID()));
            this.andrewID.setText(recStudent.getAndrewStringID());
            this.program.setText(recStudent.getProgram());
            this.gender.setText(recStudent.getGender());
            this.nationality.setText(recStudent.getNationality());
            this.lastTimeVisited.setText(String.valueOf(recStudent.getLasttime()));
            this.timesOfVisit.setText(Integer.toString(recStudent.getVisittime()));
        } else {
            this.memberName.setText("Stranger");
            this.studentID.setText("NULL");
            this.andrewID.setText("NULL");
            this.program.setText("NULL");
            this.gender.setText("Unknown");
            this.nationality.setText("NULL");
            this.lastTimeVisited.setText("NULL");
            this.timesOfVisit.setText("NULL");
        }
    }

    public void stopAcquisition() {
        if (timer != null && !timer.isShutdown()) {
            try {
                // Shut down the timer.
                timer.shutdown();
                timer.awaitTermination(30, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                System.err.println("Exception in stopping the frame capture, trying to release the camera now..." + e);
            }
        }

        if (capture.isOpened()) {
            // Close the camera.
            capture.release();
        }
    }

    public static VideoCapture getCapture() {
        return capture;
    }

    public static FaceRecognition getRecognizer() {
        return rec;
    }

    void setClosed() {
        stopAcquisition();
    }

    static int id;

    @FXML
    private void handleAnotherPersonAction(ActionEvent event) throws IOException {
//        Parent anotherP_parent = FXMLLoader.load(getClass().getResource("/MainFrame/AnotherPersonAlert.fxml"));
//        Scene edit_scene = new Scene(anotherP_parent);
//        Stage window = new Stage();
//        window.setScene(edit_scene);
//        window.showAndWait();
        status = false;
        TextInputDialog dialog = new TextInputDialog("enter ID");
        dialog.setTitle("Another Person");
        dialog.setHeaderText("Please enter ID to find the right person");
        dialog.setContentText("Member ID");
        // 传统的获取输入值的方法
        Optional result = dialog.showAndWait();
        if (result.isPresent()) {
            id = 0;
            try {
                id = Integer.parseInt((String) result.get());
                System.out.println("输入了" + id);

            } catch (Exception e) {
                System.out.println("Please enter 7-digit number");
                id = -1;
            }
            setDetailInfo(id);
            setProfileImage(id);
            System.out.println("Info updated");

        }

// 
//        int anotherID = getAnotherId();
//        if (isValidCredentials(anotherID)) {
//            status = false;
//            anotherPersonPageID = anotherID;
//            this.memberName.setText("yunyitest");
//            setDetailInfo(anotherID);
//            setProfileImage(anotherID);
//            status = true;
//        } else {
//            anotherPersonID.clear();
//            invalid_label.setText("this ID doesn't exist");
////            anotherPersonPageID = -2;
//        }
    }

//    @FXML
//    private void handleConfirmButtonAction(ActionEvent event) throws IOException {
//        Stage stage = (Stage) ConfirmAnother_btn.getScene().getWindow();
//        stage.close();
//        int anotherID = getAnotherId();
//        if (isValidCredentials(anotherID)) {
//            status = false;
//            setAnotherPersonPageID(anotherID);
//            this.memberName.setText("yunyitest");
//            setDetailInfo(anotherID);
//            setProfileImage(anotherID);
//            status = true;
////         完啦以后一定要点
//        } else {
//            anotherPersonID.clear();
//            invalid_label.setText("this ID doesn't exist");
//            anotherPersonPageID = -2;
//        }
//    }
    private boolean isValidCredentials(int id) {

        boolean let_in = false;

        try {
            if (!(api.findStudent(id) == null)) {
                let_in = true;
            } else {
                let_in = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            let_in = false;
        }
        return let_in;
    }

    private int getAnotherId() {
        int id = -1;
        try {
            id = Integer.parseInt(anotherPersonID.getText());
            System.out.println(id);
        } catch (NumberFormatException e) {
            anotherPersonID.clear();
            invalid_label.setText("This ID does not exist.");
        }
        return id;
    }

    public void setInfo(String name, String studentID1, String andrewID1, String program1, String gender1, String nationality1, String lastTimeVisited, int visitTimes, Image image) {
        this.memberName.setText(name);
        this.andrewID.setText(andrewID1);
        this.studentID.setText(studentID1);
        this.program.setText(program1);
        this.gender.setText(gender1);
        this.nationality.setText(nationality1);
        this.lastTimeVisited.setText(lastTimeVisited);
        this.timesOfVisit.setText(String.valueOf(visitTimes));
        System.out.println("hi");
        this.profile_image.setImage(image);
    }

    @FXML
    private void handleStrangerAction(ActionEvent event) throws IOException {
        status = false;
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Stranger Alert");
        alert.setHeaderText("Is this a stranger?");
        alert.setContentText("Click OK to confirm stranger; Click Cancel to exit this dialog.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            id = -100;
            System.out.println("stranger id = "+ id);

        } else {
            id = currentID;
            System.out.println("THE CURRENT STATIC ID IS: " + id);
            alert.close();
        }
        setDetailInfo(id);
        setProfileImage(id);
        System.out.println("Info updated");

    }

    @FXML
    private void handleReportAction(MouseEvent event) throws IOException {

        Parent view_page_parent = FXMLLoader.load(getClass().getResource("/MainFrame/Report.fxml"));
        Scene view_page_scene = new Scene(view_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide(); //optional
        app_stage.setScene(view_page_scene);
        app_stage.show();

    }

    @FXML
    private void handleAnotherCancelAction(ActionEvent event) {
    }

//    @FXML
//    private void handleConfirmButtonAction(ActionEvent event) {
//    }
    public int getAnotherPersonPageID() {
        return anotherPersonPageID;
    }

    public void setAnotherPersonPageID(int anotherPersonPageID) {
        this.anotherPersonPageID = anotherPersonPageID;
    }
}
