package de.fhws.fiw.fds.exam02.models;

import com.owlike.genson.annotation.JsonConverter;
import com.owlike.genson.annotation.JsonIgnore;
import com.owlike.genson.annotation.JsonProperty;
import de.fhws.fiw.fds.sutton.server.api.converter.JsonServerLinkConverter;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import de.fhws.fiw.fds.sutton.utils.JsonDateTimeConverter;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;

public class StudyTripReport extends AbstractModel implements Serializable, Cloneable
{
	private String cityName;

	private String countryName;

	private int numberOfDays;

	private int numberOfStudents;

	public StudyTripReport()
	{
	}

	public StudyTripReport(String cityName, String countryName, int numberOfDays, int numberOfStudents)
	{
		this.cityName = cityName;
		this.countryName = countryName;
		this.numberOfDays = numberOfDays;
		this.numberOfStudents = numberOfStudents;
	}

	@Override @JsonIgnore public long getId()
	{
		return id;
	}

	@Override @JsonIgnore public void setId(long id)
	{
		this.id = id;
	}

	public String getCityName()
	{
		return cityName;
	}

	public void setCityName(String cityName)
	{
		this.cityName = cityName;
	}

	public String getCountryName()
	{
		return countryName;
	}

	public void setCountryName(String countryName)
	{
		this.countryName = countryName;
	}

	public int getNumberOfDays()
	{
		return numberOfDays;
	}

	public void setNumberOfDays(int numberOfDays)
	{
		this.numberOfDays = numberOfDays;
	}

	public int getNumberOfStudents()
	{
		return numberOfStudents;
	}

	public void setNumberOfStudents(int numberOfStudents)
	{
		this.numberOfStudents = numberOfStudents;
	}

	@Override public String toString()
	{
		return "StudyTripReport{" + "cityName='" + cityName + '\'' + ", countryName='" + countryName + '\''
			+ ", numberOfDays=" + numberOfDays + ", numberOfStudents=" + numberOfStudents + '}';
	}

	@Override public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}