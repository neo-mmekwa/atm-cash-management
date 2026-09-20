//author neo mmekwa 

import java.time.LocalDate;
import java.util.ArrayList;

//this class is used to analyze atm usage patterns for each individual atm and each location 
public class ATMAnalyzer 
{

	//attribrutes 
	private ArrayList<ATM> atms ;
	private CalendarManager calendar;
	
	//parameterised constructor
	public ATMAnalyzer(ArrayList<ATM> atms, CalendarManager calendar)
	{
		this.atms = atms;
		this.calendar = calendar;
	}
	
	public ArrayList<ATM> getATMs()
	{
	    return atms;
	}
	
	//ATM USAGE ANALYSIS METHODS 
	
	
	//calc total withdrawals for one atm
	public double calculateTotalWithdrawals(ATM atm)
    {
        double total = 0;

        for (ATMTransaction transaction : atm.getTransactions())
        {
            total += transaction.getWithdrawalAmount();
        }

        return total;
    }

	//count withdrawal transaction for one atm
	public int countWithdrawals(ATM atm)
    {
        int count = 0;

        for (ATMTransaction transaction : atm.getTransactions())
        {
            if (transaction.getWithdrawalAmount() > 0)
            {
                count++;
            }
        }

        return count;
    }
	
	//calc avg daily withdrawal demand per atm
	public double calculateAverageDailyWithdrawal(ATM atm)
    {
        double totalWithdrawals = calculateTotalWithdrawals(atm);

        ArrayList<LocalDate> dates = new ArrayList<LocalDate>();

        for (ATMTransaction transaction : atm.getTransactions())
        {
            LocalDate date = transaction.getDateTime().toLocalDate();

            if (!dates.contains(date))
            {
                dates.add(date);
            }
        }

        if (dates.size() == 0)
        {
            return 0;
        }

        return totalWithdrawals / dates.size();
    }

	//calc avg daily demand forspecific day type
	public double calculateDayTypeAverage(ATM atm, String dayType)
    {
        ArrayList<LocalDate> dates = new ArrayList<LocalDate>();

        double totalWithdrawals = 0;

        for (ATMTransaction transaction : atm.getTransactions())
        {
            String transactionDayType = calendar.getDayType(transaction.getDateTime());

            if (transactionDayType.equals(dayType))
            {
                totalWithdrawals += transaction.getWithdrawalAmount();

                LocalDate date =  transaction.getDateTime().toLocalDate();

                if (!dates.contains(date))
                {
                    dates.add(date);
                }
            }
        }

        if (dates.size() == 0)
        {
            return 0;
        }

        return totalWithdrawals / dates.size();
    }

	//count downtime incidents
	public int countDowntimeIncidents(ATM atm)
    {
        int count = 0;

        for (ATMTransaction transaction : atm.getTransactions())
        {
            if (transaction.isDowntime())
            {
                count++;
            }
        }

        return count;
    }
	
	
	
	//LOCATION USAGE ANALYSIS METHODS 
	
	
	//get all unique ATM locations
	public ArrayList<String> getLocations()
	{
	    ArrayList<String> locations = new ArrayList<String>();

	    for (ATM atm : atms)
	    {
	        if (!locations.contains(atm.getLocation()))
	        {
	            locations.add(atm.getLocation());
	        }
	    }

	    return locations;
	}

	//count ATMs at a location
	public int countATMsAtLocation(String location)
	{
	    int count = 0;

	    for (ATM atm : atms)
	    {
	        if (atm.getLocation().equals(location))
	        {
	            count++;
	        }
	    }

	    return count;
	}

	//calc total withdrawal amount at a location
	public double calculateLocationTotalWithdrawals(String location)
	{
	    double total = 0;

	    for (ATM atm : atms)
	    {
	        if (atm.getLocation().equals(location))
	        {
	            total += calculateTotalWithdrawals(atm);
	        }
	    }

	    return total;
	}

	//count withdrawal transactions at a location
	public int countLocationWithdrawals(String location)
	{
	    int count = 0;

	    for (ATM atm : atms)
	    {
	        if (atm.getLocation().equals(location))
	        {
	            count += countWithdrawals(atm);
	        }
	    }

	    return count;
	}

