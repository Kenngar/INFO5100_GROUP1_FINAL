/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;

import Business.UserAccount.UserAccount;

/**
 *
 * @author Kenneth Garcia
 */
public class ManufacturerQuotesRequest extends WorkRequest{
    
    private String manufacturingQuote;
    private boolean approval = false;
    private UserAccount approvedBy;
    
    

    public String getTestResult() {
        return manufacturingQuote;
    }

    public void setTestResult(String manufacturingQuote) {
        this.manufacturingQuote = manufacturingQuote;
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
        return manufacturingQuote;
    }
}
