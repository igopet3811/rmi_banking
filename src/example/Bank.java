package example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bank implements BankInterface {
	
	private List<Account> accounts; // users accounts

	public Bank(List<Account> acc) throws RemoteException
	{
		super();
		accounts = acc;
	}

	@Override
	public long login(String username, String password) throws RemoteException, InvalidLogin {
		// login, check username and password
		for(Account a: accounts){
			if(username.equals(a.getUsername()) && password.equals(a.getPassword())){
				System.out.println("LOGIN SUCCESSFUL!");
			}
			else {
				System.out.println("WRONG USERNAME OR PASSWORD.");
			}
		}
		return 0;
	}

	@Override
	public void deposit(int accountnum, int amount) throws RemoteException {
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

	public static void main(String args[]) throws Exception {

		List<Account> listAcc = new ArrayList<>();
		Account a = new Account();
		a.setAccNo(100);
		a.setBalance(1000);
		a.setPassword("pass1");
		a.setUsername("user1");
		listAcc.add(a);
		

		try {
			String name = "Bank";
			BankInterface bank = new Bank(listAcc);
			
			int port = Integer.parseInt(args[0]);
			BankInterface stub =
					(BankInterface) UnicastRemoteObject.exportObject(bank, 0);
			Registry registry = LocateRegistry.getRegistry(port);
			registry.rebind(name, stub);
			System.out.println("Bank server is up and running!");
		} catch (Exception e) {
			System.err.println("Error starting Bank server: ");
			e.printStackTrace();
		}

	}

}