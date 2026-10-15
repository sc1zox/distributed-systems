package exercise1;

public class Exercise1 extends Thread {
    @Override
    public void run(){
        System.out.println("Hello from Thread");
    }

    public static void main(String[] args) {
        Thread t1 = new Exercise1();
        Thread t2 = new Exercise1();
        t1.start();
        t2.start();
    }
}

