/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author zoe
 */
public class Reportapi {
 public DatabaseApi api=new DatabaseApi();
    private String sdate;
    
    public ResultSet getResultGroupByReason(String sdate,String edate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


try{
    java.util.Date startDate = formatter.parse(sdate);
java.util.Date endDate = formatter.parse(edate);
 Connection connect = DriverManager.getConnection(api.con.getUrl());
PreparedStatement pstmt = connect.prepareStatement(" SELECT reason , count(visit.andrewNumID) as count "
            + " FROM visit left join student on visit.andrewNumID=student.andrewNumID "

            + " where cast(visit.visitTime as date) BETWEEN ? AND ? "+
    " group by reason");
pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
pstmt.setDate(2, new java.sql.Date(endDate.getTime()));
ResultSet rs=pstmt.executeQuery();


return(rs);
}catch(ParseException e){
        System.out.print(e.getMessage());
    }catch(SQLException e){
        System.out.print(e.getMessage());
    }

      //To change body of generated methods, choose Tools | Templates.
    return null  ;
       //To change body of generated methods, choose Tools | Templates.
    }

    
    public ResultSet getResultGroupByGender(String sdate,String edate) {
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


try{
    java.util.Date startDate = formatter.parse(sdate);
java.util.Date endDate = formatter.parse(edate);
 Connection connect = DriverManager.getConnection(api.con.getUrl());
PreparedStatement pstmt = connect.prepareStatement(" SELECT student.gender , count(visit.andrewNumID) as count "
            + " FROM visit left join student on visit.andrewNumID=student.andrewNumID "

            + " where cast(visit.visitTime as date) BETWEEN ? AND ? "+
    " group by student.gender");
pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
pstmt.setDate(2, new java.sql.Date(endDate.getTime()));
ResultSet rs=pstmt.executeQuery();


return(rs);
}catch(ParseException e){
        System.out.print(e.getMessage());
    }catch(SQLException e){
        System.out.print(e.getMessage());
    }

      //To change body of generated methods, choose Tools | Templates.
    return null  ; //To change body of generated methods, choose Tools | Templates.
    }

    
    public ResultSet getResultGroupByDay(String sdate,String edate)  {
       SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


try{
    java.util.Date startDate = formatter.parse(sdate);
java.util.Date endDate = formatter.parse(edate);
 Connection connect = DriverManager.getConnection(api.con.getUrl());
PreparedStatement pstmt = connect.prepareStatement(" SELECT cast(visit.visitTime as date), count(visit.andrewNumID) as count "
            + " FROM visit left join student on visit.andrewNumID=student.andrewNumID "

            + " where cast(visit.visitTime as date) BETWEEN ? AND ? "+
    " group by cast(visit.visitTime as date)");
pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
pstmt.setDate(2, new java.sql.Date(endDate.getTime()));
ResultSet rs=pstmt.executeQuery();

return(rs);
}catch(ParseException e){
        System.out.print(e.getMessage());
    }catch(SQLException e){
        System.out.print(e.getMessage());
    }

      //To change body of generated methods, choose Tools | Templates.
    return null  ;
    }
   

    
    public ResultSet getResultGroupByMonth(String sdate,String edate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


try{
    java.util.Date startDate = formatter.parse(sdate);
java.util.Date endDate = formatter.parse(edate);
 Connection connect = DriverManager.getConnection(api.con.getUrl());
PreparedStatement pstmt = connect.prepareStatement(" SELECT month(cast(visit.visitTime as date)) , count(visit.andrewNumID) as count "
            + " FROM visit left join student on visit.andrewNumID=student.andrewNumID "

            + " where cast(visit.visitTime as date) BETWEEN ? AND ? "+
    " group by month(cast(visit.visitTime as date))");
pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
pstmt.setDate(2, new java.sql.Date(endDate.getTime()));
ResultSet rs=pstmt.executeQuery();


return(rs);
}catch(ParseException e){
        System.out.print(e.getMessage());
    }catch(SQLException e){
        System.out.print(e.getMessage());
    }

      //To change body of generated methods, choose Tools | Templates.
    return null  ;//To change body of generated methods, choose Tools | Templates.
    }
    
        public ResultSet getResultDetailStudent(String sdate,String edate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


try{
    java.util.Date startDate = formatter.parse(sdate);
java.util.Date endDate = formatter.parse(edate);
 Connection connect = DriverManager.getConnection(api.con.getUrl());
PreparedStatement pstmt = connect.prepareStatement(" select student.*  ,visit.visitTime,visit.reason from Student right join visit on visit.andrewNumID = student.andrewNumID"

            + " where cast(visit.visitTime as date) BETWEEN ? AND ? ");
pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
pstmt.setDate(2, new java.sql.Date(endDate.getTime()));
ResultSet rs=pstmt.executeQuery();


return(rs);
}catch(ParseException e){
        System.out.print(e.getMessage());
    }catch(SQLException e){
        System.out.print(e.getMessage());
    }

      //To change body of generated methods, choose Tools | Templates.
    return null  ;//To change body of generated methods, choose Tools | Templates.
    }
    
}
