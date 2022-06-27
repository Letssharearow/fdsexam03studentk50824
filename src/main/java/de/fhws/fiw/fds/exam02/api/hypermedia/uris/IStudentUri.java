package de.fhws.fiw.fds.exam02.api.hypermedia.uris;

import de.fhws.fiw.fds.exam02.Start;

public interface IStudentUri
{
	String OFFSET_PARAMETER = "offset";
	String SIZE_PARAMETER = "size";

	String PATH_ELEMENT = "students";
	String REL_PATH = Start.CONTEXT_PATH + "/api/" + PATH_ELEMENT;
	String REL_PATH_ID = REL_PATH + "/{id}";
	String REL_PATH_QUERY_PARAMETERS = REL_PATH + "?offset={" + OFFSET_PARAMETER + "}&size={" + SIZE_PARAMETER + "}";
}