package myrmi.server;

import myrmi.exception.RemoteException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import sun.swing.AccumulativeRunnable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;

public class StubInvocationHandler implements InvocationHandler, Serializable {
    private String host;
    private int port;
    private int objectKey;

    public StubInvocationHandler(String host, int port, int objectKey) {
        this.host = host;
        this.port = port;
        this.objectKey = objectKey;
        System.out.printf("Stub created to %s:%d, object key = %d\n", host, port, objectKey);
    }

    public StubInvocationHandler(RemoteObjectRef ref) {
        this(ref.getHost(), ref.getPort(), ref.getObjectKey());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws RemoteException, IOException, ClassNotFoundException, Throwable {
        /*TODO: implement stub proxy invocation handler here
         *  You need to do:
         * 1. connect to remote skeleton, send method and arguments
         * 2. get result back and return to caller transparently
         * */
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            // write parameters to outputStream
            outputStream.writeObject(objectKey);
            outputStream.writeObject(method.getName());
            outputStream.writeObject(method.getParameterTypes());
            Arguments arguments = new Arguments(args);
            outputStream.writeObject(arguments);
            outputStream.flush();

            Integer objectKey = (Integer) inputStream.readObject();

            // get result from StubInvocationHandler
            if (objectKey == -1) {
                throw new RemoteException();
            } else if (objectKey == 0) {
                throw (Throwable) inputStream.readObject();
            }

            if (objectKey == 2) {
                return inputStream.readObject();
            }

            return null;
        }

    }

}
