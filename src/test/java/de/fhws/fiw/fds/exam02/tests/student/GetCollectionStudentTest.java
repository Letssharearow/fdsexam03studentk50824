package de.fhws.fiw.fds.exam02.tests.student;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class GetCollectionStudentTest extends AbstractStudentTest
{
	private final static String CREATE_STUDENT = "createStudent";

	@Test
	public void test_200( ) throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequest( HeaderMapUtils.withAcceptJson( ) );

		assertEquals( 200, response.getLastStatusCode( ) );
		assertEquals( 4, response.getResponseCollectionData( ).size( ) );
	}

	@Test
	public void test_hypermedia( ) throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequest( HeaderMapUtils.withAcceptJson( ) );

		assertLinkHeaderStartsWith( response, CREATE_STUDENT, BASE_URL + "students" );
		assertLinkHeaderStartsWith( response, SELF, BASE_URL + "students" );
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
	public void test_pagination_negative_offset_parameter( ) throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineBaseUrl( ) + "?offset=-2&size=2" );

		assertEquals( 200, response.getLastStatusCode( ) );
		assertEquals( 2, response.getResponseCollectionData( ).size( ) );

		final List<String> expectedStudentNames = Arrays.asList( JAMES_BOND, ERIKA_MUSTERMANN );

		assertEquals( expectedStudentNames, extractStudentNamesFromResponse( response ) );
	}

	@Test
	public void test_pagination_valid_offset_parameter( ) throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineBaseUrl( ) + "?offset=2&size=2" );

		assertEquals( 200, response.getLastStatusCode( ) );
		assertEquals( 2, response.getResponseCollectionData( ).size( ) );

		final List<String> expectedStudentNames = Arrays.asList( MAX_MUSTERMANN, HARRY_POTTER );

		assertEquals( expectedStudentNames, extractStudentNamesFromResponse( response ) );
	}

	//TODO: Auskommentiert, da gewünschtes Verhalten nicht bekannt
	/*@Test
	public void test_pagination_exceeding_offset_parameter( ) throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineBaseUrl( ) + "?offset=10&size=2" );

		assertEquals( 200, response.getLastStatusCode( ) );
		assertEquals( 0, response.getResponseCollectionData( ).size( ) );
	}

	@Test
	public void test_pagination_negative_size_parameter( ) throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineBaseUrl( ) + "?offset=0&size=-1" );

		assertEquals( 200, response.getLastStatusCode( ) );
		assertEquals( 0, response.getResponseCollectionData( ).size( ) );
	}

	@Test
	public void test_pagination_zero_size_parameter( ) throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineBaseUrl( ) + "?offset=0&size=0" );

		assertEquals( 200, response.getLastStatusCode( ) );
		assertEquals( 0, response.getResponseCollectionData( ).size( ) );
	}*/

	@Test
	public void test_pagination_valid_size_parameter( ) throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineBaseUrl( ) + "?offset=0&size=2" );

		assertEquals( 200, response.getLastStatusCode( ) );
		assertEquals( 2, response.getResponseCollectionData( ).size( ) );

		final List<String> expectedStudentNames = Arrays.asList( JAMES_BOND, ERIKA_MUSTERMANN );

		assertEquals( expectedStudentNames, extractStudentNamesFromResponse( response ) );
	}

	private List<String> extractStudentNamesFromResponse( final RestApiResponse<Student> response )
	{
		return response.getResponseCollectionData( )
			.stream( )
			.map( s -> s.getFirstName( ) + " " + s.getLastName( ) )
			.collect( Collectors.toList( ) );
	}

	@Test
	public void test_sort_by_last_name_and_first_name( ) throws IOException
	{
		final RestApiResponse<Student> response = getCollectionRequest( HeaderMapUtils.withAcceptJson( ) );

		final List<String> expectedStudentNames = Arrays.asList( STUDENTS_SORTED );

		assertEquals( expectedStudentNames, extractStudentNamesFromResponse( response ) );
	}
}