/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author zoe
 */
//public Student(int andrewNumID, String andrewStringID, String Firstname, String Lastname, String gender, String nationality, String program) {
public class Test1 {

    public static void main(String[] args) throws ClassNotFoundException, ParseException, SQLException {
        DatabaseApi api = new DatabaseApi();
//        api.refreshLogin();
//        api.refreshStudent();
//        api.refreshVisit();
//api.insertRowStudent(new Student(9999999,"buzhidao","first","last","femail","china","mism",0));
//        api.displayAllStudent();
//        api.insertLoginVisit("2323131", "2323131");
//        api.insertLoginVisit("2323141", "2323141");
//        api.insertLoginVisit("2357154", "2357154");
//        api.insertLoginVisit("2323129", "2323129");
//        api.insertLoginVisit("2319128", "2319128");

api.insertRowVisit(2323131, 1);
api.insertRowVisit(2323132, 2);
api.insertRowVisit(2357154, 3);
api.insertRowVisit(2319128, 2);
api.insertRowVisit(2323129, 5);
api.insertRowVisit(2323132, 1);

//         api.createDateBase();
//         api.createStudentTable();
//         int a=api.findFirstStudent(new ArrayList<>(Arrays.asList(1,2,3,232313)));
//         System.out.print(a);
//         api.createLoginTable();
//         api.insertLoginVisit("zoe", "1234");
//         api.displayAllLogin();
//         api.insertLoginVisit("123","adf");
//         api.displayAllLogin();
//api.displayAllStudent();

//   api.refreshLogin();
//   api.displayAllLogin();
api.displayAllStudent();
//         api.insertRowVisit(2323131, 3);
//         api.insertRowVisit(2323141, 4);
//    Student student=api.getStudentDetailed(2323131);
//       System.out.print(student.getVisittime()+"   "+student.getLasttime());
//         boolean flag=api.loginFace(new ArrayList<>(Arrays.asList(122222)));
//         boolean flag2=api.loginInput("123", "adfjj");
//         System.out.print(flag2);
//         api.getcon();
//         api.refreshVisit();
//api.insertRowVisit(2357154, 5);
//         api.displayAllVisit();
//         api.displayAllStudent();

//        Student s = api.findStudent(2357154);
//        if (s == null) {
//            System.out.println("fuck");
//        }
//        String name = s.getFirstname();
//        System.out.print(name);
//         api.refreshStudent();
//         api.createVisitTable();
//         api.createStudentTable();
//         System.out.println(api.insertRowStudent(new Student(0000010,"buzhidao","first","last","femail","china","mism",0)));
//         api.updateStudent(new Student(2323131,"testround2","testupdatefunction","testupdatefunction","testupdatefunction","testupdatefunction","testupdatefunction",0));
///         api.displayAllStudent();
//         Student student=api.findStudent(1234);
//         System.out.print(student.getAndrewStringID());
//Reportapi reportapi=new Reportapi();
//reportapi.api.getcon();
//ResultSet rs=reportapi.getResultDetailStudent("24/11/2017","30/11/2017");
//while(rs.next()){
//    System.out.println(rs.getString(1)+"  "+rs.getInt(2));
//    }
//ResultSet rs=reportapi.getResultGroupByGender("24/11/2017","29/11/2017");
//while(rs.next()){
//    System.out.println(rs.getString(1)+"  "+rs.getInt(2));
//    }
//reportapi.getResultGroupByGender("24/11/2017","26/11/2017");
//reportapi.getResultGroupByDay("24/11/2017","26/11/2017");

//reportapi.getResultGroupByReason("24/11/2017","26/11/2017");
//reportapi.getResultGroupByMonth("24/11/2017","26/11/2017");
}
}
