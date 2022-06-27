package de.fhws.fiw.fds.exam02.client.rest.resources;

import com.owlike.genson.GenericType;
import de.fhws.fiw.fds.exam02.client.IBaseUrl;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;

import java.util.List;

public class StudentRestClient extends AbstractResourceRestClient<Student>
{
	public StudentRestClient( final HeaderMap headers )
	{
		super( headers );
	}

	@Override protected String defineUrl( )
	{
		return IBaseUrl.BASE_URL + "students";
	}

	@Override protected Class<Student> defineClassTypeForSingleResource( )
	{
		return Student.class;
	}

	@Override protected GenericType<List<Student>> defineClassTypeForCollectionResource( )
	{
		return new GenericType<List<Student>>( ) { };
	}
}