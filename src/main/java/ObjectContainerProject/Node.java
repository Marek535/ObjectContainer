package ObjectContainerProject;

public class Node<TYPE> {
    private TYPE value;
    private Node next;

    public Node(TYPE val) {
        this(val, null);
    }

    public Node(TYPE val, Node n) {
        value = val;
        next = n;
    }

    public TYPE getValue() {
        return value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node n) {
        next = n;
    }

    public void setValue(TYPE o) {
        value = o;
    }
}
