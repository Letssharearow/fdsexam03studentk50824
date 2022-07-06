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
		testConditionalGet304();
	}

	@Test public void test_conditional_get_200() throws IOException
	{
		testConditionalGet200();
	}

	@Test public void test_conditional_put_204() throws IOException
	{
		testConditionalPut204();
	}

	@Override public StudyTrip changeRessource(StudyTrip ressource, String newData)
	{
		ressource.setName(newData);
		return ressource;
	}

	@Test public void test_conditional_put_412() throws IOException
	{
		testConditionalPut412();
	}

	@Override protected StudyTripRestClient newRestClient(HeaderMap headers)
	{
		return new StudyTripRestClient(headers);
	}
}