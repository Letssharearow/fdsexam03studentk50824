package de.fhws.fiw.fds.exam02.api.states.students;

import de.fhws.fiw.fds.sutton.server.api.caching.CachingUtils;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.exam02.api.hypermedia.rel_types.IStudentRelTypes;
import de.fhws.fiw.fds.exam02.api.hypermedia.uris.IStudentUri;
import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.Student;

import javax.ws.rs.core.GenericEntity;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class GetCollectionStudentsState extends AbstractGetCollectionState<Student>
{
	public GetCollectionStudentsState( final Builder builder )
	{
		super( builder );
	}

	@Override protected void authorizeRequest( )
	{

	}

	@Override protected void defineHttpResponseBody( )
	{
		this.responseBuilder.entity( new GenericEntity<Collection<Student>>( this.result.getResult( ) ) { } );
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( IStudentUri.REL_PATH, IStudentRelTypes.CREATE_STUDENT, getAcceptRequestHeader( ) );
	}

	@Override protected void configureState( )
	{
		this.responseBuilder.cacheControl( CachingUtils.createNoCacheNoStoreCaching( ) );
	}

	public static class AllStudents extends AbstractQuery<Student>
	{
		@Override protected CollectionModelResult<Student> doExecuteQuery( )
		{
			final Collection<Student> studentsFromDb =
				DaoFactory.getInstance( ).getStudentDao( ).readByPredicate( all( ) ).getResult( );

			final List<Student> sortedStudents = new LinkedList<>( studentsFromDb );
			sortedStudents.sort( Student.getComparator() );

			return new CollectionModelResult<>( sortedStudents );
		}
	}

	public static class Builder extends AbstractGetCollectionStateBuilder<Student>
	{
		@Override public AbstractState build( )
		{
			return new GetCollectionStudentsState( this );
		}
	}
}