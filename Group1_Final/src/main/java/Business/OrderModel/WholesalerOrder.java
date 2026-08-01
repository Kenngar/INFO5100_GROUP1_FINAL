/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.OrderModel;

import java.util.ArrayList;

/**
 *
 * @author Kenneth Garcia
 */
// Wholesaler to retailer
public class WholesalerOrder {
    ArrayList<OrderItem> orderItemList;

    public WholesalerOrder() {
        this.orderItemList = new ArrayList<OrderItem>();
    }

    public ArrayList<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(ArrayList<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
    
    public void addNewOrderItem(Product product, double price, int quantity){
        OrderItem orderItem = new OrderItem(product,price,quantity);
        orderItemList.add(orderItem);
    
    }
    public OrderItem findProduct(Product product){
        for (OrderItem oi : this.getOrderItemList()){
            if(oi.getProduct().equals(product)){
                return oi;
            }
        }
        return null;
    }

    public void deleteItem(OrderItem item) {
        this.orderItemList.remove(item);
    }
           
}
 