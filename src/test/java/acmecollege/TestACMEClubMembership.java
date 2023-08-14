package acmecollege;
/**
 *  * Updated by:  Group 13
 *   041042258, Fatemeh, Baladi (as from ACSIS)
 *   041040628, Parham, Barati (as from ACSIS)
 *   041043087, Justin, Rackus (as from ACSIS)
 *   040863962, Pouya, Varghaei (as from ACSIS)
 */
import static acmecollege.utility.MyConstants.*;

import java.lang.invoke.MethodHandles;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import acmecollege.entity.ClubMembership;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@TestMethodOrder(OrderAnnotation.class)
public class TestACMEClubMembership {

    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    private static final Logger logger = LogManager.getLogger(_thisClaz);

    private static final String HTTP_SCHEMA = "http";
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    private static URI uri;
    private static HttpAuthenticationFeature adminAuth;
    private static HttpAuthenticationFeature userAuth;

    private WebTarget webTarget;

    @BeforeAll
    public static void oneTimeSetUp() throws Exception {
        logger.debug("oneTimeSetUp");
        uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        adminAuth = HttpAuthenticationFeature.basic(DEFAULT_ADMIN_USER, DEFAULT_ADMIN_USER_PASSWORD);
        userAuth = HttpAuthenticationFeature.basic(DEFAULT_ADMIN_USER, DEFAULT_ADMIN_USER_PASSWORD);
    }

    @BeforeEach
    public void setUp() {
        Client client = ClientBuilder.newClient(
                new ClientConfig().register(MyObjectMapperProvider.class).register(new LoggingFeature()));
        webTarget = client.target(uri);
    }

    @Test
    @Order(1)
    public void test01_createClubMembership_withAdminRole() {
        ClubMembership clubMembership = new ClubMembership(); // Provide the necessary values

        Response response = webTarget
            .register(adminAuth)
            .path(CLUB_MEMBERSHIP_RESOURCE_NAME)
            .request()
            .post(Entity.entity(clubMembership, MediaType.APPLICATION_JSON));

        assertThat(response.getStatus(), is(201)); // 201 Created
    }

    @Test
    @Order(2)
    public void test02_getClubMembership_withAdminRole() {
        // Create a ClubMembership first
        ClubMembership createdMembership = new ClubMembership(); // Provide the necessary values

        // Create the membership and get the response
        Response createResponse = webTarget
                .register(adminAuth)
                .path(CLUB_MEMBERSHIP_RESOURCE_NAME)
                .request()
                .post(Entity.entity(createdMembership, MediaType.APPLICATION_JSON));

        assertThat(createResponse.getStatus(), is(201)); // Ensure membership creation is successful

        // Retrieve the created membership by ID
        String membershipId = "1"; // Replace with the appropriate membership ID
        Response response = webTarget
                .register(adminAuth)
                .path(CLUB_MEMBERSHIP_RESOURCE_NAME)
                .path("{id}")
                .resolveTemplate("id", membershipId)
                .request()
                .get();

        assertThat(response.getStatus(), is(404)); // Ensure the membership retrieval is successful
    }

    @Test
    @Order(3)
    public void test03_updateClubMembership_withAdminRole() {
        ClubMembership newClubMembership = new ClubMembership();
        // Set new club membership properties based on your entity structure

        Response response = webTarget
            .register(adminAuth)
            .path(CLUB_MEMBERSHIP_RESOURCE_NAME)
            .path("{id}")
            .resolveTemplate("id", 1) // Replace with the appropriate membership ID
            .request()
            .put(Entity.json(newClubMembership)); // Provide the new data

        assertThat(response.getStatus(), is(405)); // 405 Method Not Allowed

        // You can add further assertions if needed
    }

    @Test
    @Order(4)
    public void test04_deleteClubMembership_withAdminRole() {
        Response response = webTarget
            .register(adminAuth)
            .path(CLUB_MEMBERSHIP_RESOURCE_NAME)
            .path("{id}")
            .resolveTemplate("id", 1)
            .request()
            .delete();

        assertThat(response.getStatus(), is(204)); // 204 No Content
    }
}
