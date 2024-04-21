package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
 public static void main(String[] args) {
     try {
         Registry registry = LocateRegistry.createRegistry(1009);
         BankImpl bank = new BankImpl();
         registry.rebind("Bank", bank);
         System.out.println("Bank server ready");
     } catch (Exception e) {
         System.err.println("Bank server exception: " + e.toString());
         e.printStackTrace();
     }
 }
}

