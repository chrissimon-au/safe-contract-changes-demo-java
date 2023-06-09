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
	public void AddPersonWithFullName() {
		WebTestClient client = WebTestClient
			.bindToServer()
				.baseUrl("http://localhost:" + port)
				.build();

        TestFullNameDto fullName = new TestFullNameDto(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        TestAddNameRequest nameRequest = new TestAddNameRequest(fullName);

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
        TestFullNameDto createdFullName = nameResponse.getFullName();
        assertThat(createdFullName.getFirstName()).isEqualTo(fullName.getFirstName());
        assertThat(createdFullName.getLastName()).isEqualTo(fullName.getLastName());
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
        TestFullNameDto checkFullName = nameResponse.getFullName();
        assertThat(checkFullName.getFirstName()).isEqualTo(fullName.getFirstName());
        assertThat(checkFullName.getLastName()).isEqualTo(fullName.getLastName());
    }
}
