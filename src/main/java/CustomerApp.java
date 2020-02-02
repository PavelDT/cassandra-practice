import model.Cassandra;

public class CustomerApp {
    public static void main(String[] args) {
        Cassandra cassandraModel = new Cassandra();
        cassandraModel.read();
        // ensure session gets terminated
        cassandraModel.closeSession();
    }
}
