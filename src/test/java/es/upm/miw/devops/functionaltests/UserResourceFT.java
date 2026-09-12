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
import org.springframework.test.context.jdbc.Sql;
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

    @Sql(scripts = "/reset-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void testReadUserById() {
        webTestClient.get()
                .uri(UserResource.USERS + "/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(user -> assertThat(user)
                        .hasFieldOrPropertyWithValue("id", "1")
                        .hasFieldOrPropertyWithValue("firstName", "Oscar"));
    }

    @Sql(scripts = "/reset-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void testReadUserByIdNotFound() {
        webTestClient.get()
                .uri(UserResource.USERS + "/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Sql(scripts = "/reset-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void testReadUserByIdOtherUser() {
        webTestClient.get()
                .uri(UserResource.USERS + "/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(user -> assertThat(user)
                        .hasFieldOrPropertyWithValue("id", "2")
                        .hasFieldOrPropertyWithValue("firstName", "Ana"));
    }

    @Sql(scripts = "/reset-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void testSearchUsersByBillableTrue() {
        webTestClient.get()
                .uri(UserResource.USERS + "?billable=true")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .value(users -> assertThat(users)
                        .hasSize(2)
                        .allMatch(User::isBillable)
                        .extracting("id").containsExactlyInAnyOrder("1", "2"));
    }

    @Sql(scripts = "/reset-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void testSearchUsersByBillableFalse() {
        webTestClient.get()
                .uri(UserResource.USERS + "?billable=false")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .value(users -> assertThat(users)
                        .hasSize(4)
                        .noneMatch(User::isBillable)
                        .extracting("id").containsExactlyInAnyOrder("3", "4", "5", "6"));
    }

    @Sql(scripts = "/reset-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void testSearchUsersAllWithoutFilter() {
        webTestClient.get()
                .uri(UserResource.USERS)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .value(users -> assertThat(users)
                        .hasSize(6)
                        .extracting("id").containsExactlyInAnyOrder("1", "2", "3", "4", "5", "6"));
    }

    @Sql(scripts = "/reset-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void testUpdateActiveDeactivate() {
        webTestClient.get()
                .uri(UserResource.USERS + "/3")
                .exchange()
                .expectBody(User.class)
                .value(user -> assertThat(user).hasFieldOrPropertyWithValue("active", false));

        webTestClient.put()
                .uri(UserResource.USERS + "/3/active?active=true")
                .exchange()
                .expectStatus().isOk();

        webTestClient.get()
                .uri(UserResource.USERS + "/3")
                .exchange()
                .expectBody(User.class)
                .value(user -> assertThat(user).hasFieldOrPropertyWithValue("active", true));
    }

    @Sql(scripts = "/reset-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void testUpdateActiveActivate() {
        webTestClient.put()
                .uri(UserResource.USERS + "/4/active?active=false")
                .exchange()
                .expectStatus().isOk();

        webTestClient.get()
                .uri(UserResource.USERS + "/4")
                .exchange()
                .expectBody(User.class)
                .value(user -> assertThat(user).hasFieldOrPropertyWithValue("active", false));
    }

    @Sql(scripts = "/reset-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void testUpdateActiveNotFound() {
        webTestClient.put()
                .uri(UserResource.USERS + "/999/active?active=false")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Sql(scripts = "/reset-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void testUpdateActiveOnOtherUser() {
        webTestClient.put()
                .uri(UserResource.USERS + "/5/active?active=false")
                .exchange()
                .expectStatus().isOk();

        webTestClient.get()
                .uri(UserResource.USERS + "/5")
                .exchange()
                .expectBody(User.class)
                .value(user -> assertThat(user).hasFieldOrPropertyWithValue("active", false));
    }

    @Sql(scripts = "/reset-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void testUpdateActiveToTrue() {
        webTestClient.put()
                .uri(UserResource.USERS + "/6/active?active=true")
                .exchange()
                .expectStatus().isOk();

        webTestClient.get()
                .uri(UserResource.USERS + "/6")
                .exchange()
                .expectBody(User.class)
                .value(user -> assertThat(user).hasFieldOrPropertyWithValue("active", true));
    }

    @Sql(scripts = "/reset-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void testUpdateActiveToFalse() {
        webTestClient.put()
                .uri(UserResource.USERS + "/1/active?active=false")
                .exchange()
                .expectStatus().isOk();

        webTestClient.get()
                .uri(UserResource.USERS + "/1")
                .exchange()
                .expectBody(User.class)
                .value(user -> assertThat(user).hasFieldOrPropertyWithValue("active", false));
    }

    @Sql(scripts = "/reset-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void testDeleteUserById() {
        webTestClient.delete()
                .uri(UserResource.USERS + "/5")
                .exchange()
                .expectStatus().isOk();

        webTestClient.get()
                .uri(UserResource.USERS + "/5")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Sql(scripts = "/reset-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void testDeleteUserByIdNotFound() {
        webTestClient.delete()
                .uri(UserResource.USERS + "/999")
                .exchange()
                .expectStatus().isNotFound();
    }
}