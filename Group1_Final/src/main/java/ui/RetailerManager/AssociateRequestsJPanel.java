package ui.RetailerManager;

import Business.Organization.Organization;
import Business.WorkQueue.ItemsRequest;
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
import javax.swing.table.DefaultTableModel;

/**
 * Store Manager reviews item-need requests submitted by associates at
 * their store (Business.WorkQueue.ItemsRequest) and can approve or
 * reject them.
 *
 * @author Christopher Carmant
 */
public class AssociateRequestsJPanel extends javax.swing.JPanel {

    private final JPanel userProcessContainer;
    private final Organization storeOrganization;

    private JTable table;

    public AssociateRequestsJPanel(JPanel userProcessContainer, Organization storeOrganization) {
        this.userProcessContainer = userProcessContainer;
        this.storeOrganization = storeOrganization;
        initComponents();
        populateTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel top = new JPanel(new BorderLayout());
        JButton btnBack = new JButton("<< Back");
        btnBack.addActionListener(e -> goBack());
        JLabel lblTitle = new JLabel("Requests From Associates", JLabel.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(java.awt.Font.BOLD, 18f));
        top.add(btnBack, BorderLayout.WEST);
        top.add(lblTitle, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        table = new JTable(new DefaultTableModel(
                new Object[][]{}, new String[]{"Request", "Sent By", "Status"}) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        });
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnApprove = new JButton("Approve Selected");
        btnApprove.addActionListener(e -> updateStatus("Approved"));
        south.add(btnApprove);
        JButton btnReject = new JButton("Reject Selected");
        btnReject.addActionListener(e -> updateStatus("Rejected"));
        south.add(btnReject);
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> populateTable());
        south.add(btnRefresh);
        add(south, BorderLayout.SOUTH);
    }

    private void updateStatus(String status) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a request.");
            return;
        }
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        ItemsRequest request = (ItemsRequest) model.getValueAt(row, 0);
        request.setStatus(status);
        request.setApproval("Approved".equals(status));
        populateTable();
    }

    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (WorkRequest wr : storeOrganization.getWorkQueue().getWorkRequestList()) {
            if (wr instanceof ItemsRequest) {
                model.addRow(new Object[]{wr, wr.getSender(), wr.getStatus()});
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
