import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author neo mmekwa 
 */

//this class predicts atm cash shortages using historical withdrawal demand and the latest recorded cash balance 
public class CashShortagePredictor
{
	//attributes 
	private ArrayList<ATM> atms;
    private ATMAnalyzer analyzer;
    private CalendarManager calendar;

	//parameterised constructor
    public CashShortagePredictor(ArrayList<ATM> atms, ATMAnalyzer analyzer, CalendarManager calendar)
    {
        this.atms = atms;
        this.analyzer = analyzer;
        this.calendar = calendar;
    }

	//latest recorded cash balance for an atm
    public double getCurrentBalance(ATM atm)
    {
        if (atm.getTransactions().size() == 0)
        {
            return 0;
        }

        ATMTransaction latestTransaction = atm.getTransactions().get(0);

        for (ATMTransaction transaction :atm.getTransactions())
        {
            if (transaction.getDateTime().isAfter(latestTransaction.getDateTime()))
            {
                latestTransaction = transaction;
            }
        }

        return latestTransaction.getCashBalance();
    }

	//predict daily withdrawal demand based on the day type
    public double predictDailyDemand(ATM atm,LocalDateTime predictionDate)
    {
        String dayType = calendar.getDayType(predictionDate);

        double predictedDemand =  analyzer.calculateDayTypeAverage(atm,dayType);

        //if theres no historical data , use the overall avg daily withdrawal
        if (predictedDemand <= 0)
        {
            predictedDemand = analyzer.calculateAverageDailyWithdrawal(atm);
        }

        return predictedDemand;
    }

    //calc est hrs until atm runs out
	public double calculateHoursRemaining(ATM atm,LocalDateTime predictionDate)
	{
	    double currentBalance =  getCurrentBalance(atm);
	
	    double predictedDailyDemand = predictDailyDemand(atm, predictionDate);
	
	    if (predictedDailyDemand <= 0)
	    {
	        return 0;
	    }
	
	    double daysRemaining = currentBalance  / predictedDailyDemand;
	
	    double hoursRemaining = daysRemaining * 24;
	
	    return hoursRemaining;
	}
    
    //determining the shortage risk according to hrs remaining
    public String determineRiskLevel(ATM atm, LocalDateTime predictionDate)
    {
        double hoursRemaining = calculateHoursRemaining(atm, predictionDate);

        if (hoursRemaining <= 24)
        {
            return "CRITICAL";
        }
        else if (hoursRemaining <= 48)
        {
            return "HIGH";
        }
        else if (hoursRemaining <= 72)
        {
            return "MEDIUM";
        }
        else
        {
            return "LOW";
        }
    }
    
    //prediction statement based on depletion time
    public String getPredictionStatement(ATM atm,LocalDateTime predictionDate)
    {
    	String riskLevel = determineRiskLevel(atm, predictionDate);

        if (riskLevel.equals("CRITICAL"))
        {
            return "ATM will likely run out of cash within 24 hours.";
        }
        else if (riskLevel.equals("HIGH"))
        {
            return "ATM will likely run out of cash within 24-48 hours.";
        }
        else if (riskLevel.equals("MEDIUM"))
        {
            return "ATM will likely run out of cash within 48-72 hours.";
        }
        else
        {
            return "ATM is not expected to run out of cash within the next 72 hours.";
        }
    }
    
    //recommending action based on atms shortage risk
    public String determineRecommendedAction(ATM atm, LocalDateTime predictionDate)
    {
        String riskLevel = determineRiskLevel( atm,predictionDate);

        if (riskLevel.equals("CRITICAL"))
        {
            return "Immediate replenishment required";
        }
        else if (riskLevel.equals("HIGH"))
        {
            return "Schedule replenishment within 24 hours";
        }
        else if (riskLevel.equals("MEDIUM"))
        {
            return "Schedule replenishment within 48 hours";
        }
        else
        {
            return "No immediate replenishment required";
        }
    }

	//recommended replenishment deadline for schedule
    public String getRecommendedBy(ATM atm,LocalDateTime predictionDate)
    {
        String riskLevel = determineRiskLevel(atm,predictionDate);

        if (riskLevel.equals("CRITICAL"))
        {
            return "Within 12 hours";
        }
        else if (riskLevel.equals("HIGH"))
        {
            return "Within 24 hours";
        }
        else if (riskLevel.equals("MEDIUM"))
        {
            return "Within 48 hours";
        }
        else
        {
            return "Not required within 72 hours";
        }
    }

    //determine if atm should be added to the replenishment schedule
    public boolean requiresReplenishment(ATM atm, LocalDateTime predictionDate)
    {
        String riskLevel = determineRiskLevel(atm,predictionDate);

        return !riskLevel.equals("LOW");
    }
    
    //latest transaction date and time in the transaction csv file
    public LocalDateTime getLatestTransactionDate()
    {
        LocalDateTime latestDate = null;

        for (ATM atm : atms)
        {
            for (ATMTransaction transaction :atm.getTransactions())
            {
                if (latestDate == null || transaction.getDateTime().isAfter(latestDate))
                {
                    latestDate = transaction.getDateTime();
                }
            }
        }

        return latestDate;
    }
    
    //date used to begin the forecast
    public LocalDateTime getPredictionDate()
    {
        LocalDateTime latestDate = getLatestTransactionDate();

        if (latestDate == null)
        {
            return null;
        }

        return latestDate.plusDays(1).toLocalDate().atStartOfDay();
    }
    
    //calc recommended replenishment amount to cover predicted demand for the next 72 hours
	public double calculateReplenishmentAmount( ATM atm, LocalDateTime predictionDate)
    {
	    double currentBalance = getCurrentBalance(atm);
	    double totalPredictedDemand = 0;

	    //calculate predicted demand for the next 3 days
		for (int day = 0; day < 3; day++)
		{
		    LocalDateTime forecastDate =  predictionDate.plusDays(day);
		    totalPredictedDemand +=   predictDailyDemand( atm,  forecastDate);
		}
		
	    double replenishmentAmount =  totalPredictedDemand - currentBalance;
	
	    //no replenishment required
	    if (replenishmentAmount < 0)
	    {
	        replenishmentAmount = 0;
	    }
	    
	      return replenishmentAmount;
	  }
    
    
    
    
    
    
    
    //accessors and mutators
    public ArrayList<ATM> getAtms() 
    {
		return atms;
	}

	public void setAtms(ArrayList<ATM> atms) 
	{
		this.atms = atms;
	}

	public ATMAnalyzer getAnalyzer()
	{
		return analyzer;
	}

	public void setAnalyzer(ATMAnalyzer analyzer) 
	{
		this.analyzer = analyzer;
	}

	public CalendarManager getCalendar() 
	{
		return calendar;
	}

	public void setCalendar(CalendarManager calendar) 
	{
		this.calendar = calendar;
	}
    
}
