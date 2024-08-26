import java.util.ArrayList;

public class UserAgent {
    final ArrayList<String> brauser;
    final ArrayList<String> operatingSystem;

    public ArrayList<String> getBrauser() {
        return brauser;
    }

    public ArrayList<String> getOperatingSystem() {
        return operatingSystem;
    }

    public UserAgent(LogEntry logEntry) {
        ArrayList<String> brauser = new ArrayList<>();
        ArrayList<String> operatingSystem = new ArrayList<>();
        String userAgent = logEntry.getUserAgent();

        if (userAgent.contains("Windows") || userAgent.contains("X11")) {
            operatingSystem.add("Windows");
        }
        if (userAgent.contains("Linux")) {
            operatingSystem.add("Linux");
        }
        if (userAgent.contains("Mac OS")) {
            operatingSystem.add("macOS");
        }
        if (userAgent.contains("Android")) {
            operatingSystem.add("Other");
        }

        if (userAgent.contains("Edg")) {
            brauser.add("Edge");
        }
        if (userAgent.contains("OPR") || userAgent.contains("Opera")) {
            brauser.add("Opera");
        }
        if (userAgent.contains("Firefox")) {
            brauser.add("Firefox");
        }
        if (userAgent.contains("Chrome")) {
            brauser.add("Chrome");
        }
        if (userAgent.contains("Safari")) {
            brauser.add("Other");
        }

        this.brauser = brauser;
        this.operatingSystem = operatingSystem;
    }
}
