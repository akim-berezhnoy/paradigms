package queue;

import java.util.Iterator;
import java.util.Objects;

public abstract class AbstractQueue implements Queue {

    protected int size;

    @Override
    public void enqueue(Object element) {
        Objects.requireNonNull(element);
        enqueueImpl(element);
        size++;
    }

    abstract void enqueueImpl(Object element);

    @Override
    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    abstract Object elementImpl();

    @Override
    public Object dequeue() {
        assert size > 0;
        Object ret = dequeueImpl();
        size--;
        return ret;
    }

    abstract Object dequeueImpl();

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        clearImpl();
        size = 0;
    }

    abstract void clearImpl();

    public int count(Object element) {
        int cnt = 0;
        for (Object el : this) {
            cnt += element.equals(el) ? 1 : 0;
        }
        return cnt;
    }

    public Object get(int index) {
        assert 1 <= index && index <= size;
        for (Object el : this) {
            if (--index == 0) {
                return el;
            }
        }
        throw new AssertionError();
    }

    @Override
    public void set(int index, Object element) {
        assert 1 <= index && index <= size;
        setImpl(index, element);
        throw new AssertionError();
    }

    abstract void setImpl(int index, Object element);

    public Object[] toArray() {
        Object[] ret = new Object[size];
        int ind = 0;
        for (Object el : this) {
            ret[ind++] = el;
        }
        return ret;
    }

    public boolean contains(Object element) {
        for (Object el : this) {
            if (element.equals(el)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeFirstOccurrence(Object element) {
        Iterator<Object> iterator = iterator();
        while (iterator.hasNext()) {
            if (element.equals(iterator.next())) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public int lastIndexOf(Object element) {
        int index = 1, ans = -1;
        for (Object el : this) {
            if (element.equals(el)) {
                ans = index;
            }
        }
        return ans;
    }
}
