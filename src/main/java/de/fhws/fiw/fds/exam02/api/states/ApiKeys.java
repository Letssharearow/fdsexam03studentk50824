package de.fhws.fiw.fds.exam02.api.states;

import javax.ws.rs.NotAuthorizedException;
import java.util.Collection;
import java.util.LinkedList;

public class ApiKeys
{
	private static ApiKeys INSTANCE;

	public static ApiKeys getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new ApiKeys();
		}

		return INSTANCE;
	}

	private final Collection<String> validKeys;

	private ApiKeys()
	{
		this.validKeys = new LinkedList<>();
		populate();
	}

	public void accessControl(final String apiKey)
	{
		if (apiKey == null || this.validKeys.contains(apiKey) == false)
		{
			throw new NotAuthorizedException("");
		}
	}

	private void populate()
	{
		this.validKeys.add("123456");
	}

}
