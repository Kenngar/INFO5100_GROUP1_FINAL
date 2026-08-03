/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Role.Role;
import Business.Role.RetailerAnalyticsRole;
import java.util.ArrayList;

/**
 *
 * @author Kenneth Garcia
 */
public class RetailerAnalyticsOrganization extends Organization {

    public RetailerAnalyticsOrganization() {
        super(Organization.Type.RetailerAnalytics.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new RetailerAnalyticsRole());
        return roles;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public Type getType() {
        return Organization.Type.RetailerAnalytics;
    }

}
