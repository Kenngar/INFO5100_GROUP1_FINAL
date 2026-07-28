/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;

/**
 *
 * @author raunak
 */
public abstract class Role {
    
    public enum RoleType{
        
         // ── Enterprise admins ────────────────────────────────────
        WholesalerEnterpriseAdmin("Wholesaler Enterprise Admin"),
        RetailerEnterpriseAdmin("Retailer Enterprise Admin"),
        ManufacturerEnterpriseAdmin("Manufacturer Enterprise Admin"),
        
        WholesalerPricingAnalyst("Wholesaler Pricing Analyst"),
        WholesalerSales("Wholesaler Sales"),
        WholesalerMarketing("Wholesaler Marketing"),
        
        RetailBusinessAnalyst("Retail Business Analyst"),
        RetailManager("Retail Manager"),
        RetailAssociate("Retail Associate"),
        
        ShippingPricingAnalyst("Shipping Pricing Analyst"),
        ShippingCoordinator("Shipping Coordinator"),
        
        ManufacturingPricingAnalyst("Manufacturing Pricing Analyst"),
        ManufacturingDataAnalyst("Manufacturing Data Analyst"),
        ProductionPlanner("Production Planner"),
        
        LabAssistant("Lab Assistant");
        
        private String value;
        private RoleType(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
    
    public abstract JPanel createWorkArea(JPanel userProcessContainer, 
            UserAccount account, 
            Organization organization, 
            Enterprise enterprise, 
            EcoSystem business);

    @Override
    public String toString() {
        return this.getClass().getName();
    }
    
    
}