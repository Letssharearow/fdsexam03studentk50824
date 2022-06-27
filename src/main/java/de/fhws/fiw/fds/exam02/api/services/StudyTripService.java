package de.fhws.fiw.fds.exam02.api.services;

import de.fhws.fiw.fds.exam02.api.states.study_trip_students.*;
import de.fhws.fiw.fds.exam02.api.states.study_trips.*;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.exam02.models.StudyTrip;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;

import static de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize.*;

@Path( "studytrips" )
public class StudyTripService extends AbstractService
{
	@GET
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getCollectionStudyTrips(
		@DefaultValue( "" ) @QueryParam( "name" ) final String name,
		@DefaultValue( "" ) @QueryParam( "startDate" ) final LocalDate startDate,
		@DefaultValue( "" ) @QueryParam( "endDate" ) final LocalDate endDate,
		@DefaultValue( "" ) @QueryParam( "city" ) final String city,
		@DefaultValue( "" ) @QueryParam( "country" ) final String country,
		@DefaultValue( "0" ) @QueryParam( QUERY_PARAM_OFFSET ) final int offset,
		@DefaultValue( DEFAULT_PAGE_SIZE_STR ) @QueryParam( QUERY_PARAM_SIZE ) final int size )
	{
		final AbstractQuery<StudyTrip> query = new GetCollectionStudyTripsState.ByAttributes(
			name,
			startDate,
			endDate,
			city,
			country);

		query.setPagingBehavior( new PagingBehaviorUsingOffsetSize<>( offset, size ) );

		return new GetCollectionStudyTripsState.Builder( )
			.setQuery( query )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getSingleStudyTrip( @PathParam( "id" ) final long id )
	{
		return new GetSingleStudyTripState.Builder( )
			.setRequestedId( id )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@POST
	@Consumes( { MediaType.APPLICATION_JSON } )
	public Response createStudyTrip( final StudyTrip studyTrip )
	{
		return new PostStudyTripState.Builder( )
			.setModelToCreate( studyTrip )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@PUT
	@Path( "{id: \\d+}" )
	@Consumes( { MediaType.APPLICATION_JSON } )
	public Response updateStudyTrip( @PathParam( "id" ) final long id, final StudyTrip studyTrip )
	{
		return new PutStudyTripState.Builder( )
			.setRequestedId( id )
			.setModelToUpdate( studyTrip )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@DELETE
	@Path( "{id: \\d+}" )
	@Consumes( { MediaType.APPLICATION_JSON } )
	public Response deleteSingleStudyTrip( @PathParam( "id" ) final long id )
	{
		return new DeleteStudyTripState.Builder( )
			.setRequestedId( id )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@GET
	@Path( "{studyTripId: \\d+}/students" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getStudentsOfStudyTrip(
		@PathParam( "studyTripId" ) final long studyTripId,
		@DefaultValue( "false" ) @QueryParam( "showAll" ) final boolean showAll )
	{
		return new GetCollectionStudentsOfStudyTripState.Builder( )
			.setParentId( studyTripId )
			.setQuery( new GetCollectionStudentsOfStudyTripState.AllStudents( studyTripId, showAll ) )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@GET
	@Path( "{studyTripId: \\d+}/students/{studentId: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getStudentByIdOfStudyTrip(
		@PathParam( "studyTripId" ) final long studyTripId,
		@PathParam( "studentId" ) final long studentId )
	{
		return new GetSingleStudentOfStudyTripState.Builder( )
			.setParentId( studyTripId )
			.setRequestedId( studentId )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@POST
	@Path( "{studyTripId: \\d+}/students" )
	@Consumes( { MediaType.APPLICATION_JSON } )
	public Response createNewStudentOfStudyTrip( @PathParam( "studyTripId" ) final long studyTripId,
												 final Student student )
	{
		return new PostStudentOfStudyTripState.Builder( )
			.setParentId( studyTripId )
			.setModelToCreate( student )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@PUT
	@Path( "{studyTripId: \\d+}/students/{studentId: \\d+}" )
	@Consumes( { MediaType.APPLICATION_JSON } )
	public Response updateStudentOfStudyTrip(
		@PathParam( "studyTripId" ) final long studyTripId,
		@PathParam( "studentId" ) final long studentId, final Student student )
	{
		return new PutStudentOfStudyTripState.Builder( )
			.setParentId( studyTripId )
			.setRequestedId( studentId )
			.setModelToUpdate( student )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}

	@DELETE
	@Path( "{studyTripId: \\d+}/students/{studentId: \\d+}" )
	public Response deleteStudentOfStudyTrip(
		@PathParam( "studyTripId" ) final long studyTripId,
		@PathParam( "studentId" ) final long studentId )
	{
		return new DeleteStudentOfStudyTripState.Builder( )
			.setParentId( studyTripId )
			.setRequestedId( studentId )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}
}