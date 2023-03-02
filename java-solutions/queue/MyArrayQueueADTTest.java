package queue;

public class MyArrayQueueADTTest {
    public static void run(ArrayQueueADT queue) {
        for (int j = 0; j < 10000; ++j) {
            assert ArrayQueueADT.element(queue) != null;
            ArrayQueueADT.enqueue(queue,"g_" + j);
            ArrayQueueADT.enqueue(queue,"g_" + j);
            ArrayQueueADT.enqueue(queue,"g_" + j);
            if (ArrayQueueADT.size(queue) > 10) {
                for (int i = 0; i < 3; i++) {
                    ArrayQueueADT.remove(queue);
                    ArrayQueueADT.remove(queue);
                    ArrayQueueADT.remove(queue);
                }
            }
            for (int i = 0; i < 100; i++) {
                ArrayQueueADT.push(queue,"e_" + i);
                ArrayQueueADT.peek(queue);
                if (i % 31 == 0) {
                    ArrayQueueADT.remove(queue);
                }
            }
            while (!ArrayQueueADT.isEmpty(queue)) {
                ArrayQueueADT.remove(queue);
            }
            for (int i = 0; i < 100; i++) {
                ArrayQueueADT.enqueue(queue,"e_" + (99 + i));
                ArrayQueueADT.push(queue,"e_" + (99 + i));
                if (i % 17 == 0) {
                    ArrayQueueADT.remove(queue);
                }
                if (i == 99) {
                    ArrayQueueADT.clear(queue);
                }
            }
        }
        System.out.println("ArrayQueueADT tests successfully passed.");
    }
}
