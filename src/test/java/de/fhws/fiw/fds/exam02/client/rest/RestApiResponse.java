package de.fhws.fiw.fds.exam02.client.rest;

import de.fhws.fiw.fds.sutton.client.Link;
import de.fhws.fiw.fds.exam02.tests.models.AbstractModel;
import okhttp3.Headers;

import java.util.Collection;
import java.util.Map;

public class RestApiResponse<M extends AbstractModel>
{
	private final int lastStatusCode;

	private final M responseSingleData;

	private final Collection<M> responseCollectionData;

	private final Headers allResponseHeaders;

	private final String locationHeader;

	private final Map<String, Link> relationTypeToLinkHeaderMap;

	public RestApiResponse(
		final int lastStatusCode,
		final M responseSingleData,
		final Headers headers,
		final Map<String, Link> relationTypeToLinkHeaderMap )
	{
		this( lastStatusCode, responseSingleData, null, headers, null, relationTypeToLinkHeaderMap );
	}

	public RestApiResponse(
		final int lastStatusCode,
		final Collection<M> responseCollectionData,
		final Headers headers,
		final Map<String, Link> relationTypeToLinkHeaderMap )
	{
		this( lastStatusCode, null, responseCollectionData, headers, null, relationTypeToLinkHeaderMap );
	}

	public RestApiResponse(
		final int lastStatusCode,
		final String locationHeader,
		final Headers headers,
		final Map<String, Link> relationTypeToLinkHeaderMap )
	{
		this( lastStatusCode, null, null, headers, locationHeader, relationTypeToLinkHeaderMap );
	}

	public RestApiResponse(
		final int lastStatusCode,
		final Headers headers,
		final Map<String, Link> relationTypeToLinkHeaderMap )
	{
		this( lastStatusCode, null, null, headers, null, relationTypeToLinkHeaderMap );
	}

	private RestApiResponse(
		final int lastStatusCode,
		final M responseSingleData,
		final Collection<M> responseCollectionData,
		final Headers headers,
		final String locationHeader,
		final Map<String, Link> relationTypeToLinkHeaderMap )
	{
		this.lastStatusCode = lastStatusCode;
		this.responseSingleData = responseSingleData;
		this.responseCollectionData = responseCollectionData;
		this.allResponseHeaders = headers;
		this.locationHeader = locationHeader;
		this.relationTypeToLinkHeaderMap = relationTypeToLinkHeaderMap;
	}

	public boolean isLinkHeaderPresent( final String relationType )
	{
		return this.relationTypeToLinkHeaderMap.containsKey( relationType );
	}

	public Link getParsedLinkHeader( final String relationType )
	{
		return this.relationTypeToLinkHeaderMap.get( relationType );
	}

	public String getLinkHeader( final String relationType )
	{
		return this.relationTypeToLinkHeaderMap.get( relationType ).getUrl( );
	}

	public String getEtagHeader( )
	{
		return this.allResponseHeaders.get( "Etag" );
	}

	public int getLastStatusCode( )
	{
		return lastStatusCode;
	}

	public M getResponseSingleData( )
	{
		return responseSingleData;
	}

	public Collection<M> getResponseCollectionData( )
	{
		return responseCollectionData;
	}

	public Headers getAllResponseHeaders( )
	{
		return allResponseHeaders;
	}

	public String getLocationHeader( )
	{
		return locationHeader;
	}

	public Map<String, Link> getRelationTypeToLinkHeaderMap( )
	{
		return relationTypeToLinkHeaderMap;
	}
}