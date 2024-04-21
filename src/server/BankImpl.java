package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class BankImpl extends UnicastRemoteObject implements Bank {
 private Map<String, Double> accounts;

 protected BankImpl() throws RemoteException {
     accounts = new HashMap<>();
 }

 @Override
 public void createAccount(String accountNumber) throws RemoteException {
     accounts.put(accountNumber, 0.0);
 }

 @Override
 public void credit(String accountNumber, double amount) throws RemoteException {
     double balance = accounts.getOrDefault(accountNumber, 0.0);
     accounts.put(accountNumber, balance + amount);
 }

 @Override
 public void debit(String accountNumber, double amount) throws RemoteException {
     double balance = accounts.getOrDefault(accountNumber, 0.0);
     if (balance >= amount) {
         accounts.put(accountNumber, balance - amount);
     } else {
         throw new RemoteException("Insufficient funds");
     }
 }

 @Override
 public void transfer(String fromAccount, String toAccount, double amount) throws RemoteException {
     debit(fromAccount, amount);
     credit(toAccount, amount);
 }

 @Override
 public double getBalance(String accountNumber) throws RemoteException {
     return accounts.getOrDefault(accountNumber, 0.0);
 }
}

