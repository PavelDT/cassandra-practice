package util;

import java.sql.Timestamp;

public class TimeUtil {
    public static Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
