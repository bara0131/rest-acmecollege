/**
 * 
 */
package acmecollege.rest.resource;

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

import static acmecollege.utility.MyConstants.CLUB_MEMBERSHIP_RESOURCE_NAME;
import static acmecollege.utility.MyConstants.ADMIN_ROLE;
import static acmecollege.utility.MyConstants.RESOURCE_PATH_ID_PATH;
import static acmecollege.utility.MyConstants.USER_ROLE;

import acmecollege.ejb.ACMECollegeService;
import acmecollege.entity.ClubMembership;
import acmecollege.entity.Course;

/**
 * @author parha
 *
 * Updated by:  Group 13
 *   041042258, Fatemeh, Baladi (as from ACSIS)
 *   041040628, Parham, Barati (as from ACSIS)
 *   041043087, Justin, Rackus (as from ACSIS)
 *   040863962, Pouya, Varghaei (as from ACSIS)
 */

@Path(CLUB_MEMBERSHIP_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClubMembershipResource {
	
	 private static final Logger LOG = LogManager.getLogger();
	 
	  	@EJB
	    protected ACMECollegeService service;

	    @Inject
	    protected SecurityContext sc;

	/**
	* @param clubmembership
	* @return
	*/
	@POST
	@RolesAllowed({ADMIN_ROLE})
	public Response create( ClubMembership newClubmembership) {
		//TODO: process the given clubmembership 
		//you may want to use the following return statement, assuming that ClubMembership#getId() or a similar method 
		//would provide the identifier to retrieve the created ClubMembership resource:
		//return Response.created(UriBuilder.fromResource(ClubMembershipResource.class).path(String.valueOf(clubmembership.getId())).build()).build();
		Response response = null;
		ClubMembership newClubmembershipWithIdTimestamps = service.persistClubMembership(newClubmembership);
		response = Response.ok(newClubmembershipWithIdTimestamps).build()
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
		//TODO: retrieve the clubmembership 
		ClubMembership clubmembership = service.getById(ClubMembership.class, ClubMembership.FIND_BY_ID, id);
		if (clubmembership == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(clubmembership).build();
	}

	/**
	* @param startPosition
	* @param maxResult
	* @return
	*/
	@GET
	@RolesAllowed({ADMIN_ROLE})
	public Response listAll() {
		//TODO: retrieve the clubmemberships 
		List<ClubMembership> clubmemberships = service.getAll(ClubMembership.class, ClubMembership.All_CLUBMEMBERSHIP_QUERY);
		 Response response = Response.ok(clubmemberships).build();
		return response;
	}

	/**
	* @param id
	* @return
	*/
	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") int id) {
		//TODO: process the clubmembership matching by the given id 
		Response response = null;
		ClubMembership clubmembership = service.deleteById(ClubMembership.class, ClubMembership.FIND_BY_ID, id);
		response = Response.ok(sc).build();
		return response;
	}

}
