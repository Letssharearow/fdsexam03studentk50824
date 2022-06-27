package de.fhws.fiw.fds.exam02.client.rest;

import com.owlike.genson.GenericType;
import de.fhws.fiw.fds.sutton.client.Link;
import de.fhws.fiw.fds.exam02.client.web.GenericWebClient;
import de.fhws.fiw.fds.exam02.client.web.WebApiResponse;
import de.fhws.fiw.fds.exam02.tests.models.AbstractModel;
import de.fhws.fiw.fds.exam02.tests.util.ClientUser;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class GenericRestClient<M extends AbstractModel>
{
	private final GenericWebClient<M> webClient;

	public GenericRestClient( )
	{
		this( new HeaderMap( ) );
	}

	public GenericRestClient( final HeaderMap headers )
	{
		this.webClient = new GenericWebClient( ClientUser.ANONYMOUS.getUserName( ), ClientUser.ANONYMOUS.getPassword( ), headers );
	}

	public RestApiResponse<M> sendGetSingleRequest( final String url ) throws IOException
	{
		return toGetSingleResourceRestApiResponse( this.webClient.sendGetSingleRequest( url ) );
	}

	public RestApiResponse<M> sendGetSingleRequest( final String url, final Class<M> clazz ) throws IOException
	{
		return toGetSingleResourceRestApiResponse( this.webClient.sendGetSingleRequest( url, clazz ) );
	}

	private RestApiResponse<M> toGetSingleResourceRestApiResponse( final WebApiResponse<M> webApiResponse )
	{
		final Optional<M> singleResourceOptional = webApiResponse.getResponseData( ).stream( ).findFirst( );

		M singleResource = null;

		if ( singleResourceOptional.isPresent( ) )
		{
			singleResource = singleResourceOptional.get( );
		}

		return new RestApiResponse<>(
			webApiResponse.getLastStatusCode( ),
			singleResource,
			webApiResponse.getResponseHeaders( ),
			parseLinkHeaders( webApiResponse ) );
	}

	private Map<String, Link> parseLinkHeaders( final WebApiResponse<M> webApiResponse )
	{
		final Collection<String> linkHeaders = webApiResponse.getResponseHeaders( ).values( "Link" );

		final Collection<Link> parsedLinkHeaders = linkHeaders
			.stream( )
			.map( Link::parseFromHttpHeader )
			.collect( Collectors.toList( ) );

		return parsedLinkHeaders
			.stream( )
			.collect( Collectors.toMap( Link::getRelationType, h -> h ) );
	}

	public RestApiResponse<M> sendGetCollectionRequest( final String url,
														final GenericType<List<M>> genericType ) throws IOException
	{
		return toGetCollectionResourceRestApiResponse( this.webClient.sendGetCollectionRequest( url, genericType ) );
	}

	private RestApiResponse<M> toGetCollectionResourceRestApiResponse( final WebApiResponse<M> webApiResponse )
	{
		return new RestApiResponse<>(
			webApiResponse.getLastStatusCode( ),
			webApiResponse.getResponseData( ),
			webApiResponse.getResponseHeaders( ),
			parseLinkHeaders( webApiResponse ) );
	}

	public RestApiResponse<M> sendPostRequest( final String url, final M object ) throws IOException
	{
		return toPostResourceRestApiResponse( this.webClient.sendPostRequest( url, object ) );
	}

	private RestApiResponse<M> toPostResourceRestApiResponse( final WebApiResponse<M> webApiResponse )
	{
		return new RestApiResponse<>(
			webApiResponse.getLastStatusCode( ),
			webApiResponse.getResponseHeaders( ).get( "Location" ),
			webApiResponse.getResponseHeaders( ),
			parseLinkHeaders( webApiResponse ) );
	}

	public RestApiResponse<M> sendPutRequest( final String url, final M object ) throws IOException
	{
		return toGetCollectionResourceRestApiResponse( this.webClient.sendPutRequest( url, object ) );
	}

	public RestApiResponse<M> sendDeleteRequest( final String url ) throws IOException
	{
		return toGetCollectionResourceRestApiResponse( this.webClient.sendDeleteRequest( url ) );
	}
}