/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.WorkQueue;
import Business.UserAccount.UserAccount;
/**
 *
 * @author lajon
 */
public class PriceChangeRequest extends WorkRequest {
 private String priceChange;
    private boolean approval = false;
    private UserAccount approvedBy;
    
    

    public String getTestResult() {
        return priceChange;
    }

    public void setTestResult(String priceChange) {
        this.priceChange = priceChange;
    }   
    
     public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    public UserAccount getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(UserAccount approvedBy) {
        this.approvedBy = approvedBy;
    }

    @Override
    public String toString() {
        return priceChange;
    }
}
