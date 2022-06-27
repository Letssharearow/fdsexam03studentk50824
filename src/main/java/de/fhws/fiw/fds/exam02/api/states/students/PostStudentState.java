package de.fhws.fiw.fds.exam02.api.states.students;

import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;

public class PostStudentState extends AbstractPostState<Student>
{
	public PostStudentState( final Builder builder )
	{
		super( builder );
	}

	@Override protected void authorizeRequest( )
	{

	}

	@Override protected NoContentResult saveModel( )
	{
		return DaoFactory.getInstance( ).getStudentDao( ).create( this.modelToStore );
	}

	@Override protected void defineTransitionLinks( )
	{

	}

	public static class Builder extends AbstractPostStateBuilder<Student>
	{
		@Override public AbstractState build( )
		{
			return new PostStudentState( this );
		}
	}
}