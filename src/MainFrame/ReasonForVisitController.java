/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFrame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import Database.DatabaseApi;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author KanBaru
 */
public class ReasonForVisitController implements Initializable {

    DatabaseApi api = new DatabaseApi();
    @FXML
    private CheckBox enquiryBox;
    @FXML
    private CheckBox collectMailBox;
    @FXML
    private CheckBox borrowItemBox;
    @FXML
    private CheckBox feePaymentBox;
    @FXML
    private CheckBox submitAssignmentBox;
    @FXML
    private CheckBox otherReasonBox;
    @FXML
    private Button closeButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Label hidedLabel;
//CheckBox enquiry, CheckBox collectMail, CheckBox borrowItem, CheckBox feePayment, CheckBox submitAssignment, CheckBox otherReason
    //String reasonForVisit = "";
    int reasonForVisit;
    int currentId;
    
    public void initialize() {
     //   currentId = Controller.currentID;
        reasonForVisit = 0;
    }

    @FXML
    private void insertReason() {
        if (currentId != 0) {
            try {
                api.insertRowVisit(currentId, reasonForVisit);
                System.out.print("insert id"+currentId);
                closeWindow();
            } catch (Exception e) {
                hidedLabel.setText("add reason failed!");
            }
            
        }else{
            hidedLabel.setText("Failed! Current id is 0!");
        }
    }

    @FXML
    private void checkFull() {
        boolean empty = !(enquiryBox.isSelected() || collectMailBox.isSelected() || borrowItemBox.isSelected() || feePaymentBox.isSelected() || submitAssignmentBox.isSelected() || otherReasonBox.isSelected());
        confirmButton.setDisable(empty);
    }

    @FXML
    private void handleEquiryBox() {
        if (enquiryBox.isSelected()) {
            reasonForVisit = 1;
            collectMailBox.setSelected(false);
            borrowItemBox.setSelected(false);
            feePaymentBox.setSelected(false);
            submitAssignmentBox.setSelected(false);
            otherReasonBox.setSelected(false);
        }
    }

    @FXML
    private void handleCollectMailBox() {
        if (collectMailBox.isSelected()) {
            reasonForVisit = 2;
            enquiryBox.setSelected(false);
            borrowItemBox.setSelected(false);
            feePaymentBox.setSelected(false);
            submitAssignmentBox.setSelected(false);
            otherReasonBox.setSelected(false);
        }
    }

    @FXML
    private void handleBorrowItemBox() {
        if (borrowItemBox.isSelected()) {
            reasonForVisit = 3;
            enquiryBox.setSelected(false);
            collectMailBox.setSelected(false);
            feePaymentBox.setSelected(false);
            submitAssignmentBox.setSelected(false);
            otherReasonBox.setSelected(false);
        }
    }

    @FXML
    private void handleFeePaymentBox() {
        if (feePaymentBox.isSelected()) {
            reasonForVisit = 4;
            enquiryBox.setSelected(false);
            collectMailBox.setSelected(false);
            borrowItemBox.setSelected(false);
            submitAssignmentBox.setSelected(false);
            otherReasonBox.setSelected(false);
        }
    }

    @FXML
    private void handleSubmitAssignmentBox() {
        if (submitAssignmentBox.isSelected()) {
            reasonForVisit = 5;
            enquiryBox.setSelected(false);
            collectMailBox.setSelected(false);
            borrowItemBox.setSelected(false);
            feePaymentBox.setSelected(false);
            otherReasonBox.setSelected(false);
        }
    }

    @FXML
    private void handleOtherReasonBoxBox() {
        if (otherReasonBox.isSelected()) {
            reasonForVisit = 6;
            enquiryBox.setSelected(false);
            collectMailBox.setSelected(false);
            borrowItemBox.setSelected(false);
            feePaymentBox.setSelected(false);
            submitAssignmentBox.setSelected(false);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        confirmButton.setDisable(true);
        if(Controller.id!=0){
            currentId=Controller.id;
            System.out.print("mmmmmmp"+currentId);
        }else{
            System.out.print("mmp");
            currentId=Controller.currentID;
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Controller.status = true;
    }

//    /**
//     * 等航哥的接口
//     */
//    public int getCurrentId() {
//        return 2323131;
//    }
}
