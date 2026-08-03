/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Role.Role;

import Business.Role.WholesalerSalesRole;
import java.util.ArrayList;

/**
 *
 * @author Kenneth Garcia
 */
public class WholesalerSalesOrganization extends Organization{


    public WholesalerSalesOrganization() {
        super(Organization.Type.WholesalerSales.getValue());
    }


    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new WholesalerSalesRole());
        return roles;
    }

    @Override
    public String toString() {
        return getName();
    }

    public Type getType() {
        return Type.WholesalerSales;
    }
    
    
}
 