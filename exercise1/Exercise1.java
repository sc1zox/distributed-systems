package exercise1;

class Thread1 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Hello from Thread 1 " +i);
        }
    }
}

class Thread2 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Hello from Thread 2 " +i);
        }
    }
}

public class Exercise1 {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Thread1());
        Thread t2 = new Thread(new Thread2());
        t1.start();
        t2.start();
        System.out.println("Hello from Main");

        /*
        * Threadausführung ist nicht garantiert in der Reihenfolge. Die Threads werden vom Scheduler gesteuert und wechseln sich somit teilweise in der Ausgabe ab.
        * */
    }
}

