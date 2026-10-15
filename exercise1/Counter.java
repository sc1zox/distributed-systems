package exercise1;

public class Counter {
    private int c = 0;

    public void increment() {
        int b = c;
        b++;
        c = b;
    }

    public void decrement() {
        int d = c;
        d--;
        c = d;
    }

    public int value() {
        return c;
    }
}
