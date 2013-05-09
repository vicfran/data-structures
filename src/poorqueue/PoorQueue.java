package poorqueue;

import java.util.Stack;

public class PoorQueue {

    private Stack stackA;
    private Stack stackB;

    public PoorQueue() {
        stackA = new Stack();
        stackB = new Stack();
    }

    public void enqueue(Object object) {
        stackA.push(object);
    }

    public Object dequeue() {
        if ((!stackB.isEmpty()) && (stackB.peek() != null)) return stackB.pop();
        while ((!stackA.isEmpty()) && (stackA.peek() != null)) stackB.push(stackA.pop());

        return (stackB.isEmpty()) ? null : stackB.pop();
    }

    public Object peek() {
        if (!stackB.isEmpty()) return stackB.peek();
        else if (!stackA.isEmpty()) return stackA.peek();

        return null;
    }

    public boolean isEmpty() {
        return ((stackA.isEmpty()) && (stackB.isEmpty()));
    }

    public int size() {
        return (stackA.size() + stackB.size());
    }
}