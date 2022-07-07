package de.fhws.fiw.fds.exam03.authorization;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.client.rest.resources.StudyTripRestClient;
import de.fhws.fiw.fds.exam02.tests.AbstractTest;
import de.fhws.fiw.fds.exam02.tests.models.StudyTrip;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AdminTest extends AbstractTest<StudyTrip, StudyTripRestClient>
{
	@Test public void test_admin_get_request_200() throws IOException
	{
		RestApiResponse<StudyTrip> response = getCollectionRequestByUrl(HeaderMapUtils.withAcceptJson(),
			defineBaseUrl() + "studytrips");
		assertEquals(200, response.getLastStatusCode());

	}

	@Test public void test_lecturer_put_request_204() throws IOException
	{
		RestApiResponse<StudyTrip> responseGet = getSingleRequestByUrl(HeaderMapUtils.withAcceptJson(),
			defineBaseUrl() + "studytrips/4");
		StudyTrip responseSingleData = responseGet.getResponseSingleData();
		responseSingleData.setName("blabla");
		RestApiResponse<StudyTrip> response = putRequestByUrl(HeaderMapUtils.withContentTypeJson(), responseSingleData,
			defineBaseUrl() + "studytrips/4");
		assertEquals(204, response.getLastStatusCode());

	}

	@Override protected StudyTripRestClient newRestClient(HeaderMap headers)
	{
		return new StudyTripRestClient(headers, "idAdmin", "passwordAdmin");
	}
}
