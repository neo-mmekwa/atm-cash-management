/**
 * @author neo mmekwa 
 */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main 
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		//load data
        CalendarManager calendar = FileManager.loadCalendarData("data/salary_dates.csv", "data/public_holidays.csv");

        ArrayList<ATM> atms = FileManager.loadATMData("data/atm_transactions.csv");

        ATMAnalyzer analyzer = new ATMAnalyzer(atms, calendar);

        Scanner scanner = new Scanner(System.in);

        int option = 0;

        while (option != 4)
        {
            System.out.println();
            System.out.println(
                    "****************************************");
            System.out.println(
                    "       ATM CASH MANAGEMENT SYSTEM");
            System.out.println(
                    "****************************************");
            System.out.println();

            System.out.println("1. Analyze ATM Usage Trends");
            System.out.println("2. Predict Cash Shortages");
            System.out.println("3. Analyze Denominations");
            System.out.println("4. Exit");
            System.out.println();

            System.out.print("Select option: ");
            option = scanner.nextInt();

            switch (option)
            {
                case 1:
                    displayTrendAnalysisMenu(scanner, analyzer, atms);
                    break;

                case 2:
                    System.out.println();
                    System.out.println("Cash shortage prediction coming soon");
                    break;

                case 3:
                    System.out.println();
                    System.out.println("Denomination analysis coming soon");
                    break;

                case 4:
                    System.out.println();
                    System.out.println("Thank you for using the ATM Cash Management System.");
                    break;

                default:
                    System.out.println();
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    // displays the ATM usage trend analysis menu
    public static void displayTrendAnalysisMenu(Scanner scanner, ATMAnalyzer analyzer, ArrayList<ATM> atms)
    {
        int option = 0;

        while (option != 4)
        {
            System.out.println();
            System.out.println(
                    "****************************************");
            System.out.println(
                    "       ATM USAGE TREND ANALYSIS");
            System.out.println(
                    "****************************************");
            System.out.println();

            System.out.println("1. View Summary of Findings");
            System.out.println("2. Analyze a Location");
            System.out.println("3. Analyze an Individual ATM");
            System.out.println("4. Return to Main Menu");
            System.out.println();

            System.out.print("Select option: ");
            option = scanner.nextInt();

            switch (option)
            {
                case 1:
                    displaySummary(analyzer);
                    break;

                case 2:
                    displayLocationSelection(scanner, analyzer);
                    break;

                case 3:
                    displayATMSelection(scanner, analyzer, atms);
                    break;

                case 4:
                    break;

                default:
                    System.out.println();
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    //displays summary findings from the historical data
    public static void displaySummary(ATMAnalyzer analyzer)
    {
        System.out.println();
        System.out.println( "****************************************");
        System.out.println( "          SUMMARY OF FINDINGS");
        System.out.println("****************************************");

        //location comparison
        System.out.println();
        System.out.println("LOCATION COMPARISON");

        for (String location : analyzer.getLocations())
        {
            System.out.println();
            System.out.println(location);

            System.out.printf("Total Withdrawal Amount:       R%,.2f%n", analyzer.calculateLocationTotalWithdrawals(location));
            System.out.printf("Average Daily Withdrawal:      R%,.2f%n", analyzer.calculateLocationAverageDailyWithdrawal(location));
            System.out.println("Highest Demand Day Type:       " + getHighestLocationDayType(analyzer, location));
            System.out.println("Number of ATMs:                " + analyzer.countATMsAtLocation(location));
        }

        //ATM comparison
        ATM highestATM = analyzer.getHighestDemandATM();
        ATM lowestATM = analyzer.getLowestDemandATM();
        ATM downtimeATM = analyzer.getATMWithMostDowntime();

        System.out.println();
        System.out.println("ATM COMPARISON");
        System.out.println();

        System.out.println("Highest Withdrawal Demand:      " + highestATM.getAtmID() + " - "  + highestATM.getLocation());
        System.out.println("Lowest Withdrawal Demand:       " + lowestATM.getAtmID() + " - " + lowestATM.getLocation());
        System.out.println("Most Downtime Incidents:        " + downtimeATM.getAtmID() + " (" + analyzer.countDowntimeIncidents(downtimeATM) + " incidents)");

        //day type trends
        System.out.println();
        System.out.println("DAY-TYPE TRENDS");
        System.out.println();

        System.out.println("Highest Demand Day Type:        " + analyzer.getHighestDemandDayType());
        System.out.println("Lowest Demand Day Type:         " + analyzer.getLowestDemandDayType());

        //specific date trends
        LocalDate highestDate = analyzer.getHighestWithdrawalDate();
        LocalDate highestSalaryDate = analyzer.getHighestWithdrawalSalaryDate();

        System.out.println();
        System.out.println("DATE TRENDS");
        System.out.println();

        System.out.println("Highest Withdrawal Date:        " + highestDate);
        System.out.printf( "Total Withdrawal Amount:        R%,.2f%n", analyzer.calculateWithdrawalsForDate(highestDate));
        System.out.println("Busiest Salary Date:            " + highestSalaryDate);
        System.out.printf( "Total Withdrawal Amount:        R%,.2f%n", analyzer.calculateWithdrawalsForDate(highestSalaryDate));
    }

    //allows user to select a location to analyze
    public static void displayLocationSelection(Scanner scanner,ATMAnalyzer analyzer)
    {
        ArrayList<String> locations = analyzer.getLocations();

        System.out.println();
        System.out.println("AVAILABLE LOCATIONS");
        System.out.println();

        for (int i = 0; i < locations.size(); i++)
        {
            System.out.println((i + 1) + ". " + locations.get(i));
        }

        System.out.println();
        System.out.print("Select location: ");

        int choice = scanner.nextInt();

        if (choice < 1 || choice > locations.size())
        {
            System.out.println("Invalid location selection.");
            return;
        }

        String location = locations.get(choice - 1);

        displayLocationAnalysis(analyzer, location);
    }

    //displays detailed analysis for one location
    public static void displayLocationAnalysis(ATMAnalyzer analyzer, String location)
    {
        System.out.println();
        System.out.println("****************************************");
        System.out.println("       " + location.toUpperCase()+ " - USAGE ANALYSIS");
        System.out.println("****************************************");
        System.out.println();

        System.out.println("Number of ATMs:                 "+ analyzer.countATMsAtLocation(location));

        System.out.printf("Total Withdrawal Amount:        R%,.2f%n",analyzer.calculateLocationTotalWithdrawals( location));

        System.out.println("Withdrawal Transactions:        " + analyzer.countLocationWithdrawals(location));

        System.out.printf("Average Daily Withdrawal:       R%,.2f%n",analyzer.calculateLocationAverageDailyWithdrawal(location));

        System.out.println();
        System.out.println("AVERAGE DAILY WITHDRAWAL BY DAY TYPE");
        System.out.println();

        System.out.printf("Salary Day Average:             R%,.2f%n",analyzer.calculateLocationDayTypeAverage(location, "Salary Day"));

        System.out.printf("Public Holiday Average:         R%,.2f%n",analyzer.calculateLocationDayTypeAverage(location, "Public Holiday"));

        System.out.printf("Weekend Average:                R%,.2f%n", analyzer.calculateLocationDayTypeAverage(location, "Weekend"));

        System.out.printf("Normal Weekday Average:         R%,.2f%n",analyzer.calculateLocationDayTypeAverage(location, "Normal Weekday"));

        System.out.println();

        System.out.println("Downtime Incidents:             " + analyzer.countLocationDowntimeIncidents(location));

        System.out.println();
        System.out.println("ATMs AT LOCATION");

        for (ATM atm : analyzer.getATMs())
        {
            if (atm.getLocation().equals(location))
            {
                System.out.println(atm.getAtmID());
            }
        }
    }

    //allows user to select an ATM
    public static void displayATMSelection(Scanner scanner, ATMAnalyzer analyzer, ArrayList<ATM> atms)
    {
        System.out.println();
        System.out.println("AVAILABLE ATMs");
        System.out.println();

        for (int i = 0; i < atms.size(); i++)
        {
            ATM atm = atms.get(i);

            System.out.println((i + 1) + ". " + atm.getAtmID() + " - " + atm.getLocation());
        }

        System.out.println();
        System.out.print("Select ATM: ");

        int choice = scanner.nextInt();

        if (choice < 1 || choice > atms.size())
        {
            System.out.println("Invalid ATM selection.");
            return;
        }

        ATM selectedATM = atms.get(choice - 1);

        displayATMAnalysis(analyzer, selectedATM);
    }

    //displays detailed analysis for one ATM
    public static void displayATMAnalysis(ATMAnalyzer analyzer, ATM atm)
    {
        System.out.println();
        System.out.println("****************************************");
        System.out.println("       " + atm.getAtmID() + " - " + atm.getLocation().toUpperCase());
        System.out.println("****************************************");
        System.out.println();

        System.out.printf("Total Withdrawal Amount:        R%,.2f%n",analyzer.calculateTotalWithdrawals(atm));

        System.out.println("Withdrawal Transactions:        " + analyzer.countWithdrawals(atm));

        System.out.printf("Average Daily Withdrawal:       R%,.2f%n",analyzer.calculateAverageDailyWithdrawal(atm));

        System.out.println();
        System.out.println( "AVERAGE DAILY WITHDRAWAL BY DAY TYPE");
        System.out.println();

        System.out.printf( "Salary Day Average:             R%,.2f%n",analyzer.calculateDayTypeAverage(atm, "Salary Day"));

        System.out.printf("Public Holiday Average:         R%,.2f%n", analyzer.calculateDayTypeAverage(atm, "Public Holiday"));

        System.out.printf( "Weekend Average:                R%,.2f%n", analyzer.calculateDayTypeAverage(atm, "Weekend"));

        System.out.printf( "Normal Weekday Average:         R%,.2f%n", analyzer.calculateDayTypeAverage(atm, "Normal Weekday"));

        System.out.println();

        System.out.println("Downtime Incidents:             "  + analyzer.countDowntimeIncidents(atm));
    }

    //determines highest demand day type for a location
    public static String getHighestLocationDayType(ATMAnalyzer analyzer, String location)
    {
        String[] dayTypes =
        {
            "Salary Day",
            "Public Holiday",
            "Weekend",
            "Normal Weekday"
        };

        String highestDayType = dayTypes[0];

        double highestAverage = analyzer.calculateLocationDayTypeAverage(location, highestDayType);

        for (String dayType : dayTypes)
        {
            double average = analyzer.calculateLocationDayTypeAverage(location,dayType);

            if (average > highestAverage)
            {
                highestAverage = average;
                highestDayType = dayType;
            }
        }

        return highestDayType;
    
	}

}
