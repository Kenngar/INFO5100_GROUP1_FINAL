package ui.RetailerManager;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.OrderModel.Product;
import Business.OrderModel.RetailerProductCatalog;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.RestockRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

/**
 * Cross-enterprise restock request: a Store Manager asks the Wholesaler
 * Sales Organization to ship more units of a product to their store.
 * The same RestockRequest object is placed in three queues (the store's
 * own queue, the wholesaler sales org's queue, and the manager's personal
 * queue) so status changes made on either side are visible everywhere.
 *
 * @author Christopher Carmant
 */
public class RestockRequestJPanel extends javax.swing.JPanel {

    private final JPanel userProcessContainer;
    private final UserAccount account;
    private final Organization storeOrganization;
    private final Enterprise enterprise;
    private final EcoSystem business;
    private final RetailerProductCatalog catalog;

    private JTable table;
    private JTextField txtProductName;
    private JSpinner spnQuantity;

    public RestockRequestJPanel(JPanel userProcessContainer, UserAccount account,
            Organization storeOrganization, Enterprise enterprise, EcoSystem business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.storeOrganization = storeOrganization;
        this.enterprise = enterprise;
        this.business = business;
        this.catalog = business.getRetailerProductCatalog();
        initComponents();
        populateTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel top = new JPanel(new BorderLayout());
        JButton btnBack = new JButton("<< Back");
        btnBack.addActionListener(e -> goBack());
        JLabel lblTitle = new JLabel("Restock Requests to Wholesaler", JLabel.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(java.awt.Font.BOLD, 18f));
        top.add(btnBack, BorderLayout.WEST);
        top.add(lblTitle, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        table = new JTable(new DefaultTableModel(
                new Object[][]{}, new String[]{"Request", "Status"}) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        });
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        south.add(new JLabel("Product name:"));
        txtProductName = new JTextField(15);
        south.add(txtProductName);
        south.add(new JLabel("Quantity:"));
        spnQuantity = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        south.add(spnQuantity);
        JButton btnSubmit = new JButton("Send Restock Request");
        btnSubmit.addActionListener(e -> submitRequest());
        south.add(btnSubmit);
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> populateTable());
        south.add(btnRefresh);
        add(south, BorderLayout.SOUTH);
    }

    private void submitRequest() {
        String productName = txtProductName.getText().trim();
        if (productName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a product name.");
            return;
        }
        Product product = catalog.searchProductByName(productName);
        if (product == null) {
            JOptionPane.showMessageDialog(this,
                    "\"" + productName + "\" was not found in the product catalog. "
                    + "Please check the spelling and try again.");
            return;
        }
        int quantity = (Integer) spnQuantity.getValue();
        if (quantity <= 0) {
            JOptionPane.showMessageDialog(this, "Quantity must be a positive number.");
            return;
        }

        Organization wholesalerSalesOrg = findWholesalerSalesOrganization();
        if (wholesalerSalesOrg == null) {
            JOptionPane.showMessageDialog(this,
                    "Could not locate the Wholesaler Sales Organization on the network.");
            return;
        }

        RestockRequest request = new RestockRequest();
        request.setStoreName(storeOrganization.getName());
        request.setProductName(product.getProdName());
        request.setQuantity(quantity);
        request.setMessage("Restock " + quantity + " x " + product.getProdName()
                + " for " + storeOrganization.getName());
        request.setSender(account);
        request.setStatus("Sent");

        // visible to: the wholesaler sales org (recipient), this store (sent history),
        // and the manager's own personal work queue.
        wholesalerSalesOrg.getWorkQueue().getWorkRequestList().add(request);
        storeOrganization.getWorkQueue().getWorkRequestList().add(request);
        account.getWorkQueue().getWorkRequestList().add(request);

        JOptionPane.showMessageDialog(this, "Restock request sent to the Wholesaler Sales Organization.");
        txtProductName.setText("");
        spnQuantity.setValue(1);
        populateTable();
    }

    private Organization findWholesalerSalesOrganization() {
        for (Network network : business.getNetworkList()) {
            for (Enterprise e : network.getEnterpriseDirectory().getEnterpriseList()) {
                if (e.getEnterpriseType() != Enterprise.EnterpriseType.WHOLESALER) {
                    continue;
                }
                for (Organization org : e.getOrganizationDirectory().getOrganizationList()) {
                    if (org.getName() != null
                            && org.getName().equalsIgnoreCase("Wholesaler Sales Organization")) {
                        return org;
                    }
                }
            }
        }
        return null;
    }

    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (WorkRequest wr : storeOrganization.getWorkQueue().getWorkRequestList()) {
            if (wr instanceof RestockRequest) {
                model.addRow(new Object[]{wr, wr.getStatus()});
            }
        }
    }

    private void goBack() {
        if (userProcessContainer == null) {
            return;
        }
        userProcessContainer.remove(this);
        ((CardLayout) userProcessContainer.getLayout()).previous(userProcessContainer);
    }
}
