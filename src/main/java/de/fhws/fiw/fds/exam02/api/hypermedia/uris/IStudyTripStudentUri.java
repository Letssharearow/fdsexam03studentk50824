package de.fhws.fiw.fds.exam02.api.hypermedia.uris;

import de.fhws.fiw.fds.exam02.Start;

public interface IStudyTripStudentUri
{
	String SHOW_ALL_PARAMETER = "showAll";
	String OFFSET_PARAMETER = "offset";
	String SIZE_PARAMETER = "size";

	String PATH_ELEMENT = "studytrips/{id}/students";
	String REL_PATH = Start.CONTEXT_PATH + "/api/" + PATH_ELEMENT;
	String REL_PATH_ID = REL_PATH + "/{id}";
	String REL_PATH_SHOW_ALL =
		Start.CONTEXT_PATH + "/api/" + PATH_ELEMENT + "?" + SHOW_ALL_PARAMETER + "=true";
	String REL_PATH_SHOW_ONLY_LINKED =
		Start.CONTEXT_PATH + "/api/" + PATH_ELEMENT + "?" + SHOW_ALL_PARAMETER + "=false";
	String REL_PATH_SHOW_ALL_QUERY_PARAMETERS =
		REL_PATH_SHOW_ALL + "&offset={" + OFFSET_PARAMETER + "}&size={" + SIZE_PARAMETER + "}";
	String REL_PATH_SHOW_ONLY_LINKED_QUERY_PARAMETERS =
		REL_PATH_SHOW_ONLY_LINKED + "&offset={" + OFFSET_PARAMETER + "}&size={" + SIZE_PARAMETER + "}";
}