
package MainFrame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Database.DatabaseApi;
import Database.Student;

/**
 *
 * @author KanBaru
 */
public class EditInfoController {
DatabaseApi api=new DatabaseApi();
    @FXML
    private TextField newAndrewIdText;
    @FXML
    private TextField newFirstNameText;
    @FXML
    private TextField newLastNameText;
    @FXML
    private TextField newGenderText;
    @FXML
    private TextField newNationalityText;
    @FXML
    private TextField newProgramText;
    @FXML
    private Button closeButton;
    @FXML
    private Button confirmButton;

    

    int selectedID;

    /**
     * action for edit information button
     */
    public void editInfo() {
            //case student:

            selectedID = getId(); //等着航哥OPENCV的接口
            String newAndrewID = newAndrewIdText.getText();
            String newFirstName = newFirstNameText.getText();
            String newLastName = newLastNameText.getText();
            String newGender = newGenderText.getText();
            String newNationality = newNationalityText.getText();
            String newProgram = newProgramText.getText();
 
            api.updateStudent(new Student(selectedID, newAndrewID, newFirstName, newLastName, newGender, newNationality, newProgram, 0));
//       

//            /*
//            批量更新数据库中数据
//             */
//            String[] infos = {"andrewID", "name", "gender", "nationality", "program"};
//            String[] newInfos = {newAndrewID, newName, newGender, newNationality, newProgram};
//            //保存当前自动提交模式
//            boolean autoCommit = con.getAutoCommit();
//            //关闭自动提交
//            con.setAutoCommit(false);
//            //使用Statement同时收集多条sql语句
//
//            for (int i = 0; i < infos.length; i++) {
//                String sql = "UPDATE Student SET " + infos[i] + "=" + newInfos[i] + " WHERE ID = selectedID";
//                stmt.addBatch(sql);
//            }
//            //同时提交所有的sql语句
//            stmt.executeBatch();
//            //提交修改
//            con.commit();
//            con.setAutoCommit(autoCommit);
//            System.out.println("successfully edited student's information");
//
//        } catch (SQLException ex) {
//            while (ex != null) {
//                System.out.println("SQLState:  " + ex.getSQLState());
//                System.out.println("Error Code:" + ex.getErrorCode());
//                System.out.println("Message:   " + ex.getMessage());
//                Throwable t = ex.getCause();
//                while (t != null) {
//                    System.out.println("Cause:" + t);
//                    t = t.getCause();
//                }
//                ex = ex.getNextException();
//            }
//        }
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }

        @FXML
        private void closeWindow
        
            () {
        Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }
        /**
         * 为了不报错，等航哥OPENCV出来的接口
         */
    public int getId() {
        return 2323131;
    }
}
