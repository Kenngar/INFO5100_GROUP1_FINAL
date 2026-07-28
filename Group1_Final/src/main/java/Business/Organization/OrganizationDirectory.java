/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Organization.Organization.Type;
import static Business.Organization.Organization.Type.Admin;
import java.util.ArrayList;

/**
 *
 * @author raunak
 */
public class OrganizationDirectory {

    private ArrayList<Organization> organizationList;

    public OrganizationDirectory() {
        organizationList = new ArrayList<Organization>();
        
    }

    public ArrayList<Organization> getOrganizationList() {
        return organizationList;
    }

    public Organization createOrganization(Type type) {
        Organization organization = null;

        switch (type) {
            case Admin:
                organization = new AdminOrganization();
                break;
            case WholesalerPricing:
                organization = new WholesalerPricingOrganization();
                break;
            case WholesalerMarketing:
                organization = new WholesalerMarketingOrganization();
                break;
            case RetailerStore:
                organization = new RetailerStoreOrganization();
                break;
            case WholesalerSales:
                 organization = new WholesalerSalesOrganization();
                 break;
                
           case RetailerAnalytics:
                organization = new RetailerAnalyticsOrganization();
                break;
                
            case ManufacturerOperations:
                organization = new ManufacturerOperationsOrganization();
                break;
          
            default:
                break;
        }
        if (organization != null) {
            organizationList.add(organization);
        }
        return organization;
    }
}