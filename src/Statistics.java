import java.time.Duration;
import java.time.LocalDateTime;

public class Statistics {
    long totalTraffic;
    LocalDateTime minTime = LocalDateTime.now();
    LocalDateTime maxTime = LocalDateTime.of
            (1900, 12, 31, 00, 00);

    public Statistics() {
    }

    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getResponseSize();

        if (logEntry.time.isBefore(minTime)) {
            minTime = logEntry.time;
        }
        if (logEntry.time.isAfter(maxTime)) {
            maxTime = logEntry.time;
        }
    }

    public long getTrafficRate() {
        long period = Duration.between(minTime, maxTime).toSeconds() / 3600;
        return totalTraffic / period;
    }
}
