package lab7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public class CalculatorView extends JFrame implements PropertyChangeListener {
    private JTextField displayField = new JTextField(20);
    private final Color defaultColor = UIManager.getColor("Button.background");
    private final Color activeColor = new Color(173, 216, 230); // Light blue
    private JButton lastPressedButton = null;
    private Map<String, JButton> buttonMap = new HashMap<>();

    public CalculatorView() {
        displayField.setName("calcDisplay");
        setTitle("Scientific Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        displayField.setEditable(false);
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setFont(new Font("Arial", Font.BOLD, 24));
        displayField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel(new GridLayout(6, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "√", "x²", "Del", "C",
            "M+", "M-", "MR", "MC"
        };

        for (String label : buttons) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setActionCommand(label);
            button.setBackground(defaultColor);

            // Visual feedback color
            button.addActionListener(e -> {
                resetLastButtonColor();
                lastPressedButton = button;
                lastPressedButton.setBackground(activeColor);
            });

            buttonPanel.add(button);
            buttonMap.put(label, button); // store for external access if needed
        }

        setLayout(new BorderLayout());
        add(displayField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setDisplay(String value) {
        displayField.setText(value);
    }

    public void addButtonListener(ActionListener listener) {
        for (JButton button : buttonMap.values()) {
            button.addActionListener(listener);
        }
    }

    public void resetLastButtonColor() {
        if (lastPressedButton != null) {
            lastPressedButton.setBackground(defaultColor);
            lastPressedButton = null;
        }
    }

    public JTextField getDisplayField() {
        return displayField;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("display".equals(evt.getPropertyName())) {
            setDisplay((String) evt.getNewValue());
        }
    }

    // Optional: get a specific button by label
    public JButton getButton(String label) {
        return buttonMap.get(label);
    }
}
