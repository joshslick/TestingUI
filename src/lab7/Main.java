package lab7;

//Main.java
public class Main {
 public static void main(String[] args) {
     CalculatorModel model = new CalculatorModel();
     CalculatorView view = new CalculatorView();
     CalculatorController controller = new CalculatorController(view, model);
     view.setVisible(true);
 }
}