/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ijse.mvc.model;

import edu.ijse.mvc.db.DBConnection;
import edu.ijse.mvc.dto.ItemDto;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 * @author Isuru Manchanayake
 */
public class ItemModel {
    private  Connection connection;

    public ItemModel() throws ClassNotFoundException, SQLException {
        this.connection = DBConnection.getInstance().getConnection();
    }
    
    public String saveItem(ItemDto itemDto) throws Exception{
       String sql="INSERT INTO item VALUES (?,?,?,?,?)";
       
       PreparedStatement statement = connection.prepareStatement(sql);
       statement.setString(1,itemDto.getCode());
       statement.setString(2,itemDto.getDescription());
       statement.setString(3,itemDto.getPackSize());
       statement.setDouble(4,itemDto.getUnitPrice());
       statement.setInt(5,itemDto.getQoh());
       
       return statement.executeUpdate() >0 ? "Success" : "Fail";
    }
     public ArrayList<ItemDto> getAllItem()throws Exception{
       String sql = "SELECT * FROM item";
       PreparedStatement statement = connection.prepareStatement(sql);
       ResultSet rst = statement.executeQuery();
       
       ArrayList<ItemDto> itemDtos = new ArrayList<>();
       
       while (rst.next()){
           ItemDto dto = new ItemDto(rst.getString("ItemCode"),rst.getString("Description"),rst.getString("PackSize"),rst.getDouble("UnitPrice"),rst.getShort("QtyOnHand"));
           itemDtos.add(dto);
       }
       return itemDtos;
    }
     public ItemDto getItem(String itemCode)throws Exception{
        String sql = "SELECT * FROM item WHERE itemCode=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, itemCode);
        ResultSet rst = statement.executeQuery();
        while(rst.next()){
         ItemDto dto = new ItemDto(rst.getString("ItemCode"),rst.getString("Description"),rst.getString("PackSize"),rst.getShort("UnitPrice"),rst.getShort("QtyOnHand"));
         return dto;
     }
       return null; 
     }

    public String cancelItem(String itemCode)throws Exception {
      String sql = "DELETE FROM item WHERE itemCode = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, itemCode);
        
        return statement.executeUpdate() >0 ? "Success" : "Fail";
     }
    
    public String updateItem(ItemDto itemDto) throws Exception{
       String sql;
        sql = "UPDATE item SET Description=?, PackSize=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode=?";
       
       PreparedStatement statement = connection.prepareStatement(sql);
       
       statement.setString(1,itemDto.getDescription());
       statement.setString(2,itemDto.getPackSize());
       statement.setDouble(3,itemDto.getUnitPrice());
       statement.setInt(4,itemDto.getQoh());
       statement.setString(5,itemDto.getCode());
       
       return statement.executeUpdate() >0 ? "Success" : "Fail";
    }
}
