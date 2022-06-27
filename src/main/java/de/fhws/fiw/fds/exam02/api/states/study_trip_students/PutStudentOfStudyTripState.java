package de.fhws.fiw.fds.exam02.api.states.study_trip_students;

import de.fhws.fiw.fds.exam02.api.hypermedia.rel_types.IStudyTripStudentRelTypes;
import de.fhws.fiw.fds.exam02.api.hypermedia.uris.IStudyTripStudentUri;
import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.sutton.server.api.caching.EtagGenerator;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Response;

public class PutStudentOfStudyTripState extends AbstractPutRelationState<Student>
{
	public PutStudentOfStudyTripState( final Builder builder )
	{
		super( builder );
	}

	@Override
	protected void authorizeRequest( )
	{

	}

	@Override
	protected SingleModelResult<Student> loadModel( )
	{
		return DaoFactory.getInstance( ).getStudentDao( ).readById( this.requestedId );
	}

	@Override
	protected NoContentResult updateModel( )
	{
		return DaoFactory.getInstance( ).getStudyTripStudentDao( ).update( this.primaryId, this.modelToUpdate );
	}

	@Override
	protected void defineTransitionLinks( )
	{
		addLink(
			IStudyTripStudentUri.REL_PATH_ID,
			IStudyTripStudentRelTypes.GET_SINGLE_STUDENT,
			getAcceptRequestHeader( ),
			this.primaryId,
			this.requestedId );
	}

	@Override
	protected boolean clientDoesNotKnowCurrentModelState( final AbstractModel modelFromDatabase )
	{
		return !doesEtagMatch( modelFromDatabase );
	}

	private boolean doesEtagMatch( final AbstractModel modelFromDatabase )
	{
		EntityTag etag = EtagGenerator.createEntityTag( modelFromDatabase );

		Response.ResponseBuilder builder = this.request.evaluatePreconditions( etag );

		return builder == null;
	}

	@Override
	protected Response createResponse( )
	{
		addEtagHeader( );

		return super.createResponse( );
	}

	private void addEtagHeader( )
	{
		final EntityTag etag = EtagGenerator.createEntityTag( this.modelToUpdate );

		this.responseBuilder.tag( etag );
	}

	public static class Builder extends AbstractPutRelationStateBuilder<Student>
	{
		@Override
		public AbstractState build( )
		{
			return new PutStudentOfStudyTripState( this );
		}
	}
}