package de.fhws.fiw.fds.exam02;

import de.fhws.fiw.fds.sutton.server.AbstractStart;

public class Start extends AbstractStart
{
	public static final String CONTEXT_PATH = "exam02";

	public static void main( final String[] args ) throws Exception
	{
		new Start( ).startTomcat( );
	}

	@Override protected String contextPath( )
	{
		return CONTEXT_PATH;
	}
}