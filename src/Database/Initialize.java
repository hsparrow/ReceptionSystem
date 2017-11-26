/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.util.ArrayList;

/**
 *
 * @author zoe
 */
public class Initialize {
   public static void main(String[] args) throws ClassNotFoundException {

        DatabaseApi api=new DatabaseApi();
        api.createDateBase();
        api.getcon();
//       con.createDateBase();
//       con.getCon();
//       con.createTable("CREATE TABLE Student ( primaryID INT not null primary key GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),andrewNumID INT,andrewStringID VARCHAR(50),Firstname VARCHAR(50),Lastname VARCHAR(50),gender VARCHAR(50),nationality VARCHAR(50),program VARCHAR(50))");

//       ArrayList<String[]> data =Readtxt.read("student.csv");
//       con.loadtxtStudent("student.csv");
//       con.displayStudent("select * from Student");
       

   }
}
 
