package de.fhws.fiw.fds.exam02.api.states;

import io.jsonwebtoken.*;

import javax.ws.rs.NotAuthorizedException;
import java.util.Calendar;
import java.util.Date;

public class JsonWebTokenHelper
{
	public static String createJsonWebToken(final String subject)
	{
		return Jwts.builder().setSubject(subject).setExpiration(timeIn30Minutes())
			.signWith(SignatureAlgorithm.HS512, MyKeyGenerator.getInstance().getKey()).compact();
	}

	public static String verifyJsonWebToken(final String token)
	{
		try
		{
			final Jws<Claims> claims = Jwts.parser().setSigningKey(MyKeyGenerator.getInstance().getKey())
				.parseClaimsJws(token);

			return claims.getBody().getSubject();
		}
		catch (final JwtException e)
		{
			throw new NotAuthorizedException("");
		}
	}

	private static Date timeIn30Minutes()
	{
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, 30);
		return calendar.getTime();
	}
}
