public class Main {


    public static void main(String[] args) {

        Resources res = new Resources();

        Thread t1 = new Thread(() -> {
            res.printA();
        });

        Thread t2 = new Thread(() -> {
            res.printB();
        });

        Thread t3 = new Thread(() -> {
            res.printC();
        });

        t1.start();
        t2.start();
        t3.start();
    }


}
