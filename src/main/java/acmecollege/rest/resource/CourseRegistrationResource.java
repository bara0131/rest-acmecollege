/**
 * 
 */
package acmecollege.rest.resource;
import static acmecollege.utility.MyConstants.USER_ROLE;
import static acmecollege.utility.MyConstants.ADMIN_ROLE;
import static acmecollege.utility.MyConstants.COURSE_REGISTRATION_RESOURCE_NAME;


import java.util.List;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.UriBuilder;

import acmecollege.ejb.ACMECollegeService;
import acmecollege.entity.Course;
import acmecollege.entity.CourseRegistration;

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

@Path(COURSE_REGISTRATION_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CourseRegistrationResource {
	
	
	 private static final Logger LOG = LogManager.getLogger();
	 
	  	@EJB
	    protected ACMECollegeService service;

	    @Inject
	    protected SecurityContext sc;

	/**
	* @param courseregistration
	* @return
	*/
	@POST
	@RolesAllowed({ADMIN_ROLE})
	public Response create( CourseRegistration courseregistration) {
		//TODO: process the given courseregistration 
		//here we use CourseRegistration#getId(), assuming that it provides the identifier to retrieve the created CourseRegistration resource. 
		Response response = null;
		CourseRegistration newCourseregistrationWithIdTimestamps = service.persistCourseregistration(courseregistration);
		response = Response.ok(newCourseregistrationWithIdTimestamps).build();
		return response;
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
		 List<CourseRegistration> courseRegistrations = service.getAll(CourseRegistration.class, "CourseRegistration.findAll");
		 Response response = Response.ok(courseRegistrations).build();
		return response;
	}



}
