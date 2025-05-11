package domainEvents;

public class OrderCancelled extends Event {
    public final String orderId;

    public OrderCancelled(String orderId) {
        this.orderId = orderId;
    }
}