/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ijse.mvc.model;

import edu.ijse.mvc.db.DBConnection;
import edu.ijse.mvc.dto.CustDto;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 * @author Isuru Manchanayake
 */
public class CustModel {
    private  Connection connection;
    
    public CustModel()throws ClassNotFoundException, SQLException{
         this.connection = DBConnection.getInstance().getConnection();
    }
    
     public String saveCust(CustDto custDto) throws Exception{
       String sql="INSERT INTO customer VALUES (?,?,?,?,?,?,?,?,?)";
       
       PreparedStatement statement = connection.prepareStatement(sql);
       statement.setString(1,custDto.getCustID());
       statement.setString(2,custDto.getCustTitle());
       statement.setString(3,custDto.getCustName());
       statement.setString(4,custDto.getDob());
       statement.setDouble(5,custDto.getSalary());
       statement.setString(6,custDto.getCustAddress());
       statement.setString(7,custDto.getCity());
       statement.setString(8,custDto.getProvince());
       statement.setString(9,custDto.getPostalCode());
       
       return statement.executeUpdate() >0 ? "Success" : "Fail";
    }

     public ArrayList<CustDto> getAllCustDtos()throws Exception{
       String sql = "SELECT * FROM customer";
       PreparedStatement statement = connection.prepareStatement(sql);
       ResultSet rst = statement.executeQuery();
       
       ArrayList<CustDto> custDtos = new ArrayList<>();
       
       while (rst.next()){
           CustDto dto = new CustDto(rst.getString("CustID"),rst.getString("CustTitle"),rst.getString("CustName"), rst.getString("DOB"),rst.getDouble(" salary"),
                   rst.getString("CustAddress"),rst.getString("City"), rst.getString("Province"), rst.getString("PostalCode"));
           custDtos.add(dto);
       }
       return custDtos;
     }
     
     public CustDto getCust(String custId)throws Exception{
        String sql = "SELECT * FROM customer WHERE custId=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, custId);
        ResultSet rst = statement.executeQuery();
        while(rst.next()){
         CustDto dto = new CustDto(
                 rst.getString("CustID"),
                 rst.getString("CustTitle"),
                 rst.getString("CustName"),
                 rst.getString("DOB"),
                 rst.getDouble("salary"),
                 rst.getString("CustAddress"),
                 rst.getString("City"), 
                 rst.getString("Province"),
                 rst.getString("PostalCode"));
         return dto;
     }
       return null; 
     }
}
