/**
 * @author - neo mmekwa 
 */

import java.time.LocalDateTime;

/**
 * this class represents everything an atm records
 */
public class ATMTransaction 
{
	//attributes
	private ATM atm;
	
	private LocalDateTime dateTime;
	
	private double cashBalance;
	private int r10notes;
	private int r20notes;
	private int r50notes;
	private int r100notes;
	private int r200notes;
	
	private double withdrawalAmount;
	private double depositAmount;
	
	private boolean downtime;


	//parameterised contructor

	public ATMTransaction(ATM atm, LocalDateTime dateTime, double cashBalance, int r10notes, int r20notes, int r50notes,
			int r100notes, int r200notes, double withdrawalAmount, double depositAmount, boolean downtime) 
	{
		this.atm = atm;
		this.dateTime = dateTime;
		this.cashBalance = cashBalance;
		this.r10notes = r10notes;
		this.r20notes = r20notes;
		this.r50notes = r50notes;
		this.r100notes = r100notes;
		this.r200notes = r200notes;
		this.withdrawalAmount = withdrawalAmount;
		this.depositAmount = depositAmount;
		this.downtime = downtime;
	}

	

	//accessors and mutators
	
	public ATM getAtm() {
		return atm;
	}

	public void setAtm(ATM atm) {
		this.atm = atm;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public double getCashBalance() {
		return cashBalance;
	}

	public void setCashBalance(double cashBalance) {
		this.cashBalance = cashBalance;
	}

	public int getR10notes() {
		return r10notes;
	}

	public void setR10notes(int r10notes) {
		this.r10notes = r10notes;
	}

	public int getR20notes() {
		return r20notes;
	}

	public void setR20notes(int r20notes) {
		this.r20notes = r20notes;
	}

	public int getR50notes() {
		return r50notes;
	}

	public void setR50notes(int r50notes) {
		this.r50notes = r50notes;
	}

	public int getR100notes() {
		return r100notes;
	}

	public void setR100notes(int r100notes) {
		this.r100notes = r100notes;
	}

	public int getR200notes() {
		return r200notes;
	}

	public void setR200notes(int r200notes) {
		this.r200notes = r200notes;
	}

	public double getWithdrawalAmount() {
		return withdrawalAmount;
	}

	public void setWithdrawalAmount(double withdrawalAmount) {
		this.withdrawalAmount = withdrawalAmount;
	}

	public double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public boolean isDowntime() {
		return downtime;
	}

	public void setDowntime(boolean downtime) {
		this.downtime = downtime;
	}

	


}
