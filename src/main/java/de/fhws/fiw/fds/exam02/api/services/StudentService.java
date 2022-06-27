package de.fhws.fiw.fds.exam02.api.services;

import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;
import de.fhws.fiw.fds.exam02.api.states.students.*;
import de.fhws.fiw.fds.exam02.models.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize.*;

@Path( "students" )
public class StudentService extends AbstractService
{
	@GET
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getCollectionStudents(
		@DefaultValue( "0" ) @QueryParam( QUERY_PARAM_OFFSET ) final int offset,
		@DefaultValue( DEFAULT_PAGE_SIZE_STR ) @QueryParam( QUERY_PARAM_SIZE ) final int size )
	{
		final AbstractQuery<Student> query = new GetCollectionStudentsState.AllStudents( );

		query.setPagingBehavior( new PagingBehaviorUsingOffsetSize<>( offset, size ) );

		return new GetCollectionStudentsState.Builder( )
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
	public Response getSingleStudent( @PathParam( "id" ) final long id )
	{
		return new GetSingleStudentState.Builder( )
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
	public Response createStudent( final Student student )
	{
		return new PostStudentState.Builder( )
			.setModelToCreate( student )
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
	public Response updateStudent( @PathParam( "id" ) final long id, final Student student )
	{
		return new PutStudentState.Builder( )
			.setRequestedId( id )
			.setModelToUpdate( student )
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
	public Response deleteSingleStudent( @PathParam( "id" ) final long id )
	{
		return new DeleteStudentState.Builder( )
			.setRequestedId( id )
			.setUriInfo( this.uriInfo )
			.setRequest( this.request )
			.setHttpServletRequest( this.httpServletRequest )
			.setContext( this.context )
			.build( )
			.execute( );
	}
}