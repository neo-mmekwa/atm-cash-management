/**
 * @author neo mmekwa 
 */

import java.util.ArrayList;

public class Main 
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("****************************************");
        System.out.println("       ATM CASH MANAGEMENT SYSTEM");
        System.out.println("****************************************");
        System.out.println();
        
        
        //load calendar data
        CalendarManager calendar = FileManager.loadCalendarData("data/salary_dates.csv", "data/public_holidays.csv");

        //load ATM data
        ArrayList<ATM> atms =FileManager.loadATMData("data/atm_transactions.csv");


        //test loaded calendar data
        System.out.println("Salary dates loaded: " + calendar.getSalaryDates().size());
        System.out.println("Public holidays loaded: "+ calendar.getPublicHolidayDates().size());


        //test loaded ATM data
        System.out.println();
        System.out.println("ATMs loaded: " + atms.size());

        int totalTransactions = 0;

        for (ATM atm : atms)
        {
            int numberOfTransactions = atm.getTransactions().size();

            totalTransactions += numberOfTransactions;

            System.out.println(atm.getAtmID() + " | "+ atm.getLocation()  + " | Transactions: " + numberOfTransactions);
        }

        System.out.println();
        System.out.println("Total transactions loaded: " + totalTransactions);
    }
        
	
}
