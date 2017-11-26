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
import javafx.scene.control.CheckBox;

/**
 * FXML Controller class
 *
 * @author KanBaru
 */
public class ReasonForVisitController implements Initializable {

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
//CheckBox enquiry, CheckBox collectMail, CheckBox borrowItem, CheckBox feePayment, CheckBox submitAssignment, CheckBox otherReason
    String reasonForVisit = "";

    @FXML
    private void handleEquiryBox() {
        if (enquiryBox.isSelected()) {
            reasonForVisit = "Enquiry";
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
            reasonForVisit = "Collect Mail";
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
            reasonForVisit = "Borrow Item";
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
            reasonForVisit = "Fee Payment";
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
            reasonForVisit = "Submit Assignment";
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
            reasonForVisit = "Other Reason";
            enquiryBox.setSelected(false);
            collectMailBox.setSelected(false);
            borrowItemBox.setSelected(false);
            feePaymentBox.setSelected(false);
            submitAssignmentBox.setSelected(false);
        }
    }

//    private void chooseReason() {
//        String reasonForVisit = "";
//
//        if (enquiryBox.isSelected()) {
//            reasonForVisit += "Enquiry";
//        }
//        if (borrowItemBox.isSelected()) {
//            reasonForVisit += "Borrow Item";
//        }
//        if (feePaymentBox.isSelected()) {
//            reasonForVisit += "Fee Payment";
//        }
//        if (submitAssignmentBox.isSelected()) {
//            reasonForVisit += "Submit Assignment";
//        }
//        if (otherReasonBox.isSelected()) {
//            reasonForVisit += "Other Reason";
//        }
//
//        // insert reason for visit
//        System.out.println(reasonForVisit);
//    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
