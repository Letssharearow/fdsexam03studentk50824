package de.fhws.fiw.fds.exam02.api.states.study_trips;

import de.fhws.fiw.fds.exam02.api.hypermedia.rel_types.IStudyTripRelTypes;
import de.fhws.fiw.fds.exam02.api.hypermedia.uris.IStudyTripUri;
import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.StudyTrip;
import de.fhws.fiw.fds.sutton.server.api.caching.EtagGenerator;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Response;

public class PutStudyTripState extends AbstractPutState<StudyTrip>
{
	public PutStudyTripState( final AbstractPutStateBuilder<StudyTrip> builder )
	{
		super( builder );
	}

	@Override
	protected void authorizeRequest( )
	{

	}

	@Override
	protected SingleModelResult<StudyTrip> loadModel( )
	{
		return DaoFactory.getInstance( ).getStudyTripDao( ).readById( this.modelToUpdate.getId( ) );
	}

	@Override
	protected NoContentResult updateModel( )
	{
		return DaoFactory.getInstance( ).getStudyTripDao( ).update( this.modelToUpdate );
	}

	@Override
	protected void defineTransitionLinks( )
	{
		addLink(
			IStudyTripUri.REL_PATH,
			IStudyTripRelTypes.GET_SINGLE_STUDY_TRIP,
			getAcceptRequestHeader( ),
			this.modelToUpdate.getId( )
		);
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

	public static class Builder extends AbstractPutStateBuilder<StudyTrip>
	{
		@Override
		public AbstractState build( )
		{
			return new PutStudyTripState( this );
		}
	}
}