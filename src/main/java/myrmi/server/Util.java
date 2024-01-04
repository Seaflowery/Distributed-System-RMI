package myrmi.server;

import myrmi.Remote;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Util {
    public static Remote createStub(RemoteObjectRef ref) {
        //TODO: finish here, instantiate an StubInvocationHandler for ref and then return a stub
        StubInvocationHandler handler = new StubInvocationHandler(ref.getHost(), ref.getPort(), ref.getObjectKey());

        try {
            Class<?> remoteInterface = Class.forName(ref.getInterfaceName());
            return (Remote) Proxy.newProxyInstance(
                    remoteInterface.getClassLoader(),
                    new Class<?>[]{remoteInterface},
                    handler
            );
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Remote interface class not found: " + ref.getInterfaceName(), e);
        }
    }
}
