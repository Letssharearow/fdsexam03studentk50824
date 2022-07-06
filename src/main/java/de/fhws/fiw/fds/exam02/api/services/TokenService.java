//package de.fhws.fiw.fds.exam02.api.services;
//
//import de.fhws.fiw.fds.exam02.api.states.BasicAuthHelper;
//import de.fhws.fiw.fds.exam02.api.states.BearerAuthHelper;
//import de.fhws.fiw.fds.exam02.api.states.DispatcherState;
//import de.fhws.fiw.fds.sutton.server.api.security.User;
//import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//@Path("") public class TokenService extends AbstractService
//{
//	@Path("me") @GET @Produces(MediaType.APPLICATION_JSON) public Response me()
//	{
//		final User user = BasicAuthHelper.accessControl(this.httpServletRequest);
//		String token = BearerAuthHelper.createToken(user.getId() + "");
//
//		return Response.ok(token).build();
//	}
//}