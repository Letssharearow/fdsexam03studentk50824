package de.fhws.fiw.fds.exam02.database.util;

import de.fhws.fiw.fds.exam02.database.DaoFactory;

public class ResetDatabase
{
	public void reset( )
	{
		DaoFactory.resetDaoFactory();
	}
}