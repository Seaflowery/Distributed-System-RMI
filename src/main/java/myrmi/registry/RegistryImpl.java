package myrmi.registry;

import myrmi.Remote;
import myrmi.exception.AlreadyBoundException;
import myrmi.exception.NotBoundException;
import myrmi.exception.RemoteException;
import myrmi.server.Skeleton;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;

public class RegistryImpl implements Registry {
    private final HashMap<String, Remote> bindings = new HashMap<>();

    /**
     * Construct a new RegistryImpl
     * and create a skeleton on given port
     **/
    public RegistryImpl(int port) throws RemoteException {
        try {
            Skeleton skeleton = new Skeleton(this, "127.0.0.1", port, 0);
            skeleton.start();
        } catch (Exception e) {
            throw new RemoteException();
        }
    }


    public Remote lookup(String name) throws RemoteException, NotBoundException {
        System.out.printf("RegistryImpl: lookup(%s)\n", name);
        //TODO: implement method here
        Remote result;
        try {
            result = bindings.get(name);
        } catch (Exception e) {
            throw new RemoteException();
        }
        if (result == null)
            throw new NotBoundException(name);
        return result;
    }

    public void bind(String name, Remote obj) throws RemoteException, AlreadyBoundException {
        System.out.printf("RegistryImpl: bind(%s)\n", name);

        //TODO: implement method here
        if (bindings.containsKey(name)) {
            throw new AlreadyBoundException(name);
        }
        try {
            bindings.put(name, obj);
        } catch (Exception e) {
            throw new RemoteException();
        }
    }

    public void unbind(String name) throws RemoteException, NotBoundException {
        System.out.printf("RegistryImpl: unbind(%s)\n", name);

        //TODO: implement method here
            if (!bindings.containsKey(name))
                throw new NotBoundException(name);
            try {
                bindings.remove(name);
            } catch (Exception e) {
                throw new RemoteException();
            }
    }

    public void rebind(String name, Remote obj) throws RemoteException {
        System.out.printf("RegistryImpl: rebind(%s)\n", name);

        //TODO: implement method here
        try {
            bindings.put(name, obj);
        } catch (Exception e) {
            throw new RemoteException();
        }
    }

    public String[] list() throws RemoteException {
        //TODO: implement method here
        try {
            return bindings.keySet().toArray(new String[0]);
        } catch (Exception e) {
            throw new RemoteException();
        }
    }

    public static void main(String args[]) {
        final int regPort = (args.length >= 1) ? Integer.parseInt(args[0])
                : Registry.REGISTRY_PORT;
        RegistryImpl registry;
        try {
            registry = new RegistryImpl(regPort);
            System.out.println(registry.getClass().getName());
        } catch (RemoteException e) {
            System.exit(1);
        }

        System.out.printf("RMI Registry is listening on port %d\n", regPort);

    }
}
