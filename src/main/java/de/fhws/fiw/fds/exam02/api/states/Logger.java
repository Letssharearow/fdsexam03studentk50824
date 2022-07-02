package de.fhws.fiw.fds.exam02.api.states;

import java.time.LocalDateTime;

public class Logger
{

	private static final String STUDY_TRIP = "studyTrip";
	private static final String STUDENT = "student";
	private static final String STUDENT_OF_STUDY_TRIP = "studentOfStudyTrip";

	public static void logGetCollectionStudyTrip()
	{
		logGetCollection(STUDY_TRIP);
	}

	public static void logGetSingleStudyTrip(final long id)
	{
		logGetSingle(STUDY_TRIP, id);
	}

	public static void logGetCollectionStudent()
	{
		logGetCollection(STUDENT);
	}

	public static void logGetSingleStudent(final long id)
	{
		logGetSingle(STUDENT, id);
	}

	private static void log(final String message)
	{
		System.out.println("[" + now() + "] " + message);
	}

	private static void logGetCollection(final String ressourceName)
	{
		logGet(ressourceName + " collection");
	}

	private static void logGetSingle(final String ressourceName, final long id)
	{
		logGet(ressourceName + " width id " + id);
	}

	private static void logGet(final String message)
	{
		log("GET access to " + message);
	}

	private static String now()
	{
		return LocalDateTime.now().toString();
	}
}