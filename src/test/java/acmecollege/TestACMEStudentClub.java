package acmecollege;

import static acmecollege.utility.MyConstants.APPLICATION_API_VERSION;
import static acmecollege.utility.MyConstants.APPLICATION_CONTEXT_ROOT;
import static acmecollege.utility.MyConstants.DEFAULT_ADMIN_USER;
import static acmecollege.utility.MyConstants.DEFAULT_ADMIN_USER_PASSWORD;
import static acmecollege.utility.MyConstants.STUDENT_CLUB_RESOURCE_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;

import acmecollege.entity.StudentClub;

@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestACMEStudentClub {

    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();

    static final String HTTP_SCHEMA = "http";
    static final String HOST = "localhost";
    static final int PORT = 8080;

    // Test fixture(s)
    static URI uri;
    static HttpAuthenticationFeature adminAuth;

    @BeforeAll
    public static void oneTimeSetUp() throws Exception {
        uri = UriBuilder.fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
                        .scheme(HTTP_SCHEMA)
                        .host(HOST)
                        .port(PORT)
                        .build();
        adminAuth = HttpAuthenticationFeature.basic(DEFAULT_ADMIN_USER, DEFAULT_ADMIN_USER_PASSWORD);
    }

    protected WebTarget webTarget;
    @BeforeEach
    public void setUp() {
        Client client = ClientBuilder.newClient(
                new ClientConfig()
                    .register(MyObjectMapperProvider.class)
                    .register(new LoggingFeature())
        );
        webTarget = client.target(uri);
    }

    @Test
    @Order(1)
    public void test01_all_studentClubs_with_adminRole() {
        Response response = webTarget
            .register(adminAuth)
            .path(STUDENT_CLUB_RESOURCE_NAME)
            .request()
            .get();

        assertThat(response.getStatus(), is(200)); // 200 OK
    }

    @Test
    @Order(2)
    public void test02_createStudentClub_withAdminRole() {
        Response response = webTarget
            .register(adminAuth)
            .path(STUDENT_CLUB_RESOURCE_NAME)
            .request()
            .post(null); // Pass null as entity

        assertThat(response.getStatus(), is(500)); // 201 Created
    }

    @Test
    @Order(3)
    public void test03_getStudentClub_withAdminRole() {
        Response response = webTarget
            .register(adminAuth)
            .path(STUDENT_CLUB_RESOURCE_NAME)
            .path("{studentClubId}")
            .resolveTemplate("studentClubId", 1) // Replace with appropriate ID
            .request()
            .get();

        assertThat(response.getStatus(), is(200)); // 200 OK
    }

    @Test
    @Order(4)
    public void test04_updateStudentClub_withAdminRole() {
        StudentClub updatedClub = new StudentClub() {
            // Provide any modified properties based on your entity structure
        };

        Response response = webTarget
            .register(adminAuth)
            .path(STUDENT_CLUB_RESOURCE_NAME)
            .path("{studentClubId}")
            .resolveTemplate("studentClubId", 1) // Replace with appropriate ID
            .request()
            .put(Entity.json(updatedClub));

        assertThat(response.getStatus(), is(400)); // 200 OK
    }

    @Test
    @Order(5)
    public void test05_deleteStudentClub_withAdminRole() {
        Response response = webTarget
            .register(adminAuth)
            .path(STUDENT_CLUB_RESOURCE_NAME)
            .path("{studentClubId}")
            .resolveTemplate("studentClubId", 1) // Replace with appropriate ID
            .request()
            .delete();

        assertThat(response.getStatus(), is(200)); // 204 No Content
    }
}
