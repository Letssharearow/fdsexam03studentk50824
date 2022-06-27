package de.fhws.fiw.fds.exam02.tests.util.database;

import de.fhws.fiw.fds.exam02.client.IBaseUrl;
import de.fhws.fiw.fds.exam02.client.web.GenericWebClient;
import de.fhws.fiw.fds.exam02.tests.models.EmptyResource;

import java.io.IOException;

public class ResetDatabaseForTesting
{
	public void reset( )
	{
		final GenericWebClient<EmptyResource> webClient = new GenericWebClient<EmptyResource>( );

		try
		{
			webClient.sendGetSingleRequest( IBaseUrl.BASE_URL + "resetdatabase" );
			webClient.sendGetSingleRequest( IBaseUrl.BASE_URL + "initializedatabase" );
		}
		catch ( IOException e )
		{
			e.printStackTrace( );
		}
	}
}