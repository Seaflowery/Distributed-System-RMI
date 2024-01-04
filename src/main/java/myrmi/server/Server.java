package myrmi.server;

import myrmi.CalculationImpl1;
import myrmi.CalculationImpl2;
import myrmi.registry.LocateRegistry;
import myrmi.registry.Registry;
import myrmi.registry.RegistryImpl;
import myrmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry();
            CalculationImpl1 cal1 = new CalculationImpl1();
            CalculationImpl2 cal2 = new CalculationImpl2();

            UnicastRemoteObject.exportObject(cal1, 5500);
            UnicastRemoteObject.exportObject(cal2, 5550);

            registry.bind("cal1", cal1);
            registry.bind("cal2", cal2);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
