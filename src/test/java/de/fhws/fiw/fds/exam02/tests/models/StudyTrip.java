package de.fhws.fiw.fds.exam02.tests.models;

import com.owlike.genson.annotation.JsonConverter;
import de.fhws.fiw.fds.sutton.server.api.converter.JsonServerLinkConverter;
import de.fhws.fiw.fds.sutton.utils.JsonDateTimeConverter;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import java.io.Serializable;
import java.time.LocalDate;

public class StudyTrip extends AbstractModel implements Serializable, Cloneable
{
	private String name;

	private LocalDate startDate;

	private LocalDate endDate;

	private String companyName;

	private String cityName;

	private String countryName;

	private String organizer;

	public StudyTrip()
	{

	}

	public StudyTrip(String name, LocalDate startDate, LocalDate endDate, String companyName, String cityName,
		String countryName, String organizer)
	{
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.companyName = companyName;
		this.cityName = cityName;
		this.countryName = countryName;
		this.organizer = organizer;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getOrganizer()
	{
		return organizer;
	}

	public void setOrganizer(String organizer)
	{
		this.organizer = organizer;
	}

	@JsonConverter(JsonDateTimeConverter.class) public LocalDate getStartDate()
	{
		return startDate;
	}

	@JsonConverter(JsonDateTimeConverter.class) public void setStartDate(final LocalDate startDate)
	{
		this.startDate = startDate;
	}

	@JsonConverter(JsonDateTimeConverter.class) public LocalDate getEndDate()
	{
		return endDate;
	}

	@JsonConverter(JsonDateTimeConverter.class) public void setEndDate(final LocalDate endDate)
	{
		this.endDate = endDate;
	}

	public String getCompanyName()
	{
		return companyName;
	}

	public void setCompanyName(final String companyName)
	{
		this.companyName = companyName;
	}

	public String getCityName()
	{
		return cityName;
	}

	public void setCityName(final String cityName)
	{
		this.cityName = cityName;
	}

	public String getCountryName()
	{
		return countryName;
	}

	public void setCountryName(final String countryName)
	{
		this.countryName = countryName;
	}

	@Override public String toString()
	{
		return "StudyTrip {" + "id:" + this.id + ", name: '" + this.name + '\'' + ", startDate:" + this.startDate
			+ ", lastDate: " + this.endDate + ", companyName: '" + this.companyName + '\'' + ", cityName: '"
			+ this.cityName + '\'' + ", countryName: '" + this.countryName + '\'' + '}';
	}

	@Override public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}