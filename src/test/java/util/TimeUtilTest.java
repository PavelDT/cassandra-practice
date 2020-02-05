package util;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class TimeUtilTest {

    @BeforeClass
    public static void prepare() {
    }

    @AfterClass
    public static void cleanup() {
    }


    @Test
    public void testTimestamp() throws InterruptedException {
        Timestamp t = TimeUtil.getTimestamp();
        Thread.sleep(10);
        assertNotEquals(t.getTime(), System.currentTimeMillis());
    }

}