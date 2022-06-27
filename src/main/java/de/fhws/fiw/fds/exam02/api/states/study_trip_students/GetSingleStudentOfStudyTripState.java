package de.fhws.fiw.fds.exam02.api.states.study_trip_students;

import de.fhws.fiw.fds.exam02.api.hypermedia.rel_types.IStudyTripStudentRelTypes;
import de.fhws.fiw.fds.exam02.api.hypermedia.uris.IStudyTripStudentUri;
import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.sutton.server.api.caching.CachingUtils;
import de.fhws.fiw.fds.sutton.server.api.caching.EtagGenerator;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Response;

public class GetSingleStudentOfStudyTripState extends AbstractGetRelationState<Student>
{
	public GetSingleStudentOfStudyTripState( final Builder builder )
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
	protected void defineTransitionLinks( )
	{
		if ( isStudentLinkedToThisStudyTrip( ) )
		{
			addLink(
				IStudyTripStudentUri.REL_PATH_ID,
				IStudyTripStudentRelTypes.UPDATE_SINGLE_STUDENT,
				getAcceptRequestHeader( ),
				this.primaryId,
				this.requestedId );

			addLink(
				IStudyTripStudentUri.REL_PATH_ID,
				IStudyTripStudentRelTypes.DELETE_LINK_FROM_STUDY_TRIP_TO_STUDENT,
				getAcceptRequestHeader( ),
				this.primaryId,
				this.requestedId );
		}
		else
		{
			addLink(
				IStudyTripStudentUri.REL_PATH_ID,
				IStudyTripStudentRelTypes.CREATE_LINK_FROM_STUDY_TRIP_TO_STUDENT,
				getAcceptRequestHeader( ),
				this.primaryId, this.requestedId );
		}

		addLink(
			IStudyTripStudentUri.REL_PATH_SHOW_ONLY_LINKED,
			IStudyTripStudentRelTypes.GET_ALL_LINKED_STUDENTS,
			getAcceptRequestHeader( ),
			this.primaryId );
	}

	private boolean isStudentLinkedToThisStudyTrip( )
	{
		return !DaoFactory.getInstance( )
			.getStudyTripStudentDao( )
			.readById( this.primaryId, this.requestedId )
			.isEmpty( );
	}

	@Override
	protected void defineHttpCaching( )
	{
		this.responseBuilder.cacheControl( CachingUtils.create30SecondsPrivateCaching( ) );
	}

	@Override
	protected boolean clientKnowsCurrentModelState( final AbstractModel modelFromDatabase )
	{
		return doesEtagMatch( modelFromDatabase );
	}

	private boolean doesEtagMatch( final AbstractModel modelFromDatabase )
	{
		EntityTag etag = EtagGenerator.createEntityTag( modelFromDatabase );

		Response.ResponseBuilder builder = this.request.evaluatePreconditions( etag );

		return builder != null;
	}

	@Override
	protected Response createResponse( )
	{
		addEtagHeader( );

		return super.createResponse( );
	}

	private void addEtagHeader( )
	{
		final EntityTag etag = EtagGenerator.createEntityTag( this.requestedModel.getResult( ) );

		this.responseBuilder.tag( etag );
	}

	public static class Builder extends AbstractGetRelationStateBuilder
	{
		@Override
		public AbstractState build( )
		{
			return new GetSingleStudentOfStudyTripState( this );
		}
	}
}