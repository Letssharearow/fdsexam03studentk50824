package de.fhws.fiw.fds.exam02.tests.models;

import com.owlike.genson.annotation.JsonConverter;
import de.fhws.fiw.fds.sutton.server.api.converter.JsonServerLinkConverter;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import java.io.Serializable;

public class Student extends AbstractModel implements Serializable, Cloneable
{
	protected String firstName;

	protected String lastName;

	protected String email;

	private String courseOfStudy;

	private int semesterOfStudy;

	private int matriculationNumber;

	@InjectLink(
		style = InjectLink.Style.ABSOLUTE,
		value = "/students/${instance.id}",
		rel = "self",
		title = "self",
		type = "application/json" )
	private Link selfLink;

	public Student( )
	{
		this( "", "", "" );
	}

	public Student(
		final String firstName,
		final String lastName,
		final String email )
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public Student(
		final String firstName,
		final String lastName,
		final String email,
		final String courseOfStudy,
		final int semesterOfStudy,
		final int matriculationNumber)
	{
		this(firstName, lastName, email);

		this.courseOfStudy = courseOfStudy;
		this.semesterOfStudy = semesterOfStudy;
		this.matriculationNumber = matriculationNumber;
	}

	public String getFirstName( )
	{
		return firstName;
	}

	public void setFirstName( String firstName )
	{
		this.firstName = firstName;
	}

	public String getLastName( )
	{
		return lastName;
	}

	public void setLastName( String lastName )
	{
		this.lastName = lastName;
	}

	public String getEmail( )
	{
		return email;
	}

	public void setEmail( String email )
	{
		this.email = email;
	}

	public String getCourseOfStudy( )
	{
		return courseOfStudy;
	}

	public void setCourseOfStudy( final String courseOfStudy )
	{
		this.courseOfStudy = courseOfStudy;
	}

	public int getSemesterOfStudy( )
	{
		return semesterOfStudy;
	}

	public void setSemesterOfStudy( final int semesterOfStudy )
	{
		this.semesterOfStudy = semesterOfStudy;
	}

	public int getMatriculationNumber( )
	{
		return matriculationNumber;
	}

	public void setMatriculationNumber( final int matriculationNumber )
	{
		this.matriculationNumber = matriculationNumber;
	}

	@JsonConverter( JsonServerLinkConverter.class )
	public Link getSelfLink( )
	{
		return selfLink;
	}

	public void setSelfLink( final Link selfLink )
	{
		this.selfLink = selfLink;
	}
}