package de.fhws.fiw.fds.exam02.database.spi;

import de.fhws.fiw.fds.exam02.database.IInMemoryStorage;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.sutton.server.database.IDatabaseRelationAccessObject;

public interface IStudyTripStudentDao extends IDatabaseRelationAccessObject<Student>, IInMemoryStorage
{

}