import java.util.ArrayList;
import java.util.Collection;

public class PersonFactory {
    public static Collection<Person> createPeople() {
        Collection<Person> people = new ArrayList<>();
        Person person = new Person("John", 35, Person.Gender.MALE);
        people.add(person);
        person = new Person("Mary", 29, Person.Gender.FEMALE);
        people.add(person);
        person = new Person("Jason", 28, Person.Gender.MALE);
        people.add(person);
        person = new Person("Jane", 42, Person.Gender.FEMALE);
        people.add(person);
        person = new Person("Wendy", 31, Person.Gender.FEMALE);
        people.add(person);
        person = new Person("Karen", 27, Person.Gender.FEMALE);
        people.add(person);
        person = new Person("Dave", 55, Person.Gender.MALE);
        people.add(person);

        return people;
    }
}
