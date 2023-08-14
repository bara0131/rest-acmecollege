/**
 *  * Updated by:  Group 13
 *   041042258, Fatemeh, Baladi (as from ACSIS)
 *   041040628, Parham, Barati (as from ACSIS)
 *   041043087, Justin, Rackus (as from ACSIS)
 *   040863962, Pouya, Varghaei (as from ACSIS)
 */
package acmecollege.rest.resource;

import java.util.List;
import static acmecollege.utility.MyConstants.USER_ROLE;
import static acmecollege.utility.MyConstants.ADMIN_ROLE;
import static acmecollege.utility.MyConstants.COURSE_RESOURCE_NAME;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import acmecollege.ejb.ACMECollegeService;
import acmecollege.entity.Course;
import acmecollege.entity.Student;

/**
 * @author parha
 *
 */

/**
 *  * Updated by:  Group 13
 *   041042258, Fatemeh, Baladi (as from ACSIS)
 *   041040628, Parham, Barati (as from ACSIS)
 *   041043087, Justin, Rackus (as from ACSIS)
 *   040863962, Pouya, Varghaei (as from ACSIS)
 */

@Path(COURSE_RESOURCE_NAME)
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class CourseResource {
	
	 private static final Logger LOG = LogManager.getLogger();
	 
	  	@EJB
	    protected ACMECollegeService service;

	    @Inject
	    protected SecurityContext sc;

	/**
	* @param course
	* @return
	*/
	@POST
	@RolesAllowed({ADMIN_ROLE})
	public Response create( Course newCourse) {
		//TODO: process the given course 
		//you may want to use the following return statement, assuming that Course#getId() or a similar method 
		//would provide the identifier to retrieve the created Course resource:
		//return Response.created(UriBuilder.fromResource(CourseResource.class).path(String.valueOf(course.getId())).build()).build();
		Response response = null;
		Course newCourseWithIdTimestamps = service.persistCourse(newCourse);
		response = Response.ok(newCourseWithIdTimestamps).build();
		return response;
	}

	/**
	* @param id
	* @return
	*/
	@GET
	@RolesAllowed({ADMIN_ROLE , USER_ROLE})
	@Path("/{id}")
	public Response findById(@PathParam("id") int id) {
		//TODO: retrieve the course 
		Course course = service.getById(Course.class, Course.FIND_BY_ID, id);
		if (course == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(course).build();
	}

	/**
	* @param startPosition
	* @param maxResult
	* @return
	*/
	
	@GET
	@RolesAllowed({ADMIN_ROLE})
	public Response listAll() {
		//TODO: retrieve the courses 
		LOG.debug("Retrieving all Courses...");
		 List<Course> courses = service.getAll(Course.class, Course.ALL_COURSES_QUERY);
	
		 Response response = Response.ok(courses).build();
		return response;
	}

	/**
	* @param id
	* @return
	*/
	@DELETE
	@RolesAllowed({ADMIN_ROLE})
	@Path("/{id}")
	public Response deleteById(@PathParam("id") int id) {
		//TODO: process the course matching by the given id 
		Response response = null;
		Course course = service.deleteById(Course.class, Course.FIND_BY_ID, id);
		response = Response.ok(sc).build();
		return response;
	}

}
