import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogEntry {
    final String ipAddr;
    final LocalDateTime time;
    final HttpMethod method;
    final String path;
    final int responseCode;
    final int responseSize;
    final String referer;
    final String userAgent;

    public LogEntry(String line) {
        String[] parts1 = line.split(" ");

        String[] parts2 = line.split("\\[");
        String[] partOfParts2 = parts2[1].split("]");

        String[] parts3 = line.split("\"");
        String[] partOfParts3 = parts3[1].split(" ");

        String dateTime = partOfParts2[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLL/yyyy:HH:mm:ss XXXX", Locale.ENGLISH);

        this.ipAddr = parts1[0];
        this.time = LocalDateTime.parse(dateTime, formatter);
        this.method = HttpMethod.valueOf(partOfParts3[0]);
        this.path = parts1[6];
        this.responseCode = Integer.parseInt(parts1[8]);
        this.responseSize = Integer.parseInt(parts1[9]);
        this.referer = parts3[3];
        this.userAgent = parts3[5];
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
