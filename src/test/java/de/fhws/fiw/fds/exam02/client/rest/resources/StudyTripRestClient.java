package de.fhws.fiw.fds.exam02.client.rest.resources;

import com.owlike.genson.GenericType;
import de.fhws.fiw.fds.exam02.client.IBaseUrl;
import de.fhws.fiw.fds.exam02.tests.models.StudyTrip;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;

import java.util.List;

public class StudyTripRestClient extends AbstractResourceRestClient<StudyTrip>
{
	public StudyTripRestClient(final HeaderMap headers)
	{
		super(headers);
	}

	public StudyTripRestClient(final HeaderMap headers, String username, String password)
	{
		super(headers, username, password);
	}

	@Override protected String defineUrl()
	{
		return IBaseUrl.BASE_URL + "studyTrips";
	}

	@Override protected Class<StudyTrip> defineClassTypeForSingleResource()
	{
		return StudyTrip.class;
	}

	@Override protected GenericType<List<StudyTrip>> defineClassTypeForCollectionResource()
	{
		return new GenericType<List<StudyTrip>>()
		{
		};
	}
}