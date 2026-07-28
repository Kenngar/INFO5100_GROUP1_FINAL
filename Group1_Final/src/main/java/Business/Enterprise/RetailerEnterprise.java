/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise;

import Business.Organization.Organization;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author Kenneth Garcia
 */
public class RetailerEnterprise extends Enterprise {
    
    public RetailerEnterprise(String name){
        super(name,Enterprise.EnterpriseType.RETAILER);
    }
    @Override
    public ArrayList<Role> getSupportedRole() {
        return null;
    }
    
    public Organization.Type getType() {
    return null; // EcoSystem is not a typed organization
}
    
}
