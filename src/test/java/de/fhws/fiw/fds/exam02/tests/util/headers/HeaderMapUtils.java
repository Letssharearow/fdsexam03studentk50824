package de.fhws.fiw.fds.exam02.tests.util.headers;

import javax.ws.rs.core.EntityTag;

public class HeaderMapUtils
{
	public static HeaderMap empty()
	{
		return new HeaderMap();
	}

	public static HeaderMap withAcceptJson()
	{
		final HeaderMap headers = new HeaderMap();
		headers.addHeader("Accept", "application/json");

		return headers;
	}

	public static HeaderMap withAcceptXml()
	{
		final HeaderMap headers = new HeaderMap();
		headers.addHeader("Accept", "application/xml");

		return headers;
	}

	public static HeaderMap withContentTypeJson()
	{
		final HeaderMap headers = new HeaderMap();
		headers.addHeader("Content-Type", "application/json");

		return headers;
	}

	public static HeaderMap withContentTypeXml()
	{
		final HeaderMap headers = new HeaderMap();
		headers.addHeader("Content-Type", "application/xml");

		return headers;
	}

	public static HeaderMap withConditionalGet(EntityTag etag)
	{
		final HeaderMap headers = new HeaderMap();
		headers.addHeader("If-None-Match", etag.getValue());

		return headers;
	}

	public static HeaderMap withConditionalPut(EntityTag etag)
	{
		final HeaderMap headers = new HeaderMap();
		headers.addHeader("If-Match", etag.getValue());

		return headers;
	}
}