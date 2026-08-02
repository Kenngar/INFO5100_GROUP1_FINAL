package Business;

import Business.Employee.Employee;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.OrderModel.OrderItem;
import Business.OrderModel.Product;
import Business.OrderModel.RetailerProductCatalog;
import Business.OrderModel.WholesalerOrder;
import Business.OrderModel.WholesalerProductCatalog;
import Business.Organization.Organization;
import Business.Role.ManufacturerEnterpriseAdminRole;
import Business.Role.ManufacturerOperationsManagerRole;
import Business.Role.ProductionAnalystRole;
import Business.Role.RetailerAnalyticsRole;
import Business.Role.RetailerEnterpriseAdminRole;
import Business.Role.StoreAssociateRole;
import Business.Role.StoreManagerRole;
import Business.Role.SystemAdminRole;
import Business.Role.WholesalerEnterpriseAdminRole;
import Business.Role.WholesalerMarketingRole;
import Business.Role.WholesalerPricingRole;
import Business.Role.WholesalerSalesRole;
import Business.UserAccount.UserAccount;
import com.github.javafaker.Faker;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author rrheg
 */
public class ConfigureASystem {

    static Faker faker = new Faker(new Random(12345L));

    public static EcoSystem configure() {

        EcoSystem system = EcoSystem.getInstance();
        configureUsers(system);
        // -- Network ----------------------------------------------------------
        Network network = system.createAndAddNetwork();
        network.setName("MSIS Network");
        configureUsers(system);
        // -- Enterprises ------------------------------------------------------
        Enterprise wholesalerEnterprise = network.getEnterpriseDirectory()
                .createAndAddEnterprise("Wholesaler", Enterprise.EnterpriseType.WHOLESALER);

        Enterprise manufacturerEnterprise = network.getEnterpriseDirectory()
                .createAndAddEnterprise("Manufacturer", Enterprise.EnterpriseType.MANUFACTURER);

        Enterprise retailerEnterprise = network.getEnterpriseDirectory()
                .createAndAddEnterprise("Retailer", Enterprise.EnterpriseType.RETAILER);

        // -- Organizations ----------------------------------------------------
        configureOrganizations(wholesalerEnterprise, manufacturerEnterprise, retailerEnterprise);

        // -- Product Catalog (owned by wholesaler) ------------------------------
        configureRetailerCatalog(system.getRetailerProductCatalog());
        configureWholesalerCatalog(system.getWholesalerProductCatalog());

        // -- Wholesaler orders (wholesaler -> retailer)
        configureWholesalerOrders(system.getWholesalerProductCatalog(), system);

        return system;
    }

