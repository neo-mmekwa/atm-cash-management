/*
 * @author neo mmekwa 
 * */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/*
 * this class manages the 3 csv files thabt store the transactions, salary dates and holidays
 * */
public class FileManager 
{
	//load dates from csv files
    public static ArrayList<LocalDate> loadDates(String fileName)
    {
        ArrayList<LocalDate> dates = new ArrayList<LocalDate>();

        try
        {
            BufferedReader reader =   new BufferedReader(new FileReader(fileName));

            String line;

            //skip the heading
            reader.readLine();

            while ((line = reader.readLine()) != null)
            {
                if (!line.trim().isEmpty())
                {
                    LocalDate date = LocalDate.parse(line.trim());

                    dates.add(date);
                }
            }

            reader.close();
        }
        catch (IOException e)
        {
            System.out.println(
                    "Error reading file: " + fileName);
        }

        return dates;
    }


    //loads salary dates and public holiday dates and then create the calendar manager
    public static CalendarManager loadCalendarData(String salaryFile, String holidayFile)
    {
        ArrayList<LocalDate> salaryDates = loadDates(salaryFile);

        ArrayList<LocalDate> publicHolidayDates = loadDates(holidayFile);

        CalendarManager calendar = new CalendarManager(salaryDates, publicHolidayDates);

        return calendar;
    }


	    
     //loads ATM transaction data from CSV file
     
    public static ArrayList<ATM> loadATMData(String fileName)
    {
        ArrayList<ATM> atms = new ArrayList<ATM>();

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line;

            //skip the headings
            reader.readLine();

            while ((line = reader.readLine()) != null)
            {
                if (line.trim().isEmpty())
                {
                    continue;
                }

                String[] data = line.split(",");


                //read ATM information
                String atmID = data[0];
                String location = data[1];


                //read transaction information
                LocalDateTime dateTime =  LocalDateTime.parse(data[2]);

                double cashBalance = Double.parseDouble(data[3]);

                int r10notes = Integer.parseInt(data[4]);

                int r20notes = Integer.parseInt(data[5]);

                int r50notes =  Integer.parseInt(data[6]);

                int r100notes = Integer.parseInt(data[7]);

                int r200notes = Integer.parseInt(data[8]);

                double withdrawalAmount = Double.parseDouble(data[9]);

                double depositAmount = Double.parseDouble(data[10]);

                boolean downtime = Boolean.parseBoolean(data[11]);


                //check if ATM has already been created
                ATM atm = findATM(atms, atmID);

                //create ATM if this is its first record
                if (atm == null)
                {
                    atm = new ATM(atmID, location);
                    atms.add(atm);
                }


                //create transaction
                ATMTransaction transaction = new ATMTransaction(
                                atm,
                                dateTime,
                                cashBalance, 
                                r10notes,
                                r20notes, 
                                r50notes,
                                r100notes,
                                r200notes, 
                                withdrawalAmount,
                                depositAmount,
                                downtime);


                //add transaction to its ATM
                atm.addTransaction(transaction);
            }

            reader.close();
        }
        catch (IOException e)
        {
            System.out.println("Error reading ATM transaction file: " + fileName);
        }

        return atms;
    }


    // searchthe ATM list for a specific ATM ID
     
    private static ATM findATM(ArrayList<ATM> atms,String atmID)
    {
        for (ATM atm : atms)
        {
            if (atm.getAtmID().equals(atmID))
            {
                return atm;
            }
        }

        return null;
    }
	
	
}
