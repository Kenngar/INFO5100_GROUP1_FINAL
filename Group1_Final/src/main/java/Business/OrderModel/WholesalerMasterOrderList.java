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
public class WholesalerMasterOrderList {
    ArrayList<WholesalerOrder> orderList;
    

    public WholesalerMasterOrderList() {
        orderList = new ArrayList<WholesalerOrder>();
    }

    public ArrayList<WholesalerOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<WholesalerOrder> orderList) {
        this.orderList = orderList;
    }
    public void addNewOrder(WholesalerOrder order){
        this.orderList.add(order); 
    }
}
