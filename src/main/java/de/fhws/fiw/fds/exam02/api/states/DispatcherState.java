package de.fhws.fiw.fds.exam02.api.states;

import de.fhws.fiw.fds.exam02.api.hypermedia.rel_types.IOtherRelTypes;
import de.fhws.fiw.fds.exam02.api.hypermedia.rel_types.IStudentRelTypes;
import de.fhws.fiw.fds.exam02.api.hypermedia.rel_types.IStudyTripRelTypes;
import de.fhws.fiw.fds.exam02.api.hypermedia.uris.IOtherServicesUri;
import de.fhws.fiw.fds.exam02.api.hypermedia.uris.IStudentUri;
import de.fhws.fiw.fds.exam02.api.hypermedia.uris.IStudyTripUri;
import de.fhws.fiw.fds.sutton.server.api.caching.CachingUtils;
import de.fhws.fiw.fds.sutton.server.api.caching.EtagGenerator;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetDispatcherState;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class DispatcherState extends AbstractGetDispatcherState
{
	public DispatcherState(final Builder builder)
	{
		super(builder);
	}

	@Override protected Response createResponse()
	{
		defineHttpResponseBody();

		defineSelfLink();

		defineTransitionLinks();

		this.responseBuilder.header("Authentication", "Bearer Token");

		return this.responseBuilder.build();
	}

	@Override protected void defineTransitionLinks()
	{
		addLink(IStudyTripUri.REL_PATH_QUERY_PARAMETERS, IStudyTripRelTypes.GET_ALL_STUDY_TRIPS,
			MediaType.APPLICATION_JSON);
		addLink(IStudentUri.REL_PATH_QUERY_PARAMETERS, IStudentRelTypes.GET_ALL_STUDENTS, MediaType.APPLICATION_JSON);
		addLink(IOtherServicesUri.ME, IOtherRelTypes.GET_TOKEN, MediaType.APPLICATION_JSON);
		addLink(IOtherServicesUri.STUDY_TRIP_REPORT, IOtherRelTypes.GET_STUDY_TRIP_REPORT, MediaType.APPLICATION_JSON);
	}

	public static class Builder extends AbstractDispatcherStateBuilder
	{
		@Override public AbstractState build()
		{
			return new DispatcherState(this);
		}
	}
}