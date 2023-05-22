package au.chrissimon.safecontractchangesdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HealthCheckTests {
    @Value(value="${local.server.port}")
    private int port;
    
    @Test
	public void CheckHealth() {
        WebTestClient client = WebTestClient
			.bindToServer()
				.baseUrl("http://localhost:" + port)
				.build();

        ResponseSpec response = client.get().uri("/").exchange();

        response.expectStatus().isOk();
    }
}
