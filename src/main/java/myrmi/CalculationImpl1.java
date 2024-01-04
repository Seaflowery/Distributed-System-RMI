package myrmi;

public class CalculationImpl1 implements Calculation {
    int a, b;

    @Override
    public int mul(int a, int b) {
        this.a = a;
        this.b = b;
        return 0;
    }

    @Override
    public void set(int a, int b) {
        this.a = b;
        this.b = a;
    }

    @Override
    public void printNum() {
        System.out.println("Number a * b = " + a * b);
    }
}
