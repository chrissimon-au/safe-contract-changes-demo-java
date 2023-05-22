package au.chrissimon.safecontractchangesdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.UUID;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class NameTests {

    @Value(value="${local.server.port}")
    private int port;
    
    @Test
	public void AddPerson() {
		WebTestClient client = WebTestClient
			.bindToServer()
				.baseUrl("http://localhost:" + port)
				.build();

        String name = UUID.randomUUID().toString();

        TestAddNameRequest nameRequest = new TestAddNameRequest(name);

        ResponseSpec response = client
            .post()
                .uri("/names")
                .bodyValue(nameRequest)
            .exchange();

        EntityExchangeResult<TestNameResponse> result = response
            .expectBody(TestNameResponse.class)
            .returnResult();

        TestNameResponse nameResponse = result
                .getResponseBody();

        URI newNameLocation = result
                .getResponseHeaders().getLocation();

        response.expectStatus().isCreated();
        assertThat(nameResponse.getName()).isEqualTo(name);
        assertThat(nameResponse.getId()).isNotNull();
        assertThat(nameResponse.getId()).isNotEqualTo(new UUID(0, 0));
        assertThat(newNameLocation.toString()).isEqualTo("http://localhost:" + port + "/names/" + nameResponse.getId());
	
        ResponseSpec checkResponse = client
            .get()
                .uri(newNameLocation)
            .exchange();

        checkResponse.expectStatus().isOk();

        TestNameResponse checkNameResponse = checkResponse
            .expectBody(TestNameResponse.class)
            .returnResult()
            .getResponseBody();
        
        assertThat(checkNameResponse.getId()).isEqualTo(nameResponse.getId());
        assertThat(checkNameResponse.getName()).isEqualTo(name);
    }
}
