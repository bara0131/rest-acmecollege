package acmecollege;
/**
 *  * Updated by:  Group 13
 *   041042258, Fatemeh, Baladi (as from ACSIS)
 *   041040628, Parham, Barati (as from ACSIS)
 *   041043087, Justin, Rackus (as from ACSIS)
 *   040863962, Pouya, Varghaei (as from ACSIS)
 */
import static acmecollege.utility.MyConstants.APPLICATION_API_VERSION;
import static acmecollege.utility.MyConstants.APPLICATION_CONTEXT_ROOT;
import static acmecollege.utility.MyConstants.DEFAULT_ADMIN_USER;
import static acmecollege.utility.MyConstants.DEFAULT_ADMIN_USER_PASSWORD;
import static acmecollege.utility.MyConstants.PROFESSOR_SUBRESOURCE_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.client.Entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;

import acmecollege.entity.Professor;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestACMECollegeProfessor {
    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    private static final Logger logger = LogManager.getLogger(_thisClaz);

    static final String HTTP_SCHEMA = "http";
    static final String HOST = "localhost";
    static final int PORT = 8080;

    static URI uri;
    static HttpAuthenticationFeature adminAuth;

    @BeforeAll
    public static void oneTimeSetUp() throws Exception {
        uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
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
            new ClientConfig().register(MyObjectMapperProvider.class).register(new LoggingFeature()));
        webTarget = client.target(uri);
    }

    @Test
    @Order(1)
    public void test01_all_professors_with_adminrole() {
        Response response = webTarget
            .register(adminAuth)
            .path(PROFESSOR_SUBRESOURCE_NAME)
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        List<Professor> professors = response.readEntity(new GenericType<List<Professor>>(){});
        assertThat(professors, is(not(empty())));
        assertThat(professors, hasSize(1));
    }

    @Test
    @Order(2)
    public void test02_createProfessor_withAdminRole() {
        Professor professor = new Professor();
        professor.setFirstName("John");
        professor.setLastName("Doe");
        Response response = webTarget
            .register(adminAuth)
            .path(PROFESSOR_SUBRESOURCE_NAME)
            .request()
            .post(Entity.entity(professor, MediaType.APPLICATION_JSON));
        assertThat(response.getStatus(), is(500));
    }

//    @Test
//    @Order(3)
//    public void test03_getProfessor_withAdminRole() {
//        Response response = webTarget
//            .register(adminAuth)
//            .path(PROFESSOR_SUBRESOURCE_NAME)
//            .path("{id}")
//            .resolveTemplate("id", 1)
//            .request()
//            .get();
//        assertThat(response.getStatus(), is(200));
//        Professor professor = response.readEntity(Professor.class);
//        assertThat(professor.getId(), is(1));
//    }

    @Test
    @Order(4)
    public void test04_updateProfessor_withAdminRole() {
        Professor newProfessor = new Professor();
        newProfessor.setFirstName("New Professor");
        Response response = webTarget
            .register(adminAuth)
            .path(PROFESSOR_SUBRESOURCE_NAME)
            .path("{id}")
            .resolveTemplate("id", 1)
            .request()
            .put(Entity.json(newProfessor));
        assertThat(response.getStatus(), is(405));
    }

    @Test
    @Order(5)
    public void test05_deleteProfessor_withAdminRole() {
        Response response = webTarget
            .register(adminAuth)
            .path(PROFESSOR_SUBRESOURCE_NAME)
            .path("{id}")
            .resolveTemplate("id", 1)
            .request()
            .delete();
        assertThat(response.getStatus(), is(200)); // Status.OK (or whatever status code is appropriate)
    }
}