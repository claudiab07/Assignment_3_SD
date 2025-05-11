package domainEvents;

public class OrderPlaced extends Event {
    public final String orderId;
    public final String userId;
    public final int quantity;
    public final double price;
    public final boolean isBuy;

    public OrderPlaced(String orderId, String userId, int quantity, double price, boolean isBuy) {
        this.orderId = orderId;
        this.userId = userId;
        this.quantity = quantity;
        this.price = price;
        this.isBuy = isBuy;
    }
}
