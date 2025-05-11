package commandHandlers;

import aggregates.*;
import domainEvents.*;
import eventStore.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

public class CommandHandler {
    public final EventStore store;
    private final Account account = new Account();
    private final OrderBook orderBook = new OrderBook();

    public CommandHandler(EventStore store) {
        this.store = store;
        replay();
    }

    private void replay() {
        for (Event event : store.getAllEvents()) {
            account.apply(event);
            orderBook.apply(event);
        }
    }

    public String placeOrder(String userId, int quantity, double price, boolean isBuy) {
        double cost = quantity * price;
        if (isBuy && account.getBalance(userId) < cost) {
            throw new RuntimeException("Insufficient funds");
        }

        String orderId = UUID.randomUUID().toString();
        if (isBuy) store.append(new FundsDebited(userId, cost));
        store.append(new OrderPlaced(orderId, userId, quantity, price, isBuy));

        replay();
        return orderId;
    }

    public void cancelOrder(String orderId) {
        store.append(new OrderCancelled(orderId));
        replay();
    }

    public void creditFunds(String userId, double amount) {
        store.append(new FundsCredited(userId, amount));
        replay();
    }

    public void showState() {
        System.out.println("--- Balances: ");
        for (String userId : List.of("Claudia Brinzan", "Tudor Manciu")) {
            System.out.println("--- " + userId + ": $" + String.format("%.2f", account.getBalance(userId)));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("--- Active Orders:");
        for (Order o : orderBook.getActiveOrders()) {
            LocalDateTime localTime = LocalDateTime.ofInstant(o.timestamp, ZoneId.systemDefault());
            String formattedTime = localTime.format(formatter);
            System.out.println("--- " + o.orderId + " containing: " + o.quantity + " piece/s of $" + o.price
                    + " (" + (o.isBuy ? "Buy" : "Sell") + ")"
                    + " placed at " + formattedTime); }
    }
}
