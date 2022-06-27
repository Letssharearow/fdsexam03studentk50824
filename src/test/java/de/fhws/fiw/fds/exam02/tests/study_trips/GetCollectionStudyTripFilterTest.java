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

public class GetCollectionStudyTripFilterTest extends AbstractStudyTripTest
{
	@Test
	public void filter_name_empty_string( ) throws IOException
	{
		filterTest( "?name=", ALL_STUDY_TRIPS_SORTED );
	}

	@Test
	public void filter_name_partial_string( ) throws IOException
	{
		filterTest( "?name=Würz", WUE_21, WUE_22 );
	}

	@Test
	public void filter_name_ignore_case_string( ) throws IOException
	{
		filterTest( "?name=WüRz", WUE_21, WUE_22 );
	}

	@Test
	public void filter_time_period_missing_end_parameter( ) throws IOException
	{
		filterTest( "?startDate=2022-02-01", ALL_STUDY_TRIPS_SORTED );
	}

	@Test
	public void filter_time_period_missing_start_parameter( ) throws IOException
	{
		filterTest( "?startDate=2022-03-31", ALL_STUDY_TRIPS_SORTED );
	}

	@Test
	public void filter_time_period_before_specified_interval( ) throws IOException
	{
		filterTest( "?startDate=2022-02-01&endDate=2022-02-14" );
	}

	@Test
	public void filter_time_period_at_start_date_of_specified_interval( ) throws IOException
	{
		filterTest( "?startDate=2022-02-01&endDate=2022-03-31", INDIA_GATEWAY_22 );
	}

	@Test
	public void filter_time_period_complete_specified_interval( ) throws IOException
	{
		filterTest( "?startDate=2022-02-01&endDate=2022-02-15", INDIA_GATEWAY_22 );
	}

	@Test
	public void filter_time_period_at_end_date_of_specified_interval( ) throws IOException
	{
		filterTest( "?startDate=2022-03-01&endDate=2022-03-31", INDIA_GATEWAY_22 );
	}

	@Test
	public void filter_time_period_after_specified_interval( ) throws IOException
	{
		filterTest( "?startDate=2022-03-02&endDate=2022-03-31" );
	}

	@Test
	public void filter_city_empty_string( ) throws IOException
	{
		filterTest( "?cityName=", ALL_STUDY_TRIPS_SORTED );
	}

	@Test
	public void filter_city_partial_string( ) throws IOException
	{
		filterTest( "?city=Würzb", WUE_21, WUE_22 );
	}

	@Test
	public void filter_city_ignore_case_string( ) throws IOException
	{
		filterTest( "?city=WüRzB", WUE_21, WUE_22 );
	}

	@Test
	public void filter_country_empty_string( ) throws IOException
	{
		filterTest( "?country=", ALL_STUDY_TRIPS_SORTED );
	}

	@Test
	public void filter_country_partial_string( ) throws IOException
	{
		filterTest( "?country=Frankre", FR_20 );
	}

	@Test
	public void filter_country_ignore_case_string( ) throws IOException
	{
		filterTest( "?country=FrAnKrE", FR_20 );
	}

	@Test
	public void filter_using_all_query_parameters( ) throws IOException
	{
		filterTest( "?name=India Gateway" +
				"&startDate=2022-02-01" +
				"&endDate=2022-04-01" +
				"&supervisorNames=Peter Br" +
				"&city=Bangalo" +
				"&country=Ind" +
				"&isNational=true",
			INDIA_GATEWAY_22 );
	}

	private void filterTest( final String queryString, final String... expectedNamesOfStudyTrips ) throws IOException
	{
		final RestApiResponse<StudyTrip> response = getCollectionRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineBaseUrl( ) + queryString );

		assertEquals( 200, response.getLastStatusCode( ) );
		assertEquals( expectedNamesOfStudyTrips.length, response.getResponseCollectionData( ).size( ) );

		final List<String> namesOfStudyTrips = response.getResponseCollectionData( )
			.stream( )
			.map( StudyTrip::getName )
			.collect( Collectors.toList( ) );

		final List<String> expectedNames = Arrays.asList( expectedNamesOfStudyTrips );

		assertEquals( expectedNames, namesOfStudyTrips );
	}
}