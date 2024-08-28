import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

public class Statistics {
    long totalTraffic;
    LocalDateTime minTime = LocalDateTime.now();
    LocalDateTime maxTime = LocalDateTime.of
            (1900, 12, 31, 00, 00);
    HashSet<String> existingPagesSet = new HashSet<>();
    HashMap<String, Integer> oSStatistics = new HashMap<>();
    HashMap<String, Double> oSShares = new HashMap<>();

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

        if (logEntry.responseCode == 200) {
            existingPagesSet.add(logEntry.path);
        }

        if (logEntry.userAgent.contains("Windows") || logEntry.userAgent.contains("X11")) {
            oSStatistics.putIfAbsent("Windows", 1);
            oSStatistics.merge("Windows", 1, (x, y) -> x + y);
        }

        if (logEntry.userAgent.contains("Linux")) {
            oSStatistics.putIfAbsent("Linux", 1);
            oSStatistics.merge("Linux", 1, (x, y) -> x + y);
        }

        if (logEntry.userAgent.contains("Mac OS")) {
            oSStatistics.putIfAbsent("Mac OS", 1);
            oSStatistics.merge("Mac OS", 1, (x, y) -> x + y);
        }

        if (logEntry.userAgent.contains("Android")) {
            oSStatistics.putIfAbsent("Other", 1);
            oSStatistics.merge("Other", 1, (x, y) -> x + y);
        }
    }

    public long getTrafficRate() {
        long period = Duration.between(minTime, maxTime).toSeconds() / 3600;
        return totalTraffic / period;
    }

    public HashSet<String> getExistingPagesSet() {
        return existingPagesSet;
    }

    public HashMap<String, Integer> getOSStatistics() {
        return oSStatistics;
    }

    public HashMap<String, Double> getOSShares() {
        double sum = oSStatistics.get("Windows") + oSStatistics.get("Linux") +
                oSStatistics.get("Mac OS") + oSStatistics.get("Other");

        oSShares.put("Windows", oSStatistics.get("Windows") / sum);
        oSShares.put("Linux", oSStatistics.get("Linux") / sum);
        oSShares.put("Mac OS", oSStatistics.get("Mac OS") / sum);
        oSShares.put("Other", oSStatistics.get("Other") / sum);

        return oSShares;
    }
}
