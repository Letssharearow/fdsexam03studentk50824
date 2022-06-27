package de.fhws.fiw.fds.exam02.tests.student;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PutStudentTest extends AbstractStudentTest
{
	public static final String GET_SINGLE_STUDENT = "getSingleStudent";

	@Test
	public void test_204( ) throws IOException
	{
		final Student student = defineStudent( );

		final RestApiResponse<Student> response = putRequest( HeaderMapUtils.withContentTypeJson( ), student );

		assertEquals( 204, response.getLastStatusCode( ) );
	}

	private Student defineStudent( )
	{
		final Student student = new Student(
			"Max",
			"Mustermann",
			"max.mustermann@fhws.de",
			"BIN",
			2,
			1111 );

		student.setId( 1 );

		return student;
	}

	@Test
	public void test_resource_updated_correctly( ) throws IOException
	{
		final Student originalStudent = getResourceById( 1 );

		assertEquals( "Max", originalStudent.getFirstName( ) );

		originalStudent.setFirstName( "Julian" );

		final RestApiResponse<Student> responseFromFirstPutRequest = putRequest( HeaderMapUtils.withContentTypeJson( ), originalStudent );

		assertEquals( 204, responseFromFirstPutRequest.getLastStatusCode( ) );

		final Student studentAfterFirstPutRequest = getResourceById( 1 );

		assertEquals( "Julian", studentAfterFirstPutRequest.getFirstName( ) );
	}

	private Student getResourceById( final long id ) throws IOException
	{
		final RestApiResponse<Student> responseFromFirstGetRequest = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), id );

		final Student student = responseFromFirstGetRequest.getResponseSingleData( );

		assertNotNull( student );

		return student;
	}

	@Test
	public void test_hypermedia( ) throws IOException
	{
		final RestApiResponse<Student> response = putRequest( HeaderMapUtils.withContentTypeJson( ), defineStudent( ) );

		assertLinkHeaderEquals( response, GET_SINGLE_STUDENT, BASE_URL + "students/1" );
		assertLinkHeaderEquals( response, SELF, BASE_URL + "students/1" );
	}

	@Test
	public void test_correct_media_type( ) throws IOException
	{
		final RestApiResponse<Student> response = putRequest( HeaderMapUtils.withContentTypeJson( ), defineStudent( ) );

		assertEquals( 204, response.getLastStatusCode( ) );
	}

	@Test
	public void test_incorrect_media_type( ) throws IOException
	{
		final RestApiResponse<Student> response = putRequest( HeaderMapUtils.withContentTypeXml( ), defineStudent( ) );

		assertEquals( 415, response.getLastStatusCode( ) );
	}

	@Test
	public void test_conditional_put_204( ) throws IOException
	{
		//user A loads a student
		final RestApiResponse<Student> responseFromGetRequest = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1 );
		final Student student = responseFromGetRequest.getResponseSingleData( );

		assertHeaderExists( responseFromGetRequest, ETAG );

		final String initialEtag = responseFromGetRequest.getEtagHeader( );

		//user A updates this resource
		student.setFirstName( "Klaus" );

		final HeaderMap headersForPutRequestForUserA = HeaderMapUtils.withContentTypeJson( );
		headersForPutRequestForUserA.addHeader( IF_MATCH, initialEtag );

		final RestApiResponse<Student> responseFromPutRequest = putRequest( headersForPutRequestForUserA, student );

		assertEquals( 204, responseFromPutRequest.getLastStatusCode( ) );

		assertHeaderExists( responseFromPutRequest, ETAG );
	}

	@Test
	public void test_conditional_put_412( ) throws IOException
	{
		//user A loads a student
		final RestApiResponse<Student> responseFromGetRequestForUserA = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1 );
		final Student studentForUserA = responseFromGetRequestForUserA.getResponseSingleData( );

		assertHeaderExists( responseFromGetRequestForUserA, ETAG );

		final String initialEtagForUserA = responseFromGetRequestForUserA.getEtagHeader( );

		//user B loads the same student
		final RestApiResponse<Student> responseFromGetRequestForUserB = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1 );
		final Student studentForUserB = responseFromGetRequestForUserB.getResponseSingleData( );

		assertHeaderExists( responseFromGetRequestForUserB, ETAG );

		final String initialEtagForUserB = responseFromGetRequestForUserB.getEtagHeader( );

		//user B updates this resource
		studentForUserB.setFirstName( "Robert" );

		final HeaderMap headersForPutRequestForUserB = HeaderMapUtils.withContentTypeJson( );
		headersForPutRequestForUserB.addHeader( IF_MATCH, initialEtagForUserB );

		final RestApiResponse<Student> responseFromPutRequestForUserB = putRequest( headersForPutRequestForUserB, studentForUserB );

		assertEquals( 204, responseFromPutRequestForUserB.getLastStatusCode( ) );

		final String newEtagForUserB = responseFromPutRequestForUserB.getEtagHeader( );

		//user A updates this resource
		studentForUserA.setFirstName( "Klaus" );

		final HeaderMap headersForPutRequestForUserA = HeaderMapUtils.withContentTypeJson( );
		headersForPutRequestForUserA.addHeader( IF_MATCH, initialEtagForUserA );

		final RestApiResponse<Student> responseFromPutRequestForUserA = putRequest( headersForPutRequestForUserA, studentForUserA );

		assertEquals( 412, responseFromPutRequestForUserA.getLastStatusCode( ) );
	}
}