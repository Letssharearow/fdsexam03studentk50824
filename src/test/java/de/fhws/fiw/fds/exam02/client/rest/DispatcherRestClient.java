package de.fhws.fiw.fds.exam02.client.rest;

import de.fhws.fiw.fds.exam02.client.IBaseUrl;
import de.fhws.fiw.fds.exam02.tests.models.EmptyResource;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;

import java.io.IOException;

public class DispatcherRestClient
{
	private final String dispatcherUrl = IBaseUrl.BASE_URL;

	private final GenericRestClient<EmptyResource> restClient;

	public DispatcherRestClient(  )
	{
		this.restClient = new GenericRestClient( defineDefaultHeaders( ) );
	}

	private HeaderMap defineDefaultHeaders( )
	{
		final HeaderMap headers = new HeaderMap( );
		headers.addHeader( "Accept", "application/json" );

		return headers;
	}

	public DispatcherRestClient( final HeaderMap headers )
	{
		this.restClient = new GenericRestClient<>(headers );
	}

	public RestApiResponse<EmptyResource> triggerDispatcherRequest( ) throws IOException
	{
		return this.restClient.sendGetSingleRequest( this.dispatcherUrl );
	}
}