	//calc avg daily withdrawal amount per location
	public double calculateLocationAverageDailyWithdrawal(String location)
	{
	    double totalWithdrawals = 0;

	    ArrayList<LocalDate> dates = new ArrayList<LocalDate>();

	    for (ATM atm : atms)
	    {
	        if (atm.getLocation().equals(location))
	        {
	            for (ATMTransaction transaction : atm.getTransactions())
	            {
	                totalWithdrawals += transaction.getWithdrawalAmount();

	                LocalDate date =  transaction.getDateTime().toLocalDate();

	                if (!dates.contains(date))
	                {
	                    dates.add(date);
	                }
	            }
	        }
	    }

	    if (dates.size() == 0)
	    {
	        return 0;
	    }

	    return totalWithdrawals / dates.size();
	}

	//calc avg  daily withdrawal amount for a type of day per location
	public double calculateLocationDayTypeAverage( String location, String dayType)
	{
	    double totalWithdrawals = 0;

	    ArrayList<LocalDate> dates = new ArrayList<LocalDate>();

	    for (ATM atm : atms)
	    {
	        if (atm.getLocation().equals(location))
	        {
	            for (ATMTransaction transaction :  atm.getTransactions())
	            {
	                String transactionDayType = calendar.getDayType(transaction.getDateTime());

	                if (transactionDayType.equals(dayType))
	                {
	                    totalWithdrawals += transaction.getWithdrawalAmount();

	                    LocalDate date = transaction.getDateTime().toLocalDate();

	                    if (!dates.contains(date))
	                    {
	                        dates.add(date);
	                    }
	                }
	            }
	        }
	    }

	    if (dates.size() == 0)
	    {
	        return 0;
	    }

	    return totalWithdrawals / dates.size();
	}

	//count downtime incidents at a location
	public int countLocationDowntimeIncidents(String location)
	{
	    int count = 0;

	    for (ATM atm : atms)
	    {
	        if (atm.getLocation().equals(location))
	        {
	            count += countDowntimeIncidents(atm);
	        }
	    }

	    return count;
	}


	//SUMMARY OF FINDINGS METHODS 
	
	
	//highest demand location
	public String getHighestDemandLocation()
	{
	    ArrayList<String> locations = getLocations();

	    if (locations.size() == 0)
	    {
	        return null;
	    }

	    String highestLocation = locations.get(0);

	    double highestAverage = calculateLocationAverageDailyWithdrawal(highestLocation);

	    for (String location : locations)
	    {
	        double average = calculateLocationAverageDailyWithdrawal(location);

	        if (average > highestAverage)
	        {
	            highestAverage = average;
	            highestLocation = location;
	        }
	    }

	    return highestLocation;
	}
	
	//lowest demand location
	public String getLowestDemandLocation()
	{
	    ArrayList<String> locations = getLocations();

	    if (locations.size() == 0)
	    {
	        return null;
	    }

	    String lowestLocation = locations.get(0);

	    double lowestAverage = calculateLocationAverageDailyWithdrawal(lowestLocation);

	    for (String location : locations)
	    {
	        double average = calculateLocationAverageDailyWithdrawal(location);

	        if (average < lowestAverage)
	        {
	            lowestAverage = average;
	            lowestLocation = location;
	        }
	    }

	    return lowestLocation;
	}
	
	//highest demand atm
	public ATM getHighestDemandATM()
	{
	    if (atms.size() == 0)
	    {
	        return null;
	    }

	    ATM highestATM = atms.get(0);

	    double highestAverage = calculateAverageDailyWithdrawal(highestATM);

	    for (ATM atm : atms)
	    {
	        double average = calculateAverageDailyWithdrawal(atm);

	        if (average > highestAverage)
	        {
	            highestAverage = average;
	            highestATM = atm;
	        }
	    }

	    return highestATM;
	}
	
