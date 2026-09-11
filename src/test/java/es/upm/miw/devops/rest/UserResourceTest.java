package es.upm.miw.devops.functionaltests;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.rest.UserResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class UserResourceFT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testReadUserById() {
        webTestClient.get()
                .uri(UserResource.USERS + "/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(User.class)
                .value(user -> assertThat(user)
                        .isNotNull()
                        .hasFieldOrPropertyWithValue("id", "1")
                        .hasFieldOrPropertyWithValue("firstName", "Oscar")
                        .hasFieldOrPropertyWithValue("familyName", "Fernandez"));
    }

    @Test
    void testReadUserByIdNotFound() {
        webTestClient.get()
                .uri(UserResource.USERS + "/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteUserById() {
        webTestClient.get()
                .uri(UserResource.USERS + "/1")
                .exchange()
                .expectStatus().isOk();

        webTestClient.delete()
                .uri(UserResource.USERS + "/1")
                .exchange()
                .expectStatus().isOk();

        webTestClient.get()
                .uri(UserResource.USERS + "/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteUserByIdNotFound() {
        webTestClient.delete()
                .uri(UserResource.USERS + "/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testSearchUsersByBillable() {
        webTestClient.get()
                .uri(UserResource.USERS + "?billable=true")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(User.class)
                .value(users -> assertThat(users)
                        .isNotNull()
                        .isNotEmpty()
                        .allMatch(User::isBillable));
    }

    @Test
    void testSearchUsersNotBillable() {
        webTestClient.get()
                .uri(UserResource.USERS + "?billable=false")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(User.class)
                .value(users -> assertThat(users)
                        .isNotNull()
                        .isNotEmpty()
                        .noneMatch(User::isBillable));
    }

    @Test
    void testSearchUsersAll() {
        webTestClient.get()
                .uri(UserResource.USERS)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(User.class)
                .value(users -> assertThat(users)
                        .isNotNull()
                        .hasSize(6));
    }
}