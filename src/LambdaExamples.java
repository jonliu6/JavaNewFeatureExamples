import java.util.*;

public class LambdaExamples {
    public static void main(String[] args) {
        LambdaExamples exam = new LambdaExamples();
        exam.example1();
    }

    public void example1() {
        List<Person> people = new ArrayList<>(PersonFactory.createPeople());
        Person noAge = new Person("Mystic", null, Person.Gender.FEMALE);
        people.add(2, noAge);
        noAge = new Person("Blahlah", null, Person.Gender.MALE);
        people.add(people.size()-1, noAge);
        System.out.println("Original List: " + people);
        // sort by name with tradition an anonymous class
        Collections.sort(people, new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                if (p1 == null) {
                    return 1;
                }
                else if (p2 == null) {
                    return -1;
                }
                else {
                    return p1.getName().compareTo(p2.getName());
                }
            }
        });

        System.out.println("Name List: " + people);

        // sort by age with Lambda Expression
        Collections.sort(people, (p1, p2) -> {
            // block of Lambda Expression code; however, if the block contains many lines, it is better to create a separate method.
            return p1 == null || p1.getAge() == null ? 1 : p2 == null || p2.getAge() == null ? -1 : p1.getAge().compareTo(p2.getAge()); // of like the if-elseif-else logic above
        } );
        System.out.println("Age List: " + people);
    }
}
