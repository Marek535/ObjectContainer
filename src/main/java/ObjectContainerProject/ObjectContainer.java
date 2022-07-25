package ObjectContainerProject;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ObjectContainer<TYPE> implements Iterable<TYPE> {
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
        return removeIf(new Predicate<TYPE>() {
            @Override
            public boolean test(TYPE v) {
                return o.equals(v);
            }
        });
    }


    public boolean removeIf(Predicate<TYPE> predicate) {
        Objects.requireNonNull(predicate);
        boolean removed = false;
        final Iterator<TYPE> each = iterator();
        while (each.hasNext()) {
            if (predicate.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }


    public List<TYPE> getWithFilter(Predicate<TYPE> predicate) {
        List<TYPE> list = StreamSupport.stream(this.spliterator(), false)
                .filter(predicate).collect(Collectors.toList());
        return list;
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
        for (TYPE v : this) {
            list.add(v);
        }
        return list;
    }

    @NotNull
    @Override
    public Iterator<TYPE> iterator() {
        return new Iterator<TYPE>() {
            Node<TYPE> next = head;
            Node<TYPE> previous = null;

            @Override
            public boolean hasNext() {
                return next.getNext() != null;
            }

            @Override
            public TYPE next() {
                previous = next;
                next = next.getNext();
                return next.getValue();
            }

            @Override
            public void remove() {
                if (previous == null || previous.getNext() != next) {
                    throw new IllegalStateException();
                }
                previous.setNext(next.getNext());
                size--;
            }
        };
    }

    @Override
    public void forEach(Consumer<? super TYPE> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<TYPE> spliterator() {
        return Iterable.super.spliterator();
    }
}

