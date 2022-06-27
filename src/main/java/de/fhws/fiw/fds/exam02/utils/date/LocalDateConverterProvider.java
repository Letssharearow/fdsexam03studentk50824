package de.fhws.fiw.fds.exam02.utils.date;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;

@Provider
public class LocalDateConverterProvider implements ParamConverterProvider
{
	@Override
	public <T> ParamConverter<T> getConverter( final Class<T> rawType, final Type genericType,
											   final Annotation[] annotations )
	{
		if ( rawType == LocalDate.class )
		{
			return ( ParamConverter<T> ) new LocalDateConverter( );
		}
		return null;
	}
}