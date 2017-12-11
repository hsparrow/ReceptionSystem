/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFrame;

import Database.Reportapi;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yunyi.ZHANG
 */
public class ReportController implements Initializable {
Reportapi reportapi=new Reportapi();

    @FXML
    private DatePicker dateStart_r1;

    @FXML
    private DatePicker dateEnd_r1;

    @FXML
    private PieChart pieChart_r1;

    @FXML
    private Label recordNum;

    @FXML
    private Label studentNum;

    @FXML
    private Label reasonNum;

    @FXML
    private TableView<VisitRecord> table;

    @FXML
    private TableColumn<VisitRecord, Integer> andrewNumIDCol;
    @FXML
    private TableColumn<VisitRecord, String> andrewStringIDCol;
    @FXML
    private TableColumn<VisitRecord, String> firstNameCol;
    @FXML
    private TableColumn<VisitRecord, String> lastNameCol;
    @FXML
    private TableColumn<VisitRecord, String> genderCol;
    @FXML
    private TableColumn<VisitRecord, String> nationalityCol;
    @FXML
    private TableColumn<VisitRecord, String> programCol;
    @FXML
    private TableColumn<VisitRecord, Integer> statusCol;
    @FXML
    private TableColumn<VisitRecord, String> visitTimeCol;
    @FXML
    private TableColumn<VisitRecord, String> reasonCol;

    @FXML
    private DatePicker dateStart_r2;

    @FXML
    private DatePicker dateEnd_r2;

    @FXML
    private BarChart<String, Integer> barChart1;

    @FXML
    private Button generateReport_btn_r1;

    @FXML
    private Button exportReport_btn_r1;

    @FXML
    private Button generateReport_btn_r2;

    @FXML
    private Button exportReport_btn_r2;

    @FXML
    private ChoiceBox groupBy_choiceBox;

    @FXML
    private Label nullAlert_r2;
    
    @FXML
    private ImageView backToMain;


    @FXML
    private void handleExportAction(ActionEvent event) throws IOException {
        //TODO
    }

    @FXML
    private void handleGenerateAction(ActionEvent event) throws IOException {
        String choice = "";
        if (groupBy_choiceBox.getValue() == null) {
            choice = "";
            nullAlert_r2.setText("Please choose 'Group by'");
        } else {
            choice = groupBy_choiceBox.getValue().toString();
        }
//        System.out.println(choice);
        switch (choice) {
            case "Visit Reasons":
                barChart1.getData().clear();
                drawChartGroupByReason();
                break;

            case "Gender":
                barChart1.getData().clear();
                drawChartGroupByGender();
                break;

            case "Month":
                barChart1.getData().clear();
                drawChartGroupByMonth();
                break;

            case "Day":
                barChart1.getData().clear();
                drawChartGroupByDay();
                break;

            default:
                barChart1.getData().clear();
                System.out.println("Please choose group by");
                break;
        }
    }

