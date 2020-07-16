import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * annotation convention of generics:
 * E - element (in a collection)
 * K - key
 * N - number
 * T - type
 * V - value
 * S,U,M etc. other multiple types
 */
public class Lecture8Generics {
    public static void main(String[] args) {
        Lecture8Generics lct8 = new Lecture8Generics();
        lct8.chapter1();
        lct8.chapter2BoundedTypeParameters();
        lct8.chapter3Wildcard();
    }

    public void chapter1() {
        Triple<String, Integer, String> person1 = new Triple<>("John", 35, "Male");
        Triple<String, Integer, String> person2 = new Triple<>("Rosey", 29, "Female");
        Triple<Integer, String, String> employee = new Triple<>(1001, "Jane", "Office Administrator");

        System.out.println("Person: " + person1 + "\nPerson: " + person2 + "\nEmployee: " + employee);
    }

    public void chapter2BoundedTypeParameters() {
        System.out.println("1+2+3+4+5.5=" +  Calculator.add(1,2,3,4,5.5) );
        // Calculator.multiply("hello", 1, 2); // compiler error due to the Bounded Type is Number
        System.out.println("1.1 * 2.2 * 3.3=" +  Calculator.multiply(1.1, 2.2, 3.3) );
    }

    public void chapter3Wildcard() {
        System.out.println("=====================Chapter 3====================");
        System.out.println("String List: ");
        printOutAnyCollection(createAStringList());

        System.out.println("Numeric List: ");
        printOutAnyCollection(createANumricList());

        System.out.println("Numeric List: ");
        // printOutAnyNumberCollection(createAStringList()); // compiler error, String v.s. Number
        printOutAnyNumberCollection(createANumricList());

        System.out.println("Integer List: ");
        printOutAnyNumberCollection(createAnIntegerList());

        System.out.println("Double List: ");
        printOutAnyNumberCollection(createADoubleList());

        System.out.println("==================================================");
    }

    private List<String> createAStringList() {
        List<String> strColl = new ArrayList<>();
        strColl.add("John");
        strColl.add("Jonathan");
        strColl.add("Jay");
        strColl.add("Jane");

        return strColl;
    }

    private List<Number> createANumricList() {
        List<Number> numColl = new ArrayList<>();
        numColl.add(59);
        numColl.add(1.09f);
        numColl.add(0.987d);
        numColl.add(1024102410241024L);

        return numColl;
    }

    private List<Integer> createAnIntegerList() {
        List<Integer> intColl = new ArrayList<>();
        intColl.add(59);
        intColl.add(4096);
        intColl.add(8192);
        intColl.add(1024);

        return intColl;
    }

    private List<Double> createADoubleList() {
        List<Double> dblColl = new ArrayList<>();
        dblColl.add(5.99d);
        dblColl.add(0.229d);
        dblColl.add(22.78d);
        dblColl.add(09.567d);
        return dblColl;
    }

    /**
     * Upper Bounded Wildcard Type
     * @param coll
     */
    private void printOutAnyNumberCollection(Collection<? extends Number> coll) {
        for( Number n : coll) {
            System.out.println(n);
        }
    }

    /**
     * Lower Bounded Widlcard Type
     * @param coll
     */
    private void printOutAnyIntegerCollection(Collection<? super Number> coll) {
        System.out.println(coll);
    }

    private void printOutAnyCollection(Collection<?> coll) {
        for( Object o : coll) {
            System.out.println(o);
        }
    }

    /**
     * generics of a class hosting 3 generic attributes
     * @param <F> first attribute
     * @param <S> second attribute
     * @param <T> third attribute
     */
    public class Triple<F,S,T> {
        private F first;
        private S second;
        private T third;

        public Triple(F first, S second, T third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        public F getFirst() {
            return first;
        }

        public void setFirst(F first) {
            this.first = first;
        }

        public S getSecond() {
            return second;
        }

        public void setSecond(S second) {
            this.second = second;
        }

        public T getThird() {
            return third;
        }

        public void setThird(T third) {
            this.third = third;
        }

        @Override
        public String toString() {
            return "Triple{" +
                    "first=" + first +
                    ", second=" + second +
                    ", third=" + third +
                    '}';
        }
    }

}
