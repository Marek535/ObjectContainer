package ObjectContainerProject;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        ObjectContainer<Person> peopleFromWarsaw = new ObjectContainer<>(p -> p.getCity().equals("Warsaw"));//tu w konstruktorze podajemy warunek do dodawania osoby do kontenera.
        peopleFromWarsaw.add(new Person("Jan", "Warsaw", 60)); // powinno byc ok, Jan jest z warszawy
        peopleFromWarsaw.add(new Person("Weronika","Warsaw", 51)); // powinno byc tez okej, Weronika jest z warszawy
        peopleFromWarsaw.add(new Person("Waldek", "Monaco", 34));// no waldka niedoda, jest z Monaco, wiec tu powinno albo rzucic wyjatkiem, albo metoda add powinna zwrocic po prostu false - co wybierzesz to twoja decyzja.
        peopleFromWarsaw.add(new Person("Waldeka", "Warsaw", 51));
        peopleFromWarsaw.add(new Person("Waldeka", "Warsaw", 34));

        System.out.println(peopleFromWarsaw);
//do tego ponizej mozesz juz stosowac liste, no bo masz zwrocic liste:)
        List<Person> females = peopleFromWarsaw.getWithFilter(p -> p.getName().endsWith("a")); // zwraca nam wszystkie osoby spelniajace dany warunek.

        System.out.println(females.size());
        peopleFromWarsaw.removeIf(p -> p.getAge() > 50); // powinno nam usuwac ludzi spelniajacy dany warunek w nawiasie.

        System.out.println(peopleFromWarsaw);
////tu naturalnie mozesz stosowac zapis do pliku bo wymaga tego polecenie
//        peopleFromWarsaw.storeToFile("youngPeopleFromWarsaw.txt", p -> p.getAge() < 30, p -> p.getName()+";"+p.getAge()+";"+p.getCity());

    }

}
