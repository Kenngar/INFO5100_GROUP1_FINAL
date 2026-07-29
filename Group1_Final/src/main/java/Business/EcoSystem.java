/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Business.Network.Network;
import Business.OrderModel.RetailerMasterOrderList;
import Business.OrderModel.RetailerProductCatalog;
import Business.OrderModel.WholesalerMasterOrderList;
import Business.OrderModel.WholesalerProductCatalog;
import Business.Organization.Organization;
import Business.Role.Role;
import Business.Role.SystemAdminRole;
import java.util.ArrayList;

/**
 *
 * @author MyPC1
 */
public class EcoSystem extends Organization {

    private static EcoSystem business;
    private ArrayList<Network> networkList;
    WholesalerMasterOrderList wholesalerMasterOrderList;
    RetailerMasterOrderList retailerMasterOrderList;
    RetailerProductCatalog retailerProductCatalog;
    WholesalerProductCatalog wholesalerProductCatalog;

    public static EcoSystem getInstance() {
        if (business == null) {
            business = new EcoSystem();
        }
        return business;
    }

    public Network createAndAddNetwork() {
        Network network = new Network();
        networkList.add(network);
        return network;
    }
    
    public void deleteNetwork(Network network) {
        networkList.remove(network);
    }


    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roleList = new ArrayList<Role>();
        roleList.add(new SystemAdminRole());
        return roleList;
    }

    private EcoSystem() {
        super(null);
        networkList = new ArrayList<Network>();
        wholesalerMasterOrderList = new WholesalerMasterOrderList();
        retailerMasterOrderList = new RetailerMasterOrderList();
        retailerProductCatalog = new RetailerProductCatalog();
        wholesalerProductCatalog = new WholesalerProductCatalog();

    }

    public ArrayList<Network> getNetworkList() {
        return networkList;
    }

    public void setNetworkList(ArrayList<Network> networkList) {
        this.networkList = networkList;
    }

    public boolean checkIfUserIsUnique(String userName) {
        if (!this.getUserAccountDirectory().checkIfUsernameIsUnique(userName)) {
            return false;
        }
        for (Network network : networkList) {

        }
        return true;
    }

    public static EcoSystem getBusiness() {
        return business;
    }

    public WholesalerMasterOrderList getWholesalerMasterOrderList() {
        return wholesalerMasterOrderList;
    }

    public RetailerMasterOrderList getRetailerMasterOrderList() {
        return retailerMasterOrderList;
    }

    public RetailerProductCatalog getRetailerProductCatalog() {
        return retailerProductCatalog;
    }

    public WholesalerProductCatalog getWholesalerProductCatalog() {
        return wholesalerProductCatalog;
    }

    public void setWholesalerMasterOrderList(WholesalerMasterOrderList wholesalerMasterOrderList) {
        this.wholesalerMasterOrderList = wholesalerMasterOrderList;
    }

    public void setRetailerMasterOrderList(RetailerMasterOrderList retailMasterOrderList) {
        this.retailerMasterOrderList = retailerMasterOrderList;
    }

    public void setRetailerProductCatalog(RetailerProductCatalog retailerProductCatalog) {
        this.retailerProductCatalog = retailerProductCatalog;
    }

    public void setSupplierProductCatalog(WholesalerProductCatalog wholesalerProductCatalog) {
        this.wholesalerProductCatalog = wholesalerProductCatalog;
    }

    @Override
    public Organization.Type getType() {
        return null; // EcoSystem is not a typed organization
    }
}
