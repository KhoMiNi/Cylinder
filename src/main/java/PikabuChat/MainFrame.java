package PikabuChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame {

    private double height;
    private double radius;
    private double diameter;
    private double amper;
    private double result;
    private JFrame frame;
    private JPanel scrollPane;


    void go(){
        createFrame();
        inputValues();
    }

    private void createFrame() {
        String title = "Pikabu chat task";
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 250);
        frame.setLocation(50, 50);
        frame.setResizable(true);
        scrollPane = new JPanel();
        scrollPane.setPreferredSize(new Dimension(190, 190));
        final JLabel textlabel = new JLabel();
        textlabel.setText("Input values");
        scrollPane.add(textlabel, 0);
        final JTextField resultField = new JTextField(10);
        scrollPane.add(resultField,1);
        final JButton calcButton = new JButton();
        calcButton.setText("Calculate new");
        calcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputValues();
            }
        });
        scrollPane.add(calcButton,2);
        frame.getContentPane().add(BorderLayout.SOUTH, scrollPane);
        frame.setVisible(true);

    }

    private void inputValues() {
        JTextField heightField = new JTextField(5);
        JTextField diameterField = new JTextField(5);
        JTextField amperField = new JTextField(5);
        JPanel myPanel = createInputValuesDialog(heightField, diameterField, amperField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Enter values from 0.1 to 1000.0", JOptionPane.OK_CANCEL_OPTION);
        switch (result) {
            case JOptionPane.OK_OPTION:
                try {
                    height = Double.parseDouble(heightField.getText());
                    diameter = Double.parseDouble(diameterField.getText());
                    amper = Double.parseDouble(amperField.getText());
                    calculateActions();
                } catch (NumberFormatException e) {
                    inputValues();
                }
                break;
            default:
                System.exit(0);
        }

    }

    private void calculateActions() {
        checkValues();
        radius = diameter/2;
        result = calculateResult(calculateSurface(radius,height), amper) ;
        setResults();
    }

    private void setResults() {
        JLabel label = (JLabel) scrollPane.getComponent(0);
        //String values = String.format("Amperage = %.2f \n Cylinder: \n height = %.2f  \n diameter = %.2f \n Results: \n", amper,height,diameter);
        //System.out.println(values);
        label.setText("Result:");
        JTextField resultField =(JTextField)scrollPane.getComponent(1);
        String resultText = String.format("%.2f A", result);
        resultField.setText(resultText);
    }

    private void checkValues() {
        diameter = diameter > 0.1 && diameter < 1000.0 ? diameter : 50; // default
        height = height > 0.1 && height < 1000.0 ? height : 50; // default
        amper = amper > 0.1 && amper < 1000.0 ? amper : 50; // default
    }

    private JPanel createInputValuesDialog(JTextField heightField, JTextField diameterField, JTextField amperField) {
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Height:"));
        myPanel.add(heightField);
        myPanel.add(Box.createHorizontalStrut(10));
        myPanel.add(new JLabel("Diameter:"));
        myPanel.add(diameterField);
        myPanel.add(new JLabel("Ampers:"));
        myPanel.add(amperField);

        return myPanel;
    }
    private static double calculateResult(double surfaceArea, double amper) {
        return surfaceArea*amper;
    }

    private static double calculateSurface(double radius, double height){
        double result;
        double r = radius*0.01;
        double h = height*0.01;
        result = 2 * Math.PI * r *(h + r);
        return result;
    }
}
