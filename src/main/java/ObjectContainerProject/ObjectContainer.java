package ObjectContainerProject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ObjectContainer<TYPE> {
    private Node<TYPE> head = new Node<>(null);
    protected int size;
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
                ", values=" + toList() +
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
            if (head.getNext() == null) {
                head.setNext(new Node(value));
            }
            Node last = head.getNext();
            while (last.getNext() != null) {
                last = last.getNext();
            }
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
        if (predicate.test(head.getValue())) {
            head = head.getNext();
            size--;
        }

        Node<TYPE> delete = head;
        while (delete.getNext() != null) {
            delete = delete.getNext();
            if (predicate.test(delete.getValue())) {
                delete.setNext(delete.getNext().getNext());
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

    protected Node<TYPE> getLastNode() {
        Node last = head;
        while (last.getNext() != null) {
            last = last.getNext();
        }
        return last;
    }

    protected List<TYPE> toList() {
        List<TYPE> list = new ArrayList<>(size);
        Node<TYPE> last = head;
        do {
            list.add(last.getValue());
        } while ((last = last.getNext()) != null);
        return list;
    }

}

