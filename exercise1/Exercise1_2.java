package exercise1;

public class Exercise1_2 {
    static final int N = 1_000_000;

    public static void main(String[] args) throws InterruptedException {
        Counter c = new Counter();
        SynchronizedCounter sc = new SynchronizedCounter();
        Thread t1 = new Thread1_2(c);
        Thread t2 = new Thread2_2(c);
        Thread t3 = new Thread3_2(sc);
        Thread t4 = new Thread4_2(sc);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Ohne Synchronisierung: " + c.value() + " (erwartet: 0)");

        System.out.println("--------------------------------------------------------\n");
        t3.start();
        t4.start();
        t3.join();
        t4.join();
        System.out.println("Mit Synchronisierung: " + sc.value() + " (erwartet: 0)");
    }
}

class Thread1_2 extends Thread {
    Counter c;

    public Thread1_2(Counter c) {
        this.c = c;
    }

    @Override
    public void run() {
        for (int i = 0; i < Exercise1_2.N; i++) {
            c.increment();
        }
    }
}

class Thread2_2 extends Thread {

    Counter c;

    public Thread2_2(Counter c) {
        this.c = c;
    }

    @Override
    public void run() {
        for (int i = 0; i < Exercise1_2.N; i++) {
            c.decrement();
        }
    }
}

class Thread3_2 extends Thread {

    SynchronizedCounter c;

    public Thread3_2(SynchronizedCounter c) {
        this.c = c;
    }

    @Override
    public void run() {
        for (int i = 0; i < Exercise1_2.N; i++) {
            c.decrement();
        }
    }
}

class Thread4_2 extends Thread {

    SynchronizedCounter c;

    public Thread4_2(SynchronizedCounter c) {
        this.c = c;
    }

    @Override
    public void run() {
        for (int i = 0; i < Exercise1_2.N; i++) {
            c.increment();
        }
    }
}
