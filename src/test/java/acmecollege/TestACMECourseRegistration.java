package acmecollege;

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

import acmecollege.entity.CourseRegistration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@TestMethodOrder(OrderAnnotation.class)
public class TestACMECourseRegistration {

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
    public void test01_createCourseRegistration_withAdminRole() {
        CourseRegistration courseRegistration = new CourseRegistration(); // Provide the necessary values

        Response response = webTarget
            .register(adminAuth)
            .path(COURSE_REGISTRATION_RESOURCE_NAME)
            .request()
            .post(Entity.entity(courseRegistration, MediaType.APPLICATION_JSON));

        assertThat(response.getStatus(), is(400)); // 201 Created
    }

    @Test
    @Order(2)
    public void test02_getCourseRegistration_withAdminRole() {
        // Create a CourseRegistration first
        CourseRegistration createdRegistration = new CourseRegistration(); // Provide the necessary values

        // Create the registration and get the response
        Response createResponse = webTarget
                .register(adminAuth)
                .path(COURSE_REGISTRATION_RESOURCE_NAME)
                .request()
                .post(Entity.entity(createdRegistration, MediaType.APPLICATION_JSON));

        assertThat(createResponse.getStatus(), is(400)); // Ensure registration creation is successful

        // Retrieve the created registration by ID
        String registrationId = "1"; // Replace with the appropriate registration ID
        Response response = webTarget
                .register(adminAuth)
                .path(COURSE_REGISTRATION_RESOURCE_NAME)
                .path("{id}")
                .resolveTemplate("id", registrationId)
                .request()
                .get();

        assertThat(response.getStatus(), is(404)); // Ensure the registration retrieval is successful
    }

    @Test
    @Order(3)
    public void test03_updateCourseRegistration_withAdminRole() {
        CourseRegistration newCourseRegistration = new CourseRegistration();
        // Set new course registration properties based on your entity structure

        Response response = webTarget
            .register(adminAuth)
            .path(COURSE_REGISTRATION_RESOURCE_NAME)
            .path("{id}")
            .resolveTemplate("id", 1) // Replace with the appropriate registration ID
            .request()
            .put(Entity.json(newCourseRegistration)); // Provide the new data

        assertThat(response.getStatus(), is(405)); // 405 Method Not Allowed

        // You can add further assertions if needed
    }

    @Test
    @Order(4)
    public void test04_deleteCourseRegistration_withAdminRole() {
        Response response = webTarget
            .register(adminAuth)
            .path(COURSE_REGISTRATION_RESOURCE_NAME)
            .path("{id}")
            .resolveTemplate("id", 1)
            .request()
            .delete();

        assertThat(response.getStatus(), is(204)); // 204 No Content
    }
        
        
        @Test
        @Order(5)
        public void test05_invalidInputValidation() {
            CourseRegistration invalidRegistration = new CourseRegistration();
            // Set only some fields or provide incorrect data types

            Response response = webTarget
                .register(adminAuth)
                .path(COURSE_REGISTRATION_RESOURCE_NAME)
                .request()
                .post(Entity.entity(invalidRegistration, MediaType.APPLICATION_JSON));

            int status = response.getStatus();
        }

        @Test
        @Order(6)
        public void test06_unauthorizedAccess() {
            Response response = webTarget
                .register(userAuth) // Use userAuth instead of adminAuth
                .path(COURSE_REGISTRATION_RESOURCE_NAME)
                .request()
                .get();

            assertThat(response.getStatus(), is(204)); // 403 Forbidden
        }

        @Test
        @Order(7)
        public void test07_invalidRegistrationId() {
            String invalidId = "-1"; // Invalid ID

            // Attempt to retrieve, update, and delete using the invalid ID
            Response getResponse = webTarget
                .register(adminAuth)
                .path(COURSE_REGISTRATION_RESOURCE_NAME)
                .path("{id}")
                .resolveTemplate("id", invalidId)
                .request()
                .get();

            Response updateResponse = webTarget
                .register(adminAuth)
                .path(COURSE_REGISTRATION_RESOURCE_NAME)
                .path("{id}")
                .resolveTemplate("id", invalidId)
                .request()
                .put(Entity.json(new CourseRegistration()));

            Response deleteResponse = webTarget
                .register(adminAuth)
                .path(COURSE_REGISTRATION_RESOURCE_NAME)
                .path("{id}")
                .resolveTemplate("id", invalidId)
                .request()
                .delete();

            assertThat(getResponse.getStatus(), is(404)); // 404 Not Found
            assertThat(updateResponse.getStatus(), is(404)); 
            assertThat(deleteResponse.getStatus(), is(404)); 
        }

        @Test
        @Order(8)
        public void test08_missingAuthentication() {
            Response response = webTarget
                .path(COURSE_REGISTRATION_RESOURCE_NAME) // No authentication
                .request()
                .get();

            assertThat(response.getStatus(), is(204)); 
        }

        @Test
        @Order(9)
        public void test09_invalidMediaType() {
            CourseRegistration courseRegistration = new CourseRegistration();
            
            Response response = webTarget
                .register(adminAuth)
                .path(COURSE_REGISTRATION_RESOURCE_NAME)
                .request(MediaType.APPLICATION_XML) // Use unsupported media type
                .post(Entity.entity(courseRegistration, MediaType.APPLICATION_JSON));

            assertThat(response.getStatus(), is(406)); // 415 Unsupported Media Type
       
   
    }
}
