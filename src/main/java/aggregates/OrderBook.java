package aggregates;

import domainEvents.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OrderBook {
    private final Map<String, Order> orders = new HashMap<>();

    public void apply(Event event) {
        if (event instanceof OrderPlaced o) {
            orders.put(o.orderId, new Order(o.orderId, o.userId, o.quantity, o.price, o.isBuy, o.timestamp));
        } else if (event instanceof OrderCancelled o) {
            if (orders.containsKey(o.orderId)) {
                orders.get(o.orderId).cancelled = true;
            }
        }
    }

    public Collection<Order> getActiveOrders() {
        return orders.values().stream().filter(o -> !o.cancelled).toList();
    }
}
