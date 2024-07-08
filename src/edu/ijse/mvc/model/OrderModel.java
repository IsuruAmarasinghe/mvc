/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ijse.mvc.model;

import edu.ijse.mvc.db.DBConnection;
import edu.ijse.mvc.dto.OrderDetailDto;
import edu.ijse.mvc.dto.OrderDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Isuru Manchanayake
 */
public class OrderModel {
    private Connection connection;
    
    public OrderModel()throws ClassNotFoundException, SQLException{
         connection = DBConnection.getInstance().getConnection();
    }
    
    public String placeOrder(OrderDto orderDto, ArrayList<OrderDetailDto>orderDetailDtos)throws Exception{
        try {
            connection.setAutoCommit(false);
            
            String orderSql = "INSERT INTO orders VALUES(?,?,?)";
            PreparedStatement orderStatement = connection.prepareStatement(orderSql);
            orderStatement.setString(1, orderDto.getOrderId());
            orderStatement.setString(2, orderDto.getDate());
            orderStatement.setString(3, orderDto.getCustId());
            
            boolean isOrderSave = orderStatement.executeUpdate() > 0;
            
            if(isOrderSave){
                
                boolean isOrderDetailSaved = true;
                
                String orderDetailSql = "INSERT INTO orderDetail VALUES(?,?,?,?)";
                for(OrderDetailDto orderDetailDto : orderDetailDtos){
                   PreparedStatement orderDetailStatment = connection.prepareStatement(orderDetailSql);
                   orderDetailStatment.setString(1, orderDto.getOrderId());
                   orderDetailStatment.setString(2, orderDetailDto.getItemCode());
                   orderDetailStatment.setInt(3, orderDetailDto.getQty());
                   orderDetailStatment.setInt(4, orderDetailDto.getDiscount());
                   
                   if(!(orderDetailStatment.executeUpdate()>0)){
                      isOrderDetailSaved = false;
                   }
                   
                }
                
                if(isOrderDetailSaved){
                  boolean isItemUpdated = true;
                  
                  String itemUpdateSql = "UPDATE item SET QtyOnHand = QtyOnHand - ? WHERE ItemCode = ?";
                  
                  for(OrderDetailDto orderDetailDto : orderDetailDtos){
                    PreparedStatement itemStatement = connection.prepareStatement(itemUpdateSql);
                    itemStatement.setInt(1, orderDetailDto.getQty());
                    itemStatement.setString(2, orderDetailDto.getItemCode());
                    
                    if(!(itemStatement.executeUpdate()>0)){
                        isItemUpdated = false;
                    }
                  }
                  
                  if(isItemUpdated){
                    connection.commit();
                    return "Success";
                  }else{
                    connection.rollback();
                    return "Item update error";
                  }
                }else{
                  connection.rollback();
                  return "Order Detail save error";
                }
                
            
            }else{
              connection.rollback();
              return "Order save error";
            }
            
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            return e.getMessage();
        }finally {
          connection.setAutoCommit(true);
        }
    }
    
}
