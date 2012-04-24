package com.course.berkley;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MainFrame {
    private JTextField input;
    private JPanel panel;
    private Box inputBox;
    private Box controlBox;
    private ArrayList<JLabel> messages;
    private Berkley berkleyAlgorithm;
    private Polynom polynomAlgorithm;

    public MainFrame() {
        messages = new ArrayList<JLabel>();
        JFrame frame = new JFrame("Berkley");
        Container container = frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        container.add(createPanel(), BorderLayout.CENTER);
        container.add(createInputBox(), BorderLayout.PAGE_START);

        frame.pack();
        frame.setSize(600, 500);
        frame.setVisible(true);
    }


    private JPanel createPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        return panel;
    }

    private Box createInputBox() {
        JLabel label = new JLabel("Program");
        input = new JTextField(10);
        inputBox = Box.createHorizontalBox();

        AbstractAction generateAction = new AbstractAction("Generate") {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (messages.isEmpty()) {
                            String text = input.getText();
                            if (!input.equals("")) {

                                int[] sequence = parseInput(text);

                                berkleyAlgorithm = new Berkley(sequence);
                                polynomAlgorithm = new Polynom(sequence);
                                addMessage();

                                redraw();
                            }
                        }
                    }

                    private void addMessage() {
                        berkleyAlgorithm.encode();
                        polynomAlgorithm.encode();
                        JLabel berkleyLabel = new JLabel(berkleyAlgorithm.toString());
                        JLabel polynomLabel = new JLabel(polynomAlgorithm.toString());
                        JLabel spanLabel = new JLabel(polynomAlgorithm.span());
                        messages.add(berkleyLabel);
                        messages.add(spanLabel);
                        messages.add(polynomLabel);
                        panel.add(berkleyLabel);
                        panel.add(polynomLabel);
                        panel.add(spanLabel);
                    }

                    private int[] parseInput(String input) {
                        String[] result = input.split(",");
                        int[] sequence = new int[result.length];
                        for (int i = 0; i < result.length; i++) {
                            sequence[i] = new Integer(result[i].trim());
                        }
                        return sequence;
                    }

                    private void redraw() {
                        panel.validate();
                        panel.repaint();
                    }


                });
            }
        };

        AbstractAction restartAction = new AbstractAction("Restart") {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        input.setText("");
                        for (JLabel message : messages) {
                            panel.remove(message);
                        }
                        messages.clear();
                        redraw();
                    }
                    private void redraw() {
                        panel.validate();
                        panel.repaint();
                    }
                });
            }
        };


        inputBox.add(label);
        inputBox.add(input);
        inputBox.add(new JButton(generateAction));
        inputBox.add(new JButton(restartAction));
        return inputBox;
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
        }
        MainFrame mainFrame = new MainFrame();
    }
}
