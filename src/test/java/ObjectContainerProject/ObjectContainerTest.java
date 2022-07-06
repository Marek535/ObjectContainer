package ObjectContainerProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class ObjectContainerTest {

    private static final String CONDITION_PREFIX = "add_it_";

    private ObjectContainer<String> objectContainer;

    @BeforeEach
    void setUp() {
        objectContainer = new ObjectContainer<>(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s != null && s.startsWith(CONDITION_PREFIX);
            }
        });
    }


    @Test
    void addOne() {
        assertEquals(0, objectContainer.size);
        String value = CONDITION_PREFIX + 0;
        objectContainer.add(value);
        assertEquals(1, objectContainer.size);
        assertEquals(value, objectContainer.getLastNode().getValue());
    }

    @Test
    void addInvalid() {
        assertEquals(0, objectContainer.size);
        String value = CONDITION_PREFIX.replace(CONDITION_PREFIX.charAt(0), (char) (CONDITION_PREFIX.charAt(0) + 1));
        objectContainer.add(value);
        assertEquals(0, objectContainer.size, "wrong size " + objectContainer.toString());
        assertEquals(0, objectContainer.toList().size() - 1, "wrong element count " + objectContainer.toString());
    }

    @Test
    void addTwo() {
        assertEquals(0, objectContainer.size);
        String value0 = CONDITION_PREFIX + 0;
        String value1 = CONDITION_PREFIX + 1;
        objectContainer.add(value0);
        objectContainer.add(value1);
        assertEquals(2, objectContainer.size);
        List<String> list = objectContainer.toList();
        assertTrue(list.contains(value0));
        assertTrue(list.contains(value1));
        assertEquals(2, list.size() - 1, "wrong element count " + objectContainer.toString());
        assertEquals(value1, objectContainer.getLastNode().getValue());
    }

    @Test
    void clear() {
        assertEquals(0, objectContainer.size);
        String value0 = CONDITION_PREFIX + 0;
        String value1 = CONDITION_PREFIX + 1;
        objectContainer.add(value0);
        objectContainer.add(value1);
        System.out.println(objectContainer);
        assertEquals(2, objectContainer.size);
        objectContainer.clear();
        assertEquals(0, objectContainer.size);
        assertNull(objectContainer.getLastNode().getValue());
    }

    @Test
    void delete() {
        assertEquals(0, objectContainer.size);
        String value0 = CONDITION_PREFIX + 0;
        String value1 = CONDITION_PREFIX + 1;
        objectContainer.add(value0);
        objectContainer.add(value1);
        assertEquals(2, objectContainer.size);
        objectContainer.delete(value1);
        assertEquals(1, objectContainer.size, "wrong size " + objectContainer.toString());
        List<String> list = objectContainer.toList();
        assertEquals(1, list.size() -1, "wrong element count " + objectContainer.toString());
        assertEquals(value0, objectContainer.getLastNode().getValue());
    }

    @Test
    void removeIf() {
        String suffix = "_remove_it";
        assertEquals(0, objectContainer.size);
        String value0 = CONDITION_PREFIX + 0;
        String value1 = CONDITION_PREFIX + 1 + suffix;
        String value2 = CONDITION_PREFIX + 2;
        objectContainer.add(value0);
        objectContainer.add(value1);
        objectContainer.add(value2);
        assertEquals(3, objectContainer.size);
        assertTrue(objectContainer.toList().contains(value1));
        objectContainer.removeIf(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s != null && s.endsWith(suffix);
            }
        });
        assertFalse(objectContainer.toList().contains(value1), "object " + value1 + " still there: " + objectContainer);
        assertEquals(2, objectContainer.size, "size not reduced");

    }

    @Test
    void getWithFilter() {
        String suffix = "_find_it";
        assertEquals(0, objectContainer.size);
        String value0 = CONDITION_PREFIX + 0;
        String value1 = CONDITION_PREFIX + 1 + suffix;
        String value2 = CONDITION_PREFIX + 2;
        objectContainer.add(value0);
        objectContainer.add(value1);
        objectContainer.add(value2);
        List<String> found = objectContainer.getWithFilter(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s != null && s.endsWith(suffix);
            }
        });
        assertEquals(1, found.size());
        assertTrue(found.contains(value1));
    }
}
