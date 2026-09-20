/*
 * @author - neo mmekwa 
 */

import java.util.ArrayList;

/*
 * this class represents a single atm, specifies its id and location and list of its transactions
 */
public class ATM 
{
	//attributes 
	private String atmID;
	private String location;
	private ArrayList<ATMTransaction> transactions;

	
	//defualt constructor 
	 
	public ATM() 
	{
		this.atmID = null;
		this.location = null;
		this.transactions = new ArrayList<ATMTransaction>();
	}
	
	//parameterised contructor
	public ATM(String atmID, String location ) 
	{
		this.atmID = atmID;
		this.location = location;
		this.transactions =  new ArrayList<ATMTransaction>();
	}
	
	//helper mehtod to add transactions
	public void addTransaction(ATMTransaction transaction)
	{
	    transactions.add(transaction);
	}
	
    /*
     * accessors and mutators 
     */

	public String getAtmID() 
	{
		return atmID;
	}

	public void setAtmID(String atmID)
	{
		this.atmID = atmID;
	}

	public String getLocation() 
	{
		return location;
	}

	public void setLocation(String location) 
	{
		this.location = location;
	}

	public ArrayList<ATMTransaction> getTransactions() 
	{
		return transactions;
	}

	public void setTransactions(ArrayList<ATMTransaction> transactions) 
	{
		this.transactions = transactions;
	}


}
