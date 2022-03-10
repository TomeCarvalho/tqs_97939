import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> {
    private LinkedList<T> collection;
    private int limit;



    public TqsStack(int limit) {
        this.collection = new LinkedList<>();
        this.limit = limit;
    }

    public TqsStack() {
        this(-1);
    }

    public void push(T element) {
        if ((limit < 0) || (collection.size() < limit))
            collection.push(element);
        else
            throw new IllegalStateException();
    }

    public T pop() {
        return collection.pop();
    }

    public T peek() {
        T element = collection.peek();
        if (element == null)
            throw new NoSuchElementException();
        return element;
    }

    public int size() {
        return collection.size();
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }
}
