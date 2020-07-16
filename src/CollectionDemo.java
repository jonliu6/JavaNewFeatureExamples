import java.util.*;

/**
 * Examples for Java collections
 */
public class CollectionDemo {
    public static void main(String[] args) {
        CollectionDemo lct4 = new CollectionDemo();
        lct4.listDemo();
        lct4.question1();
        lct4.question2();
        lct4.example3();
        lct4.hashMapDemo();
    }

    public void listDemo() {
        List<String> languages = new ArrayList<String>();
        languages.add("Java");
        languages.add("Python");
        languages.add("C#");
        languages.add("Go");

        ListIterator<String> it = languages.listIterator(languages.size()); // initializing the starting of the iterator to the end of the list
        System.out.println("Language List: ");
        while (it.hasPrevious()) {
            String language = it.previous();
            System.out.println(language);
        }

        List<Company> compLst1 = new ArrayList<Company>();
        Company honda = new Company("Honda", "Japan");
        Company tata = new Company("Tata", "India");
        compLst1.add(honda);
        compLst1.add(tata);

        List<Company> compLst3 = new ArrayList<Company>();
        Company anotherHonda = new Company("Honda", "Japan");
        Company anotherTata = new Company("Tata", "India");
        compLst3.add(honda);
        compLst3.add(tata);

        System.out.println("Company List:\nIs compLst1 equal to compLst3? " + compLst1.equals(compLst3));

        List<Company> compLst2 = new ArrayList<Company>();
        compLst2.addAll(compLst1);
        // compLst2.addAll(compLst3);

        compLst2.remove(honda);
        compLst2.remove(anotherTata);

        System.out.println("Company List:\ncompLst2 has: " + compLst2.size());

        Vector<Company> vec = new Vector<>(3, 14);
        vec.add(anotherHonda);
        vec.add(honda);
        vec.add(anotherTata);
        vec.add(tata);
        System.out.println("The size of Vector is: " + vec.size() + ", and the capacity is: " + vec.capacity());
    }

    public void question1() {
        NavigableSet<Integer> aSet = new TreeSet<>();
        aSet.add(12);
        aSet.add(10);
        aSet.add(30);
        aSet.add(25);
        aSet.add(18);

        System.out.println("First element in the set is: " + aSet.pollFirst());
        System.out.println("Last element in the set is: " + aSet.pollLast());
        System.out.println("Set is: " + aSet.toString());
    }

    public void question2() {
        HashSet<Integer> s1 = new HashSet<>(Arrays.asList(10,20,30,40,50,60));
        HashSet<Integer> s2 = new HashSet<>(Arrays.asList(15,5,35,25,65,45,55));

        System.out.println("Set 1 added into Set 2: " + s2.addAll(s1));
        System.out.println("Set 1: " + s1.toString());
        System.out.println("Set 2: " + s2.toString());
    }
    public void example3() {
        String[] strArr = {"Python", "JavaScript", "Java", "C#", "Go"};
        HashSet<String> s1 = new HashSet<>(Arrays.asList(strArr));
        LinkedHashSet<String> s2 = new LinkedHashSet<>(Arrays.asList(strArr));
        TreeSet<String> s3 = new TreeSet<>(Arrays.asList(strArr));

        System.out.println("Array: " + Arrays.toString(strArr));
        System.out.println("HashSet: " + s1);
        System.out.println("LinkedHashSet: " + s2);
        System.out.println("TreeSet: " + s3);

    }

    public void hashMapDemo() {
        Map<Company, String> map1 = new HashMap<>();
        map1.put(new Company("Fujitsu","Japan"), "Consulting");
        map1.put(new Company("BC Hydro","Canada"), "Utility");
        map1.put(new Company("BC Hydro","Canada"), "Utility");

        System.out.println(map1);
    }
}
