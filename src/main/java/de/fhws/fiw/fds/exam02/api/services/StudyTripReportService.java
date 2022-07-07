package de.fhws.fiw.fds.exam02.api.services;

import de.fhws.fiw.fds.exam02.api.states.study_trips.GetCollectionStudyTripReportState;
import de.fhws.fiw.fds.exam02.models.StudyTripReport;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;

@Path("studytripReport") public class StudyTripReportService extends AbstractService
{

	@GET @Produces({ MediaType.APPLICATION_JSON, "text/csv", "text/plain" }) public Response getStudyTripReport(
		@DefaultValue("") @QueryParam("startDate") final LocalDate startDate,
		@DefaultValue("") @QueryParam("endDate") final LocalDate endDate)
	{
		final AbstractQuery<StudyTripReport> query = new GetCollectionStudyTripReportState.ByAttributes(startDate,
			endDate);

		//query.setPagingBehavior(new PagingBehaviorUsingOffsetSize<>(offset, size));

		return new GetCollectionStudyTripReportState.Builder().setQuery(query).setUriInfo(this.uriInfo)
			.setRequest(this.request).setHttpServletRequest(this.httpServletRequest).setContext(this.context).build()
			.execute();
	}
}