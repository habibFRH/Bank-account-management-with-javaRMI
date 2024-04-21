package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Bank extends Remote {
 void createAccount(String accountNumber) throws RemoteException;
 void credit(String accountNumber, double amount) throws RemoteException;
 void debit(String accountNumber, double amount) throws RemoteException;
 void transfer(String fromAccount, String toAccount, double amount) throws RemoteException;
 double getBalance(String accountNumber) throws RemoteException;
}

