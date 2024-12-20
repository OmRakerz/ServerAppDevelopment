public class Lab3Sync {
    public static final int ITERATIONS = 100000;
    private static int counter = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Lab3Sync <n> <m>");
            return;
        }

        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);

        long startTime = System.nanoTime();

        Thread[] incrementThreads = new Thread[n];
        Thread[] decrementThreads = new Thread[m];

        // Создание и запуск потоков, которые инкрементируют счетчик
        for (int i = 0; i < n; i++) {
            incrementThreads[i] = new Thread(new IncrementTask());
            incrementThreads[i].start();
        }

        // Создание и запуск потоков, которые декрементируют счетчик
        for (int i = 0; i < m; i++) {
            decrementThreads[i] = new Thread(new DecrementTask());
            decrementThreads[i].start();
        }

        // Ожидание завершения всех потоков
        try {
            for (int i = 0; i < n; i++) {
                incrementThreads[i].join();
            }
            for (int i = 0; i < m; i++) {
                decrementThreads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Время в миллисекундах

        System.out.println("Final Counter Value: " + counter);
        System.out.println("Execution Time: " + duration + " ms");
    }

    static class IncrementTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < ITERATIONS; i++) {
                synchronized (lock) {
                    counter++;
                }
            }
        }
    }

    static class DecrementTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < ITERATIONS; i++) {
                synchronized (lock) {
                    counter--;
                }
            }
        }
    }
}
