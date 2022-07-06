package de.fhws.fiw.fds.exam02.api.states;

import de.fhws.fiw.fds.exam02.api.hypermedia.rel_types.IStudentRelTypes;
import de.fhws.fiw.fds.exam02.api.hypermedia.uris.IStudentUri;
import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.exam02.models.Users;
import de.fhws.fiw.fds.sutton.server.api.caching.CachingUtils;
import de.fhws.fiw.fds.sutton.server.api.caching.EtagGenerator;
import de.fhws.fiw.fds.sutton.server.api.security.User;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Response;

public class GetTokenState extends AbstractGetState<User>
{
	String token;

	public GetTokenState(final Builder builder)
	{
		super(builder);
	}

	@Override protected void authorizeRequest()
	{

	}

	@Override protected SingleModelResult<User> loadModel()
	{
		return null;
	}

	@Override protected void defineTransitionLinks()
	{
	}

	@Override protected void defineHttpCaching()
	{
		this.responseBuilder.cacheControl(CachingUtils.createNoCacheNoStoreCaching());
	}

	@Override protected boolean clientKnowsCurrentModelState(final AbstractModel modelFromDatabase)
	{
		return false;
	}

	@Override protected Response createResponse()
	{
		return super.createResponse();
	}

	public static class Builder extends AbstractGetStateBuilder
	{
		@Override public AbstractState build()
		{
			return new GetTokenState(this);
		}
	}
}