package ObjectContainerProject;

import java.util.Objects;
import java.util.function.Predicate;

public class ObjectContainer<TYPE> {
    private Node<TYPE> head = new Node<>(null);
    private int size;
    private Predicate predicate;

    public ObjectContainer() {
        clear();
    }

    public void clear() {
        head.setNext(null);
        size = 0;
    }

    public void add(TYPE value) {
        if (head.getNext() == null) head.setNext(new Node(value));
        Node last = head.getNext();
        while (last.getNext() != null)
            last = last.getNext();
        ++size;
        last.setNext(new Node(value));
    }

    public boolean delete(TYPE o) {
        if (head.getNext() == null) return false;
        if (head.getNext().getValue().equals(o)) {
            head.setNext(head.getNext().getNext());
            size--;
            return true;
        }
        Node delete = head.getNext();
        while (delete != null && delete.getNext() != null) {
            if (delete.getNext().getValue().equals(o)) {
                delete.setNext(delete.getNext().getNext());
                size--;
                return true;
            }
            delete = delete.getNext();
        }
        return false;
    }

    public void addFromCity(TYPE value) {
        if (head.getNext() == null) head.setNext(new Node(value));
        Node last = head.getNext();
        while (last.getNext() != null)
            last = last.getNext();
        ++size;
        last.setNext(new Node(value));
    }


}
