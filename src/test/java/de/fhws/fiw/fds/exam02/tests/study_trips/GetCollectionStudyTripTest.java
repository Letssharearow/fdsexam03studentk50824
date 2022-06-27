package de.fhws.fiw.fds.exam02.tests.study_trips;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.tests.models.StudyTrip;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class GetCollectionStudyTripTest extends AbstractStudyTripTest
{
	@Test
	public void test_sort_by_start_date( ) throws IOException
	{
		final RestApiResponse<StudyTrip> response = getCollectionRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineBaseUrl( ) );

		final List<String> receivedStudyTripNames = extractStudyTripNames( response );

		final List<String> expectedStudyTripNames = Arrays.asList( ALL_STUDY_TRIPS_SORTED );

		assertEquals( expectedStudyTripNames, receivedStudyTripNames );
	}

	private List<String> extractStudyTripNames( final RestApiResponse<StudyTrip> response )
	{
		return response.getResponseCollectionData( )
			.stream( )
			.map( StudyTrip::getName )
			.collect( Collectors.toList( ) );
	}
}