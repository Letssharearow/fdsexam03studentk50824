package de.fhws.fiw.fds.exam02.api.states.students;

import de.fhws.fiw.fds.exam02.api.hypermedia.rel_types.IStudentRelTypes;
import de.fhws.fiw.fds.exam02.api.hypermedia.uris.IStudentUri;
import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.sutton.server.api.caching.EtagGenerator;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Response;

public class PutStudentState extends AbstractPutState<Student>
{
	public PutStudentState( final Builder builder )
	{
		super( builder );
	}

	@Override protected void authorizeRequest( )
	{

	}

	@Override protected SingleModelResult<Student> loadModel( )
	{
		return DaoFactory.getInstance( ).getStudentDao( ).readById( this.modelToUpdate.getId( ) );
	}

	@Override protected NoContentResult updateModel( )
	{
		return DaoFactory.getInstance( ).getStudentDao( ).update( this.modelToUpdate );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink(
			IStudentUri.REL_PATH_ID,
			IStudentRelTypes.GET_SINGLE_STUDENT,
			getAcceptRequestHeader( ),
			this.modelToUpdate.getId( ) );
	}

	@Override protected boolean clientDoesNotKnowCurrentModelState( final AbstractModel modelFromDatabase )
	{
		return !doesEtagMatch( modelFromDatabase );
	}

	private boolean doesEtagMatch( final AbstractModel modelFromDatabase )
	{
		EntityTag etag = EtagGenerator.createEntityTag( modelFromDatabase );

		Response.ResponseBuilder builder = this.request.evaluatePreconditions( etag );

		return builder == null;
	}

	@Override protected Response createResponse( )
	{
		addEtagHeader( );

		return super.createResponse( );
	}

	private void addEtagHeader( )
	{
		final EntityTag etag = EtagGenerator.createEntityTag( this.modelToUpdate);

		this.responseBuilder.tag( etag );
	}

	public static class Builder extends AbstractPutStateBuilder<Student>
	{
		@Override public AbstractState build( )
		{
			return new PutStudentState( this );
		}
	}
}