    private static void configureOrganizations(Enterprise wholesaler,
            Enterprise manufacturer, Enterprise retailer) {

        // -----------------------------------------------------------------------
        // Wholesaler
        // -----------------------------------------------------------------------
        // 1. Wholesaler Pricing Organization
        //    -> Wholesaler Pricing Analyst
     
        Organization wholesalerAdminOrg = wholesaler.getOrganizationDirectory()
                .createOrganization(Organization.Type.Admin);
        wholesalerAdminOrg.setName("Wholesaler Admin Organization");

        Employee wholesalerAdminEmp = wholesalerAdminOrg.getEmployeeDirectory()
                .createEmployee(faker.name().fullName());
        wholesalerAdminOrg.getUserAccountDirectory().createUserAccount(
                "wholesaleradmin", "wholesaleradmin", wholesalerAdminEmp, new WholesalerEnterpriseAdminRole());

        Organization wholesalerPricingOrg = wholesaler.getOrganizationDirectory().createOrganization(Organization.Type.WholesalerPricing);
        wholesalerPricingOrg.setName("Wholesaler Pricing Organization");

        Employee wholesalerPricingEmp = wholesalerPricingOrg.getEmployeeDirectory()
                .createEmployee(faker.name().fullName());
        wholesalerPricingOrg.getUserAccountDirectory().createUserAccount(
                "wholesalerpricing", "wholesalerpricing", wholesalerPricingEmp, new WholesalerPricingRole());

        Organization wholesalerSalesOrg = wholesaler.getOrganizationDirectory()
                .createOrganization(Organization.Type.WholesalerSales);
        wholesalerSalesOrg.setName("Wholesaler Sales Organization");

        Employee wholesalerSalesEmp = wholesalerSalesOrg.getEmployeeDirectory()
                .createEmployee(faker.name().fullName());
        wholesalerSalesOrg.getUserAccountDirectory().createUserAccount(
                "wholesalersales", "wholesalersales", wholesalerSalesEmp, new WholesalerSalesRole());

        // 2. Wholesaler Marketing Organization
        //    -> Marketing Specialist
        Organization wholesalerMarketingOrg = wholesaler.getOrganizationDirectory()
                .createOrganization(Organization.Type.WholesalerMarketing);
        wholesalerMarketingOrg.setName("Wholesaler Marketing Organization");

        Employee marketingEmp = wholesalerMarketingOrg.getEmployeeDirectory()
                .createEmployee(faker.name().fullName());
        wholesalerMarketingOrg.getUserAccountDirectory().createUserAccount(
                "marketing", "marketing", marketingEmp, new WholesalerMarketingRole());

        // -----------------------------------------------------------------------
        // RETAILER (Sports Goods)
        // -----------------------------------------------------------------------
        Organization retailerAdminOrg = retailer.getOrganizationDirectory()
                .createOrganization(Organization.Type.Admin);
        retailerAdminOrg.setName("Retailer Admin Organization");

        Employee retailerAdminEmp = retailerAdminOrg.getEmployeeDirectory()
                .createEmployee(faker.name().fullName());
        retailerAdminOrg.getUserAccountDirectory().createUserAccount(
                "retaileradmin", "retaileradmin", retailerAdminEmp, new RetailerEnterpriseAdminRole());

        // 3. Retailer Analytics Organization
        //    -> Retailer Analytics
        Organization retailerAnalyticsOrg = retailer.getOrganizationDirectory()
                .createOrganization(Organization.Type.RetailerAnalytics); 
        retailerAnalyticsOrg.setName("Retailer Analytics Organization");

        Employee retailerAnalyticsEmp = retailerAnalyticsOrg.getEmployeeDirectory()
                .createEmployee(faker.name().fullName());
        retailerAnalyticsOrg.getUserAccountDirectory().createUserAccount(
                "retaileranalytics", "retaileranalytics", retailerAnalyticsEmp, new RetailerAnalyticsRole());

        // 4. Retail Store Organization - Store 1
        //    -> Store Manager + Store Associate
        Organization store1 = retailer.getOrganizationDirectory()
                .createOrganization(Organization.Type.RetailerStore);
        store1.setName("Sports - Store 1");

        Employee storeManager1Emp = store1.getEmployeeDirectory()
                .createEmployee(faker.name().fullName());
        store1.getUserAccountDirectory().createUserAccount(
                "storemanager", "storemanager", storeManager1Emp, new StoreManagerRole());

        Employee storeAssociate1Emp = store1.getEmployeeDirectory()
                .createEmployee(faker.name().fullName());
        store1.getUserAccountDirectory().createUserAccount(
                "storeassociate", "storeassociate", storeAssociate1Emp, new StoreAssociateRole());

        // 5. Retail Store Organization - Store 2
        Organization store2 = retailer.getOrganizationDirectory()
                .createOrganization(Organization.Type.RetailerStore);
        store2.setName("Sports - Store 2");

        Employee storeManager2Emp = store2.getEmployeeDirectory()
                .createEmployee(faker.name().fullName());
        store2.getUserAccountDirectory().createUserAccount(
                "storemanager2", "storemanager2", storeManager2Emp, new StoreManagerRole());

        Employee storeAssociate2Emp = store2.getEmployeeDirectory()
                .createEmployee(faker.name().fullName());
        store2.getUserAccountDirectory().createUserAccount(
                "storeassociate2", "storeassociate2", storeAssociate2Emp, new StoreAssociateRole());

        // 6. Retail Store Organization - Store 3
        Organization store3 = retailer.getOrganizationDirectory()
                .createOrganization(Organization.Type.RetailerStore);
        store3.setName("Sports - Store 3");

        Employee storeManager3Emp = store3.getEmployeeDirectory()
                .createEmployee(faker.name().fullName());
        store3.getUserAccountDirectory().createUserAccount(
                "storemanager3", "storemanager3", storeManager3Emp, new StoreManagerRole());

        Employee storeAssociate3Emp = store3.getEmployeeDirectory()
                .createEmployee(faker.name().fullName());
        store3.getUserAccountDirectory().createUserAccount(
                "storeassociate3", "storeassociate3", storeAssociate3Emp, new StoreAssociateRole());

        // -----------------------------------------------------------------------
        // MANUFACTURER (Sports Manufacturing Co.)
        // -----------------------------------------------------------------------
        Organization manuAdminOrg = manufacturer.getOrganizationDirectory()
                .createOrganization(Organization.Type.Admin);
        manuAdminOrg.setName("Manufacturer Admin Organization");

        Employee manuAdminEmp = manuAdminOrg.getEmployeeDirectory()
                .createEmployee(faker.name().fullName());
        manuAdminOrg.getUserAccountDirectory().createUserAccount(
                "manufactureradmin", "manufactureradmin", manuAdminEmp, new ManufacturerEnterpriseAdminRole());

        // 9. Manufacturer Operations Organization
        //    -> Manufacturing Operations
        Organization manuOperationsOrg = manufacturer.getOrganizationDirectory()
                .createOrganization(Organization.Type.ManufacturerOperations);
        manuOperationsOrg.setName("Manufacturer Operations Organization");

        Employee manuOperationsEmp = manuOperationsOrg.getEmployeeDirectory()
                .createEmployee(faker.name().fullName());
        manuOperationsOrg.getUserAccountDirectory().createUserAccount(
                "opsmanager", "123456", manuOperationsEmp, new ManufacturerOperationsManagerRole());

        Employee productionAnalystEmp = manuOperationsOrg.getEmployeeDirectory()
                .createEmployee(faker.name().fullName());
        manuOperationsOrg.getUserAccountDirectory().createUserAccount(
                "prodanalyst", "analyst", productionAnalystEmp, new ProductionAnalystRole());

    }

