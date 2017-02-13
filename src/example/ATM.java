package example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ATM {

	public static void main (String args[]) throws Exception {
		
		try {
			String name = "Bank";
			String host = (String)args[0];
			int port = Integer.parseInt(args[1]);
			String operation = (String)args[2];

			Registry registry = LocateRegistry.getRegistry(host,port);
			BankInterface bank = (BankInterface)registry.lookup(name);

			switch(operation) {
			case "login" :
				// Statements
				System.out.println("LOGGING IN ");
				bank.login((String)args[3], (String)args[4]);
				break;
			case "inquiry":
				// Statements
				System.out.println("ENQUIRY ");
				break;
			case "deposit":
				// Statements
				System.out.println("DEPOSIT MONEY ");
				bank.deposit(Integer.parseInt(args[3]), Integer.parseInt(args[4]));
				break;
			case "withdraw":
				// Statements
				System.out.println("WITHDRAW MONEY ");
				break;
			case "statement":
				// Statements
				System.out.println("GET STATEMENT ");
				break;
			default : // Optional
				System.out.println("UNKNOWN COMMAND ");
			}

			/*Pi task = new Pi(Integer.parseInt(args[2]));
        BigDecimal pi = comp.executeTask(task);
        System.out.println(pi);*/

		} catch (Exception e) {
			System.err.println("Client ATM exception: ");
			e.printStackTrace();
		}

	}

}
