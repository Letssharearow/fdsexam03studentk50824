package de.fhws.fiw.fds.exam02.api.states;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class StateHelper
{
	public static void addVaryHeader(Response.ResponseBuilder responseBuilder)
	{
		responseBuilder.header("Vary", "Accept");
	}

	public static void addExpiresHeader(Response.ResponseBuilder responseBuilder, LocalDate expireDate)
	{
		responseBuilder.expires(getDateFromLocalDate(expireDate));
	}

	public static void addNeverExpireHeader(Response.ResponseBuilder responseBuilder)
	{
		responseBuilder.expires(new Date(Long.MAX_VALUE));
	}

	public static Date getDateFromLocalDate(LocalDate localDate)
	{
		ZoneId defaultZoneId = ZoneId.systemDefault();
		return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
	}

}
