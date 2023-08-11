/**
 * 
 */
package acmecollege.rest.resource;



import java.util.List;
import static acmecollege.utility.MyConstants.RESOURCE_PATH_ID_ELEMENT;


import static acmecollege.utility.MyConstants.PROFESSOR_SUBRESOURCE_NAME;
import static acmecollege.utility.MyConstants.ADMIN_ROLE;
import static acmecollege.utility.MyConstants.USER_ROLE;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import acmecollege.ejb.ACMECollegeService;
import acmecollege.entity.Professor;
import acmecollege.entity.Student;

/**
 * @author parha
 *
 */

@Path(PROFESSOR_SUBRESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfessorResource {
	
	private static final Logger LOG = LogManager.getLogger();

    @EJB
    protected ACMECollegeService service;

    @Inject
    protected SecurityContext sc;

	/**
	* @param professor
	* @return
	* 
	*/
	@POST
	@RolesAllowed({ADMIN_ROLE})
	public Response create( Professor newProfessor) {
		LOG.debug("Adding a new professor = {}", newProfessor);
		Response response = null;
		Professor newProfessorWithIdTimestamps = service.persistProfessor(newProfessor);
		response = Response.ok(newProfessorWithIdTimestamps).build();
		//TODO: process the given professor 
		//you may want to use the following return statement, assuming that Professor#getId() or a similar method 
		//would provide the identifier to retrieve the created Professor resource:
		//return Response.created(UriBuilder.fromResource(ProfessorEndpoint.class).path(String.valueOf(professor.getId())).build()).build();
		return response;
	}

	/**
	* @param id
	* @return
	* DONE
	*/
	@GET
	@RolesAllowed({ADMIN_ROLE, USER_ROLE})
	@Path("/{id}")
	public Response findById(@PathParam("id") int id) {
		LOG.debug("try to retrieve specific professor " + id);
		Response response = null;
		Professor professor = service.getById(Professor.class, "Professor.findById", id);
		
		if(professor != null && professor.getId() == id) {
			 response = Response.status(Status.OK).entity(professor).build();
			return response;
		}else {
			 response = Response.status(Status.BAD_REQUEST).build();
		}
		
		return response;
		
		
	}

	/**
	* @param startPosition
	* @param maxResult
	* @return
	* DONE
	*/
	@GET
	 @RolesAllowed({ADMIN_ROLE})
	public Response listAll() {
		//TODO: retrieve the professors 
		  LOG.debug("retrieving all professors ...");
		 List<Professor> professors = service.getAll(Professor.class,"Professor.findAll");
		Response response = Response.ok(professors).build();
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
		//TODO: process the professor matching by the given id 
		Response response = null;
		service.deleteProfessorId(id);
			 response = Response.status(Status.OK).entity(sc).build();
			return response;
	}

}
