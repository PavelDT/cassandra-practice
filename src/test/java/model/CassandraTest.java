package model;

import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;

public class CassandraTest {

    private Cassandra cs = new Cassandra();
    @BeforeClass
    public static void prepare() throws IOException, InterruptedException, TTransportException {
        // this will start embedded cassasndra with port 9142 used for cql
        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
    }

    @AfterClass
    public static void cleanup() {
    }

    @Test
    public void testCreateSchema(){
        assertTrue(cs.createSchema());
    }
}
