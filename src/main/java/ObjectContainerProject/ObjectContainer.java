package ObjectContainerProject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ObjectContainer<TYPE> {
    private Node<TYPE> head = new Node<>(null);
    private int size;
    private Predicate<TYPE> predicate;

    public ObjectContainer(Predicate<TYPE> predicate) {
        this.predicate = predicate;
    }

    @Override
    public String toString() {
        return "ObjectContainer{" +
                "head=" + head +
                ", size=" + size +
                ", predicate=" + predicate +
                '}';
    }

    public ObjectContainer() {
        clear();
    }

    public void clear() {
        head.setNext(null);
        size = 0;
    }

    public void add(TYPE value) {
        if (predicate.test(value)) {
            if (head.getNext() == null) head.setNext(new Node(value));
            Node last = head.getNext();
            while (last.getNext() != null)
                last = last.getNext();
            ++size;
            last.setNext(new Node(value));
        }
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

    public boolean removeIf(Predicate<TYPE> predicate) {
        Node<TYPE> delete = head.getNext();
        while (delete.getNext() != null) {
            delete = delete.getNext();
            if (predicate.test(delete.getValue())) {
                delete.setNext(delete.getNext());
                size--;
            }
        }
        return false;
    }

    public List<TYPE> getWithFilter(Predicate<TYPE> predicate) {
        List<TYPE> typeList = new ArrayList<>();
        Node<TYPE> last = head.getNext();
        while (last.getNext() != null) {
            last = last.getNext();
            if (predicate.test(last.getValue())) {
                typeList.add(last.getValue());
            }
        }
        return typeList;
    }


}