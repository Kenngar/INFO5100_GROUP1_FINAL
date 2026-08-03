package Business.WorkQueue;

/**
 * Cross-enterprise request: a Retailer Store Manager asks the Wholesaler
 * Sales Organization to ship more units of a product to their store.
 *
 * Lifecycle: created with status "Sent" by the Store Manager, then the
 * Wholesaler Sales side can mark it "Approved" or "Rejected" (status is
 * inherited from WorkRequest so any org holding a reference to this same
 * object sees status updates immediately).
 *
 * @author Kenneth Garcia
 */
public class RestockRequest extends WorkRequest {

    private String storeName;
    private String productName;
    private int quantity;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
    return getMessage() != null ? getMessage() : "";
    }
}
