import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This includes the examples using functional interfaces introduced in Java 8
 * https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html
 * Characteristics: has only one abstract method declaration (unless overridden parents' ones) but multiple static or "default" methods
 * The common ones are:
 * Predicate - receives a value and return a boolean value
 *   test()
 * Consumer - takes an argument but return nothing
 *   access()
 * Supplier - takes no arguments but return a value, opposite to Consumer
 *   get()
 * Function - receives one value and returns another
 *   apply()
 * Operators: UnaryOperator, BinaryOperator
 */
public class FunctionalInterfaceDemo {
    public static void main(String[] args) {
        FunctionalInterfaceDemo demo = new FunctionalInterfaceDemo();
        demo.example1();
        demo.example2();
        demo.example3();
        demo.example4();
    }

    /**
     * use Predicate for checking even/odd numbers
     * use Consumer for printing
     */
    public void example1() {
        Predicate<Integer> isEvenNumber = new Predicate<Integer>() {
            @Override
            public boolean test(Integer intVal) {
                return intVal % 2 == 0;
            }
        };
        Consumer<Integer> printer = new Consumer<Integer>() {
            @Override
            public void accept(Integer intVal) {
                System.out.print(intVal + " ");
            }
        };
        Stream.of(12,23,33,42,54,57,59,66,87,88,99) // quick way to create a stream of numbers
                .filter(isEvenNumber).forEach(printer);

        // Lambda Express implementation with using functional interfaces
        Stream.of(12,23,33,42,54,57,59,66,87,88,99)
                .filter( odd -> odd % 2 > 0)
                .forEach( p -> System.out.println(p) );
    }

    /**
     * use Suppiler to create a new Person, use Function to return a String of person information
     */
    public void example2() {
        Collection<Person> people = PersonFactory.createPeople();
        Supplier<Person> personSupplier = new Supplier<Person>() {
            @Override
            public Person get() {
                return new Person();
            }
        };
        Function<Person, String> personInfo = new Function<Person, String>() {
            @Override
            public String apply(Person person) {
                return "Name: " + person.getName() + ", Age: " + person.getAge() + ", Gender: " + person.getGender();
            }
        };

        Person blankPerson = personSupplier.get();
        people.add(blankPerson);

        people.stream()
                .map(personInfo) // map the function to get personInfo
                .forEach(c -> System.out.println("Personnel: " + c)); // iterate each person to print out her/his information

        System.out.println("Male information: ");
        Supplier<Person> harrySupplier = () -> new Person("Harry", 16, Person.Gender.MALE);
        List<Person> malePeople = people.stream()
                .filter(m -> m.getGender() != null && m.getGender().equals(Person.Gender.MALE))
                .collect(Collectors.toList());
        malePeople.add(harrySupplier.get());

        malePeople.stream()
                .map(personInfo)
                .forEach(c -> System.out.println(c));
    }

    /**
     * use Supplier to get a person, use UnaryOperator to increase the person's age
     */
    public void example3() {
        System.out.println("\n=======================Example 3===========================");
        UnaryOperator<Person> decadeOlder = new UnaryOperator<Person>() {
            @Override
            public Person apply(Person person) {
                person.setAge(person.getAge() + 10);
                return person;
            }
        };

        Supplier<Person> personSupplier = () -> new Person("Samansa", 22, Person.Gender.FEMALE);
        Person aPerson = personSupplier.get();
        System.out.println("New person: " + aPerson);
        Person olderPerson = decadeOlder.apply(aPerson);
        System.out.println("Decade later person: " + olderPerson);
        System.out.println("===========================================================");
    }

    /**
     * use Supplier to generate random numbers (between 0 and 99), use BinaryOperator to add 2 numbers
     */
    public void example4 () {
        System.out.println("\n=======================Example 4===========================");
        Supplier<Long> rndGen = () -> Math.round(Math.random() * 99);
//        BinaryOperator<Long> biOpt = new BinaryOperator<Long>() {
//            @Override
//            public Long apply(Long aLong, Long aLong2) {
//                return aLong + aLong2;
//            }
//        };
        BinaryOperator<Long> biOpt = (l1, l2) -> l1 + l2;
        Long lng1 = rndGen.get();
        Long lng2 = rndGen.get();
        System.out.println(lng1 + "+" + lng2 + "=" + biOpt.apply(lng1, lng2));
        System.out.println("===========================================================");
    }
}
