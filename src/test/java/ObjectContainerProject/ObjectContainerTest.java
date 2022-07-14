package ObjectContainerProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class ObjectContainerTest {

    /**
     * String with this prefix will be added to the container.
     * It's used in the predicate created in #setUp method.
     */
    private static final String CONDITION_PREFIX = "add_it_";
    /**
     * String with this suffix will be removed by removeIf method.
     * It's used in the predicate created in #removeIf test method.
     */
    static final String REMOVE_IT_SUFFIX = "_remove_it";

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
        assertEquals(0, objectContainer.size, "wrong size " + objectContainer);
        assertEquals(0, objectContainer.toList().size(), "wrong element count " + objectContainer);
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
        assertEquals(2, list.size(), "wrong element count " + objectContainer);
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

    @ParameterizedTest
    @CsvSource({
            "3, 0",
            "3, 1",
            "3, 2",
            "1, 0",
            "10, 5"
    })
    void delete(int valueCount, int removeValueIdx) {
        assertEquals(0, objectContainer.size);
        List<String> values = new ArrayList<>();
        for (int i = 0; i < valueCount ; i++) {
            String v = CONDITION_PREFIX + i;
            values.add(v);
            objectContainer.add(v);
        }
        assertEquals(valueCount, objectContainer.size);
        String toRemove = values.get(removeValueIdx);
        assertTrue(objectContainer.delete(toRemove));
        assertEquals(valueCount - 1, objectContainer.size, "wrong size " + objectContainer);
        List<String> list = objectContainer.toList();
        assertEquals(valueCount - 1, list.size(), "wrong element count " + objectContainer);
        if (removeValueIdx != valueCount - 1) {
            // if we didn't remove the last value
            assertEquals(values.get(valueCount - 1), objectContainer.getLastNode().getValue());
        }
    }

    /**
     * Test if delete returns false if object wasn't deleted.
     */
    @Test
    void deleteReturnValObjectNotPresent () {
        assertEquals(0, objectContainer.size);
        objectContainer.add("val1");
        assertFalse(objectContainer.delete("val2"));
    }

    @ParameterizedTest
    @CsvSource({
            CONDITION_PREFIX + 0 + REMOVE_IT_SUFFIX + ", " + CONDITION_PREFIX + 1 + ", " + CONDITION_PREFIX + 2,
            CONDITION_PREFIX + 0 + ", " + CONDITION_PREFIX + 1 + REMOVE_IT_SUFFIX + ", " + CONDITION_PREFIX + 2,
            CONDITION_PREFIX + 0 + ", " + CONDITION_PREFIX + 1 + ", " + CONDITION_PREFIX + 2 + REMOVE_IT_SUFFIX
    })
    void removeIf(String value0, String value1, String value2) {
        assertEquals(0, objectContainer.size);
        objectContainer.add(value0);
        objectContainer.add(value1);
        objectContainer.add(value2);
        assertEquals(3, objectContainer.size);
        assertTrue(objectContainer.toList().contains(value1));
        objectContainer.removeIf(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s != null && s.endsWith(REMOVE_IT_SUFFIX);
            }
        });
        List<String> values = objectContainer.toList();
        for (int i = 1; i < values.size(); i++) {
            assertFalse(values.get(i).endsWith(REMOVE_IT_SUFFIX));
        }
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