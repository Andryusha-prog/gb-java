import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private final CountDownLatch countDownLatch;
    Lock lock = new ReentrantLock();
    private static int finishedCars = 0;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CountDownLatch countDownLatch) {
        this.race = race;
        this.speed = speed;
        this.countDownLatch = countDownLatch;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            countDownLatch.countDown();
            if(countDownLatch.getCount() == 0) System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            countDownLatch.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {

            try {
                lock.lock();
                race.getStages().get(i).go(this);
            } finally {
                lock.unlock();
                if(i == race.getStages().size() - 1) {
                    finishedCars++;
                    if(finishedCars == CARS_COUNT)
                        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
                }
            }
        }
    }
}
