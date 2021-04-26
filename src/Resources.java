public class Resources {
    public static char charElem = 'A';

    public synchronized void printA() {
        for (int i = 0; i < 5; i++) {
            while (charElem != 'A') {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print('A');
            charElem = 'B';
            notifyAll();
        }
    }

    public synchronized void printB() {
        for (int i = 0; i < 5; i++) {
            while (charElem != 'B') {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print('B');
            charElem = 'C';
            notifyAll();
        }
    }

    public synchronized void printC() {
        for (int i = 0; i < 5; i++) {
            while (charElem != 'C') {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print('C');
            charElem = 'A';
            notifyAll();
        }
    }
}
