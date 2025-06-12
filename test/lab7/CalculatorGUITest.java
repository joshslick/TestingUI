package lab7;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.Robot;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.assertj.swing.fixture.JButtonFixture;

import javax.swing.*;
import javax.swing.border.Border;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.awt.*;

public class CalculatorGUITest {
    private FrameFixture window;
    private static final int SLOW_MOTION_DELAY_MS = 1; // Adjust this to control speed
    private final Color activeColor = new Color(173, 216, 230);
    private final Color defaultColor = UIManager.getColor("Button.background");

    @Before
    public void setUp() {
        // Ensure GUI mode is enabled
        System.setProperty("java.awt.headless", "false");
        
        // Create calculator with slower motion
        CalculatorView view = GuiActionRunner.execute(() -> {
            CalculatorModel model = new CalculatorModel();
            CalculatorView v = new CalculatorView();
            new CalculatorController(v, model);
            return v;
        });

        Robot robot = BasicRobot.robotWithCurrentAwtHierarchy();
        robot.settings().delayBetweenEvents(SLOW_MOTION_DELAY_MS); // Slow down interactions
        window = new FrameFixture(robot, view);
        window.show();
        window.moveTo(new Point(100, 100)); // Position window for better visibility
    }
    @Test
    public void testSubtraction() {
    	executeTest("9", "-", "4", "=", "5.0");    }
    @Test
    public void testClearMemory() {
        clickButton("5");
        clickButton("M+");
        clickButton("MC");
        clickButton("MR");
        assertDisplayEquals("0.0");
    }
    @Test
    public void testNegativeNumbers() {
        executeTest("5", "-", "7", "=", "-2.0");
    }
    @Test
    public void testMemorySubtraction() {
        clickButton("1");
        clickButton("+");
        clickButton("9");
        clickButton("=");
        clickButton("M+");
        clickButton("5");
        clickButton("+");
        clickButton("0");
        clickButton("=");
        clickButton("M-");
        clickButton("MR");
        assertDisplayEquals("5.0");
    }
    @Test
    public void testBasicAddition() {
        executeTest("1", "2", "+", "3", "4", "=", "46.0");
    }
    @Test
    public void testComplexOperation() {
        executeTest("5", "x²", "+", "3", "=", "28.0");
    }

    

    @Test
    public void testMemoryOperations() {
        // Test 1: Store value and recall
        clickButton("5");
        clickButton("+");
        clickButton("0");
        clickButton("=");
        clickButton("M+");
        clickButton("C");
        clickButton("MR");
        assertDisplayEquals("5.0");
        
        // Test 2: Verify memory persists after new calculation
        clickButton("C");  // Clear display
        clickButton("3");
        clickButton("+");
        clickButton("2");
        clickButton("=");  // New calculation (3+2=5)
        clickButton("MR"); // Should still recall original 5.0
        assertDisplayEquals("5.0");
        
        // Test 3: Verify memory addition
        clickButton("1");
        clickButton("+");
        clickButton("2");
        clickButton("=");
        clickButton("M+"); // Add 3 to memory (now 8.0)
        clickButton("C");
        clickButton("MR");
        assertDisplayEquals("8.0");
        
        // Test 4: Verify memory clear
        clickButton("MC"); // Clear memory
        clickButton("MR");
        assertDisplayEquals("0.0"); // Memory should be cleared
    }

    @Test
    public void testDivisionByZero() {
        executeTest("5", "/", "0", "=", "Error");
    }

 
    @Test
    public void testOperandDisplayOnly() {
        executeTest("5", "+", "5");
    }

    @Test
    public void testDeleteOperation() {
        executeTest("1", "2", "3", "Del", "12");
    }

    // Helper methods
    private void executeTest(String... inputsAndExpected) {
        String expected = inputsAndExpected[inputsAndExpected.length - 1];
        String[] inputs = new String[inputsAndExpected.length - 1];
        System.arraycopy(inputsAndExpected, 0, inputs, 0, inputs.length);

        // Visual feedback
        System.out.println("\nExecuting test: " + String.join(" ", inputs) + " → " + expected);
        
        // Perform button clicks
        for (String input : inputs) {
            System.out.println("Clicking: " + input);
            clickButton(input);
        }

        // Verify result
        System.out.println("Verifying display should show: " + expected);
        assertDisplayEquals(expected);
    }

    private void clickButton(String buttonText) {
        try {
            JButtonFixture button = getButton(buttonText);
            button.click();
            pause(SLOW_MOTION_DELAY_MS); // Visual delay
        } catch (Exception e) {
            throw new RuntimeException("Failed to click button: " + buttonText, e);
        }
    }

    private JButtonFixture getButton(String text) {
        return window.button(new GenericTypeMatcher<JButton>(JButton.class) {
            @Override
            protected boolean isMatching(JButton button) {
                return text.equals(button.getText());
            }
        });
    }

    private void assertDisplayEquals(String expected) {
        pause(SLOW_MOTION_DELAY_MS);
        JTextComponentFixture display = getDisplayTextField();
        String actual = display.text();
        System.out.println("Current display: " + actual);
        
        if (!expected.equals(actual)) {
            System.err.println("TEST FAILED! Expected: " + expected + " but got: " + actual);
            throw new AssertionError("Expected display to show '" + expected + "' but was '" + actual + "'");
        }
        System.out.println("✓ Test passed");
    }

    private JTextComponentFixture getDisplayTextField() {
      
        try {
            return window.textBox("calcDisplay");
        } catch (Exception e) {
            System.out.println("Couldn't find display by name, falling back to generic matcher");
            return window.textBox(new GenericTypeMatcher<JTextField>(JTextField.class) {
                @Override
                protected boolean isMatching(JTextField textField) {
                    return !textField.isEditable() && 
                           textField.getFont().getSize() >= 20;
                }
            });
        }
    }
    
    @Test
    public void testOperationButtonHighlights() {
        JButtonFixture plusButton = getButton("+");
        plusButton.click();

        JButton plusSwing = plusButton.target();
        Color actual = plusSwing.getBackground();
        assertEquals("Operation button should be highlighted", activeColor, actual);
    }
    
    @Test
    public void testOnlyOneOperationHighlighted() {
        JButtonFixture add = getButton("+");
        JButtonFixture subtract = getButton("-");

        add.click();      
        subtract.click(); 

        Color addColor = getButton("+").target().getBackground();
        Color subColor = getButton("-").target().getBackground();

        assertEquals("Only one button should remain highlighted", activeColor, subColor);
        assertNotEquals("Previous operation button should not stay highlighted", activeColor, addColor);
    }
    
    @Test
    public void testHighlightClearedAfterEquals() {
        clickButton("3");
        getButton("*").click();
        clickButton("4");
        getButton("=").click();

        Color multColor = getButton("*").target().getBackground();
        assertNotEquals("Operator highlight should be cleared after equals", activeColor, multColor);
    }
    
    private void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    @After
    public void tearDown() {
        if (window != null) {
            window.cleanUp();
        }
    }
}