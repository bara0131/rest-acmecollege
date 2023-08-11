/**
 * 
 */
package acmecollege.rest.resource;

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

import static acmecollege.utility.MyConstants.CLUB_MEMBERSHIP_RESOURCE_NAME;
import static acmecollege.utility.MyConstants.ADMIN_ROLE;
import static acmecollege.utility.MyConstants.RESOURCE_PATH_ID_PATH;
import static acmecollege.utility.MyConstants.USER_ROLE;

import acmecollege.entity.ClubMembership;

/**
 * @author parha
 *
 */

@Path(CLUB_MEMBERSHIP_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClubMembershipResource {

	/**
	* @param clubmembership
	* @return
	*/
	@POST
	public Response create(final ClubMembership clubmembership) {
		//TODO: process the given clubmembership 
		//you may want to use the following return statement, assuming that ClubMembership#getId() or a similar method 
		//would provide the identifier to retrieve the created ClubMembership resource:
		//return Response.created(UriBuilder.fromResource(ClubMembershipResource.class).path(String.valueOf(clubmembership.getId())).build()).build();
		return Response.created(null).build();
	}

	/**
	* @param id
	* @return
	*/
	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		//TODO: retrieve the clubmembership 
		ClubMembership clubmembership = null;
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
	public List<ClubMembership> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		//TODO: retrieve the clubmemberships 
		final List<ClubMembership> clubmemberships = null;
		return clubmemberships;
	}

	/**
	* @param id
	* @return
	*/
	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		//TODO: process the clubmembership matching by the given id 
		return Response.noContent().build();
	}

}