	//lowest demand atm
	public ATM getLowestDemandATM()
	{
	    if (atms.size() == 0)
	    {
	        return null;
	    }

	    ATM lowestATM = atms.get(0);

	    double lowestAverage = calculateAverageDailyWithdrawal(lowestATM);

	    for (ATM atm : atms)
	    {
	        double average = calculateAverageDailyWithdrawal(atm);

	        if (average < lowestAverage)
	        {
	            lowestAverage = average;
	            lowestATM = atm;
	        }
	    }

	    return lowestATM;
	}
	
	//atm with most downtime 
	public ATM getATMWithMostDowntime()
	{
	    if (atms.size() == 0)
	    {
	        return null;
	    }

	    ATM highestATM = atms.get(0);

	    int highestDowntime = countDowntimeIncidents(highestATM);

	    for (ATM atm : atms)
	    {
	        int downtime = countDowntimeIncidents(atm);

	        if (downtime > highestDowntime)
	        {
	            highestDowntime = downtime;
	            highestATM = atm;
	        }
	    }

	    return highestATM;
	}
	
	//highest demand day type
	public String getHighestDemandDayType()
	{
	    String[] dayTypes =
	    {
	        "Salary Day",
	        "Public Holiday",
	        "Weekend",
	        "Normal Weekday"
	    };

	    String highestDayType = dayTypes[0];
	    double highestAverage = 0;

	    for (String dayType : dayTypes)
	    {
	        double total = 0;

	        for (ATM atm : atms)
	        {
	            total += calculateDayTypeAverage(atm, dayType);
	        }

	        if (total > highestAverage)
	        {
	            highestAverage = total;
	            highestDayType = dayType;
	        }
	    }

	    return highestDayType;
	}
	
	//lowest demand day type
	public String getLowestDemandDayType()
	{
	    String[] dayTypes =
	    {
	        "Salary Day",
	        "Public Holiday",
	        "Weekend",
	        "Normal Weekday"
	    };

	    String lowestDayType = dayTypes[0];

	    double lowestAverage = 0;

	    for (ATM atm : atms)
	    {
	        lowestAverage += calculateDayTypeAverage(atm, lowestDayType);
	    }

	    for (String dayType : dayTypes)
	    {
	        double total = 0;

	        for (ATM atm : atms)
	        {
	            total += calculateDayTypeAverage(atm, dayType);
	        }

	        if (total < lowestAverage)
	        {
	            lowestAverage = total;
	            lowestDayType = dayType;
	        }
	    }

	    return lowestDayType;
	}

	//highest withdrawal date 
	public LocalDate getHighestWithdrawalDate()
	{
	    ArrayList<LocalDate> dates = new ArrayList<LocalDate>();

	    for (ATM atm : atms)
	    {
	        for (ATMTransaction transaction : atm.getTransactions())
	        {
	            LocalDate date = transaction.getDateTime().toLocalDate();

	            if (!dates.contains(date))
	            {
	                dates.add(date);
	            }
	        }
	    }

	    LocalDate highestDate = null;
	    double highestAmount = 0;

	    for (LocalDate date : dates)
	    {
	        double total = calculateWithdrawalsForDate(date);

	        if (total > highestAmount)
	        {
	            highestAmount = total;
	            highestDate = date;
	        }
	    }

	    return highestDate;
	}
	
	//calc withdrawals per date
	public double calculateWithdrawalsForDate(LocalDate date)
	{
	    double total = 0;

	    for (ATM atm : atms)
	    {
	        for (ATMTransaction transaction : atm.getTransactions())
	        {
	            LocalDate transactionDate = transaction.getDateTime().toLocalDate();

	            if (transactionDate.equals(date))
	            {
	                total += transaction.getWithdrawalAmount();
	            }
	        }
	    }

	    return total;
	}
	
	//highest withdrawal salary date 
	public LocalDate getHighestWithdrawalSalaryDate()
	{
	    LocalDate highestDate = null;
	    double highestAmount = 0;

	    for (LocalDate salaryDate : calendar.getSalaryDates())
	    {
	        double total = calculateWithdrawalsForDate(salaryDate);

	        if (total > highestAmount)
	        {
	            highestAmount = total;
	            highestDate = salaryDate;
	        }
	    }

	    return highestDate;
	}

}
