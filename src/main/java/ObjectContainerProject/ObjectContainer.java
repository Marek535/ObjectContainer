package ObjectContainerProject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
            Node<TYPE> last = getLastNode();
            last.setNext(new Node(value));
            ++size;
        }
    }

    public boolean delete(TYPE o) {
        if (o == null) {
            //we use null value to mark head
            throw new IllegalArgumentException("Can't delete null value.");
        }
        int oldSize = size;
        Node<TYPE> previous = head;
        Node<TYPE> current;
        while ((current = previous.getNext()) != null) {
            if (o.equals(current.getValue())) {
                previous.setNext(current.getNext());
                size--;

            }
            previous = current;
        }
        return size != oldSize;
    }


    public boolean removeIf(Predicate<TYPE> predicate) throws NullPointerException {
        int oldSize = size;
        Node<TYPE> previous = head;
        Node<TYPE> current;
        while (previous.getNext() != null) {
            current = previous.getNext();
            if (predicate.test(current.getValue())) {
                previous.setNext(current.getNext());
                size--;
            }
            previous = current;
        }
        return size != oldSize;
    }


    public List<TYPE> getWithFilter(Predicate<TYPE> predicate) {
        List<TYPE> typeList = new ArrayList<>();
        Node<TYPE> last = head;
        while (last.getNext() != null) {
            last = last.getNext();
            if (predicate.test(last.getValue())) {
                typeList.add(last.getValue());
            }
        }
        return typeList;
    }

//    public void storeToFile(String path, Predicate<TYPE> predicate, Predicate<TYPE> secondPredicate) throws IOException {
//        Node<TYPE> previous = head;
//        Node<TYPE> current;
//        while ((current = previous.getNext()) != null) {
//            if (predicate.test(current.getValue())) {
//                try (BufferedWriter out = new BufferedWriter(new FileWriter(path))) {
//
//                } catch (Exception e) {
//                    throw new IllegalStateException(e);
//                }
//            }
//
//        }
//    }

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

