package de.fhws.fiw.fds.exam03.caching;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.client.rest.resources.StudyTripRestClient;
import de.fhws.fiw.fds.exam02.client.rest.resources.StudyTripStudentRestClient;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import de.fhws.fiw.fds.exam02.tests.models.StudyTrip;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class GetStudyTripStudentsTest extends CachingTestHelper<Student, StudyTripStudentRestClient>
{

	@Test public void testGetStudyTripCollectionCaching() throws IOException, InterruptedException
	{
		RestApiResponse<Student> response = getCollectionRequestByUrl(HeaderMapUtils.withAcceptJson(),
			getCollectionUrl());
		assertTrue(response.headerExists("X-Proxy-Cache", "MISS"));
		response = getCollectionRequestByUrl(HeaderMapUtils.withAcceptJson(), getCollectionUrl());
		assertTrue(response.headerExists("X-Proxy-Cache", "MISS"));
	}

	private String getCollectionUrl()
	{
		return defineCacheUrl() + "studytrips/1/students";
	}

	private String getSingleUrl()
	{
		return defineCacheUrl() + "studytrips/1/students/1";
	}

	@Test public void testGetSingleStudentPrivateCachingMiss() throws IOException, InterruptedException
	{
		RestApiResponse<Student> response = getSingleRequestByUrl(HeaderMapUtils.withAcceptJson(), getSingleUrl());
		assertTrue(response.headerExists("X-Proxy-Cache", "MISS"));
		response = getSingleRequestByUrl(HeaderMapUtils.withAcceptJson(), getSingleUrl());
		assertTrue(response.headerExists("X-Proxy-Cache", "MISS"));
	}

	@Test public void test_cache_control_single() throws IOException
	{
		final RestApiResponse<Student> response = getSingleRequestById(HeaderMapUtils.withAcceptJson(), 2);
		assertHeaderExists(response, CACHE_CONTROL);
	}

	@Test public void test_cache_control_collection() throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequest(HeaderMapUtils.withAcceptJson());
		assertHeaderExists(response, CACHE_CONTROL);
	}

	@Test public void test_cache_control_single_private_30s() throws IOException
	{
		final RestApiResponse<Student> response = getSingleRequestById(HeaderMapUtils.withAcceptJson(), 2);
		assertTrue(response.headerExists(CACHE_CONTROL, "private, no-transform, must-revalidate, max-age=30"));
	}

	@Test public void test_cache_control_collection_no_caching_no_storing() throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequest(HeaderMapUtils.withAcceptJson());
		assertTrue(response.headerExists(CACHE_CONTROL, "private, no-transform, must-revalidate, max-age=30"));
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

	@Test public void test_conditional_put_412() throws IOException
	{
		testConditionalPut412();
	}

	@Override protected StudyTripStudentRestClient newRestClient(HeaderMap headers)
	{
		return new StudyTripStudentRestClient(headers);
	}

	@Override public Student changeRessource(Student ressource, String newData)
	{
		ressource.setFirstName(newData);
		return ressource;
	}

	//TODO getSingle30SecondsPrivateCaching
	//TODO getNoStoringNoCachingCollection

	//TODO conditional get success
	//TODO conditional put success
	//TODO conditional get failure
	//TODO conditional put failure

	//maybe collection too?
	//TODO conditional get success
	//TODO conditional get failure

}
