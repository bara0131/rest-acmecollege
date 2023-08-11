/**
 * 
 */
package acmecollege.rest.resource;

import java.util.List;

import static acmecollege.utility.MyConstants.ADMIN_ROLE;
import static acmecollege.utility.MyConstants.MEMBERSHIP_CARD_RESOURCE_NAME;
import static acmecollege.utility.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static acmecollege.utility.MyConstants.RESOURCE_PATH_ID_PATH;
import static acmecollege.utility.MyConstants.USER_ROLE;
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

import acmecollege.entity.MembershipCard;

/**
 * @author parha
 *
 */
@Path(MEMBERSHIP_CARD_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MembershipCardResource {

	/**
	* @param membershipcard
	* @return
	*/
	@POST
	public Response create(final MembershipCard membershipcard) {
		//TODO: process the given membershipcard 
		//you may want to use the following return statement, assuming that MembershipCard#getId() or a similar method 
		//would provide the identifier to retrieve the created MembershipCard resource:
		//return Response.created(UriBuilder.fromResource(MembershipCardResource.class).path(String.valueOf(membershipcard.getId())).build()).build();
		return Response.created(null).build();
	}

	/**
	* @param id
	* @return
	*/
	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		//TODO: retrieve the membershipcard 
		MembershipCard membershipcard = null;
		if (membershipcard == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(membershipcard).build();
	}

	/**
	* @param startPosition
	* @param maxResult
	* @return
	*/
	@GET
	public List<MembershipCard> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		//TODO: retrieve the membershipcards 
		final List<MembershipCard> membershipcards = null;
		return membershipcards;
	}

	/**
	* @param id
	* @return
	*/
	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		//TODO: process the membershipcard matching by the given id 
		return Response.noContent().build();
	}

}
