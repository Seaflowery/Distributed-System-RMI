package myrmi;

import myrmi.exception.AlreadyBoundException;
import myrmi.exception.NotBoundException;
import myrmi.registry.LocateRegistry;
import myrmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry();

            CalculationImpl1 cal1 = (CalculationImpl1) registry.lookup("cal1");
            CalculationImpl2 cal2 = (CalculationImpl2) registry.lookup("cal2");
            System.out.println(cal1.mul(1, 2));
            System.out.println(cal2.mul(1, 2));
            cal1.printNum();
            cal2.printNum();
//            registry.lookup("cal1");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
