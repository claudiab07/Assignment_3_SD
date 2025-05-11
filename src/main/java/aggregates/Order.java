package aggregates;

import java.time.Instant;

public class Order {
    public final String orderId;
    public final String userId;
    public final int quantity;
    public final double price;
    public final boolean isBuy;
    public boolean cancelled = false;
    public Instant timestamp;

    public Order(String orderId, String userId, int quantity, double price, boolean isBuy, Instant timestamp) {
        this.orderId = orderId;
        this.userId = userId;
        this.quantity = quantity;
        this.price = price;
        this.isBuy = isBuy;
        this.timestamp = timestamp;
    }
}