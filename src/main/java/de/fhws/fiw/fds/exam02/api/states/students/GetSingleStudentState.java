package de.fhws.fiw.fds.exam02.api.states.students;

import de.fhws.fiw.fds.exam02.api.hypermedia.rel_types.IStudentRelTypes;
import de.fhws.fiw.fds.exam02.api.hypermedia.uris.IStudentUri;
import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.sutton.server.api.caching.CachingUtils;
import de.fhws.fiw.fds.sutton.server.api.caching.EtagGenerator;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Response;

public class GetSingleStudentState extends AbstractGetState<Student>
{
	public GetSingleStudentState( final Builder builder )
	{
		super( builder );
	}

	@Override protected void authorizeRequest( )
	{

	}

	@Override protected SingleModelResult<Student> loadModel( )
	{
		return DaoFactory.getInstance( ).getStudentDao( ).readById( this.requestedId );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( IStudentUri.REL_PATH_ID, IStudentRelTypes.UPDATE_SINGLE_STUDENT, getAcceptRequestHeader( ) );
		addLink( IStudentUri.REL_PATH_ID, IStudentRelTypes.DELETE_SINGLE_STUDENT, getAcceptRequestHeader( ) );
	}

	@Override protected void defineHttpCaching( )
	{
		this.responseBuilder.cacheControl( CachingUtils.create30SecondsPrivateCaching( ) );
	}

	@Override protected boolean clientKnowsCurrentModelState( final AbstractModel modelFromDatabase )
	{
		return doesEtagMatch( modelFromDatabase );
	}

	private boolean doesEtagMatch( final AbstractModel modelFromDatabase )
	{
		EntityTag etag = EtagGenerator.createEntityTag( modelFromDatabase );

		Response.ResponseBuilder builder = this.request.evaluatePreconditions( etag );

		return builder != null;
	}

	@Override protected Response createResponse( )
	{
		addEtagHeader( );

		return super.createResponse( );
	}

	private void addEtagHeader( )
	{
		final EntityTag etag = EtagGenerator.createEntityTag( this.requestedModel.getResult( ) );

		this.responseBuilder.tag( etag );
	}

	public static class Builder extends AbstractGetStateBuilder
	{
		@Override public AbstractState build( )
		{
			return new GetSingleStudentState( this );
		}
	}
}