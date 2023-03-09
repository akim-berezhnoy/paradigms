package queue;

public class QueueTests {
    public static void main(String[] args) {
        System.out.println("RUNNING TESTS");
        System.out.print("      ");
        MyArrayQueueModuleTest.run();
        System.out.print("      ");
        MyArrayQueueADTTest.run(ArrayQueueADT.create());
        System.out.print("      ");
//        MyArrayQueueTest.run();
        System.out.println("ALL TESTS PASSED");
    }
}
