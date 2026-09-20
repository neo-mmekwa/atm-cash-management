/**
 * @author neo mmekwa 
 */

import java.util.ArrayList;


//this class determines the diff denominations for each location
public class DenominationAnalyzer 
{
	//attributes
    private ArrayList<ATM> atms;


    //parameterised constructor
    public DenominationAnalyzer(ArrayList<ATM> atms)
    {
        this.atms = atms;
    }

    //tot num of R100 notes used at a loc
    public int getR10Total(String location)
    {
        int total = 0;

        for (ATM atm : atms)
        {
            if (atm.getLocation().equals(location))
            {
                for (ATMTransaction transaction : atm.getTransactions())
                {
                    total += transaction.getR10notes();
                }
            }
        }
        return total;
    }
    
    //tot num of R20 notes used at a loc
    public int getR20Total(String location)
    {
        int total = 0;

        for (ATM atm : atms)
        {
            if (atm.getLocation().equals(location))
            {
                for (ATMTransaction transaction : atm.getTransactions())
                {
                    total += transaction.getR20notes();
                }
            }
        }
        return total;
    }
    
    //tot num of R50 notes used at a loc
    public int getR50Total(String location)
    {
        int total = 0;

        for (ATM atm : atms)
        {
            if (atm.getLocation().equals(location))
            {
                for (ATMTransaction transaction : atm.getTransactions())
                {
                    total += transaction.getR50notes();
                }
            }
        }
        return total;
    }
    
    //tot num of R100 notes used at a loc
    public int getR100Total(String location)
    {
        int total = 0;

        for (ATM atm : atms)
        {
            if (atm.getLocation().equals(location))
            {
                for (ATMTransaction transaction : atm.getTransactions())
                {
                    total += transaction.getR100notes();
                }
            }
        }
        return total;
    }
    
    //tot num of R200 notes used at a loc
    public int getR200Total(String location)
    {
        int total = 0;

        for (ATM atm : atms)
        {
            if (atm.getLocation().equals(location))
            {
                for (ATMTransaction transaction : atm.getTransactions())
                {
                    total += transaction.getR200notes();
                }
            }
        }
        return total;
    }
    
    //most frequently used denomination
    public String getMostUsedDenomination(String location)
    {
        int highest = getR10Total(location);
        String denomination = "R10";

        if (getR20Total(location) > highest)
        {
            highest = getR20Total(location);
            denomination = "R20";
        }

        if (getR50Total(location) > highest)
        {
            highest = getR50Total(location);
            denomination = "R50";
        }

        if (getR100Total(location) > highest)
        {
            highest = getR100Total(location);
            denomination = "R100";
        }

        if (getR200Total(location) > highest)
        {
            denomination = "R200";
        }

        return denomination;
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

}
