package de.fhws.fiw.fds.exam02;

import de.fhws.fiw.fds.exam02.api.services.*;
import de.fhws.fiw.fds.exam02.database.util.InitializeDatabase;
import de.fhws.fiw.fds.exam02.utils.date.LocalDateConverterProvider;
import de.fhws.fiw.fds.sutton.server.api.AbstractApplication;

import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath( "api" )
public class Exam02 extends AbstractApplication
{
	public Exam02( )
	{
		super( );

		register( LocalDateConverterProvider.class );

		initializeDatabase( );
	}

	private void initializeDatabase( )
	{
		new InitializeDatabase( ).initialize( );
	}

	@Override
	protected Set<Class<?>> getServiceClasses( )
	{
		final Set<Class<?>> returnValue = new HashSet<>( );

		returnValue.add( DispatcherService.class );
		returnValue.add( StudyTripService.class );
		returnValue.add( StudentService.class );

		returnValue.add( InitializeDatabaseService.class );
		returnValue.add( ResetDatabaseService.class );

		return returnValue;
	}
}