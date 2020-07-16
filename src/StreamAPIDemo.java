import javax.xml.stream.FactoryConfigurationError;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAPIDemo {
    /**
     * Lecture 5 Advanced Features in Java: Using the Java Stream API with Collections
     * from skillport.com
     * @param args
     * Since Java 8, seamless conversion between Collections and Stream is supported
     */
    public static void main(String[] args) {

        StreamAPIDemo demo = new StreamAPIDemo();

        demo.chapter1();
        demo.chapter2();
        demo.chapter3();
        demo.chapter4();
        demo.chapter5();
        demo.chapter6();
    }

    /**
     * test Stream methods: skip(), count(), distinct(), sorted(), limit(). toArray()
     */
    public void chapter1() {
        System.out.println("CHAPTER 1");
        String[] strArr = {"x", "u", "a", "v", "y", "b", "w", "z", "c", "a", "u", "c"};
        Collection<String> col = Arrays.asList(strArr);
        Stream<String> stm = col.stream();
        System.out.println("Stream count: " + stm.count()); // count() is a terminated method closed the stream
        Stream<String> skipStream = col.stream().skip(2);
        System.out.println("Stream count after skip 2: " + skipStream.count());
        Stream<String> distinctStream = col.stream().distinct().sorted();
        System.out.println("Stream after distincted and sorted: " + Arrays.toString(distinctStream.toArray()));
        Stream<String> limitedStream = col.stream().limit(6);
        System.out.println("Stream after limit(6): " + Arrays.toString(limitedStream.toArray()));
        System.out.println("--------------------------------------------------");
    }

    /**
     * Stream Predicates with Lambda expression instead of the traditional anonymous inner class
     */
    public void chapter2() {
        System.out.println("CHAPTER 2");
        Integer[] numbers = {22, 11, 23, 50, 35, 28, 99, 102, 8};
        Collection<Integer> intColl = Arrays.asList(numbers);
        Stream<Integer> intStrm = intColl.stream();
        // traditional way without using Lambda expression
        boolean areAllPositive = intStrm.allMatch(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer > 0;
            }
        });
        System.out.println("Original Integer Array: " + Arrays.toString(numbers));
        System.out.println("Are all positive numbers: " + areAllPositive);

        // Lambda expression way
        areAllPositive = intColl.stream().noneMatch(e -> e < 1);
        System.out.println("Are all positive numbers: " + areAllPositive);
        boolean isAnyGreaterThanHundred = intColl.stream().anyMatch(e -> e > 100);
        System.out.println("Is any number greater than 100: " + isAnyGreaterThanHundred);

        System.out.println("--------------------------------------------------");
    }

    /**
     * Predicates that return Streams using traditional anonymous inner class and Lambda expression
     */
    public void chapter3() {
        System.out.println("CHAPTER 3");
        Collection<Company> companies = CompanyFactory.createCompanies();
        // traditional way of forEach()
        System.out.println("All companies: " + Arrays.toString(companies.toArray()));
        System.out.println("Canadian Companies: " + companies.stream().filter(c -> "Canada".equals(c.getCountry())).count());
        companies.stream().filter(c -> "Canada".equals(c.getCountry())).forEach(new Consumer<Company>() {
            @Override
            public void accept(Company company) {
                System.out.println("\t" + company.toString());
            }
        });

        // Lambda expression of forEach()
        System.out.println("US Companies: " + companies.stream().filter(c -> "United States".equals(c.getCountry())).count());
        companies.stream().filter(company -> "United States".equals(company.getCountry())).forEach(c -> System.out.println(c.toString()));

        System.out.println("--------------------------------------------------");
    }

    /**
     * Mapping a predicate
     */
    public void chapter4() {
        System.out.println("CHAPTER 4 - Mapping a predicate");
        Collection<Company> companies = CompanyFactory.createCompanies();
        // tradition anonymous inner class for map()
        System.out.println("All companies: ");
        companies.stream().map(new Function<Company, String>() {
            @Override
            public String apply(Company company) {
                return company.getName() + " - " + company.getCountry();
            }
        }).forEach( c -> System.out.println(c.toString()));
        System.out.println("--------------------------------------------------");

        // Lambda expression
        System.out.println("US companies printed in uppercases: ");
        companies.stream().filter( usc -> "United States".equals(usc.getCountry())) // filter out Stream<Company>
                .map(mc -> mc.getName() + " - " + mc.getCountry()) // map filtered companies or a concatenated String of name and country
                // .map(m -> m.toUpperCase()) // map the concatenated String of name and country values to uppercases
                .map(String::toUpperCase)  // simpler version of above Lambda expression
                .forEach(e -> System.out.println(e));

        // mapToInt, mapToLong, mapToDouble can map the pipeline input to numeric types
    }

    /**
     * Terminal operations using predicates
     */
    public void chapter5() {
        System.out.println("CHAPTER 5 - Terminal operations using predicates");
        Collection<Person> people = PersonFactory.createPeople();
        System.out.println("Everyone: " + people);
        // average male age
        double averageAgeOfMales = people.stream().filter(m -> m.getGender() == Person.Gender.MALE)
                .mapToInt(a -> a.getAge())
                .average()
                .getAsDouble();
        System.out.println("Average age of males is: " + averageAgeOfMales);
        int oldestFemale = people.stream().filter(f -> f.getGender() == Person.Gender.FEMALE)
                // .mapToInt(a -> a.getAge())
                .mapToInt(Person::getAge) // shorter form to the above line
                .max()
                .getAsInt();
        // other aggregation methods: min(), sum()
        System.out.println("Oldest age of females is: " + oldestFemale);

        // another way to get oldest
        int oldestAge = people.stream().mapToInt(Person::getAge)
                .reduce(0, new IntBinaryOperator() {
                    @Override
                    public int applyAsInt(int left, int right) {
                        return left >= right ? left : right;
                    }
                });
        System.out.println("Oldest age is: " + oldestAge);

        // another way to get youngest age of women
        int younestAgeOfWomen = people.stream().filter(f -> f.getGender() == Person.Gender.FEMALE)
                .mapToInt(f -> f.getAge())
                .reduce(Integer.MAX_VALUE, (age1, age2) -> age1 <= age2 ? age1 : age2); // instead of anonymous inner class, use Lambda expression
        System.out.println("The age of youngest woman is: " + younestAgeOfWomen);

        // another way to get the summation of the ages of men
        int totalAgeOfMen = people.stream().filter(f -> f.getGender() == Person.Gender.MALE)
                .mapToInt(Person::getAge)
                .reduce(0, (age1, age2) -> age1 + age2);
        System.out.println("Total age of men is: " + totalAgeOfMen);

        System.out.println("--------------------------------------------------");
    }

    /**
     * Use of collectors and custom collectors
     */
    public void chapter6() {
        System.out.println("CHAPTER 6 - Collectors");
        Collection<Person> people = PersonFactory.createPeople();
        // get male names as a list
        List<String> maleNames = people.stream().filter(g -> g.getGender() == Person.Gender.MALE)
                .map(Person::getName)
                .collect(Collectors.toList());
        System.out.println("All males: " + maleNames);

        // get female names in a sorted tree set
        Set<String> sortedFemaleNames = people.stream().filter(g -> g.getGender() == Person.Gender.FEMALE)
                .map(Person::getName)
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println("All females: " + sortedFemaleNames);
        // get a concatenated string of all people's names
        String peopleNames = people.stream().map(Person::getName)
                .collect(Collectors.joining(", "));
        System.out.println("All names: " + peopleNames);

        // Summary Statistics
        IntSummaryStatistics statsOfWomen = people.stream().filter(g -> g.getGender() == Person.Gender.FEMALE)
                .collect(Collectors.summarizingInt(Person::getAge));
        System.out.println("Women statistics summary: " + statsOfWomen);

        // another way to list all names
        List<String> names = people.stream().map(Person::getName)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("All males: " + names);

        // use custom collector - Averager
        Averager avgCollectionOfWomen = people.stream().filter(g -> g.getGender() == Person.Gender.FEMALE)
                .map(Person::getAge)
                .collect(Averager::new, Averager::accumulate, Averager::combine);
        System.out.println("Average females: " + avgCollectionOfWomen.average());

        // male, female groups
        Map<Person.Gender, List<Person>> genderMap = people.stream().collect(Collectors.groupingBy(Person::getGender));
        System.out.println("Gender groups: ");
        for (Map.Entry<Person.Gender, List<Person>> entry : genderMap.entrySet()) {
            System.out.println(entry);
        }

        // Gender average age map
        Map<Person.Gender, Double> genderAverageAgeMap = people.stream().collect(Collectors.groupingBy(Person::getGender, Collectors.averagingInt(Person::getAge)));
        System.out.println("Average ages by gender groups: ");
        for (Map.Entry<Person.Gender, Double> entry : genderAverageAgeMap.entrySet()) {
            System.out.println(entry);
        }

        // use .parallelStream() for the operations performed in parallel
        genderAverageAgeMap = people.parallelStream().collect(Collectors.groupingBy(Person::getGender, Collectors.averagingInt(Person::getAge)));
        System.out.println("Average ages by gender groups in parallel: ");
        for (Map.Entry<Person.Gender, Double> entry : genderAverageAgeMap.entrySet()) {
            System.out.println(entry);
        }

        System.out.println("--------------------------------------------------");
    }

    /**
     * used for custom collectors
     */
    private static class Averager {
        private int total = 0;
        private int count = 0;

        public double average() {
            return count > 0 ? ((double)total)/count : 0;
        }

        public void accumulate(int i) {
            total +=i;
            count++;
        }

        public void combine(Averager other) {
            total += other.total;
            count += other.count;
        }
    }
}
