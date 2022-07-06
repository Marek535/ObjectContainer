package ObjectContainerProject;

public class Node<TYPE> {
    private TYPE value;
    private Node<TYPE> next;

    public Node(TYPE val) {
        this(val, null);
    }

    public Node(TYPE val, Node<TYPE> n) {
        value = val;
        next = n;
    }

    public TYPE getValue() {
        return value;
    }

    public Node<TYPE> getNext() {
        return next;
    }

    public void setNext(Node<TYPE> n) {
        next = n;
    }

    public void setValue(TYPE o) {
        value = o;
    }
}
