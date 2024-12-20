import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class Lab3ReentrantLock {
    public static final int ITERATIONS = 100000;
    private static int counter = 0;
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        int[] threadCounts = {1, 2, 4, 8}; // Набор потоков для тестирования

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Lab3.txt"))) {
            writer.write("Streams (n, m) | Counter | Time (мс)\n");

            for (int n : threadCounts) {
                for (int m : threadCounts) {
                    counter = 0; // Сброс счетчика перед каждым запуском
                    long startTime = System.nanoTime(); // Замер времени с использованием System.nanoTime()

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
                    for (int i = 0; i < n; i++) {
                        incrementThreads[i].join();
                    }
                    for (int i = 0; i < m; i++) {
                        decrementThreads[i].join();
                    }

                    long endTime = System.nanoTime();
                    long executionTime = (endTime - startTime) / 1000000;

                    writer.write(String.format("(%d, %d) | %d | %d ms\n", n, m, counter, executionTime));
                }
            }

            // Добавление информации о системе
            writer.write("\nCPU: " + System.getProperty("os.arch") + "\n");
            writer.write("RAM: " + Runtime.getRuntime().totalMemory() / (1024 * 1024) + " MB\n");
            writer.write("OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version") + "\n");

            System.out.println("Results saved to Lab3.txt");
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    static class IncrementTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < ITERATIONS; i++) {
                lock.lock();
                try {
                    counter++;
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class DecrementTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < ITERATIONS; i++) {
                lock.lock();
                try {
                    counter--;
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}