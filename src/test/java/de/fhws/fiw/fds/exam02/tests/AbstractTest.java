package de.fhws.fiw.fds.exam02.tests;

import de.fhws.fiw.fds.exam02.client.IBaseUrl;
import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.client.rest.resources.AbstractResourceRestClient;
import de.fhws.fiw.fds.exam02.tests.util.database.ResetDatabaseForTesting;
import de.fhws.fiw.fds.exam02.tests.models.AbstractModel;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;
import org.junit.Before;

import java.io.IOException;

import static org.junit.Assert.*;

public abstract class AbstractTest<R extends AbstractModel, C extends AbstractResourceRestClient<R>>
{
	protected final static String BASE_URL = IBaseUrl.BASE_URL;

	protected final static String SELF = "self";

	protected final static String CACHE_CONTROL = "Cache-Control";

	protected final static String ETAG = "Etag";

	protected final static String IF_MATCH = "If-Match";

	protected final static String IF_NONE_MATCH = "If-None-Match";

	@Before
	public void resetDatabaseState( )
	{
		System.out.println( "Resetting database for testing" );

		new ResetDatabaseForTesting( ).reset( );
	}

	protected String defineBaseUrl( )
	{
		return IBaseUrl.BASE_URL;
	}

	protected abstract C newRestClient( final HeaderMap headers );

	protected RestApiResponse<R> getCollectionRequest(
		final HeaderMap headers )
		throws IOException
	{
		return newRestClient( headers ).loadAllResources( );
	}

	protected RestApiResponse<R> getCollectionRequestByUrl(
		final HeaderMap headers,
		final String url )
		throws IOException
	{
		return newRestClient( headers ).loadAllResourcesByUrl( url );
	}

	protected RestApiResponse<R> getSingleRequestByUrl(
		final HeaderMap headers,
		final String url )
		throws IOException
	{
		return newRestClient( headers ).loadSingleResourceByUrl( url );
	}

	protected RestApiResponse<R> getSingleRequestById(
		final HeaderMap headers,
		final long id )
		throws IOException
	{
		return newRestClient( headers ).loadSingleResourceById( id );
	}

	protected RestApiResponse<R> postRequest(
		final HeaderMap headers,
		final R resource )
		throws IOException
	{
		return newRestClient( headers ).create( resource );
	}

	protected RestApiResponse<R> postRequestByUrl(
		final HeaderMap headers,
		final R resource,
		final String url )
		throws IOException
	{
		return newRestClient( headers ).createByUrl( url, resource );
	}

	protected RestApiResponse<R> putRequest(
		final HeaderMap headerMap,
		final R resource )
		throws IOException
	{
		return newRestClient( headerMap ).update( resource );
	}

	protected RestApiResponse<R> putRequestByUrl(
		final HeaderMap headerMap,
		final R resource,
		final String url )
		throws IOException
	{
		return newRestClient( headerMap ).updateByUrl( resource, url );
	}

	protected RestApiResponse<R> deleteRequest(
		final HeaderMap headers,
		final R resource )
		throws IOException
	{
		return newRestClient( headers ).delete( resource );
	}

	protected RestApiResponse<R> deleteRequestById(
		final HeaderMap headers,
		final long id )
		throws IOException
	{
		final R resource = getSingleRequestById( headers, id ).getResponseSingleData( );

		return deleteRequest( headers, resource );
	}

	protected RestApiResponse<R> deleteRequestByUrl(
		final HeaderMap headers,
		final String url )
		throws IOException
	{
		return newRestClient( headers ).deleteByUrl( url );
	}

	protected void assertLinkHeaderExists(
		final RestApiResponse<R> response,
		final String relationType )
	{
		assertTrue( doesLinkHeaderExist( response, relationType ) );
	}

	private boolean doesLinkHeaderExist(
		final RestApiResponse<R> response,
		final String relationType )
	{
		return response.getParsedLinkHeader( relationType ) != null;
	}

	protected void assertLinkHeaderDoesNotExist(
		final RestApiResponse<R> response,
		final String relationType )
	{
		assertFalse( doesLinkHeaderExist( response, relationType ) );
	}

	protected void assertLinkHeaderStartsWith(
		final RestApiResponse<R> response,
		final String relationType,
		final String startsWith )
	{
		assertTrue( response.getLinkHeader( relationType ).startsWith( startsWith ) );
	}

	protected void assertLinkHeaderEquals(
		final RestApiResponse<R> response,
		final String relationType,
		final String equalString )
	{
		assertEquals( equalString, response.getLinkHeader( relationType ) );
	}

	protected long cutIdFromLocationHeader( final RestApiResponse<R> response )
	{
		final String locationHeader = response.getLocationHeader( );

		final String[] split = locationHeader.split( "/" );

		final String idAsString = split[ split.length - 1 ];

		return Long.parseLong( idAsString );
	}

	protected void assertHeaderExists( final RestApiResponse<R> response, final String headerName )
	{
		assertNotNull( response.getAllResponseHeaders( ).get( headerName ) );
	}
}