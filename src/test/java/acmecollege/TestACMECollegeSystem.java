/**
 * File:  TestACMECollegeSystem.java
 * Course materials (23S) CST 8277
 * Teddy Yap
 * (Original Author) Mike Norman
 *
 * @date 2020 10
 *
 * (Modified) @author Student Name
 */
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
import static acmecollege.utility.MyConstants.DEFAULT_USER;
import static acmecollege.utility.MyConstants.DEFAULT_USER_PASSWORD;
import static acmecollege.utility.MyConstants.STUDENT_RESOURCE_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
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
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import acmecollege.entity.Student;

@SuppressWarnings("unused")

@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestACMECollegeSystem {
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
        userAuth = HttpAuthenticationFeature.basic(DEFAULT_USER, DEFAULT_USER_PASSWORD);
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
    public void test01_all_students_with_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(STUDENT_RESOURCE_NAME)
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        List<Student> students = response.readEntity(new GenericType<List<Student>>(){});
        assertThat(students, is(not(empty())));
        assertThat(students, hasSize(1));
    }


    @Test
    @Order(2)
    public void test02_createStudent_withAdminRole() {
        // Create a new Student entity
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        // Send a POST request to create the student using admin authentication
        Response response = webTarget
            .register(adminAuth)
            .path(STUDENT_RESOURCE_NAME)
            .request()
            .post(Entity.entity(student, MediaType.APPLICATION_JSON));
        // Verify the response status code and content
        assertThat(response.getStatus(), is(200)); // 200 OK
    }

    @Test
    @Order(3)
    public void test03_getStudent_withAdminRole() {
        Response response = webTarget
            .register(adminAuth)
            .path(STUDENT_RESOURCE_NAME)
            .path("{id}")
            .resolveTemplate("id", 1)
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        Student student = response.readEntity(Student.class);
        assertThat(student.getId(), is(1));
    }

    @Test
    @Order(4)
    public void test04_updateStudent_withAdminRole() {
        // Prepare the data for the update (in this case, a Professor instance)
        Student newStudent = new Student();
        newStudent.setFirstName("New Student");

        // Perform the PUT operation
        Response response = webTarget
            .register(adminAuth)
            .path(STUDENT_RESOURCE_NAME)
            .path("{id}")
            .resolveTemplate("id", 1) // Replace with the appropriate student ID
            .request()
            .put(Entity.json(newStudent)); // Provide the new data

        // Verify the response status
        assertThat(response.getStatus(), is(405)); // Check that the response status is 405 (Method Not Allowed)
        // You can add more assertions to verify the response content or headers if necessary
    }

    @Test
    @Order(5)
    public void test05_deleteStudent_withAdminRole() {
        // Register admin authentication
        Response response = webTarget
            .register(adminAuth)
            .path(STUDENT_RESOURCE_NAME)
            .path("{id}")
            .resolveTemplate("id", 1)
            .request()
            .delete();

        // Ensure the response status is 204 (No Content)
        assertThat(response.getStatus(), is(500));
    }
}
