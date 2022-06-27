package de.fhws.fiw.fds.exam02.tests.study_trip_students;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class PostNewStudentOfStudyTripTest extends AbstractStudyTripStudentsTest
{
	@Test
	public void test_201( ) throws IOException
	{
		//create new sub resource
		final RestApiResponse<Student> response = postRequestByUrl( HeaderMapUtils.withContentTypeJson( ), defineNewResource( ), defineBaseUrl( ) );
		final String locationHeader = response.getLocationHeader( );

		assertEquals( 201, response.getLastStatusCode( ) );
		assertNotNull( locationHeader );
		assertTrue( locationHeader.startsWith( defineBaseUrl( ) ) );

		//test if resource attributes are set correctly
		final RestApiResponse<Student> responseFromGetSingleRequest = getSingleRequestByUrl( HeaderMapUtils.withAcceptJson( ), locationHeader );
		final Student student = responseFromGetSingleRequest.getResponseSingleData( );

		assertNotNull( student );

		assertTrue( student.getId( ) > 0 );
		assertEquals( "Patrick", student.getFirstName( ) );
		assertEquals( "Müller", student.getLastName( ) );
		assertEquals( "patrick.mueller@fhws.de", student.getEmail( ) );
		assertEquals( "BIN", student.getCourseOfStudy( ) );
		assertEquals( 5, student.getSemesterOfStudy( ) );
		assertEquals( 1234, student.getMatriculationNumber( ) );
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
	public void test_correct_media_type( ) throws IOException
	{
		final RestApiResponse<Student> response = postRequestByUrl( HeaderMapUtils.withContentTypeJson( ), defineNewResource( ), defineBaseUrl( ) );

		assertEquals( 201, response.getLastStatusCode( ) );
	}

	@Test
	public void test_incorrect_media_type( ) throws IOException
	{
		final RestApiResponse<Student> response = postRequestByUrl( HeaderMapUtils.withContentTypeXml( ), defineNewResource( ), defineBaseUrl( ) );

		assertEquals( 415, response.getLastStatusCode( ) );
	}
}