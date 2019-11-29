package de.chainsaw.app.user.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import io.quarkus.test.junit.QuarkusTest;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

@QuarkusTest
public class UserResourceTest {

    private static EmbeddedPostgres postgres;

    @BeforeAll
    public static void initPgServer() throws IOException {
        postgres = new EmbeddedPostgres(Version.V9_6_11);
        // TODO: password hard-coded is not a good idea [2]
        postgres.start("localhost",
            5432,
            "dbtest",
            "postgres",
            "01STVLNuslHspFXWopvEK4muU011lBPRzMxJEmssf0O7kMj3Bq0wejizVoiCZlj7");
    }

    @AfterAll
    public static void closePgServer() {
        postgres.close();
    }

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/user")
          .then()
             .statusCode(200)
             .body(is("[]"));
    }

}