/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.WorkQueue;

/**
 * Cross-enterprise request: a Wholesaler Pricing Analyst asks a Manufacturer
 * Production Analyst for a manufacturing quote on an item/quantity. The
 * Production Analyst fills in quotedPrice and marks it approved to respond.
 * 
 * @author anhnguyen
 */
public class QuoteRequest extends WorkRequest {

    private String itemName;
    private int quantity;
    private double quotedPrice;   // filled in by the Production Analyst
    private boolean approved = false;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getQuotedPrice() {
        return quotedPrice;
    }

    public void setQuotedPrice(double quotedPrice) {
        this.quotedPrice = quotedPrice;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

}
