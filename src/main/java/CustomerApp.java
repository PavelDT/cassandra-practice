import model.Cassandra;

import java.util.UUID;

public class CustomerApp {
    public static void main(String[] args) {
        Cassandra cassandraModel = new Cassandra();
        // idempotent - safe to run multiple times
        cassandraModel.createSchema();
        // test an insert
        cassandraModel.insert(UUID.randomUUID().toString(), "Elliot", "the moon");
        // verify insert
        cassandraModel.read(" customers", "user");
        // ensure session gets terminated
        cassandraModel.closeSession();
    }
}
