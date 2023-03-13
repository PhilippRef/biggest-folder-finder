package org.example;

public class MyThread extends Thread { //Thread реализует выполнение потоков

    private int threadNumber;

    public MyThread(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() { //код, который будет работать в отдельном потоке
        for (; ; ) {
            System.out.println(threadNumber);
        }
    }
}
