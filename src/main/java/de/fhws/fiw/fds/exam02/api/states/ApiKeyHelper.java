package de.fhws.fiw.fds.exam02.api.states;

import javax.servlet.http.HttpServletRequest;

public class ApiKeyHelper
{
	private static final String HEADER = "api-key";

	public static void accessControl(final HttpServletRequest request)
	{
		final String apiKey = request != null ? request.getHeader(HEADER) : null;

		ApiKeys.getInstance().accessControl(apiKey);
	}
}