    public void drawChartGroupByReason() {
        System.out.println("Entered this reason method");
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        HashMap<String, String> reasonMap = new HashMap<>();
        reasonMap.put("1", "Enquiries");
        reasonMap.put("2", "Collect Mail");
        reasonMap.put("3", "Borrow Items");
        reasonMap.put("4", "Fee Payment");
        reasonMap.put("5", "Submit Assignment");
        reasonMap.put("6", "Others");

        try {
            reportapi.api.getcon();

            ResultSet rs = reportapi.getResultGroupByReason(getSDate_r2(), getEDate_r2());

            while (rs.next()) {
                System.out.println("Has next");
                System.out.println("The reason is: " + reasonMap.get(rs.getString(1)));
                System.out.println("The count is: " + rs.getInt(2));
                series.getData().add(new XYChart.Data<>(reasonMap.get(rs.getString(1)), rs.getInt(2)));
            }

            barChart1.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void drawChartGroupByGender() {
        System.out.println("draw chart group by gender");
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        try {
            reportapi.api.getcon();

            ResultSet rs = reportapi.getResultGroupByGender(getSDate_r2(), getEDate_r2());

            while (rs.next()) {
                System.out.println("The gender is: " + rs.getString(1));
                System.out.println("The count is: " + rs.getInt(2));
                series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }

            barChart1.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void drawChartGroupByMonth() {
        System.out.println("draw chart group by month");
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        try {
            reportapi.api.getcon();

            ResultSet rs = reportapi.getResultGroupByMonth(getSDate_r2(), getEDate_r2());

            while (rs.next()) {
                System.out.println("The month is: " + rs.getInt(1));
                System.out.println("The count is: " + rs.getInt(2));
                series.getData().add(new XYChart.Data<>(String.valueOf(rs.getInt(1)), rs.getInt(2)));
            }

            barChart1.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void drawChartGroupByDay() {
        System.out.println("draw chart group by day");
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        try {
            reportapi.api.getcon();

            ResultSet rs = reportapi.getResultGroupByDay(getSDate_r2(), getEDate_r2());
            System.out.println(getSDate_r2());
            System.out.println(getEDate_r2());

            while (rs.next()) {
                System.out.println("The day is: " + rs.getDate(1));
                System.out.println("The count is: " + rs.getInt(2));
                series.getData().add(new XYChart.Data<>(String.valueOf(rs.getDate(1)), rs.getInt(2)));
            }

            barChart1.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getSDate_r1() {
        if (dateStart_r1.getValue() == null) {
            return "01/01/1900";
        } else {
            int dayStart = dateStart_r1.getValue().getDayOfMonth();
            int monthStart = dateStart_r1.getValue().getMonthValue();
            int yearStart = dateStart_r1.getValue().getYear();
            String sdate = dayStart + "/" + monthStart + "/" + yearStart;
            return sdate;
        }
    }

    public String getEDate_r1() {
        if (dateEnd_r1.getValue() == null) {
            return "01/01/1900";
        } else {
            int dayEnd = dateEnd_r1.getValue().getDayOfMonth();
            int monthEnd = dateEnd_r1.getValue().getMonthValue();
            int yearEnd = dateEnd_r1.getValue().getYear();
            String edate = dayEnd + "/" + monthEnd + "/" + yearEnd;
            return edate;
        }
    }

    public String getSDate_r2() {
        if (dateStart_r2.getValue() == null) {
            return "01/01/1900";
        } else {
            int dayStart = dateStart_r2.getValue().getDayOfMonth();
            int monthStart = dateStart_r2.getValue().getMonthValue();
            int yearStart = dateStart_r2.getValue().getYear();
            String sdate = dayStart + "/" + monthStart + "/" + yearStart;
            return sdate;
        }
    }

    public String getEDate_r2() {
        if (dateEnd_r2.getValue() == null) {
            return "01/01/1900";
        } else {
            int dayEnd = dateEnd_r2.getValue().getDayOfMonth();
            int monthEnd = dateEnd_r2.getValue().getMonthValue();
            int yearEnd = dateEnd_r2.getValue().getYear();
            String edate = dayEnd + "/" + monthEnd + "/" + yearEnd;
            return edate;
        }
    }

    // Generate tableview and pie chart and 3 nums
    @FXML
    private void analyzeVisitRecord() {
        ObservableList<VisitRecord> visitRecords = FXCollections.observableArrayList();
        visitRecords = select();
        table.setItems(visitRecords);

//        PieChart pieChart = new PieChart();
        pieChart_r1.setData(getChartData());
//        Stage pieStage = new Stage();
//        pieStage.setTitle("Salary Analysis");
//
//        AnchorPane root = new AnchorPane();
//        root.getChildren().add(pieChart);
//        pieStage.setScene(new Scene(root, 500, 450));
//        pieStage.show();

        recordNum.setText(String.valueOf(countRecords()));
        studentNum.setText(String.valueOf(countStudents()));
        reasonNum.setText(String.valueOf(countReasons()));

    }

    private ObservableList<VisitRecord> select() {
        ObservableList<VisitRecord> list = FXCollections.observableArrayList();

//// get the rs of visit record in specific time range from Zoe
     reportapi.api.getcon();
ResultSet rs=reportapi.getResultDetailStudent(getSDate_r1(),getEDate_r1());
        int andrewNumID = -1;
        String andrewStringID = "";
        String Firstname = "";
        String Lastname = "";
        String gender = "";
        String nationality = "";
        String program = "";
        int status = -1;
        String visitTime;
        String visitReason = "";

        HashMap<String, String> reasonMap = new HashMap<>();
        reasonMap.put("1", "Enquiries");
        reasonMap.put("2", "Collect Mail");
        reasonMap.put("3", "Borrow Items");
        reasonMap.put("4", "Fee Payment");
        reasonMap.put("5", "Submit Assignment");
        reasonMap.put("6", "Others");

        try {
            if (rs != null) {
                while (rs.next()) {
                    andrewNumID = rs.getInt(2);
                    andrewStringID = rs.getString(3);
                    Firstname = rs.getString(4);
                    Lastname = rs.getString(5);
                    gender = rs.getString(6);
                    nationality = rs.getString(7);
                    program = rs.getString(8);
                    status = rs.getInt(9);
                    visitTime = rs.getString(10);
                    visitReason = reasonMap.get(rs.getString(11));
                    VisitRecord visitRecord = new VisitRecord(andrewNumID, andrewStringID, Firstname, Lastname, gender, nationality, program, status, visitTime, visitReason);
                    list.add(visitRecord);
                }
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return list;
    }

    /**
     * Gets chart data from database.
     *
     * @return Returns the chart data.
     */
    private ObservableList<PieChart.Data> getChartData() {
        ObservableList<PieChart.Data> answer = FXCollections.observableArrayList();

//// get the rs of visit record in specific time range from Zoe
        ResultSet rs=reportapi.getResultDetailStudent(getSDate_r1(),getEDate_r1());

        ArrayList<String> reason = new ArrayList<>();
        try {
            while (rs.next()) {
                reason.add(rs.getString(11));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int r1 = 0, r2 = 0, r3 = 0, r4 = 0, r5 = 0, r6 = 0;
        for (String d : reason) {
            if (d.equals("1")) {
                r1++;
            } else if (d.equals("2")) {
                r2++;
            } else if (d.equals("3")) {
                r3++;
            } else if (d.equals("4")) {
                r4++;
            } else if (d.equals("5")) {
                r5++;
            } else if (d.equals("6")) {
                r6++;
            }
        }

        answer.addAll(new PieChart.Data("Enqueries", r1 * 1.0  / reason.size()),
                new PieChart.Data("Collect Mail", r2 * 1.0/ reason.size()),
                new PieChart.Data("Borrow Items", r3 * 1.0 / reason.size()),
                new PieChart.Data("Fee Payment", r4  * 1.0/ reason.size()),
                new PieChart.Data("Submit Assignmet", r5 * 1.0 / reason.size()),
                new PieChart.Data("Others", r6 * 1.0 / reason.size()));

        return answer;
    }

    private int countRecords() {
         ResultSet rs=reportapi.getResultDetailStudent(getSDate_r1(),getEDate_r1());
           ArrayList<String> records = new ArrayList<>();
        try {
            while (rs.next()) {
                records.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int count = 0;
        for (String i: records){
            count++;
        }
        return count;
    }

    private int countStudents() {
         ResultSet rs=reportapi.getResultDetailStudent(getSDate_r1(),getEDate_r1());
           ArrayList<String> students = new ArrayList<>();
           
           
        try {
            while (rs.next()) {
                students.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        HashSet h = new HashSet(students);
        students.clear();
        students.addAll(h);
        
        int count = 0;
        for (String i: students){
            count++;
        }
        return count;

    }

    private int countReasons() {
                 ResultSet rs=reportapi.getResultDetailStudent(getSDate_r1(),getEDate_r1());
           ArrayList<String> reasons = new ArrayList<>();
           
           
        try {
            while (rs.next()) {
                reasons.add(rs.getString(11));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        HashSet h = new HashSet(reasons);
        reasons.clear();
        reasons.addAll(h);
        
        int count = 0;
        for (String i: reasons){
            count++;
        }
        return count;
    }
    
    @FXML
    private void backToMain(MouseEvent event)throws IOException{
     System.out.println("back to homepage");
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/MainFrame/Face.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                  app_stage.hide(); //optional
        app_stage.setScene(home_page_scene);
      app_stage.show();  
           
 
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        groupBy_choiceBox.getItems().addAll("Visit Reasons", "Gender", "Month", "Day");

        andrewNumIDCol.setCellValueFactory(new PropertyValueFactory<VisitRecord, Integer>("AndrewNumID"));
        andrewStringIDCol.setCellValueFactory(new PropertyValueFactory<VisitRecord, String>("AndrewStringID"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<VisitRecord, String>("Firstname"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<VisitRecord, String>("Lastname"));
        genderCol.setCellValueFactory(new PropertyValueFactory<VisitRecord, String>("Gender"));
        nationalityCol.setCellValueFactory(new PropertyValueFactory<VisitRecord, String>("Nationality"));
        programCol.setCellValueFactory(new PropertyValueFactory<VisitRecord, String>("Program"));
        statusCol.setCellValueFactory(new PropertyValueFactory<VisitRecord, Integer>("Status"));
        visitTimeCol.setCellValueFactory(new PropertyValueFactory<VisitRecord, String>("VisitTime"));
        reasonCol.setCellValueFactory(new PropertyValueFactory<VisitRecord, String>("VisitReason"));
    }

}
