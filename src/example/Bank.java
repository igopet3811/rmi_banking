package example;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Bank implements BankInterface {

	//accounts and active sessions stored on server side
	private List<Account> accounts;
	private HashMap<Account, Session> activeSessions;

	public Bank(List<Account> acc, HashMap<Account, Session> as) throws RemoteException {
		super();
		accounts = acc;
		activeSessions = as;
	}

	@Override
	public long login(String username, String password) throws RemoteException, InvalidLogin {

		long sessionId = -1;
		//check if session exists for specified username and password and return session id
		if(hasActiveSession(username, password) > -1){
			for(Account a: accounts){
				if(username.equals(a.getUsername()) && password.equals(a.getPassword())){
					sessionId = activeSessions.get(a).getSessionId();
				}
			}
			System.out.println("ALREADY ACTIVE SESSION! PROCEED WITH TRANSACTIONS.");
		}

		else {
			// login, check username and password,create session object and store it on the server
			for(Account a: accounts){
				if(username.equals(a.getUsername()) && password.equals(a.getPassword())){
					System.out.println("LOGIN SUCCESSFUL!");
					Session ses = new Session(getClientHost());
					activeSessions.put(a, ses);
					sessionId = ses.getSessionId();
					System.out.println("SESSION DETAILS: " + ses.getIpAddress() + " " + ses.getSessionId() + " " + ses.getExpiry().toString());
				}
				else {
					throw new InvalidLogin("INCORRECT USERNAME OR PASSWORD.");
				}
			}
		}
		return sessionId;
	}

	@Override
	public void deposit(int accountnum, int amount, long sessionId) throws RemoteException {
		// check if account exist and perform operation
		for(Account a: accounts){
			if(accountnum == a.getAccNo()){
				System.out.println("FOUND ACCOUNT " + accountnum);
				int bal = a.getBalance();
				a.setBalance(bal + amount);
				System.out.println("ADDED TO ACCno: " + accountnum + " " + amount +". OLD BALANCE: " + bal + " NEW: " + a.getBalance());
			}
			else {
				System.out.println("ACCOUNT DOESNT EXIST!");
			}
		}
	}

	@Override
	public void withdraw(int accountnum, int amount) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public int inquiry(int accountnum) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Statement getStatement(Date from, Date to) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method to check if active session exists for a selected name - password combination
	 */
	@Override
	public long hasActiveSession(String u, String p) throws RemoteException, InvalidLogin{
		long session = -1;
		for(Account a : accounts){
			if(u.equals(a.getUsername()) && p.equals(a.getPassword())){
				if(activeSessions.get(a) != null && activeSessions.get(a).getExpiry().after(new Date())){
					System.out.println("SESSION ACTIVE UNTIL " + activeSessions.get(a).getExpiry());
					System.out.println(activeSessions.get(a).getExpiry().before(new Date()));

					session = activeSessions.get(a).getSessionId();
				}
				else {
					System.out.println("SESSION EXPIRED. PLEASE LOGIN AGAIN.");
				}
			}
		}
		return session;
	}
	
	/**
	 * Method to fetch session from account number provided
	 */
	@Override
	public long hasActiveSession(int accNum) throws RemoteException, InvalidSession{
		
		long session = -1;
		
		for(Account acc : accounts){
			if(acc.getAccNo() == accNum){
				if(activeSessions.get(acc).getExpiry().after(new Date())){
					session = activeSessions.get(acc).getSessionId();
				}
				else {
					throw new InvalidSession("SESSION EXPIRED. PLEASE LOGIN AGAIN.");
				}
			}
		}		

		return session;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public HashMap<Account, Session> getActiveSessions() {
		return activeSessions;
	}

	public void setActiveSessions(HashMap<Account, Session> activeSessions) {
		this.activeSessions = activeSessions;
	}


	public static void main(String args[]) throws Exception {

		// setup an account
		List<Account> listAcc = new ArrayList<>();
		HashMap<Account,Session> sessions = new HashMap<>();
		Account a = new Account();
		a.setAccNo(100);
		a.setBalance(1000);
		a.setPassword("pass1");
		a.setUsername("user1");
		listAcc.add(a);

		try {
			String name = "Bank";
			BankInterface bank = new Bank(listAcc, sessions);

			int port = Integer.parseInt(args[0]);
			BankInterface stub = (BankInterface) UnicastRemoteObject.exportObject(bank, 0);
			Registry registry = LocateRegistry.getRegistry(port);
			registry.rebind(name, stub);
			System.out.println("Bank server is up and running!");
		} catch (Exception e) {
			System.err.println("Error starting Bank server: ");
			e.printStackTrace();
		}

	}

	public static String getClientHost(){
		String host = null;
		try {
			host= RemoteServer.getClientHost();
		}
		catch (ServerNotActiveException e) {
			e.printStackTrace();
		}
		return host;
	}

}