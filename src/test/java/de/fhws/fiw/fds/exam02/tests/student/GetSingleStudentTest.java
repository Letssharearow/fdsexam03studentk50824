package de.fhws.fiw.fds.exam02.tests.student;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class GetSingleStudentTest extends AbstractStudentTest
{
	public final static String UPDATE_STUDENT = "updateStudent";

	public final static String DELETE_STUDENT = "deleteStudent";

	@Test
	public void test_200( ) throws IOException
	{
		final RestApiResponse<Student> response = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1 );

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
	public void test_hypermedia( ) throws IOException
	{
		final RestApiResponse<Student> response = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1 );

		checkLinkHeaders( response );
	}

	private void checkLinkHeaders( final RestApiResponse<Student> response )
	{
		assertLinkHeaderEquals( response, UPDATE_STUDENT, BASE_URL + "students/{id}" );
		assertLinkHeaderEquals( response, DELETE_STUDENT, BASE_URL + "students/{id}" );
		assertLinkHeaderEquals( response, SELF, BASE_URL + "students/1" );
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
	public void test_conditional_get_304( ) throws IOException
	{
		//user A loads a student
		final RestApiResponse<Student> responseFromFirstGetRequest = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1 );

		assertHeaderExists( responseFromFirstGetRequest, ETAG );

		final String etag = responseFromFirstGetRequest.getEtagHeader( );

		//user A revalidates
		final HeaderMap headersForSecondGetRequest = HeaderMapUtils.withAcceptJson( );
		headersForSecondGetRequest.addHeader( IF_NONE_MATCH, etag );

		final RestApiResponse<Student> responseFromSecondGetRequest = getSingleRequestById( headersForSecondGetRequest, 1 );

		assertEquals( 304, responseFromSecondGetRequest.getLastStatusCode( ) );
	}

	@Test
	public void test_conditional_get_200( ) throws IOException
	{
		//user A loads a student
		final RestApiResponse<Student> responseFromFirstGetRequest = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1 );
		final Student student = responseFromFirstGetRequest.getResponseSingleData( );

		assertHeaderExists( responseFromFirstGetRequest, ETAG );

		final String initialEtag = responseFromFirstGetRequest.getEtagHeader( );

		//user B updates this resource
		student.setFirstName( "Robert" );

		final HeaderMap headersForPutRequestForUserB = HeaderMapUtils.withContentTypeJson( );
		headersForPutRequestForUserB.addHeader( IF_MATCH, initialEtag );

		final RestApiResponse<Student> responseFromPutRequest = putRequest( headersForPutRequestForUserB, student );

		assertEquals( 204, responseFromPutRequest.getLastStatusCode( ) );

		final String newEtag = responseFromPutRequest.getEtagHeader( );

		//user A revalidates using old etag
		final HeaderMap headersForSecondGetRequest = HeaderMapUtils.withAcceptJson( );
		headersForSecondGetRequest.addHeader( IF_NONE_MATCH, initialEtag );

		final RestApiResponse<Student> responseFromSecondGetRequest = getSingleRequestById( headersForSecondGetRequest, 1 );

		assertEquals( 200, responseFromSecondGetRequest.getLastStatusCode( ) );
		assertNotNull( responseFromSecondGetRequest.getResponseSingleData( ) );
	}
}