package example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Statement implements StatementInterface {

	private static final long serialVersionUID = 1L;
	private List<Transaction> transactions;
	private Account account;
	private Date from;
	private Date to;

	//constructor
	public Statement(Account a, Date f, Date t, List<Transaction> tt) {
		transactions = tt;
		this.account = a;
		this.from = f;
		this.to = t;
	}

	//getters and setters
	public void setTransaction(Transaction t) {
		transactions.add(t);
	}

	@Override
	public int getAccountnum() {
		return account.getAccNo();
	}

	@Override
	public Date getStartDate() {
		return from;
	}

	@Override
	public Date getEndDate() {
		return to;
	}

	@Override
	public String getAccoutName() {
		return account.getUsername();
	}

	@Override
	public List<Transaction> getTransactions() {
		List<Transaction> trs = new ArrayList<>();

		for (Transaction t: transactions) {
			if (t.getDate().after(from) && t.getDate().before(to) && t.getAccount() == account.getAccNo()) {
				trs.add(t);
			}
		}
		return trs;
		
	}
}
