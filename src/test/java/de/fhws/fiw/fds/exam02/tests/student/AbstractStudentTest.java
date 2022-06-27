package de.fhws.fiw.fds.exam02.tests.student;

import de.fhws.fiw.fds.exam02.client.rest.resources.StudentRestClient;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import de.fhws.fiw.fds.exam02.tests.AbstractTest;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;

public abstract class AbstractStudentTest extends AbstractTest<Student, StudentRestClient>
{
	protected final static String JAMES_BOND = "James Bond";

	protected final static String ERIKA_MUSTERMANN = "Erika Mustermann";

	protected final static String MAX_MUSTERMANN = "Max Mustermann";

	protected final static String HARRY_POTTER = "Harry Potter";

	protected final static String[] STUDENTS_SORTED = new String[]{ JAMES_BOND, ERIKA_MUSTERMANN, MAX_MUSTERMANN, HARRY_POTTER };

	@Override protected StudentRestClient newRestClient( final HeaderMap headers )
	{
		return new StudentRestClient( headers );
	}

	@Override protected String defineBaseUrl( )
	{
		return super.defineBaseUrl( ) + "students";
	}
}