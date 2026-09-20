/**
 * @author neo mmekwa 
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        
        CashShortagePredictor predictor = new CashShortagePredictor(atms,analyzer,calendar);

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
                	displayCashShortageMenu(scanner,predictor,atms,calendar);
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
	
	//MENUS
	
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

    //displays the cash shortage prediction menu
    public static void displayCashShortageMenu(
            Scanner scanner,
            CashShortagePredictor predictor,
            ArrayList<ATM> atms,
            CalendarManager calendar)
    {
        int option = 0;

        while (option != 3)
        {
            System.out.println();
            System.out.println(
                    "****************************************");
            System.out.println(
                    "       CASH SHORTAGE PREDICTION");
            System.out.println(
                    "****************************************");
            System.out.println();

            System.out.println("1. View ATM Shortage Overview");
            System.out.println("2. View Individual ATM Prediction");
            System.out.println("3. Return to Main Menu");
            System.out.println();

            System.out.print("Select option: ");
            option = scanner.nextInt();
           
            //forecast starts from the day after the historical transaction data ends
            LocalDateTime predictionDate = predictor.getPredictionDate();

            switch (option)
            {
                case 1:
                    displayShortageOverview(predictor,atms, predictionDate);
                    break;

                case 2:
                    displayPredictionATMSelection(scanner, predictor, atms, calendar,predictionDate);
                    break;

                case 3:
                    break;

                default:
                    System.out.println();
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    
    //TREND ANALYSIS 
    
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

    
    //CASH SHORTAGE PREDICTION
    
    //displays shortage predictions for all ATMs
    public static void displayShortageOverview(CashShortagePredictor predictor,ArrayList<ATM> atms,LocalDateTime predictionDate)
    {
        System.out.println();
        System.out.println(
                "****************************************");
        System.out.println(
                "          ATM SHORTAGE OVERVIEW");
        System.out.println(
                "****************************************");
        System.out.println();

        System.out.println("Forecast Date: " + predictionDate.toLocalDate());


        for (ATM atm : atms)
        {
            System.out.println();
            System.out.println(atm.getAtmID() + " - "+ atm.getLocation());

            System.out.println();

            System.out.printf("Current Cash Balance:           R%,.2f%n",predictor.getCurrentBalance(atm));
            System.out.printf("Predicted Daily Demand:         R%,.2f%n",predictor.predictDailyDemand(atm, predictionDate));

            System.out.printf("Predicted Depletion Time:       %.1f hours%n",predictor.calculateHoursRemaining(atm, predictionDate));
            
            System.out.println("Prediction:                     "+ predictor.getPredictionStatement(atm,predictionDate));

            System.out.println("Risk Level:                     " + predictor.determineRiskLevel(atm, predictionDate));
            System.out.println("Recommended Action:             " + predictor.determineRecommendedAction( atm,predictionDate));
            
            if (predictor.requiresReplenishment(atm,predictionDate))
            {
                System.out.printf("Recommended Replenishment:      R%,.2f%n", predictor.calculateReplenishmentAmount(atm,predictionDate));
            }
        
        }

        displayReplenishmentSchedule(predictor, atms, predictionDate);
    }
    
    //displays ATMs in replenishment priority order
    public static void displayReplenishmentSchedule(CashShortagePredictor predictor, ArrayList<ATM> atms, LocalDateTime predictionDate)
    {
        ArrayList<ATM> schedule = new ArrayList<ATM>();

        //only ATMs requiring replenishment
        for (ATM atm : atms)
        {
            if (predictor.requiresReplenishment(atm, predictionDate))
            {
                schedule.add(atm);
            }
        }

        //sort by predicted depletion time
        for (int i = 0; i < schedule.size() - 1; i++)
        {
            for (int j = i + 1; j < schedule.size(); j++)
            {
                double firstHours = predictor.calculateHoursRemaining(schedule.get(i), predictionDate);

                double secondHours =predictor.calculateHoursRemaining( schedule.get(j), predictionDate);

                if (secondHours < firstHours)
                {
                    ATM temp = schedule.get(i);

                    schedule.set(i, schedule.get(j));

                    schedule.set(j,temp);
                }
            }
        }


        System.out.println();
        System.out.println(
                "==============================================================");
        System.out.println(
                "                 REPLENISHMENT SCHEDULE");
        System.out.println(
                "==============================================================");
        System.out.println();

        System.out.printf( "%-10s %-10s %-27s %-10s %-20s %-20s%n", "Priority","ATM", "Location", 
        		"Risk","Recommended By", "Amount");

        System.out.println(
                "--------------------------------------------------------------------------");

        if (schedule.size() == 0)
        {
            System.out.println("No ATMs currently require replenishment within the next 72 hours.");

            return;
        }

        for (int i = 0; i < schedule.size(); i++)
        {
            ATM atm = schedule.get(i);

            System.out.printf("%-10d %-10s %-27s %-10s %-20s R%,.2f%n", (i + 1), atm.getAtmID(), 
            		atm.getLocation(), predictor.determineRiskLevel(atm,predictionDate),
            		predictor.getRecommendedBy(atm,  predictionDate), 
            		predictor.calculateReplenishmentAmount(atm, predictionDate));
        }
    }
    
    //allows user to select an ATM for shortage prediction
    public static void displayPredictionATMSelection(Scanner scanner, CashShortagePredictor predictor, ArrayList<ATM> atms,CalendarManager calendar,LocalDateTime predictionDate)
    {
        System.out.println();
        System.out.println("AVAILABLE ATMs");
        System.out.println();

        for (int i = 0; i < atms.size(); i++)
        {
            ATM atm = atms.get(i);
            System.out.println((i + 1)+ ". "  + atm.getAtmID()  + " - " + atm.getLocation());
        }

        System.out.println();
        System.out.print("Select ATM: ");

        int choice = scanner.nextInt();

        if (choice < 1 || choice > atms.size())
        {
            System.out.println("Invalid ATM selection.");
            return;
        }

        ATM selectedATM =atms.get(choice - 1);

        displayIndividualPrediction( predictor, selectedATM,calendar,predictionDate);
    }
    
    //displays shortage prediction for one ATM
    public static void displayIndividualPrediction(CashShortagePredictor predictor, ATM atm, CalendarManager calendar, LocalDateTime predictionDate)
    {
        System.out.println();
        System.out.println("****************************************");
        System.out.println("       " + atm.getAtmID() + " - SHORTAGE PREDICTION");
        System.out.println("****************************************");
        System.out.println();

        System.out.println("Location:                       " + atm.getLocation());
        System.out.println("Forecast Date:                  " + predictionDate.toLocalDate());
        System.out.println("Forecast Day Type:              " + calendar.getDayType(predictionDate));

        System.out.println();

        System.out.printf("Current Cash Balance:           R%,.2f%n", predictor.getCurrentBalance(atm));
        System.out.printf("Predicted Daily Demand:         R%,.2f%n", predictor.predictDailyDemand(atm, predictionDate));
        System.out.printf("Predicted Depletion Time:       %.1f hours%n",predictor.calculateHoursRemaining( atm, predictionDate));
        
        System.out.println("Prediction:                     " + predictor.getPredictionStatement(atm,predictionDate));

        System.out.println();
        System.out.println("RISK ASSESSMENT");
        System.out.println();

        System.out.println("Risk Level:                     " + predictor.determineRiskLevel(atm, predictionDate));
        System.out.println("Recommended Action:             " + predictor.determineRecommendedAction(atm,predictionDate));
        if (predictor.requiresReplenishment(atm,predictionDate))
        {
            System.out.printf("Recommended Replenishment:    R%,.2f%n", predictor.calculateReplenishmentAmount(atm,predictionDate));
        }
    
    }
    
    
    
}
