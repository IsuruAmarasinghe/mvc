/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ijse.mvc.controller;

import edu.ijse.mvc.model.CustModel;
import edu.ijse.mvc.dto.CustDto;
import java.util.ArrayList;

/**
 *
 * @author Isuru Manchanayake
 */
public class CustController {

    /**
     *
     * @param custId
     * @return
     */
   
    private  CustModel custModel;
    
    public CustController() throws Exception{
      this.custModel=new CustModel();
    }
    
    public String saveCust(CustDto custDto)throws Exception{
        String resp = custModel.saveCust(custDto);
        return resp;
        
    }

    public ArrayList<CustDto> getAllCustDtos()throws Exception{
       ArrayList<CustDto>custDtos = custModel.getAllCustDtos();
       return custDtos;
       
    }
    
    public CustDto searchCustomer(String custId)throws Exception{
      CustDto custDto = custModel.getCust(custId);
      return custDto;
    }

    

    
    
}
