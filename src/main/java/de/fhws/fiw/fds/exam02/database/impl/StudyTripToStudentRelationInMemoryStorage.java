package de.fhws.fiw.fds.exam02.database.impl;

import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.database.spi.IStudyTripStudentDao;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.sutton.server.database.IDatabaseAccessObject;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryRelationStorage;

public class StudyTripToStudentRelationInMemoryStorage
	extends AbstractInMemoryRelationStorage<Student>
	implements IStudyTripStudentDao
{
	@Override
	protected IDatabaseAccessObject<Student> getSecondaryStorage( )
	{
		return DaoFactory.getInstance( ).getStudentDao( );
	}

	@Override
	public void clearStorage( )
	{
		this.storage.clear( );
	}
}