package de.fhws.fiw.fds.exam02.api.states;

import java.time.LocalDateTime;

public class Logger
{
	public static void logGetCollection()
	{
		log("GET access to studyTrip collection");
	}

	public static void logGetSingle(final long personId)
	{
		log("GET access to studyTrip with id " + personId);
	}

	private static void log(final String message)
	{
		System.out.println("[" + now() + "] " + message);
	}

	private static String now()
	{
		return LocalDateTime.now().toString();
	}
}