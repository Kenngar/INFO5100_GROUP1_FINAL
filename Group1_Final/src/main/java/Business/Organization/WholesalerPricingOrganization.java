/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

//import Business.Role.LabAssistantRole;
import Business.Role.Role;
import Business.Role.WholesalerPricingRole;
import java.util.ArrayList;

/**
 *
 * @author Kenneth Garcia
 */
public class WholesalerPricingOrganization extends Organization {

    public WholesalerPricingOrganization() {
        super(Organization.Type.WholesalerPricing.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new WholesalerPricingRole());
        return roles;
    }

    public Organization.Type getType() {
        return Organization.Type.WholesalerPricing;
    }

}
