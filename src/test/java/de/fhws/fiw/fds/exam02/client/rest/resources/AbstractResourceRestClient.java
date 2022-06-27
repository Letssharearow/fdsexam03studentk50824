package de.fhws.fiw.fds.exam02.client.rest.resources;

import com.owlike.genson.GenericType;
import de.fhws.fiw.fds.exam02.client.rest.GenericRestClient;
import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.tests.models.AbstractModel;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;

import java.io.IOException;
import java.util.List;

public abstract class AbstractResourceRestClient<R extends AbstractModel>
{
	private final GenericRestClient<R> restClient;

	public AbstractResourceRestClient( final HeaderMap headers )
	{
		this.restClient = new GenericRestClient( headers );
	}

	protected abstract String defineUrl( );

	public RestApiResponse<R> loadSingleResourceByUrl( final String url ) throws IOException
	{
		return this.restClient.sendGetSingleRequest( url, defineClassTypeForSingleResource( ) );
	}

	protected abstract Class<R> defineClassTypeForSingleResource( );

	public RestApiResponse<R> loadSingleResourceById( final long id ) throws IOException
	{
		return this.restClient.sendGetSingleRequest( appendIdToUrl( id ), defineClassTypeForSingleResource( ) );
	}

	private String appendIdToUrl( final long id )
	{
		return String.format( "%s/%d", defineUrl( ), id );
	}

	public RestApiResponse<R> loadAllResources( ) throws IOException
	{
		return loadAllResourcesByUrl( defineUrl( ) );
	}

	public RestApiResponse<R> loadAllResourcesByUrl( final String url ) throws IOException
	{
		return this.restClient.sendGetCollectionRequest( url, defineClassTypeForCollectionResource( ) );
	}

	protected abstract GenericType<List<R>> defineClassTypeForCollectionResource( );

	public RestApiResponse<R> loadAllResourcesByParameters(
		final int offset,
		final int size
	)
		throws IOException
	{
		final String url = String.format( "%s?offset=%d&size=%d",
			defineUrl( ),
			offset,
			size );

		return loadAllResourcesByUrl( url );
	}

	public RestApiResponse<R> create( final R resource ) throws IOException
	{
		return this.restClient.sendPostRequest( defineUrl( ), resource );
	}

	public RestApiResponse<R> createByUrl( final String url, final R resource ) throws IOException
	{
		return this.restClient.sendPostRequest( url, resource );
	}

	public RestApiResponse<R> update( final R resource ) throws IOException
	{
		return this.restClient.sendPutRequest( appendIdToUrl( resource.getId( ) ), resource );
	}

	public RestApiResponse<R> updateByUrl( final R resource, final String url ) throws IOException
	{
		return this.restClient.sendPutRequest( url, resource );
	}

	public RestApiResponse<R> delete( final R resource ) throws IOException
	{
		return this.restClient.sendDeleteRequest( appendIdToUrl( resource.getId( ) ) );
	}

	public RestApiResponse<R> deleteByUrl( final String url ) throws IOException
	{
		return this.restClient.sendDeleteRequest( url );
	}
}