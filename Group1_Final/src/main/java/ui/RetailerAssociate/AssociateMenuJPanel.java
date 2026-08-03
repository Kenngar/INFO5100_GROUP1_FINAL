package ui.RetailerAssociate;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import java.awt.CardLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Store Associate work area: view on-shelf inventory, ring up a sale
 * (checkout), and flag items that are needed at the store.
 *
 * @author Christopher Carmant
 */
public class AssociateMenuJPanel extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private EcoSystem business;
    private UserAccount userAccount;
    private Organization organization;
    private Enterprise enterprise;

    public AssociateMenuJPanel() {
        this(null, null, null, null, null);
    }

    public AssociateMenuJPanel(JPanel userProcessContainer,
            UserAccount account,
            Organization organization,
            Enterprise enterprise,
            EcoSystem business) {
        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;
        this.organization = organization;
        this.enterprise = enterprise;
        this.business = business;
        initComponents();
    }


    private void initComponents() {
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        setLayout(new java.awt.BorderLayout(0, 30));

        JLabel jLabel1 = new JLabel("Store Associate Menu", JLabel.CENTER);
        jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 24));
        add(jLabel1, java.awt.BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 20));

        JButton btnViewInventory = new JButton("View Inventory");
        styleButton(btnViewInventory);
        btnViewInventory.addActionListener(evt -> btnViewInventoryActionPerformed());
        buttonPanel.add(btnViewInventory);

        JButton btnProcessSale = new JButton("Process Sale (Checkout)");
        styleButton(btnProcessSale);
        btnProcessSale.addActionListener(evt -> btnProcessSaleActionPerformed());
        buttonPanel.add(btnProcessSale);

        JButton btnRequestItems = new JButton("Request Items From Manager");
        styleButton(btnRequestItems);
        btnRequestItems.addActionListener(evt -> btnRequestItemsActionPerformed());
        buttonPanel.add(btnRequestItems);

        add(buttonPanel, java.awt.BorderLayout.CENTER);
    }

    private void styleButton(JButton button) {
        button.setBackground(new java.awt.Color(102, 153, 255));
        button.setForeground(new java.awt.Color(255, 255, 255));
        button.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        button.setFont(button.getFont().deriveFont(java.awt.Font.BOLD, 14f));
    }

    private void btnViewInventoryActionPerformed() {
        if (userProcessContainer == null) {
            return;
        }
        RetailerInventoryJPanel panel = new RetailerInventoryJPanel(
                userProcessContainer, business.getRetailerProductCatalog(), "Store Inventory");
        userProcessContainer.add("RetailerInventoryJPanel", panel);
        ((CardLayout) userProcessContainer.getLayout()).next(userProcessContainer);
    }

    private void btnProcessSaleActionPerformed() {
        if (userProcessContainer == null) {
            return;
        }
        SellRetailerProductsJPanel panel = new SellRetailerProductsJPanel(userProcessContainer, business);
        userProcessContainer.add("SellRetailerProductsJPanel", panel);
        ((CardLayout) userProcessContainer.getLayout()).next(userProcessContainer);
    }

    private void btnRequestItemsActionPerformed() {
        if (userProcessContainer == null) {
            return;
        }
        RequestItemsJPanel panel = new RequestItemsJPanel(userProcessContainer, userAccount, organization);
        userProcessContainer.add("RequestItemsJPanel", panel);
        ((CardLayout) userProcessContainer.getLayout()).next(userProcessContainer);
    }
}
