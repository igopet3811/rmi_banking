package example;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable{
	
	private int account;
	private Date tDate;
	private String type;
	private int amount;

	public Transaction(int account, Date tDate, String type, int amt){
		this.account = account;
		this.tDate = tDate;
		this.type = type;
		this.amount = amt;
	}
	
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	public Date getDate() {
		return tDate;
	}
	public void setDate(Date tDate) {
		this.tDate = tDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() { 
	    return "Account #: " + account + ". Date: " + tDate + ". Type: " + type + ". Amount: " + amount;
	} 
}
