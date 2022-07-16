package de.fhws.fiw.fds.exam03.authorization;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.client.rest.resources.StudentRestClient;
import de.fhws.fiw.fds.exam02.client.rest.resources.StudyTripRestClient;
import de.fhws.fiw.fds.exam02.tests.AbstractTest;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import de.fhws.fiw.fds.exam02.tests.models.StudyTrip;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class LecturerTest extends AbstractTest<StudyTrip, StudyTripRestClient>
{
	@Test public void test_lecturer_get_request_200() throws IOException
	{
		RestApiResponse<StudyTrip> response = getCollectionRequestByUrl(HeaderMapUtils.withAcceptJson(),
			defineBaseUrl() + "studyTrips");
		assertEquals(200, response.getLastStatusCode());

	}

	@Test public void test_lecturer_put_request_403() throws IOException
	{
		RestApiResponse<StudyTrip> responseGet = getSingleRequestByUrl(HeaderMapUtils.withAcceptJson(),
			defineBaseUrl() + "studyTrips/4");
		StudyTrip responseSingleData = responseGet.getResponseSingleData();
		responseSingleData.setName("blabla");
		RestApiResponse<StudyTrip> response = putRequestByUrl(HeaderMapUtils.withContentTypeJson(), responseSingleData,
			defineBaseUrl() + "studyTrips/4");
		assertEquals(403, response.getLastStatusCode());

	}

	@Test public void test_lecturer_put_request_204() throws IOException
	{
		RestApiResponse<StudyTrip> responseGet = getSingleRequestByUrl(HeaderMapUtils.withAcceptJson(),
			defineBaseUrl() + "studyTrips/1");
		StudyTrip responseSingleData = responseGet.getResponseSingleData();
		responseSingleData.setName("blabla");
		RestApiResponse<StudyTrip> response = putRequestByUrl(HeaderMapUtils.withContentTypeJson(), responseSingleData,
			defineBaseUrl() + "studyTrips/1");
		assertEquals(204, response.getLastStatusCode());

	}

	@Test public void test_lecturer_post_request_201() throws IOException
	{
		RestApiResponse<StudyTrip> responseGet = getSingleRequestByUrl(HeaderMapUtils.withAcceptJson(),
			defineBaseUrl() + "studyTrips/1");
		StudyTrip responseSingleData = responseGet.getResponseSingleData();
		responseSingleData.setId(0L);
		RestApiResponse<StudyTrip> response = postRequestByUrl(HeaderMapUtils.withContentTypeJson(), responseSingleData,
			defineBaseUrl() + "studyTrips");
		assertEquals(201, response.getLastStatusCode());

	}

	@Override protected StudyTripRestClient newRestClient(HeaderMap headers)
	{
		return new StudyTripRestClient(headers, "idLecturer", "passwordLecturer");
	}
}
