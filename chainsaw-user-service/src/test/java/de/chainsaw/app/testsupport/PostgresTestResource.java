package de.chainsaw.app.testsupport;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Collections;
import java.util.Map;

public class PostgresTestResource implements QuarkusTestResourceLifecycleManager {

    private static final PostgreSQLContainer DATABASE = new PostgreSQLContainer<>()
            .withDatabaseName("dbtest")
            .withUsername("sa")
            .withPassword("sa")
            .withExposedPorts(5432);

    @Override
    public Map<String, String> start() {
        DATABASE.start();
        return Map.of(
                "quarkus.datasource.url", DATABASE.getJdbcUrl(),
                "quarkus.datasource.username", DATABASE.getUsername(),
                "quarkus.datasource.password", DATABASE.getPassword());
    }

    @Override
    public void stop() {
        DATABASE.stop();
    }
}
