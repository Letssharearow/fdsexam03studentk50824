package de.fhws.fiw.fds.exam02.utils.study_trip;

import de.fhws.fiw.fds.exam02.models.StudyTrip;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class StudyTripDateUtils
{
	private final StudyTrip studyTrip;

	private final LocalDate intervalStart;

	private final LocalDate intervalEnd;

	public StudyTripDateUtils(
		final StudyTrip studyTrip,
		final LocalDate intervalStart,
		final LocalDate intervalEnd )
	{
		this.studyTrip = studyTrip;
		this.intervalStart = intervalStart;
		this.intervalEnd = intervalEnd;
	}

	public boolean isStudyTripWithinInterval( )
	{
		boolean isWithinInterval = true;

		if ( isIntervalDefined( ) )
		{
			isWithinInterval = checkInterval( );
		}

		return isWithinInterval;
	}

	private boolean isIntervalDefined( )
	{
		return isDateDefined( this.intervalStart ) && isDateDefined( this.intervalEnd );
	}

	private boolean isDateDefined( final LocalDate date )
	{
		return date != null;
	}

	private boolean checkInterval( )
	{
		final int studyTripDurationInDays = computeStudyTripDuration( );

		LocalDate currentDate = this.studyTrip.getStartDate( );

		boolean isStudyTripInInterval = false;

		for ( int i = 0; i < studyTripDurationInDays; i++ )
		{
			if ( isDateInInterval( currentDate ) )
			{
				isStudyTripInInterval = true;

				break;
			}

			currentDate = currentDate.plusDays( 1 );
		}

		return isStudyTripInInterval;
	}

	private int computeStudyTripDuration( )
	{
		return ( int ) ChronoUnit.DAYS.between( this.studyTrip.getStartDate( ), this.studyTrip.getEndDate( ) ) + 1;
	}

	private boolean isDateInInterval( final LocalDate date )
	{
		return date.isEqual( this.intervalStart ) ||
			( date.isAfter( this.intervalStart ) && date.isBefore( this.intervalEnd ) ) ||
			date.isEqual( this.intervalEnd );
	}
}