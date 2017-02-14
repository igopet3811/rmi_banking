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

			if(operation.equals("login")){
				try{
					System.out.println("TRYING TO LOG IN.");
					bank.login((String)args[3], (String)args[4]);
				} 
				catch (InvalidLogin e){
					e.printStackTrace();
				}
			}

			if(operation.equals("deposit")){
				try{
					System.out.println("DEPOSIT.");
					long sessionId = bank.hasActiveSession(Integer.parseInt(args[3]));
					System.out.println("DEPOSIT MONEY - ACTIVE SESSION IS: " + sessionId);
					bank.deposit(Integer.parseInt(args[3]), Integer.parseInt(args[4]), sessionId);
				} 
				catch (InvalidSession e){
					e.printStackTrace();
				}
			}

			/*switch(operation) {
			case "login" :
				System.out.println("LOGGING IN ");
				sessionId = bank.login((String)args[3], (String)args[4]);
				break;
			case "inquiry":
				System.out.println("ENQUIRY ");
				break;
			case "deposit":
				System.out.println("DEPOSIT MONEY ");
				bank.deposit(Integer.parseInt(args[3]), Integer.parseInt(args[4]));
				break;
			case "withdraw":
				System.out.println("WITHDRAW MONEY ");
				break;
			case "statement":
				System.out.println("GET STATEMENT ");
				break;
			default :
				System.out.println("UNKNOWN COMMAND ");
			}*/

		} catch (Exception e) {
			System.err.println("Client ATM exception: ");
			e.printStackTrace();
		}

	}

}
