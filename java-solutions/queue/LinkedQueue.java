package queue;

import java.util.Iterator;

public class LinkedQueue extends AbstractQueue {
    private final Node head;

    public LinkedQueue() {
        head = new Node();
        head.ahead = head;
        head.behind = head;
    }

    protected void enqueueImpl(final Object element) {
        Node newNode = new Node(head.ahead, head, element);
        head.ahead.behind = newNode;
        head.ahead = newNode;
    }

    protected Object elementImpl() {
        return head.behind.value;
    }

    protected Object dequeueImpl() {
        Object ret = head.behind.value;
        head.behind = head.behind.behind;
        head.behind.ahead = head;
        return ret;
    }

    protected void clearImpl() {
        head.ahead = head.behind = head;
    }

    protected void setImpl(int index, Object element) {
        Node currentNode = head;
        while (index-- > 0) {
            currentNode = currentNode.behind;
        }
        currentNode.value = element;
    }

    private static class Node {
        public Node() {}

        public Node(Node ahead, Node behind, Object value) {
            this.ahead = ahead;
            this.behind = behind;
            this.value = value;
        }

        Node ahead;
        Node behind;
        Object value;
    }

    @Override
    public QueueIterator iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Object> {
        Node iter = head;

        private QueueIterator() {}

        @Override
        public boolean hasNext() {
            return iter.behind != head;
        }

        @Override
        public Object next() {
            iter = iter.behind;
            return iter.value;
        }

        @Override
        public void remove() {
            iter.ahead.behind = iter.behind;
            iter.behind.ahead = iter.ahead;
            size--;
        }
    }
}
