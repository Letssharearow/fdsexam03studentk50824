package de.fhws.fiw.fds.exam02.database.spi;

import de.fhws.fiw.fds.sutton.server.database.IDatabaseAccessObject;
import de.fhws.fiw.fds.exam02.database.IInMemoryStorage;
import de.fhws.fiw.fds.exam02.models.Student;

public interface IStudentDao extends IDatabaseAccessObject<Student>, IInMemoryStorage
{

}