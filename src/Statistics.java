import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Statistics {
    long totalTraffic;
    LocalDateTime minTime = LocalDateTime.now();
    LocalDateTime maxTime = LocalDateTime.of
            (1900, 12, 31, 00, 00);
    int logTimeInHours;
    HashSet<String> existingPagesSet = new HashSet<>();
    HashSet<String> notExistingPagesSet = new HashSet<>();
    HashMap<String, Integer> oSStatistics = new HashMap<>();
    HashMap<String, Double> oSShares = new HashMap<>();
    HashMap<String, Integer> brausersStatistics = new HashMap<>();
    HashMap<String, Double> brausersShares = new HashMap<>();
    int numberOfVisits;
    int numberOfUnqueVisits;
    int numberOfErrorResponses;
    ArrayList<String> ipAddresses = new ArrayList<>();

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

        logTimeInHours = (int) Duration.between(minTime, maxTime).toSeconds() / 3600;

        if (logEntry.responseCode == 200) {
            existingPagesSet.add(logEntry.path);
        }

        if (logEntry.responseCode == 404) {
            notExistingPagesSet.add(logEntry.path);
        }

        if (logEntry.responseCode / 100 == 4 || logEntry.responseCode / 100 == 5) {
            numberOfErrorResponses++;
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

        if (logEntry.userAgent.contains("Edg")) {
            brausersStatistics.putIfAbsent("Edge", 1);
            brausersStatistics.merge("Edge", 1, (a, b) -> a + b);
        }

        if (logEntry.userAgent.contains("OPR") || logEntry.userAgent.contains("Opera")) {
            brausersStatistics.putIfAbsent("Opera", 1);
            brausersStatistics.merge("Opera", 1, (a, b) -> a + b);
        }

        if (logEntry.userAgent.contains("Firefox")) {
            brausersStatistics.putIfAbsent("Firefox", 1);
            brausersStatistics.merge("Firefox", 1, (a, b) -> a + b);
        }

        if (logEntry.userAgent.contains("Chrome")) {
            brausersStatistics.putIfAbsent("Chrome", 1);
            brausersStatistics.merge("Chrome", 1, (a, b) -> a + b);
        }

        if (logEntry.userAgent.contains("Safari")) {
            brausersStatistics.putIfAbsent("Other", 1);
            brausersStatistics.merge("Other", 1, (a, b) -> a + b);
        }

        if (!logEntry.isBot()) {
            numberOfVisits++;
        }

        if (!logEntry.isBot() && !ipAddresses.contains(logEntry.ipAddr)) {
            ipAddresses.add(logEntry.ipAddr);
            numberOfUnqueVisits++;
        }
    }

    public long getTrafficRate() {
        return totalTraffic / logTimeInHours;
    }

    public HashSet<String> getExistingPagesSet() {
        return existingPagesSet;
    }

    public HashSet<String> getNotExistingPagesSet() {
        return notExistingPagesSet;
    }

    public HashMap<String, Integer> getOSStatistics() {
        return oSStatistics;
    }

    public HashMap<String, Integer> getBrausersStatistics() {
        return brausersStatistics;
    }

    public HashMap<String, Double> getOSShares() {
        double sumOfOSShares = oSStatistics.get("Windows") + oSStatistics.get("Linux") +
                oSStatistics.get("Mac OS") + oSStatistics.get("Other");

        oSShares.put("Windows", oSStatistics.get("Windows") / sumOfOSShares);
        oSShares.put("Linux", oSStatistics.get("Linux") / sumOfOSShares);
        oSShares.put("Mac OS", oSStatistics.get("Mac OS") / sumOfOSShares);
        oSShares.put("Other", oSStatistics.get("Other") / sumOfOSShares);

        return oSShares;
    }

    public HashMap<String, Double> getBrausersShares() {
        double sumOfBrausersShares = brausersStatistics.get("Edge") + brausersStatistics.get("Opera") +
                brausersStatistics.get("Firefox") + brausersStatistics.get("Chrome") +
                brausersStatistics.get("Other");

        brausersShares.put("Edge", brausersStatistics.get("Edge") / sumOfBrausersShares);
        brausersShares.put("Opera", brausersStatistics.get("Opera") / sumOfBrausersShares);
        brausersShares.put("Firefox", brausersStatistics.get("Firefox") / sumOfBrausersShares);
        brausersShares.put("Chrome", brausersStatistics.get("Chrome") / sumOfBrausersShares);
        brausersShares.put("Other", brausersStatistics.get("Other") / sumOfBrausersShares);

        return brausersShares;
    }

    public int getNumberOfVisits() {
        return numberOfVisits / logTimeInHours;
    }

    public int getAvgNumberOfUnqueVisits() {
        return numberOfVisits / numberOfUnqueVisits;
    }

    public int getNumberOfErrorResponses() {
        return numberOfErrorResponses / logTimeInHours;
    }
}