    // System admin only — lives at EcoSystem level
    private static void configureUsers(EcoSystem system) {
        Employee sysAdminEmp = system.getEmployeeDirectory().createEmployee("sysadmin");
        system.getUserAccountDirectory().createUserAccount(
                "sysadmin", "sysadmin", sysAdminEmp, new SystemAdminRole());
    }

    // Shared item data: name, price, supplier qty, retail qty
    private static final String[][] PRODUCT_DATA = {
        {"Running Shoes", "129.99", "200", "80"},
        {"Compression Shorts", "34.99", "350", "120"},
        {"Athletic Tank Top", "24.99", "500", "150"},
        {"Yoga Mat", "49.99", "150", "60"},
        {"Sports Bra", "39.99", "300", "100"},
        {"Basketball Jersey", "59.99", "180", "70"},
        {"Cycling Helmet", "89.99", "75", "30"},
        {"Weightlifting Gloves", "19.99", "220", "90"},};

    // Wholesaler catalog — higher quantities (warehouse stock)
    private static void configureWholesalerCatalog(WholesalerProductCatalog catalog) {

        for (String[] item : PRODUCT_DATA) {
            Product p = catalog.addProduct();
            p.setProdName(item[0]);
            p.setPrice(Double.parseDouble(item[1]));
            p.setAvail(Integer.parseInt(item[2])); // supplier qty (column 2)
        }
    }

    // Retailer catalog — lower quantities (store shelf stock)
    private static void configureRetailerCatalog(RetailerProductCatalog catalog) {

        for (String[] item : PRODUCT_DATA) {
            Product p = catalog.addProduct();
            p.setProdName(item[0]);
            p.setPrice(Double.parseDouble(item[1]));
            p.setAvail(Integer.parseInt(item[3])); // retail qty (column 3)
        }

        // Console verification
        System.out.println("=== Retail Catalog (Store - Shelf Stock) ===");
        System.out.printf("%-5s %-25s %-12s %s%n", "ID", "Item", "Price", "Qty");
        System.out.println("-".repeat(50));
        for (Product p : catalog.getProductcatalog()) {
            System.out.printf("%-5d %-25s $%-11.2f %d%n",
                    p.getModelNumber(),
                    p.getProdName(),
                    p.getPrice(),
                    p.getAvail());
        }
    }

    // Documents sales from wholesaler to retailer
    private static void configureWholesalerOrders(WholesalerProductCatalog wholesalerCatalog, EcoSystem system) {

        String[][] stores = {
            {"1", "Sports - Store 1"},
            {"2", "Sports - Store 2"},
            {"3", "Sports - Store 3"}
        };

        for (String[] store : stores) {
            String storeID = store[0];
            String storeName = store[1];

            WholesalerOrder order = new WholesalerOrder();

            int itemCount = faker.number().numberBetween(3, 6);
            for (int j = 0; j < itemCount; j++) {
                int randomIndex = faker.number()
                        .numberBetween(0, wholesalerCatalog.getProductcatalog().size());
                Product p = wholesalerCatalog.getProductcatalog().get(randomIndex);
                int qty = faker.number().numberBetween(5, 50);
                order.addNewOrderItem(p, p.getPrice(), qty);
            }

            // Store in EcoSystem's WholesalerMasterOrderList
            system.getWholesalerMasterOrderList().addNewOrder(order);

            // Console verification
            System.out.println("=== Wholesale Order | Store #" + storeID + " - " + storeName + " ===");
            System.out.printf("%-5s %-25s %-12s %-10s %s%n",
                    "ID", "Product", "Price", "Qty", "Revenue");
            System.out.println("-".repeat(60));
            for (OrderItem oi : order.getOrderItemList()) {
                System.out.printf("%-5d %-25s $%-11.2f %-10d $%.2f%n",
                        oi.getProduct().getModelNumber(),
                        oi.getProduct().getProdName(),
                        oi.getProduct().getPrice(),
                        oi.getQuantity(),
                        oi.getProduct().getPrice() * oi.getQuantity());
            }
            System.out.println();
        }
    }
}
