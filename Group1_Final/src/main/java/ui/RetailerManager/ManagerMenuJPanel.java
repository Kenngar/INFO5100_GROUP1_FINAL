package ui.RetailerManager;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import ui.RetailerAssociate.RetailerInventoryJPanel;
import java.awt.CardLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Store Manager work area: view store inventory, request restocking from
 * the wholesaler (cross-enterprise), and review/approve items requested
 * by associates at their store.
 *
 * @author Kenneth Garcia
 */
public class ManagerMenuJPanel extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private EcoSystem business;
    private UserAccount userAccount;
    private Organization organization;
    private Enterprise enterprise;

    public ManagerMenuJPanel() {
        this(null, null, null, null, null);
    }

    public ManagerMenuJPanel(JPanel userProcessContainer,
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

        JLabel jLabel1 = new JLabel("Store Manager Menu", JLabel.CENTER);
        jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 24));
        add(jLabel1, java.awt.BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 20));

        JButton btnViewInventory = new JButton("View Inventory");
        styleButton(btnViewInventory);
        btnViewInventory.addActionListener(evt -> btnViewInventoryActionPerformed());
        buttonPanel.add(btnViewInventory);

        JButton btnRestockRequests = new JButton("Restock Requests (to Wholesaler)");
        styleButton(btnRestockRequests);
        btnRestockRequests.addActionListener(evt -> btnRestockRequestsActionPerformed());
        buttonPanel.add(btnRestockRequests);

        JButton btnAssociateRequests = new JButton("Requests From Associates");
        styleButton(btnAssociateRequests);
        btnAssociateRequests.addActionListener(evt -> btnAssociateRequestsActionPerformed());
        buttonPanel.add(btnAssociateRequests);

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

    private void btnRestockRequestsActionPerformed() {
        if (userProcessContainer == null) {
            return;
        }
        RestockRequestJPanel panel = new RestockRequestJPanel(
                userProcessContainer, userAccount, organization, enterprise, business);
        userProcessContainer.add("RestockRequestJPanel", panel);
        ((CardLayout) userProcessContainer.getLayout()).next(userProcessContainer);
    }

    private void btnAssociateRequestsActionPerformed() {
        if (userProcessContainer == null) {
            return;
        }
        AssociateRequestsJPanel panel = new AssociateRequestsJPanel(userProcessContainer, organization);
        userProcessContainer.add("AssociateRequestsJPanel", panel);
        ((CardLayout) userProcessContainer.getLayout()).next(userProcessContainer);
    }
}
