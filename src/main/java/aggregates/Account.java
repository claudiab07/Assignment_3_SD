package aggregates;

import domainEvents.Event;
import domainEvents.FundsCredited;
import domainEvents.FundsDebited;

import java.util.HashMap;
import java.util.Map;

public class Account {
    private final Map<String, Double> balances = new HashMap<>();

    public void apply(Event event) {
        if (event instanceof FundsDebited f) {
            balances.put(f.userId, balances.getOrDefault(f.userId, 0.0) - f.amount);
        } else if (event instanceof FundsCredited f) {
            balances.put(f.userId, balances.getOrDefault(f.userId, 0.0) + f.amount);
        }
    }

    public double getBalance(String userId) {
        return balances.getOrDefault(userId, 0.0);
    }
}