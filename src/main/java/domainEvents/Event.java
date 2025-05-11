package domainEvents;

import java.time.Instant;

public abstract class Event {
    public final Instant timestamp = Instant.now();
}