package myrmi.server;

import myrmi.Remote;
import myrmi.exception.RemoteException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class SkeletonReqHandler extends Thread {
    private Socket socket;
    private Remote obj;
    private int objectKey;

    public SkeletonReqHandler(Socket socket, Remote remoteObj, int objectKey) {
        this.socket = socket;
        this.obj = remoteObj;
        this.objectKey = objectKey;
    }

    @Override
    public void run() {
        int objectKey;
        String methodName;
        Class<?>[] argTypes;
        Object[] args;
        Object result;

        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {

            objectKey = (int) objectInputStream.readObject();
            methodName = (String) objectInputStream.readObject();
            argTypes = (Class<?>[]) objectInputStream.readObject();

            args = ((Arguments) objectInputStream.readObject()).getArgs();
//                Object obj = (objectInputStream.readObject());


            int processState = -1;
            Method method = obj.getClass().getMethod(methodName, argTypes);
            try {
                result = method.invoke(obj, args);
                processState = method.getReturnType().equals(Void.TYPE) ? 1 : 2;
                if (processState == 2) {
                    objectOutputStream.writeObject(processState);
                    objectOutputStream.writeObject(result);
                    return;
                }
            } catch (Exception e) {
                processState = 0;
                objectOutputStream.writeObject(processState);
                objectOutputStream.writeObject(e);
                return;
            }
//            System.out.println("processState: " + processState);

            objectOutputStream.writeObject(processState);

        } catch (IOException | ClassNotFoundException | NoSuchMethodException e) {
//        } catch (IOException | ClassNotFoundException e) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
                objectOutputStream.writeObject(-1); // 表示调用过程中的错误
                objectOutputStream.writeObject(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        /*TODO: implement method here
         * You need to:
         * 1. handle requests from stub, receive invocation arguments, deserialization
         * 2. get result by calling the real object, and handle different cases (non-void method, void method, method throws exception, exception in invocation process)
         * Hint: you can use an int to represent the cases: -1 invocation error, 0 exception thrown, 1 void method, 2 non-void method
         *
         *  */


    }
}
