package ui.RetailerAssociate;

import Business.EcoSystem;
import Business.OrderModel.OrderItem;
import Business.OrderModel.Product;
import Business.OrderModel.RetailerMasterOrderList;
import Business.OrderModel.RetailerOrder;
import Business.OrderModel.RetailerProductCatalog;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
 * In-store point-of-sale checkout for a Store Associate: add products from
 * the retailer's shelf-stock catalog to a cart, then check out. Checking
 * out records a RetailerOrder in the EcoSystem-wide RetailerMasterOrderList
 * (this is what feeds the Retail Analyst's sales report) and decrements
 * on-shelf availability.
 *
 * @author Kenneth Garcia
 */
public class SellRetailerProductsJPanel extends javax.swing.JPanel {

    private final JPanel userProcessContainer;
    private final RetailerMasterOrderList masterOrderList;
    private final RetailerProductCatalog productCatalog;
    private RetailerOrder currentOrder;

    private JTable tblProductCatalog;
    private JTable tblCart;
    private JTextField txtSearch;
    private JSpinner spnQuantity;

    public SellRetailerProductsJPanel(JPanel userProcessContainer, EcoSystem business) {
        this.userProcessContainer = userProcessContainer;
        this.masterOrderList = business.getRetailerMasterOrderList();
        this.productCatalog = business.getRetailerProductCatalog();
        this.currentOrder = new RetailerOrder();
        initComponents();
        populateProductTable(null);
        populateCartTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel top = new JPanel(new BorderLayout());
        JButton btnBack = new JButton("<< Back");
        btnBack.addActionListener(e -> goBack());
        JLabel lblTitle = new JLabel("Process Sale", JLabel.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(java.awt.Font.BOLD, 20f));
        top.add(btnBack, BorderLayout.WEST);
        top.add(lblTitle, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(2, 1, 0, 10));

        // -- product catalog + search/add controls --------------------------
        JPanel catalogPanel = new JPanel(new BorderLayout(5, 5));
        catalogPanel.add(new JLabel("Product Catalog:"), BorderLayout.NORTH);
        tblProductCatalog = new JTable(new DefaultTableModel(
                new Object[][]{}, new String[]{"Name", "Product Id", "Price", "Avail"}) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        });
        catalogPanel.add(new JScrollPane(tblProductCatalog), BorderLayout.CENTER);

        JPanel catalogControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        catalogControls.add(new JLabel("Search:"));
        txtSearch = new JTextField(12);
        catalogControls.add(txtSearch);
        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> populateProductTable(txtSearch.getText()));
        catalogControls.add(btnSearch);
        catalogControls.add(new JLabel("Quantity:"));
        spnQuantity = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        catalogControls.add(spnQuantity);
        JButton btnAddToCart = new JButton("Add to Cart");
        btnAddToCart.addActionListener(e -> addToCart());
        catalogControls.add(btnAddToCart);
        catalogPanel.add(catalogControls, BorderLayout.SOUTH);

        // -- cart -------------------------------------------------------------
        JPanel cartPanel = new JPanel(new BorderLayout(5, 5));
        cartPanel.add(new JLabel("Items in cart:"), BorderLayout.NORTH);
        tblCart = new JTable(new DefaultTableModel(
                new Object[][]{}, new String[]{"Item Name", "Price", "Quantity", "Total"}) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        });
        cartPanel.add(new JScrollPane(tblCart), BorderLayout.CENTER);

        JPanel cartControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRemove = new JButton("Remove Selected");
        btnRemove.addActionListener(e -> removeFromCart());
        cartControls.add(btnRemove);
        cartPanel.add(cartControls, BorderLayout.SOUTH);

        center.add(catalogPanel);
        center.add(cartPanel);
        add(center, BorderLayout.CENTER);

        JButton btnCheckOut = new JButton("Check Out");
        btnCheckOut.setFont(btnCheckOut.getFont().deriveFont(java.awt.Font.BOLD, 16f));
        btnCheckOut.addActionListener(e -> checkOut());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        south.add(btnCheckOut);
        add(south, BorderLayout.SOUTH);
    }

    private void addToCart() {
        int row = tblProductCatalog.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a product.");
            return;
        }
        String name = (String) tblProductCatalog.getValueAt(row, 0);
        Product product = productCatalog.searchProductByName(name);
        if (product == null) {
            JOptionPane.showMessageDialog(this, "Product not found.");
            return;
        }
        int quantity = (Integer) spnQuantity.getValue();

        OrderItem existing = currentOrder.findProduct(product);
        int alreadyInCart = existing == null ? 0 : existing.getQuantity();
        int requestedTotal = alreadyInCart + quantity;

        if (product.getAvail() < quantity) {
            JOptionPane.showMessageDialog(this, "Not enough stock available.");
            return;
        }

        if (existing == null) {
            currentOrder.addNewOrderItem(product, product.getPrice(), quantity);
        } else {
            existing.setQuantity(requestedTotal);
        }
        product.setAvail(product.getAvail() - quantity);

        populateProductTable(txtSearch.getText());
        populateCartTable();
    }

    private void removeFromCart() {
        int row = tblCart.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a cart item.");
            return;
        }
        String name = (String) tblCart.getValueAt(row, 0);
        Product product = productCatalog.searchProductByName(name);
        OrderItem item = product == null ? null : currentOrder.findProduct(product);
        if (item == null) {
            return;
        }
        product.setAvail(product.getAvail() + item.getQuantity());
        currentOrder.deleteItem(item);
        populateProductTable(txtSearch.getText());
        populateCartTable();
    }

    private void checkOut() {
        if (currentOrder.getOrderItemList().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty.");
            return;
        }
        masterOrderList.addNewOrder(currentOrder);
        currentOrder = new RetailerOrder();
        populateProductTable(null);
        populateCartTable();
        txtSearch.setText("");
        JOptionPane.showMessageDialog(this, "Sale completed. Thank you!");
    }

    private void populateProductTable(String keyword) {
        DefaultTableModel model = (DefaultTableModel) tblProductCatalog.getModel();
        model.setRowCount(0);
        boolean filtering = keyword != null && !keyword.trim().isEmpty();
        for (Product p : productCatalog.getProductcatalog()) {
            if (filtering && (p.getProdName() == null
                    || !p.getProdName().toLowerCase().contains(keyword.trim().toLowerCase()))) {
                continue;
            }
            model.addRow(new Object[]{p.getProdName(), p.getModelNumber(), p.getPrice(), p.getAvail()});
        }
    }

    private void populateCartTable() {
        DefaultTableModel model = (DefaultTableModel) tblCart.getModel();
        model.setRowCount(0);
        for (OrderItem oi : currentOrder.getOrderItemList()) {
            model.addRow(new Object[]{
                oi.getProduct().getProdName(),
                oi.getProduct().getPrice(),
                oi.getQuantity(),
                oi.getProduct().getPrice() * oi.getQuantity()
            });
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
