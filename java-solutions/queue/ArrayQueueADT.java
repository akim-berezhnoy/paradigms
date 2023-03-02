package queue;
import java.util.Objects;

/*
Model: a[1]..a[n]
Invariant: n >= 0 && for i=1..n: a[i] != null

Let immutable(n): for i=1..n a'[i] = a[i]

Inv: queue.size >= 0 && forall i = 1..n: elements[i] != null
 */
public class ArrayQueueADT {
    private Object[] elements = new Object[10];
    private int head;
    private int size;

    //  Pred: true
    //  Post: R = new ArrayQueueADT()
    //  create()
    public static ArrayQueueADT create() {
        return new ArrayQueueADT();
    }

    //  Pred: queue != null && element != null
    //  Post: n' = n + 1 && a[n'] = element && immutable(n)
    //  enqueue(queue, element)
    public static void enqueue(final ArrayQueueADT queue, final Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(queue, queue.size + 1);
        queue.elements[(queue.head+queue.size)%queue.elements.length] = element;
        queue.size++;
    }

    //  Pred: queue != null && element != null
    //  Post: n' = n + 1 && a[1] = element && for i=1..n-1: a'[i+1] = a[i]
    //  push(queue, element)
    public static void push(final ArrayQueueADT queue, final Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(queue, queue.size + 1);
        queue.head = (queue.elements.length + queue.head - 1)%queue.elements.length;
        queue.elements[queue.head] = element;
        queue.size++;
    }

    //  Pred: n >= 1
    //  Post: R = a[1] && n' = n && immutable(n)
    //  element(queue)
    public static Object element(final ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[queue.head];
    }

    //  Pred: n >= 1
    //  Post: R = a[n] && n' = n && immutable(n)
    //  peek(queue)
    public static Object peek(final ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[(queue.head+queue.size-1)%queue.elements.length];
    }

    //  Pred: n >= 1
    //  Post: R = a[1] && n' = n - 1 && for i=1..n-1: a'[i] = a[i+1]
    //  dequeue(queue)
    public static Object dequeue(final ArrayQueueADT queue) {
        assert queue.size > 0;
        Object ret = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        queue.size--;
        return ret;
    }

    //  Pred: n >= 1
    //  Post: R = a[n] && n' = n - 1 && immutable(n-1)
    //  remove(queue)
    public static Object remove(final ArrayQueueADT queue) {
        assert queue.size > 0;
        Object ret = queue.elements[(queue.head+queue.size-1)%queue.elements.length];
        queue.elements[(queue.head+queue.size-1)%queue.elements.length] = null;
        queue.size--;
        return ret;
    }

    //  Pred: true
    //  Post: R = n && n' = n && immutable(n)
    //  size(queue)
    public static int size(final ArrayQueueADT queue) {
        return queue.size;
    }

    //  Pred: true
    //  Post: R = (n == 0) && n' = n && immutable(n)
    //  isEmpty(queue)
    public static boolean isEmpty(final ArrayQueueADT queue) {
        return queue.size == 0;
    }

    //  Pred: true
    //  Post: n' = 0
    //  clear(queue)
    public static void clear(final ArrayQueueADT queue) {
        queue.elements = new Object[10];
        queue.head = 0;
        queue.size = 0;
    }

    // Pred: true
    // Post: R = a[] &&  immutable(n)
    // toArray(queue)
    public static Object[] toArray(final ArrayQueueADT queue) {
        Object[] res = new Object[queue.size];
        for (int i = 0; i < queue.size; i++) {
            res[i] = queue.elements[(queue.head+i) % queue.elements.length];
        }
        return res;
    }

    private static void ensureCapacity(final ArrayQueueADT queue, int new_size) {
        if (new_size <= queue.elements.length) {
            return;
        }
        Object[] extended_elements = new Object[Math.max(new_size, 2*queue.elements.length)];
        System.arraycopy(queue.elements, 0, extended_elements, 0, queue.head);
        System.arraycopy(queue.elements, queue.head, extended_elements,
                extended_elements.length - queue.elements.length + queue.head, queue.elements.length - queue.head);
        queue.head += extended_elements.length - queue.elements.length;
        queue.elements = extended_elements;
    }
}
