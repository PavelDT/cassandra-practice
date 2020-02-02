package model;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

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
                .build();

        Session session = cluster.connect(keyspace);
        return session;
    }

    public void closeSession() {
        session.close();
    }

    public void read(){
        System.out.println(session.getCluster().getMetadata().getAllHosts());
    }
}
