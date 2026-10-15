package exercise1;

public class SynchronizedCounter {
    private int c = 0;
    public synchronized void increment () {
        int b = c ;
        b++;
        c = b ;
    }
    public synchronized void decrement () {
        int d = c ;
        d--;
        c = d ;
    }
    public synchronized int value () {
        return c ;
    }
}
