// Importing necessary libraries for GUI development
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
     // Window dimensions
    int boardWidth = 360;
    int boardHeight = 540;

     // Custom colors used in calculator UI
    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    // Button labels
    String[] buttonValues = {
        "AC", "+/-", "%", "÷",
        "7", "8", "9", "×",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };

    // Operator buttons (right column)
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    // Top function buttons
    String[] topSymbols = {"AC", "+/-", "%"};

    // Main UI components
    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    //A+B, A-B, A*B, A/B
    // Variables for storing calculation values
    String A = "0";
    String operator = null;
    String B = null;

    Calculator() {
    // Configure calculator window
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

    // Configure display screen
        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

    // Add display panel
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel,BorderLayout.NORTH);

    // Configure button panel
       buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

    // Create calculator buttons dynamically
        for (int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));
    // Set button colors based on type
          if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground (customLightGray);
                button.setForeground (customBlack);
            } 
    // Arithmetic operator buttons
            else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground (customOrange);
                button.setForeground(Color.white);
            }
    // Number buttons
            else{
                button.setBackground (customDarkGray);
                button.setForeground(Color.white);
            }    
            buttonsPanel.add(button);

    // Execute action when button is clicked           
                button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  
                    
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();
                    // Handle operator buttons
                    if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                        // Calculate result when "=" is pressed
                        if (buttonValue == "=") {
                            if (A != null) {
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                if (operator == "+") {
                                    displayLabel.setText(removeZeroDecimal(numA+numB));
                                }
                                else if (operator == "-") {
                                    displayLabel.setText(removeZeroDecimal(numA-numB));
                                }
                                else if (operator == "×") {
                                    displayLabel.setText(removeZeroDecimal(numA*numB));
                                }
                                else if (operator == "÷") {
                                    displayLabel.setText(removeZeroDecimal(numA/numB));
                                }
                                // Reset values after calculation
                                clearAll();
                            }
                        }
                        // Store operator and first number
                        else if ("+-×÷".contains(buttonValue)) {
                            if (operator == null) {
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = buttonValue;
                        }
                    }
                    // Handle top function buttons
                    else if (Arrays.asList(topSymbols).contains (buttonValue)) {
                        // Clear calculator
                        if (buttonValue == "AC") {
                            clearAll();
                            displayLabel.setText("0");
                        }
                        // Change sign (+/-)
                        else if (buttonValue == "+/-") {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay *= -1;
                            displayLabel.setText(removeZeroDecimal(numDisplay));

                        }
                        // Convert to percentage
                        else if (buttonValue == "%") {
                            double  numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(Double.toString(numDisplay));
                        }

                    }
                     // Handle digits and decimal point
                    else { //digits or
                        // Decimal point
                        if (buttonValue == ".") {
                             // Prevent multiple decimal points
                           if(!displayLabel.getText().contains(buttonValue)) {
                              displayLabel.setText(displayLabel.getText() + buttonValue);
                           }                           
                        }
                        // Numeric buttons
                        else if ("0123456789".contains (buttonValue)) {
                            // Replace initial zero
                            if (displayLabel.getText() == "0") {
                                displayLabel.setText(buttonValue);
                            }
                            // Append digit
                            else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                    }
                }
            });
        // Make frame visible
            frame.setVisible(true);
        }   
    }
     // Reset all calculation variables
    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }
    // Remove unnecessary ".0" from whole numbers
    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0){
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }

}


