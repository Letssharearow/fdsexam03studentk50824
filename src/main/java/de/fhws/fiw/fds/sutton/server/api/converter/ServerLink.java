package de.fhws.fiw.fds.sutton.server.api.converter;

public class ServerLink
{
	private String href;
	private String rel;
	private String type;

	public ServerLink( )
	{
	}

	public ServerLink( final String href, final String rel, final String type )
	{
		this.href = href;
		this.rel = rel;
		this.type = type;
	}

	public String getHref( )
	{
		return href;
	}

	public void setHref( final String href )
	{
		this.href = href;
	}

	public String getRel( )
	{
		return rel;
	}

	public void setRel( final String rel )
	{
		this.rel = rel;
	}

	public String getType( )
	{
		return type;
	}

	public void setType( final String type )
	{
		this.type = type;
	}
}
