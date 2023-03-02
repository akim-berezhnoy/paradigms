package queue;

public class MyArrayQueueModuleTest {
    public static void run() {
        for (int j = 0; j < 10000; ++j) {
            assert ArrayQueueModule.element() != null;
            ArrayQueueModule.enqueue("g_" + j);
            ArrayQueueModule.enqueue("g_" + j);
            ArrayQueueModule.enqueue("g_" + j);
            if (ArrayQueueModule.size() > 10) {
                for (int i = 0; i < 3; i++) {
                    ArrayQueueModule.remove();
                    ArrayQueueModule.remove();
                    ArrayQueueModule.remove();
                }
            }
            for (int i = 0; i < 100; i++) {
                ArrayQueueModule.push("e_" + i);
                ArrayQueueModule.peek();
                if (i % 31 == 0) {
                    ArrayQueueModule.remove();
                }
            }
            while (!ArrayQueueModule.isEmpty()) {
                ArrayQueueModule.remove();
            }
            for (int i = 0; i < 100; i++) {
                ArrayQueueModule.enqueue("e_" + (99 + i));
                ArrayQueueModule.push("e_" + (99 + i));
                if (i % 17 == 0) {
                    ArrayQueueModule.remove();
                }
                if (i == 99) {
                    ArrayQueueModule.clear();
                }
            }
        }
        System.out.println("ArrayQueueModule tests successfully passed.");
    }
}
