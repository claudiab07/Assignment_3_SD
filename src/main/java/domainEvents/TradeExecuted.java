package domainEvents;

public class TradeExecuted extends Event {
    public final String buyOrderId;
    public final String sellOrderId;
    public final int quantity;
    public final double price;

    public TradeExecuted(String buyOrderId, String sellOrderId, int quantity, double price) {
        this.buyOrderId = buyOrderId;
        this.sellOrderId = sellOrderId;
        this.quantity = quantity;
        this.price = price;
    }
}