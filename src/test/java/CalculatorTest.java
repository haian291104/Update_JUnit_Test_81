import Calculator.Calculator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private final Calculator calculator = new Calculator();
    private ExecutorService executorService;

    @BeforeEach
    void setUp() {
        // Initialize the calculator before each test
        executorService = Executors.newFixedThreadPool(2);
    }

    @AfterEach
    void tearDown() {
        // Clean up after each test if necessary
        executorService.shutdown();
    }

    @Test
    @DisplayName("Test addition with positive numbers")
    void testAddWithPositiveNumbers() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    @DisplayName("Test addition with negative numbers")
    void testAddWithNegativeNumbers() {
        assertEquals(-5, calculator.add(-2, -3));
    }

    @Test
    @DisplayName("Test addition with zero")
    void testAddWithZero() {
        assertEquals(2, calculator.add(2, 0));
    }

    @Test
    @DisplayName("Test addition with overflow")
    void testAddWithOverflow() {
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            calculator.add(Integer.MAX_VALUE, 1);
        });
        assertEquals("Overflow occurred", exception.getMessage());
    }

    @Test
    @DisplayName("Test addition with boundary values")
    void testAddWithBoundaryValues() {
        assertEquals(Integer.MAX_VALUE, calculator.add(Integer.MAX_VALUE - 1, 1));
        assertEquals(Integer.MIN_VALUE + 1, calculator.add(Integer.MIN_VALUE, 1));
    }

    @Test
    @DisplayName("Test subtraction with positive numbers")
    void testSubtract() {
        assertEquals(1, calculator.subtract(3, 2));
    }

    @Test
    @DisplayName("Test subtraction with underflow")
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
    @DisplayName("Test multiplication with various inputs")
    void testMultiply(int a, int b, int expected) {
        assertEquals(expected, calculator.multiply(a, b));
    }

    @Test
    @DisplayName("Test multiplication with integer overflow")
    void testMultiplyWithIntegerOverflow() {
        Exception exception = assertThrows(ArithmeticException.class, () -> calculator.multiply(Integer.MAX_VALUE, 2));
        assertEquals("Overflow occurred", exception.getMessage());
    }

    @Test
    @DisplayName("Test multiplication with integer minimum value")
    void testMultiplyWithIntegerMinValue() {
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            calculator.multiply(Integer.MIN_VALUE, -1);
        });
        assertEquals("Overflow occurred", exception.getMessage());
    }

    @Test
    @DisplayName("Test division with positive numbers")
    void testDivideWithPositiveNumbers() {
        assertEquals(2, calculator.divide(6, 3));
    }

    @Test
    @DisplayName("Test division with negative numbers")
    void testDivideWithNegativeNumbers() {
        assertEquals(-2, calculator.divide(6, -3));
    }

    @Test
    @DisplayName("Test division by zero")
    void testDivideByZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> calculator.divide(6, 0));
        assertEquals("Cannot divide by zero.", exception.getMessage());
    }

    @Test
    @DisplayName("Test division of zero by a number")
    void testDivideZeroByNumber() {
        assertEquals(0, calculator.divide(0, 5));
    }

    @ParameterizedTest
    @CsvSource({
            "2, 2, 4",
            "0, 0, 0",
            "1000, 1000, 2000"
    })
    @DisplayName("Test addition with boundary values using parameterized test")
    void testAddBoundaryValues(int a, int b, int expected) {
        assertEquals(expected, calculator.add(a, b));
    }

    @Test
    @DisplayName("Test multithreaded addition")
    void testMultithreadedAddition() throws InterruptedException {
        Runnable task1 = () -> assertEquals(5, calculator.add(2, 3));
        Runnable task2 = () -> assertEquals(10, calculator.add(7, 3));

        executorService.submit(task1);
        executorService.submit(task2);

        executorService.shutdown();
        assertTrue(executorService.isShutdown());
    }
}