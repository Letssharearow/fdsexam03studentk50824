/*
 * Copyright 2021 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.fhws.fiw.fds.exam02.client.web;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import de.fhws.fiw.fds.exam02.client.auth.BasicAuthInterceptor;
import de.fhws.fiw.fds.exam02.tests.models.AbstractModel;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class GenericWebClient<T extends AbstractModel>
{
	private final static String CONTENT_TYPE = "Content-Type";

	private final HeaderMap headers;

	private final OkHttpClient client;

	private final Genson genson;

	public GenericWebClient( )
	{
		this( "", "" );
	}

	public GenericWebClient(
		final String userName,
		final String password )
	{
		this( userName, password, new HeaderMap( ) );
	}

	public GenericWebClient(
		final String userName,
		final String password,
		final HeaderMap headers )
	{
		this.headers = headers;

		this.client = new OkHttpClient.Builder( )
			.addInterceptor( new BasicAuthInterceptor( userName, password ) )
			.build( );

		this.genson = new Genson( );
	}

	public WebApiResponse<T> sendGetSingleRequest( final String url ) throws IOException
	{
		final Response response = executeGetRequest( url );

		final int statusCodeOfLastRequest = response.code( );

		if ( statusCodeOfLastRequest == 200 )
		{
			return new WebApiResponse<>(
				Optional.empty( ),
				response.headers( ),
				response.code( ) );
		}
		else
		{
			return new WebApiResponse<>( statusCodeOfLastRequest );
		}
	}

	private Response executeGetRequest( final String url ) throws IOException
	{
		final Request request = newBuilder( url ).get( ).build( );

		return this.client.newCall( request ).execute( );
	}

	private Request.Builder newBuilder( final String url )
	{
		final Request.Builder builder = new Request.Builder( ).url( url );

		addHeaders( builder );

		return builder;
	}

	private void addHeaders( final Request.Builder builder )
	{
		for ( String headerName : this.headers.getHeaderMap( ).keySet( ) )
		{
			final String headerValue = this.headers.getHeaderMap( ).get( headerName );

			builder.addHeader( headerName, headerValue );
		}
	}

	public WebApiResponse<T> sendGetSingleRequest(
		final String url,
		final Class<T> clazz )
		throws IOException
	{
		final Response response = executeGetRequest( url );

		final int statusCodeOfLastRequest = response.code( );

		if ( statusCodeOfLastRequest == 200 )
		{
			return new WebApiResponse<>(
				deserialize( response, clazz ),
				response.headers( ),
				response.code( ) );
		}
		else
		{
			return new WebApiResponse<>( statusCodeOfLastRequest );
		}
	}

	public WebApiResponse<T> sendGetCollectionRequest(
		final String url,
		final GenericType<List<T>> genericType )
		throws IOException
	{
		final Request request = newBuilder( url ).get( ).build( );

		final Response response = this.client.newCall( request ).execute( );

		final int statusCodeOfLastRequest = response.code( );

		if ( statusCodeOfLastRequest == 200 )
		{
			return new WebApiResponse<>(
				deserializeToCollection( response, genericType ),
				response.headers( ),
				response.code( ) );
		}
		else
		{
			return new WebApiResponse<>( statusCodeOfLastRequest );
		}
	}

	public WebApiResponse<T> sendPostRequest( final String url, final T object ) throws IOException
	{
		final String contentType = this.headers.getHeader( CONTENT_TYPE );

		final RequestBody body = RequestBody.create( MediaType.parse( contentType ), serialize( object ) );

		final Request request = new Request.Builder( )
			.url( url )
			.post( body )
			.build( );

		final Response response = this.client.newCall( request ).execute( );

		final int statusCodeOfLastRequest = response.code( );

		return new WebApiResponse<>( statusCodeOfLastRequest, response.headers( ) );
	}

	public WebApiResponse<T> sendPutRequest( final String url, final T object ) throws IOException
	{
		final String contentType = this.headers.getHeader( CONTENT_TYPE );

		final RequestBody body = RequestBody.create( MediaType.parse( contentType ), serialize( object ) );

		final Request request = newBuilder( url )
			.put( body )
			.build( );

		final Response response = this.client.newCall( request ).execute( );

		return new WebApiResponse<>( response.code( ), response.headers( ) );
	}

	public WebApiResponse<T> sendDeleteRequest( final String url ) throws IOException
	{
		final Request request = new Request.Builder( )
			.url( url )
			.delete( )
			.build( );

		final Response response = this.client.newCall( request ).execute( );

		return new WebApiResponse<>( response.code( ), response.headers( ) );
	}

	private String serialize( final T object )
	{
		return this.genson.serialize( object );
	}

	private List<T> deserializeToCollection( final Response response, final GenericType<List<T>> genericType )
		throws IOException
	{
		final String data = response.body( ).string( );

		return genson.deserialize( data, genericType );
	}

	private Optional<T> deserialize( final Response response, final Class<T> clazz ) throws IOException
	{
		final String data = response.body( ).string( );

		return Optional.ofNullable( genson.deserialize( data, clazz ) );
	}
}