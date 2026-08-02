package ui.RetailerAssociate;

import Business.OrderModel.Product;
import Business.OrderModel.RetailerProductCatalog;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Read-only view of the retailer's shelf-stock product catalog.
 * Rows are highlighted when the item is low on stock. Reused by the
 * Store Manager, Store Associate, and Retail Analyst work areas.
 *
 * @author Kenneth Garcia
 */
public class RetailerInventoryJPanel extends javax.swing.JPanel {

    public static final int LOW_STOCK_THRESHOLD = 40;

    private final JPanel userProcessContainer;
    private final RetailerProductCatalog catalog;

    private JTable table;
    private JTextField txtSearch;

    public RetailerInventoryJPanel(JPanel userProcessContainer, RetailerProductCatalog catalog, String title) {
        this.userProcessContainer = userProcessContainer;
        this.catalog = catalog;
        initComponents(title);
        populateTable(null);
    }

    private void initComponents(String title) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel top = new JPanel(new BorderLayout());
        JButton btnBack = new JButton("<< Back");
        btnBack.addActionListener(e -> goBack());
        JLabel lblTitle = new JLabel(title, JLabel.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(java.awt.Font.BOLD, 20f));
        top.add(btnBack, BorderLayout.WEST);
        top.add(lblTitle, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        table = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Product ID", "Name", "Price", "Available Qty"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        table.setDefaultRenderer(Object.class, new LowStockRenderer());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.add(new JLabel("Search by name:"));
        txtSearch = new JTextField(15);
        bottom.add(txtSearch);
        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> populateTable(txtSearch.getText()));
        bottom.add(btnSearch);
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            populateTable(null);
        });
        bottom.add(btnRefresh);
        JLabel lblLegend = new JLabel("   (red rows = low stock, below " + LOW_STOCK_THRESHOLD + " units)");
        lblLegend.setForeground(new Color(178, 34, 34));
        bottom.add(lblLegend);
        add(bottom, BorderLayout.SOUTH);
    }

    public void populateTable(String keyword) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        boolean filtering = keyword != null && !keyword.trim().isEmpty();
        for (Product p : catalog.getProductcatalog()) {
            if (filtering && (p.getProdName() == null
                    || !p.getProdName().toLowerCase().contains(keyword.trim().toLowerCase()))) {
                continue;
            }
            model.addRow(new Object[]{p.getModelNumber(), p.getProdName(), p.getPrice(), p.getAvail()});
        }
    }

    private void goBack() {
        if (userProcessContainer == null) {
            return;
        }
        userProcessContainer.remove(this);
        ((CardLayout) userProcessContainer.getLayout()).previous(userProcessContainer);
    }

    /**
     * Highlights an entire row in light red when the "Available Qty" column
     * (index 3) is below the low-stock threshold.
     */
    private class LowStockRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable tbl, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
            Object availObj = tbl.getModel().getValueAt(row, 3);
            boolean lowStock = availObj instanceof Integer && (Integer) availObj < LOW_STOCK_THRESHOLD;
            if (!isSelected) {
                c.setBackground(lowStock ? new Color(255, 224, 224) : Color.WHITE);
            }
            return c;
        }
    }
}
