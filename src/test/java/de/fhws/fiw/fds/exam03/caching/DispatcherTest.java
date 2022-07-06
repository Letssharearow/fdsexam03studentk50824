package de.fhws.fiw.fds.exam03.caching;

import de.fhws.fiw.fds.exam02.client.rest.DispatcherRestClient;
import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.client.rest.resources.EmptyResourceRestClient;
import de.fhws.fiw.fds.exam02.client.rest.resources.StudyTripRestClient;
import de.fhws.fiw.fds.exam02.tests.AbstractTest;
import de.fhws.fiw.fds.exam02.tests.models.EmptyResource;
import de.fhws.fiw.fds.exam02.tests.models.StudyTrip;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class DispatcherTest extends AbstractTest<EmptyResource, EmptyResourceRestClient>
{

	@Test public void test_cache_control_exists() throws IOException
	{
		final DispatcherRestClient dispatcherRestClient = new DispatcherRestClient();
		final RestApiResponse<EmptyResource> response = dispatcherRestClient.triggerDispatcherRequest();
		assertHeaderExists(response, CACHE_CONTROL);
	}

	@Test public void test_cache_control_no_cache() throws IOException
	{
		final DispatcherRestClient dispatcherRestClient = new DispatcherRestClient();
		final RestApiResponse<EmptyResource> response = dispatcherRestClient.triggerDispatcherRequest();
		assertTrue(response.headerExists(CACHE_CONTROL, "no-cache, no-transform"));
	}

	@Override protected EmptyResourceRestClient newRestClient(HeaderMap headers)
	{
		return new EmptyResourceRestClient(headers);
	}
	//TODO getDispatcher 304 on X-Proxy-Cache
}
