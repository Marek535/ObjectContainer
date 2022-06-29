package ObjectContainerProject;

import java.util.Arrays;
import java.util.function.Predicate;

public class ObjectContainerProjectMain {
    public static void main(String[] args) {

        ObjectContainer<Person> peopleFromWarsaw = new ObjectContainer<Person>();
        peopleFromWarsaw.addFromCity(new Person("Jan", "Warsaw", 30));


    }
}
