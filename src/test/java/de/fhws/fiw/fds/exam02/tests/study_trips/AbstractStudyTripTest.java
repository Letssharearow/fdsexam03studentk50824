package de.fhws.fiw.fds.exam02.tests.study_trips;

import de.fhws.fiw.fds.exam02.client.rest.resources.StudyTripRestClient;
import de.fhws.fiw.fds.exam02.tests.models.StudyTrip;
import de.fhws.fiw.fds.exam02.tests.AbstractTest;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;

public abstract class AbstractStudyTripTest extends AbstractTest<StudyTrip, StudyTripRestClient>
{
	protected final static String WUE_21 = "Würzburg 2021";

	protected final static String WUE_22 = "Würzburg 2022";

	protected final static String SW_21 = "Schweinfurt 2021";

	protected final static String INDIA_GATEWAY_22 = "India Gateway Program 2022";

	protected final static String INDIA_TRIP_00 = "Indienreise 2000";

	protected final static String FR_20 = "Frankreich 2020";

	protected final static String[] NATIONAL_STUDY_TRIPS_SORTED = new String[]{ SW_21, WUE_21, WUE_22 };

	protected final static String[] INTERNATIONAL_STUDY_TRIPS_SORTED = new String[]{ INDIA_TRIP_00, FR_20, INDIA_GATEWAY_22 };

	protected final static String[] ALL_STUDY_TRIPS_SORTED = new String[]{ INDIA_TRIP_00, FR_20, SW_21, WUE_21, WUE_22, INDIA_GATEWAY_22 };

	@Override
	protected StudyTripRestClient newRestClient( final HeaderMap headers )
	{
		return new StudyTripRestClient( headers );
	}

	@Override
	protected String defineBaseUrl( )
	{
		return super.defineBaseUrl( ) + "studytrips";
	}
}