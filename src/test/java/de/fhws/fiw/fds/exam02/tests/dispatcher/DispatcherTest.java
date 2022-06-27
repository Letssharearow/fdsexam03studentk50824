package de.fhws.fiw.fds.exam02.tests.dispatcher;

import de.fhws.fiw.fds.exam02.client.rest.DispatcherRestClient;
import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.client.rest.resources.EmptyResourceRestClient;
import de.fhws.fiw.fds.exam02.tests.AbstractTest;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import de.fhws.fiw.fds.exam02.tests.models.EmptyResource;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DispatcherTest extends AbstractTest<EmptyResource, EmptyResourceRestClient>
{
	@Test
	public void test_200( ) throws IOException
	{
		final DispatcherRestClient dispatcherRestClient = new DispatcherRestClient( );
		final RestApiResponse<EmptyResource> response = dispatcherRestClient.triggerDispatcherRequest( );

		assertEquals( 200, response.getLastStatusCode( ) );
	}

	@Test
	public void test_hypermedia( ) throws IOException
	{
		final DispatcherRestClient dispatcherRestClient = new DispatcherRestClient( );
		final RestApiResponse<EmptyResource> response = dispatcherRestClient.triggerDispatcherRequest( );

		assertNotNull( response.getParsedLinkHeader( "self" ) );
		assertNotNull( response.getParsedLinkHeader( "getAllStudents" ) );
		assertNotNull( response.getParsedLinkHeader( "getAllStudyTrips" ) );
	}

	@Test
	public void test_correct_media_type( ) throws IOException
	{
		final DispatcherRestClient dispatcherRestClient = new DispatcherRestClient( HeaderMapUtils.withAcceptJson( ) );
		final RestApiResponse<EmptyResource> response = dispatcherRestClient.triggerDispatcherRequest( );

		assertEquals( 200, response.getLastStatusCode( ) );
	}

	@Test
	public void test_incorrect_media_type( ) throws IOException
	{
		final DispatcherRestClient dispatcherRestClient = new DispatcherRestClient( HeaderMapUtils.withAcceptXml( ) );
		final RestApiResponse<EmptyResource> response = dispatcherRestClient.triggerDispatcherRequest( );

		assertEquals( 406, response.getLastStatusCode( ) );
	}

	@Override
	protected EmptyResourceRestClient newRestClient( final HeaderMap headers )
	{
		return new EmptyResourceRestClient( headers );
	}
}