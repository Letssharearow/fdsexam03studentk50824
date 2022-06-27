package de.fhws.fiw.fds.exam02.tests.student;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class PostStudentTest extends AbstractStudentTest
{
	@Test
	public void test_201( ) throws IOException
	{
		final RestApiResponse<Student> response = postRequest( HeaderMapUtils.withContentTypeJson( ), defineNewResource( ) );
		final String locationHeader = response.getLocationHeader( );

		assertEquals( 201, response.getLastStatusCode( ) );
		assertNotNull( locationHeader );
		assertTrue( locationHeader.startsWith( BASE_URL + "students" ) );
	}

	private Student defineNewResource( )
	{
		return new Student(
			"Patrick",
			"Müller",
			"patrick.mueller@fhws.de",
			"BIN",
			5,
			1234 );
	}

	@Test
	public void test_resource_created_correctly( ) throws IOException
	{
		final RestApiResponse<Student> responseFromPostRequest = postRequest( HeaderMapUtils.withContentTypeJson( ), defineNewResource( ) );
		final String locationHeader = responseFromPostRequest.getLocationHeader( );
		final RestApiResponse<Student> responseFromGetRequest = getSingleRequestByUrl( HeaderMapUtils.withAcceptJson( ), locationHeader );

		assertEquals( 200, responseFromGetRequest.getLastStatusCode( ) );

		final Student student = responseFromGetRequest.getResponseSingleData( );

		assertNotNull( student );

		assertTrue( student.getId( ) > 0 );
		assertEquals( "Patrick", student.getFirstName( ) );
		assertEquals( "Müller", student.getLastName( ) );
		assertEquals( "patrick.mueller@fhws.de", student.getEmail( ) );
		assertEquals( "BIN", student.getCourseOfStudy( ) );
		assertEquals( 5, student.getSemesterOfStudy( ) );
		assertEquals( 1234, student.getMatriculationNumber( ) );
	}

	@Test
	public void test_correct_media_type( ) throws IOException
	{
		final RestApiResponse<Student> response = postRequest( HeaderMapUtils.withContentTypeJson( ), defineNewResource( ) );
		final String locationHeader = response.getLocationHeader( );

		assertEquals( 201, response.getLastStatusCode( ) );
		assertNotNull( locationHeader );
	}

	@Test
	public void test_incorrect_media_type( ) throws IOException
	{
		final RestApiResponse<Student> response = postRequest( HeaderMapUtils.withContentTypeXml( ), defineNewResource( ) );

		assertEquals( 415, response.getLastStatusCode( ) );
		assertNull( response.getResponseSingleData( ) );
	}
}