package Calculator;

public class Calculator {
    public int add(int a, int b) {
        long result = (long) a + b; // Use long to check for overflow
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            throw new ArithmeticException("Overflow occurred");
        }
        return (int) result;
    }

    public int subtract(int a, int b) {
        long result = (long) a - b; // Use long to check for underflow
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            throw new ArithmeticException("Underflow occurred");
        }
        return (int) result;
    }

    public int multiply(int a, int b) {
        long result = (long) a * b; // Use long to check for overflow
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            throw new ArithmeticException("Overflow occurred");
        }
        return (int) result;
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero.");
        }
        return a / b;
    }
}