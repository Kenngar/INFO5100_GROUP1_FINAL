/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

//import Business.Role.LabAssistantRole;
import Business.Role.Role;
import Business.Role.ManufacturerOperationsManagerRole;
import Business.Role.ProductionAnalystRole;
import java.util.ArrayList;

/**
 *
 * @author Kenneth Garcia
 */
public class ManufacturerOperationsOrganization extends Organization{

    public ManufacturerOperationsOrganization() {
        super(Organization.Type.ManufacturerOperations.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new ProductionAnalystRole());
        roles.add(new ManufacturerOperationsManagerRole());
        return roles;
    }
     
    public Organization.Type getType() {
    return Organization.Type.ManufacturerOperations;
    }
    
    
}
