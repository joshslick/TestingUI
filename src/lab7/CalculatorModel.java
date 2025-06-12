package lab7;

//CalculatorModel.java
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CalculatorModel {
 private double result = 0;
 private double memory = 0;
 private String display = "";
 private String operator = "";
 private boolean startNewInput = true;
 private PropertyChangeSupport support = new PropertyChangeSupport(this);
 private boolean lastWasResult = false;
 public String getDisplay() {
     return display;
 }

 public void addPropertyChangeListener(PropertyChangeListener pcl) {
     support.addPropertyChangeListener(pcl);
 }

 public void inputDigit(String digit) {
	 if (startNewInput) {
	        display = digit;
	        startNewInput = false;
	    } else {
	        display += digit;
	    }
	    lastWasResult = false; // ✅ Reset flag when typing
	    update();
 }
 public void percent() {
	    try {
	        double val = Double.parseDouble(display);
	        result = val / 100;
	        display = String.valueOf(result);
	        update();
	    } catch (Exception e) {
	        display = "Error";
	        update();
	    }
	}


 public void inputDecimal() {
	 if (startNewInput) {
	        display = "0.";
	        startNewInput = false;
	    } else if (!display.contains(".")) {
	        display += ".";
	    }
	    lastWasResult = false; 
	    update();
 }

 public void setOperator(String op) {
     if (!display.isEmpty()) {
         result = Double.parseDouble(display);
         operator = op;
         startNewInput = true;
     }
 }

 public void calculate() {
	 try {
	        double operand = Double.parseDouble(display);
	        switch (operator) {
	            case "+": result += operand; break;
	            case "-": result -= operand; break;
	            case "*": result *= operand; break;
	            case "/":
	                if (operand == 0) throw new ArithmeticException("Division by zero");
	                result /= operand;
	                break;
	        }
	        display = String.valueOf(result);
	        startNewInput = true;
	        lastWasResult = true; 
	        update();
	    } catch (Exception e) {
	        display = "Error";
	        lastWasResult = false; 
	        update();
	    }
 }

 public void square() {
     try {
         double val = Double.parseDouble(display);
         result = val * val;
         display = String.valueOf(result);
         lastWasResult = true;
         update();
     } catch (Exception e) {
         display = "Error";
         lastWasResult = false;
         update();
     }
 }

 public void squareRoot() {
     try {
         double val = Double.parseDouble(display);
         if (val < 0) throw new ArithmeticException();
         result = Math.sqrt(val);
         display = String.valueOf(result);
         lastWasResult = true;
         update();
     } catch (Exception e) {
         display = "Error";
         lastWasResult = false;
         update();
     }
 }

 public void delete() {
	 if (!display.isEmpty()) {
	        display = display.substring(0, display.length() - 1);
	    }
	    lastWasResult = false; 
	    update();
 }

 public void clear() {
     display = "";
     result = 0;
     operator = "";
     startNewInput = true;
     lastWasResult = false;
     update();
 }


 public void memoryAdd() {
	 if (!lastWasResult) {
	        display = "Error";
	        update();
	        return;
	    }
	    try {
	        double currentValue = display.isEmpty() ? 0 : Double.parseDouble(display);
	        memory += currentValue;
	        System.out.println("Memory after M+: " + memory);
	    } catch (NumberFormatException e) {
	        display = "Error";
	        update();
	    }
	}

	public void memorySubtract() {
		if (!lastWasResult) {
	        display = "Error";
	        update();
	        return;
	    }
	    try {
	        double currentValue = display.isEmpty() ? 0 : Double.parseDouble(display);
	        memory -= currentValue;
	        System.out.println("Memory after M-: " + memory);
	    } catch (NumberFormatException e) {
	        display = "Error";
	        update();
	    }
	}

	public void memoryRecall() {
	    // Always update display with memory value
	    display = String.valueOf(memory);
	    startNewInput = true; // Important for proper display handling
	    update();
	    System.out.println("Memory recalled: " + memory); // Debug
	}

	public void memoryClear() {
	    memory = 0;
	    System.out.println("Memory cleared"); // Debug
	}

 private void update() {
     support.firePropertyChange("display", null, display);
 }
 }