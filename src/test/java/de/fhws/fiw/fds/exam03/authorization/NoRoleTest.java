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

public class NoRoleTest extends AbstractTest<Student, StudentRestClient>
{
	@Test public void test_get_token_401() throws IOException
	{
		RestApiResponse<Student> response = getSingleRequestByUrl(HeaderMapUtils.withAcceptJson(),
			defineBaseUrl() + "me");
		assertEquals(401, response.getLastStatusCode());

	}

	@Test public void test_get_Student_without_token_401() throws IOException
	{
		RestApiResponse<Student> response = getSingleRequestByUrl(HeaderMapUtils.withAcceptJson(),
			defineBaseUrl() + "students/1");
		assertEquals(401, response.getLastStatusCode());

	}

	private Student defineNewResource()
	{
		return new Student("Patrick", "Müller", "patrick.mueller@fhws.de", "BIN", 5, 1234);
	}

	@Test public void test_post_request_401() throws IOException
	{
		RestApiResponse<Student> response = postRequestByUrl(HeaderMapUtils.withContentTypeJson(), defineNewResource(),
			defineBaseUrl() + "students");
		assertEquals(401, response.getLastStatusCode());

	}

	@Override protected StudentRestClient newRestClient(HeaderMap headers)
	{
		return new StudentRestClient(headers, "", "", false);
	}
}
