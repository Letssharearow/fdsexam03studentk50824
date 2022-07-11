package de.fhws.fiw.fds.exam02.api.states;

import de.fhws.fiw.fds.exam02.api.hypermedia.rel_types.IStudentRelTypes;
import de.fhws.fiw.fds.exam02.api.hypermedia.rel_types.IStudyTripRelTypes;
import de.fhws.fiw.fds.exam02.api.hypermedia.uris.IStudentUri;
import de.fhws.fiw.fds.exam02.api.hypermedia.uris.IStudyTripUri;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetDispatcherState;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TokenState extends AbstractGetDispatcherState
{
	public TokenState(final Builder builder)
	{
		super(builder);
	}

	@Override protected Response createResponse()
	{
		defineHttpResponseBody();

		defineSelfLink();

		defineTransitionLinks();

		return this.responseBuilder.build();
	}

	@Override protected void defineTransitionLinks()
	{
	}

	public static class Builder extends AbstractDispatcherStateBuilder
	{
		@Override public AbstractState build()
		{
			return new TokenState(this);
		}
	}
}