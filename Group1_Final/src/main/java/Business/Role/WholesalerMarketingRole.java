/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Role;
import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.WholesalerMarketing.MarketingMenuJPanel;

/**
 *
 * @author Kenneth Garcia
 */
public class WholesalerMarketingRole extends Role {
     @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        return new MarketingMenuJPanel(userProcessContainer, account, organization, business);
    }
    @Override
    public String toString() {
        return Role.RoleType.WholesalerMarketing.getValue();
    }

}
 