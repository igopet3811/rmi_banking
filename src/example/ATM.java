package example;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ATM {

	public static void main (String args[]) throws Exception {

		try {
			String name = "Bank";
			String host = (String)args[0];
			int port = Integer.parseInt(args[1]);
			String operation = (String)args[2];

			Registry registry = LocateRegistry.getRegistry(host,port);
			BankInterface bank = (BankInterface)registry.lookup(name);

			//login cmd line functionality
			if(operation.equals("login")){
				try {
					long sess = bank.login((String)args[3], (String)args[4]);
					System.out.println("SESSION ID IS " + sess + " AND IT WILL LAST FOR 5 MINUTES");
				} 
				catch (InvalidLogin | RemoteException e){
					e.printStackTrace();
				}
			}

			//deposit money
			else if(operation.equals("deposit")){
				try {
					long sessionId = bank.hasActiveSession(Integer.parseInt(args[3]));

					if(sessionId == -1){
						System.out.println("DEPOSIT MONEY - SESSION DOESN'T EXIST.");
					}
					else {
						System.out.println("DEPOSIT MONEY - SESSION " + sessionId + " " + "IS ACTIVE.");
						bank.deposit(Integer.parseInt(args[3]), Integer.parseInt(args[4]), sessionId);
						System.out.println(Integer.parseInt(args[4]) + " WAS SUCCESFULLY ADDED TO YOUR ACCOUNT");
					}
				} 
				catch (InvalidSession | RemoteException e){
					e.printStackTrace();
				}
			}
			
			// withdrawal procedure
			else if(operation.equals("withdraw")){
				long sessionId = bank.hasActiveSession(Integer.parseInt(args[3]));

				try {
					if(sessionId == -1){
						System.out.println("WITHDRAW MONEY - SESSION DOESN'T EXIST.");
					}
					else {
						System.out.println("WITHDRAW MONEY - ACTIVE SESSION IS: " + sessionId);
						bank.withdraw(Integer.parseInt(args[3]), Integer.parseInt(args[4]), sessionId);
						System.out.println(Integer.parseInt(args[4]) + " WAS SUCCESFULLY WITHDRAWN FROM YOUR ACCOUNT.");
					}
				} 
				catch (RemoteException e){
					e.printStackTrace();
				}
			}

			//inquiry
			else if(operation.equals("inquiry")){
				try {
					long sessionId = bank.hasActiveSession(Integer.parseInt(args[3]));
					
					if(sessionId == -1){
						System.out.println("INQUIRY - SESSION DOESN'T EXIST.");
					}
					else {
						System.out.println("INQUIRY - ACTIVE SESSION IS: " + sessionId);
						int balance = bank.inquiry(Integer.parseInt(args[3]), sessionId);
						System.out.println("ACC NUMBER: " + Integer.parseInt(args[3]) + ". BALANCE: " + balance);
					}
				} 
				catch (InvalidSession | RemoteException e){
					e.printStackTrace();
				}
			}
			
			//inquiry
			else if(operation.equals("statement")){
				try {
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					long sessionId = bank.hasActiveSession(Integer.parseInt(args[3]));
					Date start = (Date)df.parse(args[4]);
					Date end = (Date)df.parse(args[5]);
					
					if(sessionId == -1){
						System.out.println("STATEMENT - SESSION DOESN'T EXIST.");
					}
					else {
						System.out.println("STATEMENT - ACTIVE SESSION IS: " + sessionId);
						StatementInterface si = bank.getStatement(Integer.parseInt(args[3]), start, end);
						System.out.println("ACCOUNT NUMBER: " + si.getAccountnum());
						System.out.println("ACCOUNT NAME: " + si.getAccoutName());
						System.out.println("STATEMENT FROM: " + si.getStartDate() + " TO: " + si.getEndDate());
						System.out.println(si.getTransactions().toString());
					}
				} 
				catch (InvalidSession | RemoteException e){
					e.printStackTrace();
				}
			}
			else{
				System.out.println("UNRECOGNISED COMMAND");
			}

		} catch (Exception e) {
			System.err.println("Client ATM exception: ");
			e.printStackTrace();
		}

	}

}
