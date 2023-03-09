package queue;
import java.util.Arrays;
import java.util.Iterator;

public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[10];
    private int head;

    protected void enqueueImpl(final Object element) {
        ensureCapacity(size + 1);
        elements[(head+size)%elements.length] = element;
    }

    protected Object elementImpl() {
        return elements[head];
    }

    protected Object dequeueImpl() {
        Object ret = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        return ret;
    }

    public Object remove() {
        assert size > 0;
        Object ret = elements[(head+size-1)%elements.length];
        elements[(head+size-1)%elements.length] = null;
        size--;
        return ret;
    }

    protected void clearImpl() {
        elements = new Object[10];
        head = 0;
    }

    @Override
    public Object get(int index) {
        assert 1 <= index && index <= size;
        return elements[(head+index-1)%elements.length];
    }

    protected void setImpl(int index, Object element) {
        elements[(head+index-1)%elements.length] = element;
    }

    @Override
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
        elements = Arrays.copyOf(toArray(), 2*elements.length);
        head = 0;
    }

    @Override
    public Iterator<Object> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Object> {
        int iter = (elements.length+head-1)%elements.length;
        int remaining = size;

        private QueueIterator() {}

        @Override
        public boolean hasNext() {
            return remaining > 0;
        }

        @Override
        public Object next() {
            remaining--;
            iter = iter == elements.length-1 ? (++iter)%elements.length : ++iter;
            return elements[iter];
        }

        @Override
        public void remove() {
            int initial_iter = iter;
            int initial_remaining = remaining;

            int start = initial_iter;
            while(hasNext()) {
                elements[start] = next();
                start = (++start)%elements.length;
            }
            elements[start] = null;

            this.iter = initial_iter;
            this.remaining = initial_remaining;
            size--;
        }
    }
}
