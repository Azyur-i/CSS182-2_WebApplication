package com.student;

import com.student.SaveStudentServlet;

import com.datastax.oss.driver.api.core.CqlSession;
import java.net.InetSocketAddress;

public class CassandraConnector {

    private static CqlSession session;

    public static void connect() {
        if (session == null) {
            session = CqlSession.builder()
                    .addContactPoint(new InetSocketAddress("127.0.0.1", 9042))
                    .withLocalDatacenter("datacenter1")
                    .withKeyspace("student_ks")
                    .build();
            System.out.println("Connected to Cassandra keyspace student_ks");
        }
    }

    public static CqlSession getSession() {
        if (session == null) {
            connect();
        }
        return session;
    }

    public static void close() {
        if (session != null) {
            session.close();
            session = null;
        }
    }
}
