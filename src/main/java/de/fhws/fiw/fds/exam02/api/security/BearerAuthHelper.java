package de.fhws.fiw.fds.exam02.api.security;

import de.fhws.fiw.fds.exam02.models.Users;
import de.fhws.fiw.fds.sutton.server.api.security.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.HttpHeaders;

public class BearerAuthHelper
{
	public static String createToken(final String subject)
	{
		return JsonWebTokenHelper.createJsonWebToken(subject);
	}

	public static User accessControl(final HttpServletRequest request, final String... roles)
	{
		return Users.getInstance().accessControl(getSubject(request, roles), roles);

	}

	public static User accessControlOrganizer(final HttpServletRequest request, final String organizer,
		final String... roles)
	{
		return Users.getInstance().accessControlOrganizer(getSubject(request, roles), organizer, roles);
	}

	public static String getSubject(final HttpServletRequest request, final String... roles)
	{
		final String authHeader = request != null ? request.getHeader(HttpHeaders.AUTHORIZATION) : null;

		if (authHeader != null)
		{
			if (authHeader.toLowerCase().startsWith("bearer "))
			{
				final String token = authHeader.replaceFirst("(?i)bearer ", "");
				return JsonWebTokenHelper.verifyJsonWebToken(token);
			}
		}
		throw new NotAuthorizedException("");
	}
}
