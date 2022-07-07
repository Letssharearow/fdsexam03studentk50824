package de.fhws.fiw.fds.exam02.api.states.study_trips;

import de.fhws.fiw.fds.exam02.api.hypermedia.rel_types.IStudyTripRelTypes;
import de.fhws.fiw.fds.exam02.api.hypermedia.uris.IStudyTripUri;
import de.fhws.fiw.fds.exam02.api.states.BearerAuthHelper;
import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.StudyTrip;
import de.fhws.fiw.fds.exam02.utils.study_trip.StudyTripDateUtils;
import de.fhws.fiw.fds.sutton.server.api.caching.CachingUtils;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import static de.fhws.fiw.fds.exam02.api.states.StateHelper.addVaryHeader;

public class GetCollectionStudyTripReportState extends AbstractGetCollectionState<StudyTrip>
{
	public GetCollectionStudyTripReportState(final Builder builder)
	{
		super(builder);
	}

	@Override protected void authorizeRequest()
	{
		BearerAuthHelper.accessControl(this.httpServletRequest, "lecturer", "admin");
	}

	@Override protected Response createResponse()
	{
		addVaryHeader(this.responseBuilder);
		return super.createResponse();
	}

	@Override protected void defineHttpResponseBody()
	{
		this.responseBuilder.entity(new GenericEntity<Collection<StudyTrip>>(this.result.getResult())
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

	public static class ByAttributes extends AbstractQuery<StudyTrip>
	{
		protected final LocalDate intervalStart;

		protected final LocalDate intervalEnd;

		public ByAttributes(final LocalDate intervalStart, final LocalDate intervalEnd)
		{
			this.intervalStart = intervalStart;
			this.intervalEnd = intervalEnd;
		}

		@Override protected CollectionModelResult<StudyTrip> doExecuteQuery()
		{
			final Collection<StudyTrip> studyTripsFromDb = DaoFactory.getInstance().getStudyTripDao()
				.readByPredicate(byAttributes()).getResult();

			final List<StudyTrip> sortedStudyTrips = new LinkedList<>(studyTripsFromDb);
			sortedStudyTrips.sort(StudyTrip.getComparator());

			return new CollectionModelResult<>(sortedStudyTrips);
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

	public static class Builder extends AbstractGetCollectionStateBuilder<StudyTrip>
	{
		@Override public AbstractState build()
		{
			return new GetCollectionStudyTripReportState(this);
		}
	}
}