package de.fhws.fiw.fds.exam02.api.states;

import de.fhws.fiw.fds.exam02.models.StudyTripReport;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

@Produces("text/csv") public class StudyTripCSVWriter implements MessageBodyWriter<Collection<StudyTripReport>>
{
	@Override public boolean isWriteable(final Class<?> aClass, final Type type, final Annotation[] annotations,
		final MediaType mediaType)
	{
		return true;
	}

	@Override public long getSize(final Collection<StudyTripReport> studentProjects, final Class<?> aClass,
		final Type type, final Annotation[] annotations, final MediaType mediaType)
	{
		return -1;
	}

	@Override public void writeTo(final Collection<StudyTripReport> studyTrips, final Class<?> aClass, final Type type,
		final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, Object> multivaluedMap,
		final OutputStream outputStream) throws IOException, WebApplicationException
	{
		final OutputStreamWriter osw = new OutputStreamWriter(outputStream);

		osw.append("City,Country,Number of students, Number of days");
		for (final StudyTripReport project : studyTrips)
		{
			osw.write("\n");
			osw.append(project.getCityName());
			osw.append("," + project.getCountryName());
			osw.append("," + project.getNumberOfStudents());
			osw.append("," + project.getNumberOfDays());
		}
		osw.flush();

	}
}
