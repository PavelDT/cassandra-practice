package model;

import com.datastax.driver.core.*;
import util.TimeUtil;

import java.util.Set;

public class Cassandra {

    private Session session;

    public Cassandra(){
        this.session = getSession();
    }

    private Session getSession(){

        String serverIP = "127.0.0.1";
        String keyspace = "system";

        Cluster cluster = Cluster.builder()
                .addContactPoints(serverIP)
                .withPort(9142)
                .build();

        Session session = cluster.connect(keyspace);
        return session;
    }

    public void closeSession() {
        session.close();
    }
    public boolean testConnection(){
        Set<Host> nodes = session.getCluster().getMetadata().getAllHosts();
        for(Host h: nodes){
            System.out.println("host found: " + h);
        }

        // return true if nodes are found
        return nodes.size() > 0;

//        above statement is an optimisation of the following if:
//        if(nodes.size()>0){
//            return true;
//        }
//        return false;
    }

    /**
     * Reads a cassandra table and displays results
     * WARNING: this is currently open to SQL injection
     */
    public void read(String keyspace, String table){
        // todo - protect from SQL injections
        String cqlStatement = "SELECT * FROM " + keyspace + "." + table;
        ResultSet rs = session.execute(cqlStatement);
        for (Row row : rs) {
            System.out.println(row.toString());
        }
    }

    /**
     * Inserts data to the customers keyspace and the user table
     * Timestamp of creation is automatically inserted
     * @param id - The id of the customer
     * @param name - Name of customer
     * @param address - Address of customer
     */
    public void insert(String id, String name, String address) {
        String cqlStatementString = "INSERT INTO customers.user (id, name, address, time_registered) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = session.prepare(cqlStatementString);
        session.execute(statement.bind(id, name, address, TimeUtil.getTimestamp()));
    }

    /**
     * creates cassandra keyspaces and tables AKA the schema.
     */
    public boolean createSchema(){
        // IF NOT EXISTS allows this statement to run multiple times without throwing an exception
        // this is essentially an idempotent query thanks to the 'IF NOT EXISTS'
        String keyspace = "CREATE KEYSPACE IF NOT EXISTS customers WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};";
        String table = "CREATE TABLE IF NOT EXISTS customers.user(id text, name text, address text, time_registered timestamp, PRIMARY KEY(id))";

        session.execute(keyspace);
        System.out.println("created keyspace");
        session.execute(table);
        System.out.println("created table");
        // function complete to this point without error, schema created successfully
        return true;
    }

    /**
     * Function will display information about Cassandra Internals
     * 1 - How many tables Cassandra has
     * 2 - lists all nodes in the cluster
     */
    public void getDbInfo() {
        String cqlStatement = "SELECT count(table_name) FROM system_schema.tables";
        ResultSet rs = session.execute(cqlStatement);

        Long tableCount = rs.one().getLong(0);
        System.out.println("Number of tables: " + tableCount);

        String cqlStatement2 = "SELECT cluster_name, host_id, listen_address FROM system.local";
        ResultSet rs2 = session.execute(cqlStatement2);
        for (Row row : rs2) {
            System.out.println(row.toString());
        }
    }
}
