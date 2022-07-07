package de.fhws.fiw.fds.exam02.api.states.study_trips;

import de.fhws.fiw.fds.exam02.api.hypermedia.rel_types.IStudyTripRelTypes;
import de.fhws.fiw.fds.exam02.api.hypermedia.uris.IStudyTripUri;
import de.fhws.fiw.fds.exam02.api.security.BearerAuthHelper;
import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.database.spi.IStudyTripStudentDao;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.exam02.models.StudyTrip;
import de.fhws.fiw.fds.exam02.models.StudyTripReport;
import de.fhws.fiw.fds.exam02.utils.study_trip.StudyTripDateUtils;
import de.fhws.fiw.fds.sutton.server.api.caching.CachingUtils;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

import static de.fhws.fiw.fds.exam02.api.states.StateHelper.addVaryHeader;
import static java.time.temporal.ChronoUnit.DAYS;

public class GetCollectionStudyTripReportState extends AbstractGetCollectionState<StudyTripReport>
{
	public GetCollectionStudyTripReportState(final Builder builder)
	{
		super(builder);
	}

	@Override protected void authorizeRequest()
	{
		BearerAuthHelper.accessControl(this.httpServletRequest, "admin");
	}

	@Override protected Response createResponse()
	{
		addVaryHeader(this.responseBuilder);
		return super.createResponse();
	}

	@Override protected void defineHttpResponseBody()
	{
		this.responseBuilder.entity(new GenericEntity<Collection<StudyTripReport>>(this.result.getResult())
		{
		});
	}

	@Override protected void defineTransitionLinks()
	{
		addLink(IStudyTripUri.REL_PATH, IStudyTripRelTypes.CREATE_STUDY_TRIP, getAcceptRequestHeader());
	}

	@Override protected void configureState()
	{
		this.responseBuilder.cacheControl(CachingUtils.createNoCacheNoStoreCaching());
	}

	public static class ByAttributes extends AbstractQuery<StudyTripReport>
	{
		protected final LocalDate intervalStart;

		protected final LocalDate intervalEnd;

		public ByAttributes(final LocalDate intervalStart, final LocalDate intervalEnd)
		{
			this.intervalStart = intervalStart;
			this.intervalEnd = intervalEnd;
		}

		@Override protected CollectionModelResult<StudyTripReport> doExecuteQuery()
		{
			final Collection<StudyTrip> studyTripsFromDb = DaoFactory.getInstance().getStudyTripDao()
				.readByPredicate(byAttributes()).getResult();

			return new CollectionModelResult<>(getResultCollection(studyTripsFromDb));
		}

		private static Collection<StudyTripReport> getResultCollection(Collection<StudyTrip> studyTrips)
		{
			IStudyTripStudentDao studentDao = DaoFactory.getInstance().getStudyTripStudentDao();
			Predicate<Student> all = student -> true;

			ArrayList<StudyTripReport> list = new ArrayList<>(studyTrips.size());
			studyTrips.forEach(studyTrip -> list.add(
				fromStudyTrip(studyTrip, studentDao.readAllByPredicate(1L, all).getResult().size())));

			return list;
		}

		private static StudyTripReport fromStudyTrip(StudyTrip value, int numberOfStudens)
		{
			long daysBetween = DAYS.between(value.getStartDate(), value.getEndDate());
			return new StudyTripReport(value.getCityName(), value.getCountryName(), (int) daysBetween, numberOfStudens);
		}

		protected Predicate<StudyTrip> byAttributes()
		{
			return this::matchDate;
		}

		private boolean matchDate(final StudyTrip studyTrip)
		{
			boolean isMatch = true;

			if (this.intervalStart != null && this.intervalEnd != null)
			{
				isMatch = new StudyTripDateUtils(studyTrip, this.intervalStart,
					this.intervalEnd).isStudyTripWithinInterval();
			}

			return isMatch;
		}
	}

	public static class Builder extends AbstractGetCollectionStateBuilder<StudyTripReport>
	{
		@Override public AbstractState build()
		{
			return new GetCollectionStudyTripReportState(this);
		}
	}
}