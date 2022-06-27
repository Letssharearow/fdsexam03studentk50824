package de.fhws.fiw.fds.exam02.tests.study_trip_students;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class GetStudentOfStudyTripTest extends AbstractStudyTripStudentsTest
{
	private final static String GET_ALL_LINKED_STUDENTS = "getAllLinkedStudents";

	private final static String UPDATE_STUDENT_OF_STUDY_TRIP = "updateStudentOfStudyTrip";

	private final static String UNLINK_STUDENT_FROM_STUDY_TRIP = "unlinkStudentFromStudyTrip";

	private final static String LINK_STUDENT_TO_STUDY_TRIP = "linkStudentToStudyTrip";

	@Test
	public void test_200( ) throws IOException
	{
		final RestApiResponse<Student> response = getSingleRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineBaseUrl( ) + "/1" );

		assertEquals( 200, response.getLastStatusCode( ) );

		final Student student = response.getResponseSingleData( );

		assertNotNull( student );

		assertEquals( 1, student.getId( ) );
		assertEquals( "Max", student.getFirstName( ) );
		assertEquals( "Mustermann", student.getLastName( ) );
		assertEquals( "max.mustermann@fhws.de", student.getEmail( ) );
		assertEquals( "BIN", student.getCourseOfStudy( ) );
		assertEquals( 2, student.getSemesterOfStudy( ) );
		assertEquals( 1111, student.getMatriculationNumber( ) );
	}

	@Test
	public void test_hypermedia_for_linked_student( ) throws IOException
	{
		final RestApiResponse<Student> response = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1 );

		assertLinkHeaderEquals( response, SELF, defineBaseUrl( ) + "/1" );
		assertLinkHeaderStartsWith( response, GET_ALL_LINKED_STUDENTS, defineBaseUrl( ) );
		assertLinkHeaderEquals( response, UPDATE_STUDENT_OF_STUDY_TRIP, defineBaseUrl( ) + "/1" );
		assertLinkHeaderEquals( response, UNLINK_STUDENT_FROM_STUDY_TRIP, defineBaseUrl( ) + "/1" );
		assertLinkHeaderDoesNotExist( response, LINK_STUDENT_TO_STUDY_TRIP );
	}

	@Test
	public void test_hypermedia_for_unlinked_student( ) throws IOException
	{
		final RestApiResponse<Student> response = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 4 );

		assertLinkHeaderEquals( response, SELF, defineBaseUrl( ) + "/4" );
		assertLinkHeaderStartsWith( response, GET_ALL_LINKED_STUDENTS, defineBaseUrl( ) );
		assertLinkHeaderDoesNotExist( response, UPDATE_STUDENT_OF_STUDY_TRIP );
		assertLinkHeaderDoesNotExist( response, UNLINK_STUDENT_FROM_STUDY_TRIP );
		assertLinkHeaderEquals( response, LINK_STUDENT_TO_STUDY_TRIP, defineBaseUrl( ) + "/4" );
	}

	@Test
	public void test_correct_media_type( ) throws IOException
	{
		final RestApiResponse<Student> response = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1 );

		assertEquals( 200, response.getLastStatusCode( ) );
	}

	@Test
	public void test_incorrect_media_type( ) throws IOException
	{
		final RestApiResponse<Student> response = getSingleRequestById( HeaderMapUtils.withAcceptXml( ), 1 );

		assertEquals( 406, response.getLastStatusCode( ) );
	}

	@Test
	public void test_cache_control( ) throws IOException
	{
		final RestApiResponse<Student> response = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1 );

		assertHeaderExists( response, CACHE_CONTROL );
	}
}