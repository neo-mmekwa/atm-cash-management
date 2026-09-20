/**
 * @author neo mmekwa 
 * */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


/*
 * this class manages anything date related: salary dates, holiday dates determines day types etc
 * */
public class CalendarManager 
{
    //attributes
	private ArrayList<LocalDate> salaryDates;
	private ArrayList<LocalDate> publicHolidayDates;
	
	//default constructor
	public CalendarManager() 
	{
		salaryDates = new ArrayList<LocalDate>();
        publicHolidayDates = new ArrayList<LocalDate>();
	}

	//parameterised constructor 
	public CalendarManager(ArrayList<LocalDate> salaryDates, ArrayList<LocalDate> publicHolidayDates) 
	{
		super();
		this.salaryDates = salaryDates;
		this.publicHolidayDates = publicHolidayDates;
	}
	
	//helper methods
	public boolean isWeekend(LocalDateTime dateTime)
	{
		
		//get day of week 
		int day = dateTime.getDayOfWeek().getValue();
		//check if its day 6/7 
		if(day == 6 || day == 7)
		{
			return true;
		}else 
		{
			return false;
		}
	}
	
	public boolean isSalaryDate(LocalDateTime dateTime)
	{
		
		//get transaction date
		LocalDate date = dateTime.toLocalDate();
		//check if the date is part of the salary dates list
		if(salaryDates.contains(date))
		{
			return true;
		}else 
		{
			return false;
		}
		
	}
	
	public boolean isPublicHoliday(LocalDateTime dateTime)
	{
		
		//get transaction date
		LocalDate date = dateTime.toLocalDate();
		//check if date is part of public holiday list 
		if(publicHolidayDates.contains(date)) 
		{
			return true;
		}else 
		{
			return false;
		}
		
	}
	
	public boolean isNormalDay(LocalDateTime dateTime)
	{
		//check if datetime is a salarydate/public holiday date/ weekend
		if(!isSalaryDate(dateTime) 
		   && !isPublicHoliday(dateTime)
		   && !isWeekend(dateTime))
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public String getDayType(LocalDateTime dateTime)
	{
	    if (isSalaryDate(dateTime))
	    {
	        return "Salary Day";
	    }
	    else if (isPublicHoliday(dateTime))
	    {
	        return "Public Holiday";
	    }
	    else if (isWeekend(dateTime))
	    {
	        return "Weekend";
	    }
	    else
	    {
	        return "Normal Weekday";
	    }
	}
	

	//accessors and mutators 
	public ArrayList<LocalDate> getSalaryDates() 
	{
		return salaryDates;
	}

	public void setSalaryDates(ArrayList<LocalDate> salaryDates) 
	{
		this.salaryDates = salaryDates;
	}

	public ArrayList<LocalDate> getPublicHolidayDates() 
	{
		return publicHolidayDates;
	}

	public void setPublicHolidayDates(ArrayList<LocalDate> publicHolidayDates) 
	{
		this.publicHolidayDates = publicHolidayDates;
	}

	
}
