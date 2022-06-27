package de.fhws.fiw.fds.exam02.database;

import de.fhws.fiw.fds.exam02.database.impl.*;
import de.fhws.fiw.fds.exam02.database.spi.*;

public class DaoFactory
{
	private static DaoFactory INSTANCE;

	public static final DaoFactory getInstance( )
	{
		if ( INSTANCE == null )
		{
			INSTANCE = new DaoFactory( );
		}

		return INSTANCE;
	}

	private final IStudyTripDao studyTripDao;

	private final IStudentDao studentDao;

	private final IStudyTripStudentDao studyTripStudentDao;

	public DaoFactory( )
	{
		this.studyTripDao = new StudyTripInMemoryStorage( );
		this.studentDao = new StudentInMemoryStorage( );
		this.studyTripStudentDao = new StudyTripToStudentRelationInMemoryStorage( );
	}

	public IStudyTripDao getStudyTripDao( )
	{
		return studyTripDao;
	}

	public IStudentDao getStudentDao( )
	{
		return studentDao;
	}

	public IStudyTripStudentDao getStudyTripStudentDao( )
	{
		return studyTripStudentDao;
	}

	public static void resetDaoFactory( )
	{
		INSTANCE = null;
	}
}