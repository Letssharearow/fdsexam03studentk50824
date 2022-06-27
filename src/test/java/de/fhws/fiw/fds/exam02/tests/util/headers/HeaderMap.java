package de.fhws.fiw.fds.exam02.tests.util.headers;

import java.util.HashMap;
import java.util.Map;

public class HeaderMap
{
	private final Map<String, String> headerMap;

	public HeaderMap( )
	{
		this.headerMap = new HashMap<>( );
	}

	public void addHeader( final String headerName, final String headerValue )
	{
		this.headerMap.put( headerName, headerValue );
	}

	public String getHeader( final String headerName)
	{
		return this.headerMap.getOrDefault( headerName, "" );
	}

	public Map<String, String> getHeaderMap( )
	{
		return headerMap;
	}
}