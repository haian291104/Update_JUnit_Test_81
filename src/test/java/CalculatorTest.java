import Calculator.Calculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @Test
    void testAddWithPositiveNumbers() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    void testAddWithNegativeNumbers() {
        assertEquals(-5, calculator.add(-2, -3));
    }

    @Test
    void testAddWithZero() {
        assertEquals(2, calculator.add(2, 0));
    }

    @Test
    void testAddWithOverflow() {
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            calculator.add(Integer.MAX_VALUE, 1);
        });
        assertEquals("Overflow occurred", exception.getMessage());
    }

    @Test
    void testSubtract() {
        assertEquals(1, calculator.subtract(3, 2));
    }

    @Test
    void testSubtractWithUnderflow() {
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            calculator.subtract(Integer.MIN_VALUE, 1);
        });
        assertEquals("Underflow occurred", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "2, 3, 6",
            "-2, 3, -6",
            "0, 5, 0"
    })
    void testMultiply(int a, int b, int expected) {
        assertEquals(expected, calculator.multiply(a, b));
    }

    @Test
    void testMultiplyWithIntegerOverflow() {
        Exception exception = assertThrows(ArithmeticException.class, () -> calculator.multiply(Integer.MAX_VALUE, 2));
        assertEquals("Overflow occurred", exception.getMessage());
    }

    @Test
    void testMultiplyWithIntegerMinValue() {
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            calculator.multiply(Integer.MIN_VALUE, -1);
        });
        assertEquals("Overflow occurred", exception.getMessage());
    }

    @Test
    void testDivideWithPositiveNumbers() {
        assertEquals(2, calculator.divide(6, 3));
    }

    @Test
    void testDivideWithNegativeNumbers() {
        assertEquals(-2, calculator.divide(6, -3));
    }

    @Test
    void testDivideByZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> calculator.divide(6, 0));
        assertEquals("Cannot divide by zero.", exception.getMessage());
    }

    @Test
    void testDivideZeroByNumber() {
        assertEquals(0, calculator.divide(0, 5));
    }

    @ParameterizedTest
    @CsvSource({
            "2, 2, 4",
            "0, 0, 0",
            "1000, 1000, 2000"
    })
    void testAddBoundaryValues(int a, int b, int expected) {
        assertEquals(expected, calculator.add(a, b));
    }
}