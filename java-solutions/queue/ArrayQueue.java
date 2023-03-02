package queue;
import java.util.Objects;

/*
Model: a[1]..a[n]
Invariant: n >= 0 && for i=1..n: a[i] != null

Let immutable(n): for i=1..n a'[i] = a[i]

Inv: queue.size >= 0 && forall i = 1..n: elements[i] != null
 */
public class ArrayQueue {
    private Object[] elements = new Object[10];
    private int head;
    private int size;

    //  Pred: element != null
    //  Post: n' = n + 1 && a[n'] = element && immutable(n)
    //  enqueue(element)
    public void enqueue(final Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(size + 1);
        elements[(head+size)%elements.length] = element;
        size++;
    }

    //  Pred: element != null
    //  Post: n' = n + 1 && a[1] = element && for i=1..n-1: a'[i+1] = a[i]
    //  push(element)
    public void push(final Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(size + 1);
        head = (elements.length + head - 1)%elements.length;
        elements[head] = element;
        size++;
    }

    //  Pred: n >= 1
    //  Post: R = a[1] && n' = n && immutable(n)
    //  element()
    public Object element() {
        assert size > 0;
        return elements[head];
    }

    //  Pred: n >= 1
    //  Post: R = a[n] && n' = n && immutable(n)
    //  peek()
    public Object peek() {
        assert size > 0;
        return elements[(head+size-1)%elements.length];
    }

    //  Pred: n >= 1
    //  Post: R = a[1] && n' = n - 1 && for i=1..n-1: a'[i] = a[i+1]
    //  dequeue()
    public Object dequeue() {
        assert size > 0;
        Object ret = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return ret;
    }

    //  Pred: n >= 1
    //  Post: R = a[n] && n' = n - 1 && immutable(n-1)
    //  remove()
    public Object remove() {
        assert size > 0;
        Object ret = elements[(head+size-1)%elements.length];
        elements[(head+size-1)%elements.length] = null;
        size--;
        return ret;
    }

    //  Pred: true
    //  Post: R = n && n' = n && immutable(n)
    //  size()
    public int size() {
        return size;
    }

    //  Pred: true
    //  Post: R = (n == 0) && n' = n && immutable(n)
    //  isEmpty()
    public boolean isEmpty() {
        return size == 0;
    }

    //  Pred: true
    //  Post: n' = 0
    //  clear()
    public void clear() {
        elements = new Object[10];
        head = 0;
        size = 0;
    }


    // Pred: true
    // Post: R = a[] &&  immutable(n)
    // toArray()
    public Object[] toArray() {
        Object[] res = new Object[size];
        for (int i = 0; i < size; i++) {
            res[i] = elements[(head+i) % elements.length];
        }
        return res;
    }

    private void ensureCapacity(int new_size) {
        if (new_size <= elements.length) {
            return;
        }
        final Object[] extended_elements = new Object[Math.max(new_size, 2*elements.length)];
        System.arraycopy(elements, 0, extended_elements, 0, head);
        System.arraycopy(elements, head, extended_elements,
                extended_elements.length - elements.length + head, elements.length - head);
        head += extended_elements.length - elements.length;
        elements = extended_elements;
    }
}
