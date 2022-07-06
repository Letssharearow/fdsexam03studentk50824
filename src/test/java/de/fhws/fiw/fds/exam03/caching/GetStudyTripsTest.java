package de.fhws.fiw.fds.exam03.caching;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.client.rest.resources.StudyTripRestClient;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import de.fhws.fiw.fds.exam02.tests.models.StudyTrip;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class GetStudyTripsTest extends CachingTestHelper<StudyTrip, StudyTripRestClient>
{

	@Test public void testGetStudyTripCollectionCaching() throws IOException, InterruptedException
	{
		RestApiResponse<StudyTrip> response = getCollectionRequestByUrl(HeaderMapUtils.withAcceptJson(),
			defineCacheUrl() + "studytrips");
		assertTrue(response.headerExists("X-Proxy-Cache", "MISS"));
		response = getCollectionRequestByUrl(HeaderMapUtils.withAcceptJson(), defineCacheUrl() + "studytrips");
		assertTrue(response.headerExists("X-Proxy-Cache", "MISS"));
	}

	@Test public void testGetSingleStudyTripPrivateCachingMiss() throws IOException, InterruptedException
	{
		RestApiResponse<StudyTrip> response = getSingleRequestByUrl(HeaderMapUtils.withAcceptJson(),
			defineCacheUrl() + "studytrips/2");
		assertTrue(response.headerExists("X-Proxy-Cache", "MISS"));
		response = getSingleRequestByUrl(HeaderMapUtils.withAcceptJson(), defineCacheUrl() + "studytrips/2");
		assertTrue(response.headerExists("X-Proxy-Cache", "MISS"));
	}

	@Test public void test_cache_control_single() throws IOException
	{
		final RestApiResponse<StudyTrip> response = getSingleRequestById(HeaderMapUtils.withAcceptJson(), 2);
		assertHeaderExists(response, CACHE_CONTROL);
	}

	@Test public void test_cache_control_collection() throws IOException
	{
		final RestApiResponse<StudyTrip> response = getCollectionRequest(HeaderMapUtils.withAcceptJson());
		assertHeaderExists(response, CACHE_CONTROL);
	}

	@Test public void test_conditional_get_304() throws IOException
	{
		//user A loads a studyTrip
		final RestApiResponse<StudyTrip> responseFromFirstGetRequest = getSingleRequestById(
			HeaderMapUtils.withAcceptJson(), 1);

		assertHeaderExists(responseFromFirstGetRequest, ETAG);

		final String etag = responseFromFirstGetRequest.getEtagHeader();

		//user A revalidates
		final HeaderMap headersForSecondGetRequest = HeaderMapUtils.withAcceptJson();
		headersForSecondGetRequest.addHeader(IF_NONE_MATCH, etag);

		final RestApiResponse<StudyTrip> responseFromSecondGetRequest = getSingleRequestById(headersForSecondGetRequest,
			1);

		assertEquals(304, responseFromSecondGetRequest.getLastStatusCode());
	}

	@Test public void test_conditional_get_200() throws IOException
	{
		//user A loads a studyTrip
		final RestApiResponse<StudyTrip> responseFromFirstGetRequest = getSingleRequestById(
			HeaderMapUtils.withAcceptJson(), 1);
		final StudyTrip studyTrip = responseFromFirstGetRequest.getResponseSingleData();

		assertHeaderExists(responseFromFirstGetRequest, ETAG);

		final String initialEtag = responseFromFirstGetRequest.getEtagHeader();

		//user B updates this resource
		studyTrip.setCityName("Munich2");

		final HeaderMap headersForPutRequestForUserB = HeaderMapUtils.withContentTypeJson();
		headersForPutRequestForUserB.addHeader(IF_MATCH, initialEtag);

		final RestApiResponse<StudyTrip> responseFromPutRequest = putRequest(headersForPutRequestForUserB, studyTrip);

		assertEquals(204, responseFromPutRequest.getLastStatusCode());

		final String newEtag = responseFromPutRequest.getEtagHeader();

		//user A revalidates using old etag
		final HeaderMap headersForSecondGetRequest = HeaderMapUtils.withAcceptJson();
		headersForSecondGetRequest.addHeader(IF_NONE_MATCH, initialEtag);

		final RestApiResponse<StudyTrip> responseFromSecondGetRequest = getSingleRequestById(headersForSecondGetRequest,
			1);

		assertEquals(200, responseFromSecondGetRequest.getLastStatusCode());
		assertNotNull(responseFromSecondGetRequest.getResponseSingleData());
	}

	@Test public void test_conditional_put_204() throws IOException
	{
		//user A loads a student
		final RestApiResponse<StudyTrip> responseFromGetRequest = getSingleRequestById(HeaderMapUtils.withAcceptJson(),
			1);
		final StudyTrip student = responseFromGetRequest.getResponseSingleData();

		assertHeaderExists(responseFromGetRequest, ETAG);

		final String initialEtag = responseFromGetRequest.getEtagHeader();

		//user A updates this resource
		student.setCountryName("Klaus");

		final HeaderMap headersForPutRequestForUserA = HeaderMapUtils.withContentTypeJson();
		headersForPutRequestForUserA.addHeader(IF_MATCH, initialEtag);

		final RestApiResponse<StudyTrip> responseFromPutRequest = putRequest(headersForPutRequestForUserA, student);

		assertEquals(204, responseFromPutRequest.getLastStatusCode());

		assertHeaderExists(responseFromPutRequest, ETAG);
	}

	@Test public void test_conditional_put_412() throws IOException
	{
		//user A loads a student
		final RestApiResponse<StudyTrip> responseFromGetRequestForUserA = getSingleRequestById(
			HeaderMapUtils.withAcceptJson(), 1);
		final StudyTrip studentForUserA = responseFromGetRequestForUserA.getResponseSingleData();

		assertHeaderExists(responseFromGetRequestForUserA, ETAG);

		final String initialEtagForUserA = responseFromGetRequestForUserA.getEtagHeader();

		//user B loads the same student
		final RestApiResponse<StudyTrip> responseFromGetRequestForUserB = getSingleRequestById(
			HeaderMapUtils.withAcceptJson(), 1);
		final StudyTrip studentForUserB = responseFromGetRequestForUserB.getResponseSingleData();

		assertHeaderExists(responseFromGetRequestForUserB, ETAG);

		final String initialEtagForUserB = responseFromGetRequestForUserB.getEtagHeader();

		//user B updates this resource
		studentForUserB.setCompanyName("Robert");

		final HeaderMap headersForPutRequestForUserB = HeaderMapUtils.withContentTypeJson();
		headersForPutRequestForUserB.addHeader(IF_MATCH, initialEtagForUserB);

		final RestApiResponse<StudyTrip> responseFromPutRequestForUserB = putRequest(headersForPutRequestForUserB,
			studentForUserB);

		assertEquals(204, responseFromPutRequestForUserB.getLastStatusCode());

		final String newEtagForUserB = responseFromPutRequestForUserB.getEtagHeader();

		//user A updates this resource
		studentForUserA.setName("Klaus");

		final HeaderMap headersForPutRequestForUserA = HeaderMapUtils.withContentTypeJson();
		headersForPutRequestForUserA.addHeader(IF_MATCH, initialEtagForUserA);

		final RestApiResponse<StudyTrip> responseFromPutRequestForUserA = putRequest(headersForPutRequestForUserA,
			studentForUserA);

		assertEquals(412, responseFromPutRequestForUserA.getLastStatusCode());
	}

	@Override protected StudyTripRestClient newRestClient(HeaderMap headers)
	{
		return new StudyTripRestClient(headers);
	}
}