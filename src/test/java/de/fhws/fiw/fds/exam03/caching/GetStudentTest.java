package de.fhws.fiw.fds.exam03.caching;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.client.rest.resources.StudentRestClient;
import de.fhws.fiw.fds.exam02.tests.AbstractTest;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import de.fhws.fiw.fds.exam02.tests.models.StudyTrip;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class GetStudentTest extends AbstractTest<Student, StudentRestClient>
{

	public static final String STUDENTS = "students";

	@Test public void testGetStudentCollectionCaching() throws IOException
	{
		RestApiResponse<Student> response = getCollectionRequestByUrl(HeaderMapUtils.withAcceptJson(),
			defineCacheUrl() + STUDENTS);
		assertTrue(response.headerExists("X-Proxy-Cache", "MISS"));
		response = getCollectionRequestByUrl(HeaderMapUtils.withAcceptJson(), defineCacheUrl() + STUDENTS);
		assertTrue(response.headerExists("X-Proxy-Cache", "MISS"));
	}

	@Test public void testGetStudentSingleCaching() throws IOException
	{
		RestApiResponse<Student> response = getSingleRequestByUrl(HeaderMapUtils.withAcceptJson(),
			defineCacheUrl() + "students/4");
		assertTrue(response.headerExists("X-Proxy-Cache", "MISS"));
		response = getSingleRequestByUrl(HeaderMapUtils.withAcceptJson(), defineCacheUrl() + "students/4");
		assertTrue(response.headerExists("X-Proxy-Cache", "MISS"));
	}

	@Test public void test_cache_control_single_exists() throws IOException
	{
		final RestApiResponse<Student> response = getSingleRequestById(HeaderMapUtils.withAcceptJson(), 3);
		assertHeaderExists(response, CACHE_CONTROL);
	}

	@Test public void test_cache_control_single_private_max_age_30() throws IOException
	{
		final RestApiResponse<Student> response = getSingleRequestById(HeaderMapUtils.withAcceptJson(), 2);
		assertTrue(response.headerExists(CACHE_CONTROL, "private, no-transform, max-age=30"));
	}

	@Test public void test_cache_control_collection_no_cache_no_store() throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequestByUrl(HeaderMapUtils.withAcceptJson(),
			defineCacheUrl() + STUDENTS);
		assertTrue(response.headerExists(CACHE_CONTROL, "no-cache, no-store, no-transform"));
	}

	@Test public void test_cache_control_collection_exists() throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequestByUrl(HeaderMapUtils.withAcceptJson(),
			defineCacheUrl() + STUDENTS);
		assertHeaderExists(response, CACHE_CONTROL);
	}

	@Override protected StudentRestClient newRestClient(HeaderMap headers)
	{
		return new StudentRestClient(headers);
	}
}
