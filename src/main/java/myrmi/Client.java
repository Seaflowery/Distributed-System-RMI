package myrmi;

import myrmi.exception.AlreadyBoundException;
import myrmi.exception.NotBoundException;
import myrmi.registry.LocateRegistry;
import myrmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            // Connect to the remote registry
            String registryHost = "registry"; // Registry service name in Docker
            int registryPort = 1099; // Default RMI registry port
            Registry registry = LocateRegistry.getRegistry(registryHost, registryPort);

            // Lookup remote objects
            Calculation cal1 = (Calculation) registry.lookup("cal1");
            Calculation cal2 = (Calculation) registry.lookup("cal2");

            // Call remote methods
            System.out.println(cal1.mul(1, 2));
            System.out.println(cal2.mul(1, 2));
            cal1.printNum();
            cal2.printNum();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
