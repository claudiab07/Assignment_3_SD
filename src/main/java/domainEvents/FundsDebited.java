package domainEvents;

public class FundsDebited extends Event {
    public final String userId;
    public final double amount;

    public FundsDebited(String userId, double amount) {
        this.userId = userId;
        this.amount = amount;
    }
}
