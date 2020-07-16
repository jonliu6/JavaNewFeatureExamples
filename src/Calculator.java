/**
 * class demonstrate Bounded Type Parameters
 */
public class Calculator {
    public static <T extends Number> double add(T... numbers) {
        double result = 0.0d;
        for (T num : numbers) {
            result += num.doubleValue();
        }
        return result;
    }

    public static <T extends Number> double multiply(T... numbers) {
        double result = 1.0d;
        for (T num : numbers) {
            result *= num.doubleValue();
        }
        return result;
    }
}