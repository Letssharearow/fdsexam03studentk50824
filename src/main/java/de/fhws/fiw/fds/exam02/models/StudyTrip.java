package de.fhws.fiw.fds.exam02.models;

import com.owlike.genson.annotation.JsonConverter;
import de.fhws.fiw.fds.sutton.server.api.converter.JsonServerLinkConverter;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import de.fhws.fiw.fds.sutton.utils.JsonDateTimeConverter;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;

public class StudyTrip extends AbstractModel implements Serializable, Cloneable
{
	private String name;

	private LocalDate startDate;

	private LocalDate endDate;

	private String companyName;

	private String cityName;

	private String countryName;

	@InjectLink(
		style = InjectLink.Style.ABSOLUTE,
		value = "studytrips/${instance.id}/students",
		rel = "getStudentsOfStudyTrip",
		title = "students",
		type = "application/json"
	)
	private Link students;

	@InjectLink(
		style = InjectLink.Style.ABSOLUTE,
		value = "/studytrips/${instance.id}",
		rel = "self",
		title = "self",
		type = "application/json" )
	private Link selfLink;

	public StudyTrip( )
	{

	}

	public StudyTrip(
		final String name,
		final LocalDate startDate,
		final LocalDate endDate,
		final String companyName,
		final String cityName,
		final String countryName)
	{
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.companyName = companyName;
		this.cityName = cityName;
		this.countryName = countryName;
	}

	public static Comparator<StudyTrip> getComparator( )
	{
		return Comparator.comparing( StudyTrip::getStartDate );
	}

	public String getName( )
	{
		return name;
	}

	public void setName( final String name )
	{
		this.name = name;
	}

	@JsonConverter( JsonDateTimeConverter.class )
	public LocalDate getStartDate( )
	{
		return startDate;
	}

	@JsonConverter( JsonDateTimeConverter.class )
	public void setStartDate( final LocalDate startDate )
	{
		this.startDate = startDate;
	}

	@JsonConverter( JsonDateTimeConverter.class )
	public LocalDate getEndDate( )
	{
		return endDate;
	}

	@JsonConverter( JsonDateTimeConverter.class )
	public void setEndDate( final LocalDate endDate )
	{
		this.endDate = endDate;
	}

	public String getCompanyName( )
	{
		return companyName;
	}

	public void setCompanyName( final String companyName )
	{
		this.companyName = companyName;
	}

	public String getCityName( )
	{
		return cityName;
	}

	public void setCityName( final String cityName )
	{
		this.cityName = cityName;
	}

	public String getCountryName( )
	{
		return countryName;
	}

	public void setCountryName( final String countryName )
	{
		this.countryName = countryName;
	}

	@JsonConverter( JsonServerLinkConverter.class )
	public Link getStudents( )
	{
		return students;
	}

	@JsonConverter( JsonServerLinkConverter.class )
	public void setStudents( final Link students )
	{
		this.students = students;
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

	@Override public String toString( )
	{
		return "StudyTrip {" +
			"id:" + this.id +
			", name: '" + this.name + '\'' +
			", startDate:" + this.startDate +
			", lastDate: " + this.endDate +
			", companyName: '" + this.companyName + '\'' +
			", cityName: '" + this.cityName + '\'' +
			", countryName: '" + this.countryName + '\'' +
			'}';
	}

	@Override public Object clone( ) throws CloneNotSupportedException
	{
		return super.clone( );
	}
}