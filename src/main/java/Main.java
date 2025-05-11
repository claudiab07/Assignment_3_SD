import commandHandlers.CommandHandler;
import eventStore.EventStore;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        EventStore store = new EventStore();
        CommandHandler handler = new CommandHandler(store);

        String[] users = {"Claudia Brinzan", "Tudor Manciu"};
        List<String> activeOrderIds = new ArrayList<>();

        handler.creditFunds("Claudia Brinzan", 1000);
        handler.creditFunds("Tudor Manciu", 800);
        Thread.sleep(500);

        for (int i = 0; i < 20; i++) {
            String user = users[new Random().nextInt(users.length)];
            int action = new Random().nextInt(4);

            switch (action) {
                case 0 -> {
                    double amount = Double.parseDouble(String.format("%.2f", ThreadLocalRandom.current().nextDouble(100, 500)));
                    handler.creditFunds(user, amount);
                    System.out.printf("Credited %.2f to User %s%n", amount, user);
                }
                case 1 -> {
                    boolean isBuy = new Random().nextBoolean();
                    int quantity = ThreadLocalRandom.current().nextInt(1, 3);
                    double price = Double.parseDouble(String.format("%.2f", ThreadLocalRandom.current().nextDouble(10, 50)));
                    String orderId = handler.placeOrder(user, quantity, price, isBuy);
                    if (orderId != null) {
                        activeOrderIds.add(orderId);
                    }
                    System.out.printf("User %s placed %s order of %d piece/s with price %.2f%n", user, isBuy ? "BUY" : "SELL", quantity, price);
                }
                case 2 -> {
                    if (!activeOrderIds.isEmpty()) {
                        String orderId = activeOrderIds.remove(0);
                        handler.cancelOrder(orderId);
                        System.out.printf("User %s cancelled order %s%n", user, orderId);
                    }
                }
                case 3 -> {
                    System.out.printf("Showing state for User %s: ----------------------------------------\n", user);
                    handler.showState();
                    System.out.printf("----------------------------------------\n\n");
                }
            }

            Thread.sleep(700);
        }

        System.out.println("\n\n\nFinal system state:");
        handler.showState();
    }
}

