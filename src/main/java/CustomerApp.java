import model.Cassandra;

public class CustomerApp {
    public static void main(String[] args) {
        Cassandra cassandraModel = new Cassandra();
        // idempotent - safe to run multiple times
        cassandraModel.createSchema();
        cassandraModel.read("system", "local");
        // ensure session gets terminated
        cassandraModel.closeSession();
    }
}
