package lab7;

//CalculatorController.java
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorController implements ActionListener {
 private CalculatorModel model;

 public CalculatorController(CalculatorView view, CalculatorModel model) {
     this.model = model;
     view.addButtonListener(this);
     model.addPropertyChangeListener(view);
 }

 @Override
 public void actionPerformed(ActionEvent e) {
     String command = e.getActionCommand();
     System.out.println("Button pressed: " + command); // Debug

     switch (command) {
         case "0": case "1": case "2": case "3": case "4":
         case "5": case "6": case "7": case "8": case "9":
             model.inputDigit(command);
             break;
         case ".": model.inputDecimal(); break;
         case "+": case "-": case "*": case "/":
             model.setOperator(command); break;
         case "=": model.calculate(); break;
         case "√": model.squareRoot(); break;
         case "x²": model.square(); break;
         case "%": model.percent(); break;
         case "Del": model.delete(); break;
         case "C": model.clear(); break;
         case "M+": model.memoryAdd(); break;
         case "M-": model.memorySubtract(); break;
         case "MR": model.memoryRecall(); break;
         case "MC": model.memoryClear(); break;
     }
     model.addPropertyChangeListener(evt -> {
    	    System.out.println("Display changed: " + evt.getNewValue());
    	});
 }
}