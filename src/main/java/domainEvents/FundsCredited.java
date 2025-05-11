package domainEvents;

public class FundsCredited extends Event {
    public final String userId;
    public final double amount;

    public FundsCredited(String userId, double amount) {
        this.userId = userId;
        this.amount = amount;
    }
}