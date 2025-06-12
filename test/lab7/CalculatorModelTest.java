package lab7;

import lab7.CalculatorModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CalculatorModelTest implements PropertyChangeListener {
    private String lastDisplayValue = "";
    private CalculatorModel model;
    
    public CalculatorModelTest() {
        model = new CalculatorModel();
        model.addPropertyChangeListener(this);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("display".equals(evt.getPropertyName())) {
            lastDisplayValue = (String) evt.getNewValue();
            System.out.println("Display updated: " + lastDisplayValue);
        }
    }
    
    public void runTests() {
        // Basic operations tests
        testAddition();
        testSubtraction();
        testMultiplication();
        testDivision();
        testDivisionByZero();
        
        // Scientific operations tests
        testSquare();
        testSquareRoot();
        testSquareRootOfNegative();
        testPercentage();
        
        // Memory operations tests
        testMemoryAdd();
        testMemorySubtract();
        testMemoryClear();
        
        // Input and editing tests
        testDecimalPoint();
        testDelete();
        testClear();
    }
    
    private void testAddition() {
        System.out.println("\n=== Testing Addition ===");
        model.clear();
        model.inputDigit("1");
        model.inputDigit("2");
        model.inputDecimal();
        model.inputDigit("5");
        System.out.println("Input: 12.5");
        model.setOperator("+");
        model.inputDigit("7");
        model.inputDecimal();
        model.inputDigit("5");
        System.out.println("Input: 7.5");
        model.calculate();
        System.out.println("Expected: 20.0, Actual: " + lastDisplayValue);
    }
    
    private void testSubtraction() {
        System.out.println("\n=== Testing Subtraction ===");
        model.clear();
        model.inputDigit("1");
        model.inputDigit("5");
        model.inputDecimal();
        model.inputDigit("5");
        System.out.println("Input: 15.5");
        model.setOperator("-");
        model.inputDigit("7");
        model.inputDecimal();
        model.inputDigit("5");
        System.out.println("Input: 7.5");
        model.calculate();
        System.out.println("Expected: 8.0, Actual: " + lastDisplayValue);
    }
    
    private void testMultiplication() {
        System.out.println("\n=== Testing Multiplication ===");
        model.clear();
        model.inputDigit("5");
        model.inputDecimal();
        model.inputDigit("0");
        System.out.println("Input: 5.0");
        model.setOperator("*");
        model.inputDigit("4");
        model.inputDecimal();
        model.inputDigit("0");
        System.out.println("Input: 4.0");
        model.calculate();
        System.out.println("Expected: 20.0, Actual: " + lastDisplayValue);
    }
    
    private void testDivision() {
        System.out.println("\n=== Testing Division ===");
        model.clear();
        model.inputDigit("1");
        model.inputDigit("5");
        model.inputDecimal();
        model.inputDigit("0");
        System.out.println("Input: 15.0");
        model.setOperator("/");
        model.inputDigit("3");
        model.inputDecimal();
        model.inputDigit("0");
        System.out.println("Input: 3.0");
        model.calculate();
        System.out.println("Expected: 5.0, Actual: " + lastDisplayValue);
    }
    
    private void testDivisionByZero() {
        System.out.println("\n=== Testing Division by Zero ===");
        model.clear();
        model.inputDigit("1");
        model.inputDigit("0");
        model.inputDecimal();
        model.inputDigit("0");
        System.out.println("Input: 10.0");
        model.setOperator("/");
        model.inputDigit("0");
        System.out.println("Input: 0");
        model.calculate();
        System.out.println("Expected: Error, Actual: " + lastDisplayValue);
    }
    
    private void testSquare() {
        System.out.println("\n=== Testing Square ===");
        model.clear();
        model.inputDigit("5");
        model.inputDecimal();
        model.inputDigit("0");
        System.out.println("Input: 5.0");
        model.square();
        System.out.println("Expected: 25.0, Actual: " + lastDisplayValue);
    }
    
    private void testSquareRoot() {
        System.out.println("\n=== Testing Square Root ===");
        model.clear();
        model.inputDigit("1");
        model.inputDigit("6");
        model.inputDecimal();
        model.inputDigit("0");
        System.out.println("Input: 16.0");
        model.squareRoot();
        System.out.println("Expected: 4.0, Actual: " + lastDisplayValue);
    }
    
    private void testSquareRootOfNegative() {
        System.out.println("\n=== Testing Square Root of Negative ===");
        model.clear();
        model.inputDigit("-");
        model.inputDigit("4");
        model.inputDecimal();
        model.inputDigit("0");
        System.out.println("Input: -4.0");
        model.squareRoot();
        System.out.println("Expected: Error, Actual: " + lastDisplayValue);
    }
    
    private void testPercentage() {
        System.out.println("\n=== Testing Percentage ===");
        model.clear();
        model.inputDigit("5");
        model.inputDigit("0");
        model.inputDecimal();
        model.inputDigit("0");
        System.out.println("Input: 50.0");
        model.percent();
        System.out.println("Expected: 0.5, Actual: " + lastDisplayValue);
    }
    
    private void testMemoryAdd() {
    	System.out.println("\n=== Testing Memory Add ===");
        model.clear();
        model.memoryClear();
        model.inputDigit("1");
        model.setOperator("+");
        model.inputDigit("9");
        model.calculate(); 
        model.memoryAdd(); 
        model.memoryRecall(); 
        System.out.println("Expected: 10.0, Actual: " + lastDisplayValue);
    }
    
    private void testMemorySubtract() {
    	System.out.println("\n=== Testing Memory Add ===");
        model.clear();
        model.memoryClear();
        model.inputDigit("1");
        model.setOperator("+");
        model.inputDigit("9");
        model.calculate(); 
        model.memorySubtract(); 
        model.memoryRecall(); 
        System.out.println("Expected: -10.0, Actual: " + lastDisplayValue);
    }
    
    private void testMemoryClear() {
        System.out.println("\n=== Testing Memory Clear ===");
        // Continuing from previous test
        model.memoryClear();
        model.memoryRecall();
        System.out.println("Expected: 0.0, Actual: " + lastDisplayValue);
    }
    
    private void testDecimalPoint() {
        System.out.println("\n=== Testing Decimal Point ===");
        model.clear();
        model.inputDigit("5");
        model.inputDecimal();
        model.inputDigit("2");
        model.inputDigit("5");
        System.out.println("Expected: 5.25, Actual: " + lastDisplayValue);
    }
    
    private void testDelete() {
        System.out.println("\n=== Testing Delete ===");
        model.clear();
        model.inputDigit("1");
        model.inputDigit("2");
        model.inputDigit("3");
        System.out.println("Input: 123");
        model.delete();
        System.out.println("Expected: 12, Actual: " + lastDisplayValue);
    }
    
    private void testClear() {
        System.out.println("\n=== Testing Clear ===");
        model.clear();
        model.inputDigit("1");
        model.inputDigit("2");
        model.inputDigit("3");
        System.out.println("Input: 123");
        model.clear();
        System.out.println("Expected: (empty), Actual: '" + lastDisplayValue + "'");
    }
    
    public static void main(String[] args) {
        CalculatorModelTest test = new CalculatorModelTest();
        test.runTests();
    }
}