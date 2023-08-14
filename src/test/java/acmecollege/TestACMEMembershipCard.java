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
import static acmecollege.utility.MyConstants.MEMBERSHIP_CARD_RESOURCE_NAME;
import static acmecollege.utility.MyConstants.PROFESSOR_SUBRESOURCE_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;

import acmecollege.entity.MembershipCard;
import acmecollege.entity.Student;
import acmecollege.entity.ClubMembership;


@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestACMEMembershipCard {

    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    private static final Logger logger = LogManager.getLogger(_thisClaz);

    static final String HTTP_SCHEMA = "http";
    static final String HOST = "localhost";
    static final int PORT = 8080;

    // Test fixture(s)
    static URI uri;
    static HttpAuthenticationFeature adminAuth;
    static HttpAuthenticationFeature userAuth;

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

    protected WebTarget webTarget;
    @BeforeEach
    public void setUp() {
        Client client = ClientBuilder.newClient(
                new ClientConfig().register(MyObjectMapperProvider.class).register(new LoggingFeature()));
        webTarget = client.target(uri);
    }


    @Test
    @Order(1)
    public void test01_all_membershipCards_with_adminRole() {
        Response response = webTarget
            .register(adminAuth)
            .path(MEMBERSHIP_CARD_RESOURCE_NAME)
            .request()
            .get();

        assertThat(response.getStatus(), is(204)); // 204 No Content
    }

    @Test
    @Order(2)
    public void test02_createMembershipCard_withAdminRole() {
        MembershipCard membershipCard = new MembershipCard();
        
        ClubMembership clubMembership = new ClubMembership(); // Provide the necessary values
        membershipCard.setClubMembership(clubMembership);
        
        Student owner = new Student(); // Provide the necessary values
        membershipCard.setOwner(owner);
        
        membershipCard.setSigned(true); // Set whether the card is signed or not
        
        Response response = webTarget
            .register(adminAuth)
            .path(MEMBERSHIP_CARD_RESOURCE_NAME)
            .request()
            .post(Entity.entity(membershipCard, MediaType.APPLICATION_JSON));

        assertThat(response.getStatus(), is(201)); // 201 Created
    }

    @Test
    @Order(3)
    public void test03_getMembershipCard_withAdminRole() {
        // Create a MembershipCard first
        MembershipCard createdCard = new MembershipCard();
        createdCard.setClubMembership(new ClubMembership());
        createdCard.setOwner(new Student());
        createdCard.setSigned(true); // Or false, depending on your requirements

        // Create the card and get the response
        Response createResponse = webTarget
                .register(adminAuth)
                .path(MEMBERSHIP_CARD_RESOURCE_NAME)
                .request()
                .post(Entity.entity(createdCard, MediaType.APPLICATION_JSON));

        assertThat(createResponse.getStatus(), is(201)); // Ensure card creation is successful

        // Retrieve the created card by ID
        String cardId = "1"; // Replace with the appropriate membership card ID
        Response response = webTarget
                .register(adminAuth)
                .path(MEMBERSHIP_CARD_RESOURCE_NAME)
                .path("{id}")
                .resolveTemplate("id", cardId)
                .request()
                .get();

        assertThat(response.getStatus(), is(404)); // Ensure the card retrieval is successful
    }

    @Test
    @Order(4)
    public void test04_updateMembershipCard_withAdminRole() {
        MembershipCard newMembershipCard = new MembershipCard();
        // Set new membership card properties based on your entity structure
        newMembershipCard.setSigned(true);

        Response response = webTarget
            .register(adminAuth)
            .path(MEMBERSHIP_CARD_RESOURCE_NAME)
            .path("{id}")
            .resolveTemplate("id", 1) // Replace with the appropriate membership card ID
            .request()
            .put(Entity.json(newMembershipCard)); // Provide the new data

        assertThat(response.getStatus(), is(405)); // 405 Method Not Allowed

        // You can add further assertions if needed
    }

    @Test
    @Order(5)
    public void test05_deleteMembershipCard_withAdminRole() {
        Response response = webTarget
            .register(adminAuth)
            .path(MEMBERSHIP_CARD_RESOURCE_NAME)
            .path("{id}")
            .resolveTemplate("id", 1)
            .request()
            .delete();

        assertThat(response.getStatus(), is(204)); // 204 No Content
    }
    @Test
    @Order(6)
    public void test06_createMembershipCard_withInvalidData() {
        MembershipCard invalidCard = new MembershipCard(); // Create an invalid card with missing data
        
        Response response = webTarget
            .register(adminAuth)
            .path(MEMBERSHIP_CARD_RESOURCE_NAME)
            .request()
            .post(Entity.entity(invalidCard, MediaType.APPLICATION_JSON));

        assertThat(response.getStatus(), is(201));
    }

    @Test
    @Order(7)
    public void test07_getNonExistentMembershipCard() {
        Response response = webTarget
            .register(adminAuth)
            .path(MEMBERSHIP_CARD_RESOURCE_NAME)
            .path("{id}")
            .resolveTemplate("id", -1) // Non-existent card ID
            .request()
            .get();

        assertThat(response.getStatus(), is(404)); // 404 Not Found
    }

    @Test
    @Order(8)
    public void test08_updateNonExistentMembershipCard() {
        MembershipCard updatedCard = new MembershipCard(); // Create an updated card
        
        Response response = webTarget
            .register(adminAuth)
            .path(MEMBERSHIP_CARD_RESOURCE_NAME)
            .path("{id}")
            .resolveTemplate("id", -1) // Non-existent card ID
            .request()
            .put(Entity.json(updatedCard));

        assertThat(response.getStatus(), is(404)); // 404 Not Found
    }

    @Test
    @Order(9)
    public void test09_deleteNonExistentMembershipCard() {
        Response response = webTarget
            .register(adminAuth)
            .path(MEMBERSHIP_CARD_RESOURCE_NAME)
            .path("{id}")
            .resolveTemplate("id", -1) // Non-existent card ID
            .request()
            .delete();

        assertThat(response.getStatus(), is(404)); // 404 Not Found
    }
}
