package example;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface BankInterface extends Remote {

public long login(String username, String password) throws RemoteException, InvalidLogin;

public void deposit(int accountnum, int amount, long sessionId) throws RemoteException;

public void withdraw(int accountnum, int amount, long sessionId) throws RemoteException;

public int inquiry(int accountnum, long sessionId) throws RemoteException;

public StatementInterface getStatement(int accountnum, Date from, Date to) throws RemoteException;

public long hasActiveSession(String username, String password) throws RemoteException, InvalidLogin;

public long hasActiveSession(int a) throws RemoteException, InvalidSession;
}