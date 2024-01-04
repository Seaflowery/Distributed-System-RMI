package myrmi.server;

import java.io.Serializable;

public class Arguments implements Serializable {
    private Object[] args;
    Arguments(Object[] args) {
        this.args = args;
        for (Object arg : args) {
            if (!(arg instanceof Serializable)) {
                System.out.println(arg);
                throw new IllegalArgumentException("Non-serializable object found");
            }
        }
    }

    public Object[] getArgs() {
        return args;
    }
}
