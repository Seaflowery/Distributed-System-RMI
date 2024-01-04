package myrmi;

import java.io.Serializable;

public interface Calculation extends Remote, Serializable {
    int mul(int a, int b);
    void set(int a, int b);
    void printNum();
}
