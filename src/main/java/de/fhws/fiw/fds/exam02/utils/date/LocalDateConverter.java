package de.fhws.fiw.fds.exam02.utils.date;

import org.apache.commons.lang.StringUtils;

import javax.ws.rs.ext.ParamConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter implements ParamConverter<LocalDate>
{
	@Override public LocalDate fromString( final String value )
	{
		if ( StringUtils.isEmpty( value ) )
		{
			return null;
		}
		else
		{
			final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

			return LocalDate.parse( value, formatter );
		}
	}

	@Override public String toString( final LocalDate value )
	{
		return value.format( DateTimeFormatter.ISO_LOCAL_DATE );
	}
}