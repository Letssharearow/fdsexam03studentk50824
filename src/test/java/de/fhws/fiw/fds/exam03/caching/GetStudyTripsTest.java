package de.fhws.fiw.fds.exam03.caching;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.client.rest.resources.StudyTripRestClient;
import de.fhws.fiw.fds.exam02.tests.models.StudyTrip;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class GetStudyTripsTest extends CachingTestHelper<StudyTrip, StudyTripRestClient>
{




	@Test
	public void testCollectionCaching( ) throws IOException, InterruptedException
	{
		RestApiResponse<StudyTrip> response = getCollectionRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineCacheUrl( ) + "studytrips" );
		assertTrue(response.headerExists("X-Proxy-Cache", "MISS"));
		response = getCollectionRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineCacheUrl( ) + "studytrips" );
		assertTrue(response.headerExists("X-Proxy-Cache", "HIT"));
			try
			{
				Thread.sleep(2500);
				response = getCollectionRequestByUrl(HeaderMapUtils.withAcceptJson(), defineCacheUrl()+ "studytrips" );
				assertTrue(response.headerExists("X-Proxy-Cache", "EXPIRED"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			assertEquals(200, response.getLastStatusCode());
	}

	@Override protected StudyTripRestClient newRestClient(HeaderMap headers)
	{
		return new StudyTripRestClient(headers);
	}
}