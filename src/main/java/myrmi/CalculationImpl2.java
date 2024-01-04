package myrmi;

public class CalculationImpl2 implements Calculation {
    int a, b;

    @Override
    public int mul(int a, int b) {
        return a * b;
    }

    @Override
    public void set(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void printNum() {
        System.out.println("Number a = " + a + ", number b = " + b);
    }
}
