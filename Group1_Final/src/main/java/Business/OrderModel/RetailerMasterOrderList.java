/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.OrderModel;

import java.util.ArrayList;

/**
 *
 * @author kens2
 */
public class RetailerMasterOrderList {
    ArrayList<RetailerOrder> orderList;
    

    public RetailerMasterOrderList() {
        orderList = new ArrayList<RetailerOrder>();
    }

    public ArrayList<RetailerOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<RetailerOrder> orderList) {
        this.orderList = orderList;
    }
    public void addNewOrder(RetailerOrder order){
        this.orderList.add(order); 
    }
}
