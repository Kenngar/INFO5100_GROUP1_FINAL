package ui.RetailerManager;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class RestockRequestJPanel extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private EcoSystem business;
    private UserAccount userAccount;
    private Organization organization;
    private Enterprise enterprise;

    private JTable table;
    private JTextArea txtMessage;

    public RestockRequestJPanel(JPanel userProcessContainer, UserAccount account, Organization organization, EcoSystem business) {
        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;
        this.business = business;
        this.organization = organization;
        initComponents();
        populateTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Top panel with title and back button
        JPanel top = new JPanel(new BorderLayout());
        JButton btnBack = new JButton("<< Back");
        btnBack.addActionListener(e -> goBack());
        JLabel lblTitle = new JLabel("Restock Requests to Retailer Analytics", JLabel.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(java.awt.Font.BOLD, 18f));
        top.add(btnBack, BorderLayout.WEST);
        top.add(lblTitle, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        // Table with same columns as Retailer Analytics panel
        table = new JTable(new DefaultTableModel(
                new Object[][]{}, new String[]{"Message", "Status"}) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        });
        add(new JScrollPane(table), BorderLayout.CENTER);

        // South panel with input and submit button
        JPanel south = new JPanel(new BorderLayout(10, 10));
        south.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Enter Restock Request:"));
        txtMessage = new JTextArea(3, 30);
        txtMessage.setLineWrap(true);
        inputPanel.add(new JScrollPane(txtMessage));

        JButton btnSubmit = new JButton("Send Request");
        btnSubmit.addActionListener(e -> submitRequest());
        inputPanel.add(btnSubmit);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> populateTable());
        inputPanel.add(btnRefresh);

        south.add(inputPanel, BorderLayout.NORTH);
        add(south, BorderLayout.SOUTH);
    }

    private void submitRequest() {
        String message = txtMessage.getText().trim();
        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a request message.",
                    "Empty Request",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Find the Retailer Analytics Organization
        Organization retailerAnalyticsOrg = findRetailerAnalyticsOrganization();
        if (retailerAnalyticsOrg == null) {
            JOptionPane.showMessageDialog(this,
                    "Could not locate the Retailer Analytics Organization on the network.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a RestockRequest
        RestockRequest request = new RestockRequest();
        request.setMessage(message);
        request.setSender(userAccount);
        request.setStatus("Sent");
        request.setStoreName(organization.getName());
        request.setProductName(""); // Product name would be entered separately if needed
        request.setQuantity(0); // Quantity would be entered separately if needed

        // Add to Retailer Analytics Organization's work queue
        retailerAnalyticsOrg.getWorkQueue().getWorkRequestList().add(request);

        // Also add to current organization's work queue for visibility
        organization.getWorkQueue().getWorkRequestList().add(request);

        JOptionPane.showMessageDialog(this,
                "Request sent to Retailer Analytics Organization.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);

        txtMessage.setText("");
        populateTable();
    }

    private Organization findRetailerAnalyticsOrganization() {
        for (Network network : business.getNetworkList()) {
            for (Enterprise e : network.getEnterpriseDirectory().getEnterpriseList()) {
                if (e.getEnterpriseType() == Enterprise.EnterpriseType.RETAILER) {
                    for (Organization org : e.getOrganizationDirectory().getOrganizationList()) {
                        if (org.getName() != null
                                && org.getName().equalsIgnoreCase("Retailer Analytics Organization")) {
                            return org;
                        }
                    }
                }
            }
        }
        return null;
    }

    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        // Display the same data: message and status
        for (WorkRequest wr : organization.getWorkQueue().getWorkRequestList()) {
            Object[] row = new Object[2];

            if (wr instanceof RestockRequest) {
                RestockRequest rr = (RestockRequest) wr;
                // Display the message format consistent with Retailer Analytics panel
                if (rr.getProductName() != null && !rr.getProductName().isEmpty() && rr.getQuantity() > 0) {
                    row[0] = rr.getMessage() + " [" + rr.getStoreName() + "] " + rr.getQuantity() + " x " + rr.getProductName();
                } else {
                    row[0] = rr.getMessage();
                }
            } else {
                row[0] = wr.getMessage();
            }
            row[1] = wr.getStatus();
            model.addRow(row);
        }
    }

    private void goBack() {
        if (userProcessContainer == null) {
            return;
        }
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}