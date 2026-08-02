package ui.RetailerAssociate;

import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * A Store Associate flags items that are needed at their store. The
 * request goes into the store organization's own WorkQueue, where the
 * Store Manager can see and approve it (Manager -> AssociateRequestsJPanel).
 *
 * @author Kenneth Garcia
 */
public class RequestItemsJPanel extends javax.swing.JPanel {

    private final JPanel userProcessContainer;
    private final UserAccount account;
    private final Organization storeOrganization;

    private JTable table;
    private JTextField txtItem;

    public RequestItemsJPanel(JPanel userProcessContainer, UserAccount account, Organization storeOrganization) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
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
        JLabel lblTitle = new JLabel("Request Items From Manager", JLabel.CENTER);
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
        south.add(new JLabel("Item(s) needed:"));
        txtItem = new JTextField(25);
        south.add(txtItem);
        JButton btnSubmit = new JButton("Submit Request");
        btnSubmit.addActionListener(e -> submitRequest());
        south.add(btnSubmit);
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> populateTable());
        south.add(btnRefresh);
        add(south, BorderLayout.SOUTH);
    }

    private void submitRequest() {
        String needItems = txtItem.getText().trim();
        if (needItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please describe the item(s) you need.");
            return;
        }

        ItemsRequest request = new ItemsRequest();
        request.setTestResult(needItems);
        request.setMessage(needItems);
        request.setSender(account);
        request.setStatus("Sent");

        storeOrganization.getWorkQueue().getWorkRequestList().add(request);
        account.getWorkQueue().getWorkRequestList().add(request);

        JOptionPane.showMessageDialog(this, "Request sent to your Store Manager.");
        txtItem.setText("");
        populateTable();
    }

    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (WorkRequest wr : storeOrganization.getWorkQueue().getWorkRequestList()) {
            if (wr instanceof ItemsRequest) {
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
