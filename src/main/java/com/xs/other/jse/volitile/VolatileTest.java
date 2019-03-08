package com.xs.other.jse.volitile;

/**
 * Created by xs on 2019/3/8
 */
public class VolatileTest {
    private Bean bean = new Bean(true);

    public Thread test() {

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                int counter = 0;
                while (bean.isRunning()) {
                    counter++;
                }
                System.out.println("Thread 1 finished. Counted up to " + counter);
            }
        });
        t1.start();
        new Thread(new Runnable() {
            public void run() {
                // Sleep for a bit so that thread 1 has a chance to start
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                    // catch block
                }
                bean.setRunning(false);
                System.out.println("Thread 2 finishing:runnint=" + bean.isRunning());
            }
        }).start();
        return t1;
    }

    public static void main(String[] args) throws InterruptedException {
        new VolatileTest().test().join();
        System.out.println("主线程结束");
    }
}

class Bean {
    private boolean running ;

    Bean(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}