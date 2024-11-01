import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener {
    private JTextField display;
    private String operator;
    private double firstOperand;

    public Main() {
        setTitle("계산기");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createDisplay();
        createButton();

        setVisible(true);
    }

    private void createDisplay() {
        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setPreferredSize(new Dimension(300, 100));
        add(display, BorderLayout.NORTH);
    }

    private void createButton() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4));

        String[] buttonLabels = {
                "C", "÷", "x", "Del",
                "7", "8", "9", "%",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "+/-", "0", ".", "="
        };

        for (String buttonLabel : buttonLabels) {
            JButton button = createButton(buttonLabel);

            if (buttonLabel.equals("C") || buttonLabel.equals("÷") ||
                    buttonLabel.equals("x") || buttonLabel.equals("Del") ||
                    buttonLabel.equals("%") || buttonLabel.equals("-") ||
                    buttonLabel.equals("+") || buttonLabel.equals("=")) {
                button.setBackground(Color.DARK_GRAY);
                button.setForeground(Color.white);
            } else {
                button.setBackground(Color.white);
                button.setForeground(Color.BLACK);
            }
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    public JButton createButton(String label) {
        JButton button = new JButton(label);
        button.addActionListener(this);
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch(command) {
            case "C":
                display.setText("");
                break;
            case "Del":
                String text = display.getText(); // chat gpt 이용
                if(!text.isEmpty()) {
                    display.setText(text.substring(0, text.length() - 1));
                }
                break;
            case "=":
                calculateResult();
                break;
            case "+":
            case "-":
            case "x":
            case "÷":
                prepareForOperation(command);
                break;
            case "+/-":
                text = display.getText();
                if(!text.isEmpty()) {
                    double value = Double.parseDouble(text);
                    value = -value;
                    display.setText(formatResult(value));
                }
                break;
            default:
                display.setText(display.getText() + command);
                break;
        }
    }

    private void calculateResult() { //chat gpt 이용
        if(!display.getText().isEmpty() && operator != null) {
            double secondOperand = Double.parseDouble(display.getText());
            double result = performOperation(firstOperand, secondOperand, operator);
            display.setText(formatResult(result));
            operator = null;
        }
    }

    private String formatResult(double result) { // chat gpt 이용
        return result % 1 == 0 ? String.valueOf((int) result) : String.valueOf(result);
    }

    private void prepareForOperation(String command) {
        firstOperand = Double.parseDouble(display.getText());
        operator = command.equals("x") ? "*" : command; //chat gpt 이용
        display.setText("");
    }

    private double performOperation(double first, double second, String operator) {
        return switch (operator) {
            case "+" -> first + second;
            case "-" -> first - second;
            case "*" -> first * second;
            case "÷" -> first / second;
            default -> 0;
        };
    }

    public static void main(String[] args) {
        new Main();
    }
}