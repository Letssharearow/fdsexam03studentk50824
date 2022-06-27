package de.fhws.fiw.fds.exam02.tests.study_trip_students;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

public class PutStudentOfStudyTripTest extends AbstractStudyTripStudentsTest
{
	private final static long ID_OF_UNLINKED_STUDENT = 4;

	private final static String GET_STUDENT_OF_STUDY_TRIP = "getStudentOfStudyTrip";

	private String definePutAndDeleteUrl( )
	{
		return defineBaseUrl( ) + "/" + ID_OF_UNLINKED_STUDENT;
	}

	@Test
	public void test_204_link_student( ) throws IOException
	{
		//link sub resource
		final RestApiResponse<Student> responseFromPutRequest = linkSecondaryResourceById( ID_OF_UNLINKED_STUDENT );

		assertEquals( 204, responseFromPutRequest.getLastStatusCode( ) );

		//load all linked sub resources and make sure that the linked sub resource appears in the result set
		final RestApiResponse<Student> responseFromGetCollectionRequest = getCollectionRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineBaseUrl( ) + "?showAll=false" );

		assertEquals( 200, responseFromGetCollectionRequest.getLastStatusCode( ) );

		final Optional<Student> linkedStudentOptional = responseFromGetCollectionRequest
			.getResponseCollectionData( )
			.stream( )
			.filter( s -> s.getId( ) == ID_OF_UNLINKED_STUDENT )
			.findFirst( );

		assertTrue( linkedStudentOptional.isPresent( ) );
	}

	@Test
	public void test_204_link_and_update_student( ) throws IOException
	{
		//the student (id = 4) is currently not linked to the studytrip (id = 1)
		final Student secondaryResource = loadSecondaryResourceById( HeaderMapUtils.withAcceptJson( ), ID_OF_UNLINKED_STUDENT );

		assertNotNull( secondaryResource );

		secondaryResource.setFirstName( "Hagrid" );

		//link student to studytrip. Also change the student's first name from "Harry" to "Hagrid"
		final RestApiResponse<Student> responseFromFirstPutRequest = linkSecondaryResource( HeaderMapUtils.withContentTypeJson( ), secondaryResource, defineBaseUrl( ) + "/" + ID_OF_UNLINKED_STUDENT );

		assertEquals( 204, responseFromFirstPutRequest.getLastStatusCode( ) );

		//check if first name was updated to "Hagrid"
		final RestApiResponse<Student> responseFromFirstGetSingleRequest = getSingleRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineBaseUrl( ) + "/" + ID_OF_UNLINKED_STUDENT );

		final Student studentFromFirstGetSingleRequest = responseFromFirstGetSingleRequest.getResponseSingleData( );

		assertNotNull( studentFromFirstGetSingleRequest );

		assertEquals( "Hagrid", studentFromFirstGetSingleRequest.getFirstName( ) );

		//now set the first name to the old value
		secondaryResource.setFirstName( "Harry" );

		//link and update
		final RestApiResponse<Student> responseFromSecondPutRequest = linkSecondaryResource( HeaderMapUtils.withContentTypeJson( ), secondaryResource, defineBaseUrl( ) + "/" + ID_OF_UNLINKED_STUDENT );

		assertEquals( 204, responseFromSecondPutRequest.getLastStatusCode( ) );

		//check if first name was updated to "Harry"
		final RestApiResponse<Student> responseFromSecondGetSingleRequest = getSingleRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineBaseUrl( ) + "/" + ID_OF_UNLINKED_STUDENT );

		final Student studentFromSecondGetSingleRequest = responseFromSecondGetSingleRequest.getResponseSingleData( );

		assertNotNull( studentFromSecondGetSingleRequest );

		assertEquals( "Harry", studentFromSecondGetSingleRequest.getFirstName( ) );
	}

	@Test
	public void test_hypermedia( ) throws IOException
	{
		final Student student = loadSecondaryResourceById( HeaderMapUtils.withAcceptJson( ), ID_OF_UNLINKED_STUDENT );

		final RestApiResponse<Student> responseFromPutRequest = linkSecondaryResource( HeaderMapUtils.withContentTypeJson( ), student, definePutAndDeleteUrl( ) );

		assertLinkHeaderEquals( responseFromPutRequest, SELF, definePutAndDeleteUrl( ) );
		assertLinkHeaderEquals( responseFromPutRequest, GET_STUDENT_OF_STUDY_TRIP, definePutAndDeleteUrl( ) );
	}

	@Test
	public void test_correct_media_type( ) throws IOException
	{
		final Student student = loadSecondaryResourceById( HeaderMapUtils.withAcceptJson( ), ID_OF_UNLINKED_STUDENT );

		final RestApiResponse<Student> responseFromPutRequest = linkSecondaryResource( HeaderMapUtils.withContentTypeJson( ), student, definePutAndDeleteUrl( ) );

		assertEquals( 204, responseFromPutRequest.getLastStatusCode( ) );
	}

	@Test
	public void test_incorrect_media_type( ) throws IOException
	{
		final Student student = loadSecondaryResourceById( HeaderMapUtils.withAcceptJson( ), ID_OF_UNLINKED_STUDENT );

		final RestApiResponse<Student> responseFromPutRequest = linkSecondaryResource( HeaderMapUtils.withContentTypeXml( ), student, definePutAndDeleteUrl( ) );

		assertEquals( 415, responseFromPutRequest.getLastStatusCode( ) );
	}
}