package de.fhws.fiw.fds.exam02.tests.study_trip_students;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.tests.models.AbstractModel;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DeleteStudentOfStudyTripTest extends AbstractStudyTripStudentsTest
{
	private final static long ID_OF_LINKED_STUDENT = 1;

	private final static String GET_ALL_LINKED_STUDENTS = "getAllLinkedStudents";

	private String definePutAndDeleteUrl( )
	{
		return defineBaseUrl( ) + "/" + ID_OF_LINKED_STUDENT;
	}

	@Test
	public void test_204_unlink_student( ) throws IOException
	{
		//unlink sub resource
		final RestApiResponse<Student> responseFromDeleteRequest = unlinkSecondaryResource( HeaderMapUtils.empty( ), definePutAndDeleteUrl( ) );

		assertEquals( 204, responseFromDeleteRequest.getLastStatusCode( ) );

		//load all linked sub resources and make sure that the unlinked sub resource does not appear in the result set
		final RestApiResponse<Student> responseFromGetCollectionRequest = getCollectionRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineBaseUrl( ) + "?showAll=false" );

		assertEquals( 200, responseFromGetCollectionRequest.getLastStatusCode( ) );

		final List<Long> idsOfLinkedStudents = responseFromGetCollectionRequest.getResponseCollectionData( )
			.stream( )
			.map( AbstractModel::getId )
			.collect( Collectors.toList( ) );

		assertFalse( idsOfLinkedStudents.contains( ID_OF_LINKED_STUDENT ) );
	}

	@Test
	public void test_hypermedia( ) throws IOException
	{
		final RestApiResponse<Student> response = unlinkSecondaryResource( HeaderMapUtils.empty( ), definePutAndDeleteUrl( ) );

		assertLinkHeaderEquals( response, GET_ALL_LINKED_STUDENTS, defineBaseUrl( ) );
	}
}