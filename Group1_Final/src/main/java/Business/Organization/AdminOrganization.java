/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Role.AdminRole;
import Business.Role.ManufacturerEnterpriseAdminRole;
import Business.Role.RetailerEnterpriseAdminRole;
import Business.Role.Role;
import Business.Role.WholesalerEnterpriseAdminRole;
import java.util.ArrayList;

/**
 *
 * @author raunak
 */
public class AdminOrganization extends Organization{

    public AdminOrganization() {
        super(Type.Admin.getValue());
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new WholesalerEnterpriseAdminRole());
        roles.add(new RetailerEnterpriseAdminRole());
        roles.add(new ManufacturerEnterpriseAdminRole());
        return roles;
    }

    public Organization.Type getType() {
        return Organization.Type.Admin;
    }

}
