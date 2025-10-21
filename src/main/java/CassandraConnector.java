import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

public class CassandraConnector {
    private CqlSession session;
    
    public boolean testConnection() {
        try {
            // Connect using the same settings as your cqlsh
        	session = CqlSession.builder()
        		    .addContactPoint(new java.net.InetSocketAddress("127.0.0.1", 9042))
        		    .withLocalDatacenter("datacenter1")
        		    .build();
            
            // Test query to verify connection
            ResultSet result = session.execute("SELECT release_version FROM system.local");
            Row row = result.one();
            
            if (row != null) {
                String version = row.getString("release_version");
                System.out.println("✅ Connected to Cassandra successfully!");
                System.out.println("📊 Cassandra version: " + version);
                System.out.println("🌐 Cluster: Test Cluster");
                return true;
            }
            
        } catch (Exception e) {
            System.err.println("❌ Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public void listKeyspaces() {
        try {
            System.out.println("\n📁 Available keyspaces:");
            ResultSet result = session.execute(
                "SELECT keyspace_name FROM system_schema.keyspaces"
            );
            
            for (Row row : result) {
                System.out.println("  - " + row.getString("keyspace_name"));
            }
        } catch (Exception e) {
            System.err.println("Error listing keyspaces: " + e.getMessage());
        }
    }
    
    public void close() {
        if (session != null) {
            session.close();
            System.out.println("🔌 Connection closed.");
        }
    }
    
    public static void main(String[] args) {
        CassandraConnector connector = new CassandraConnector();
        
        try {
            // Test the connection
            boolean connected = connector.testConnection();
            
            if (connected) {
                // List available keyspaces
                connector.listKeyspaces();
                
                // You can add more test operations here
                System.out.println("\n Connection test completed successfully!");
            }
            
        } finally {
            connector.close();
        }
    }
}