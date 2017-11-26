/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.sql.Date;
import java.sql.ResultSet;

/**
 *
 * @author yunyi.ZHANG
 */
public interface ReportQuery {
    
    // Given a date range, return the number of students visited,  grouped by visit reasons (for example, "fee payment", "enqueries" and etc.)
    // This is for bar chart (x axis: visit reasons; y axis: number of students visited;  filter: date range)
    public ResultSet getResultGroupByReason(Date startDate, Date endDate);

    // Given a date range, return the frequency of students visited,  grouped by gender (for example, "female", "male" and "other")
    // This is for bar chart (x axis: gender; y axis: visit frequency;  filter: date range)
    public ResultSet getResultGroupByGender(Date startDate, Date endDate);
    
    // Given a date range, return the frequency of students visited,  grouped by day
    // This is for bar chart (x axis: day; y axis: visit frequency;  filter: date range)   
    public ResultSet getResultGroupByDay(Date startDate, Date endDate);
    
    // Given a date range, return the frequency of students visited,  grouped by week
    // This is for bar chart (x axis: week; y axis: visit frequency;  filter: date range)   
    public ResultSet getResultGroupByWeek(Date startDate, Date endDate);
    
    // Given a date range, return the frequency of students visited,  grouped by month
    // This is for bar chart (x axis: month; y axis: visit frequency;  filter: date range)  
    public ResultSet getResultGroupByMonth(Date startDate, Date endDate);
    

}
