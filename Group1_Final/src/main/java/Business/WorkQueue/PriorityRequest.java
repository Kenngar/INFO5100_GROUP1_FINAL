/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.WorkQueue;

/**
 * Production Analyst asks the Operations Manager to set a priority level,
 * manufacturing schedule, and completion date for an item.
 *
 * @author anhnguyen
 */
public class PriorityRequest extends WorkRequest {

    private String itemName;
    private int priorityLevel;              // 1 (highest) - 5 (lowest), set by Operations Manager
    private String estimatedCompletionDate;  // set by Operations Manager
    private boolean approved = false;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getEstimatedCompletionDate() {
        return estimatedCompletionDate;
    }

    public void setEstimatedCompletionDate(String estimatedCompletionDate) {
        this.estimatedCompletionDate = estimatedCompletionDate;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

}
