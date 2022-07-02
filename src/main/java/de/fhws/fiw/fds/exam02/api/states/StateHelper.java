package de.fhws.fiw.fds.exam02.api.states;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class StateHelper
{
	public static void addVaryHeader(Response.ResponseBuilder responseBuilder )
	{
		responseBuilder.header( "Vary", "Accept" );
	}
	public static void addExpiresHeader(Response.ResponseBuilder responseBuilder, LocalDate expireDate )
	{
		ZoneId defaultZoneId = ZoneId.systemDefault();
		responseBuilder.expires(Date.from(expireDate.atStartOfDay(defaultZoneId).toInstant()));
	}
	public static void addExpiresHeaderNeverIfPastEndDate(Response.ResponseBuilder responseBuilder, LocalDate expireDate )
	{
		if(expireDate.isAfter(LocalDate.now())){
			addNeverExpireHeader(responseBuilder);
		}
		else{
			addExpiresHeader(responseBuilder, expireDate);
		}
	}
	public static void addNeverExpireHeader(Response.ResponseBuilder responseBuilder)
	{
		responseBuilder.expires(new Date(Long.MAX_VALUE));
	}


}
