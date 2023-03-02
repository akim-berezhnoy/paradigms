package queue;

public class MyArrayQueueTest {
    public static void run() {
        ArrayQueue queue = new ArrayQueue();
        for (int j = 0; j < 10000; ++j) {
            assert queue.element() != null;
            queue.enqueue("g_" + j);
            queue.enqueue("g_" + j);
            queue.enqueue("g_" + j);
            if (queue.size() > 10) {
                for (int i = 0; i < 3; i++) {
                    queue.remove();
                    queue.remove();
                    queue.remove();
                }
            }
            for (int i = 0; i < 100; i++) {
                queue.push("e_" + i);
                queue.peek();
                if (i % 31 == 0) {
                    queue.remove();
                }
            }
            while (!queue.isEmpty()) {
                queue.remove();
            }
            for (int i = 0; i < 100; i++) {
                queue.enqueue("e_" + (99 + i));
                queue.push("e_" + (99 + i));
                if (i % 17 == 0) {
                    queue.remove();
                }
                if (i == 99) {
                    queue.clear();
                }
            }
        }
        System.out.println("ArrayQueue tests successfully passed.");
    }
}
