package id.jason.rplproject;
public class InventoryModel {
    private String name, qty, status;
    public InventoryModel(String name, String qty, String status) {
        this.name = name; this.qty = qty; this.status = status;
    }
    public String getName() { return name; }
    public String getQty() { return qty; }
    public String getStatus() { return status; }
}
