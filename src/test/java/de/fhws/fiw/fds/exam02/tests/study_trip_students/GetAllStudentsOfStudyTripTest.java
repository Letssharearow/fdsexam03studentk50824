package de.fhws.fiw.fds.exam02.tests.study_trip_students;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetAllStudentsOfStudyTripTest extends AbstractStudyTripStudentsTest
{
	private final static String CREATE_STUDENT_OF_STUDY_TRIP = "createStudentOfStudyTrip";

	private final static String GET_ALL_LINKABLE_STUDENTS = "getAllLinkableStudents";

	@Test
	public void test_200_show_all_false( ) throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineBaseUrl( ) + "?showAll=false" );

		assertEquals( 200, response.getLastStatusCode( ) );
		assertEquals( 3, response.getResponseCollectionData( ).size( ) );

		final Collection<String> namesOfLinkedStudents = response.getResponseCollectionData( )
			.stream( )
			.map( Student::getFirstName )
			.collect( Collectors.toList( ) );

		assertTrue( namesOfLinkedStudents.containsAll( Arrays.asList( "Max", "James", "Erika" ) ) );
	}

	@Test
	public void test_200_show_all_true( ) throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineBaseUrl( ) + "?showAll=true" );

		assertEquals( 200, response.getLastStatusCode( ) );
		assertEquals( 4, response.getResponseCollectionData( ).size( ) );
	}

	@Test
	public void test_hypermedia( ) throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequest( HeaderMapUtils.withAcceptJson( ) );

		assertLinkHeaderStartsWith( response, SELF, defineBaseUrl( ) );
		assertLinkHeaderStartsWith( response, CREATE_STUDENT_OF_STUDY_TRIP, defineBaseUrl( ) );
		assertLinkHeaderStartsWith( response, GET_ALL_LINKABLE_STUDENTS, defineBaseUrl( ) );
	}

	@Test
	public void test_correct_media_type( ) throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequest( HeaderMapUtils.withAcceptJson( ) );

		assertEquals( 200, response.getLastStatusCode( ) );
	}

	@Test
	public void test_incorrect_media_type( ) throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequest( HeaderMapUtils.withAcceptXml( ) );

		assertEquals( 406, response.getLastStatusCode( ) );
	}

	@Test
	public void test_sort_by_last_name_and_first_name( ) throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineBaseUrl( ) + "?showAll=true" );

		final Collection<String> receivedFullNamesOfStudents = extractFullNamesOfStudents( response );

		final Collection<String> expectedFullNamesOfStudents = Arrays.asList( STUDENTS_SORTED );

		assertEquals( expectedFullNamesOfStudents, receivedFullNamesOfStudents );
	}

	private List<String> extractFullNamesOfStudents( final RestApiResponse<Student> response )
	{
		return response.getResponseCollectionData( )
			.stream( )
			.map( s -> s.getFirstName( ) + " " + s.getLastName( ) )
			.collect( Collectors.toList( ) );
	}
}