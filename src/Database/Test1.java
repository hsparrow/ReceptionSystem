/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

/**
 *
 * @author zoe
 */

//public Student(int andrewNumID, String andrewStringID, String Firstname, String Lastname, String gender, String nationality, String program) {

public class Test1 {
     public static void main(String[] args) throws ClassNotFoundException {
         DatabaseApi api=new DatabaseApi();
         api.getcon();
         api.refreshVisit();
         api.insertRowVisit(123, 3);
         api.insertRowVisit(123, 4);
         api.displayAllVisit();
//         api.refreshStudent();
//         api.createVisitTable();
//api.createStudentTable();
//         api.displayAllStudent();
//         api.insertRowStudent(new Student(2323131,"buzhidao","first","last","femail","china","mism",0));
//         api.updateStudent(new Student(2323131,"testround2","testupdatefunction","testupdatefunction","testupdatefunction","testupdatefunction","testupdatefunction",0));
//         api.displayAllStudent();
//         Student student=api.findStudent(1234);
//         System.out.print(student.getAndrewStringID());
     }
}
