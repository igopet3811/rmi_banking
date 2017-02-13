package example;

import java.util.Date;

public class Transaction {
	
	private int account;
	private Date tDate;
	private String type;
	private int amount;

	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	public Date gettDate() {
		return tDate;
	}
	public void settDate(Date tDate) {
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
}
