/**
 * 
 */
package acmecollege.rest.resource;
import static acmecollege.utility.MyConstants.USER_ROLE;
import static acmecollege.utility.MyConstants.ADMIN_ROLE;
import static acmecollege.utility.MyConstants.COURSE_REGISTRATION_RESOURCE_NAME;


import java.util.List;

import javax.enterprise.context.RequestScoped;
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
import javax.ws.rs.core.UriBuilder;

import acmecollege.entity.CourseRegistration;

/**
 * @author parha
 *
 */

@Path(COURSE_REGISTRATION_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CourseRegistrationResource {

	/**
	* @param courseregistration
	* @return
	*/
	@POST
	public Response create(final CourseRegistration courseregistration) {
		//TODO: process the given courseregistration 
		//here we use CourseRegistration#getId(), assuming that it provides the identifier to retrieve the created CourseRegistration resource. 
		return Response.created(UriBuilder.fromResource(CourseRegistrationResource.class)
				.path(String.valueOf(courseregistration.getId())).build()).build();
	}

	/**
	* @param id
	* @return
	*/
	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		//TODO: retrieve the courseregistration 
		CourseRegistration courseregistration = null;
		if (courseregistration == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(courseregistration).build();
	}

	/**
	* @param startPosition
	* @param maxResult
	* @return
	*/
	@GET
	public List<CourseRegistration> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		//TODO: retrieve the courseregistrations 
		final List<CourseRegistration> courseregistrations = null;
		return courseregistrations;
	}

	/**
	* @param id
	* @return
	*/
	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		//TODO: process the courseregistration matching by the given id 
		return Response.noContent().build();
	}

}
