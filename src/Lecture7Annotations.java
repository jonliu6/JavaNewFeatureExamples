import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Advanced Features in Java: Using Custom Annotations
 */
public class Lecture7Annotations {

    public static void main(String[] args) {
        Lecture7Annotations lct7 = new Lecture7Annotations();
        lct7.chapter1VarArgs();
        lct7.chapter2SafeVarArgs();
        lct7.chapter3FunctionalInterface();

        lct7.chapter4Annotation();
    }

    public void chapter1VarArgs() {
        Adder sum = new Adder();
        System.out.println("11+22+33=" + sum.add(11,22,33));
    }

    public void chapter2SafeVarArgs() {
        ListConverter<String> lc = new ListConverter<>();
        List<String> lst = lc.collect(new String("A"), new String("Tout"), new String("Le"), new String("Monde"));
        System.out.println("List: " + lst);
    }

    class Adder {
        public int add(int...args) {
            int sum = 0;
            for (int num : args) {
                sum += num;
            }
            return sum;
        }
    }

    class ListConverter<T> {
        @SafeVarargs // only can be used for final VarArgs
        public final List<T> collect(T... items) {
            List<T> list = new ArrayList<T>();

            for (T itm : items) {
                list.add(itm);
            }
            return list;
        }
    }

    @FunctionalInterface
    public interface Action<T> { // use generic name since the actual action will be defined in the caller
        void performAction(T input);
    }

    @FunctionalInterface
    /**
     * FunctionalInterface can only have one abstract method
     */
    public interface Transformation<T, V> {
        V performTransformation(T input);
        default V performNoTransformation(T input) {
            return (V) input;
        };
    }

    public void chapter3FunctionalInterface() {
        System.out.println("Chapter 3-----------------------------------------");

        MyList<String> strLst = new MyList<>();
        strLst.add("the");
        strLst.add("fun");
        strLst.add("palace");

        strLst.performActionOnEach(new Action<String>() {
            @Override
            public void performAction(String str) {
                System.out.println(str);
            }
        });

        MyList<String> upperStrLst = strLst.transformOnEach(new Transformation<String, String>() {
            @Override
            public String performTransformation(String input) {
                return input == null ? "" : input.toUpperCase();
            }
        });
        System.out.println("upperStrLst: ");
        upperStrLst.performActionOnEach(p -> System.out.println(p)); // Lambda expression shorten the anonymous inner class

        System.out.println("strLst without transformation: ");
        strLst.noTransformOnEach(t -> t == null ? "" : t.toUpperCase()); // Lambda expression has no affect since performNoTransformation is called.
        strLst.performActionOnEach(p -> System.out.println(p));

        System.out.println("--------------------------------------------------");
    }

    public class MyList<T> extends ArrayList<T>{
        public void performActionOnEach(Action<T> action) {
            for (T elem : this) {
                action.performAction(elem);
            }
        }

        public <V> MyList<V> transformOnEach(Transformation<T, V> transformer) {
            MyList<V> myList = new MyList<>();
            for (T elem : this) {
                myList.add(transformer.performTransformation(elem));
            }

            return myList;
        }

        public <V> MyList<V> noTransformOnEach(Transformation<T, V> transformer) {
            MyList<V> myList = new MyList<>();
            for (T elem: this) {
                myList.add(transformer.performNoTransformation(elem));
            }
            return myList;
        }
    }

    public void chapter4Annotation() {
        System.out.println("Chapter 4-----------------------------------------");
        Collection<Person> people = new ArrayList<>();
        Person p1 = new Person("Harry", null, Person.Gender.MALE);
        Person p2 = new Person(null, 35, Person.Gender.FEMALE);
        Person p3 = new Person("Rosey", 55, null);
        people.add(p1);
        people.add(p2);
        people.add(p3);
        try {
            checkNotNullFields(people);
        }
        catch (IllegalAccessException iae) {
            iae.printStackTrace();
        }
        catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        }
        System.out.println("--------------------------------------------------");
    }

    public void checkNotNullFields(Collection<Person> collection) throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = Person.class.getDeclaredFields();
        for (Person p : collection) {
            System.out.println("Checking Person: " + p);
            for (Field f : fields) {
                if (Modifier.isPrivate(f.getModifiers())) {
                    f.setAccessible(true);

                    Annotation[] annotations = f.getDeclaredAnnotations();
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof NotNull) {
                            System.out.println("Checking field: " + f.getName());
                            if (f.get(p) == null) {
                                System.out.println("NotNull field: " + f.getName() + " cannot be null.");
                            }
                        }
                    }
                }
            }
        }
    }
}
