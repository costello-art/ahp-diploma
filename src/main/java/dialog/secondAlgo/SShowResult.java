package dialog.secondAlgo;

import dialog.StartWindow;
import model.SGlobalTarget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SShowResult extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextArea textAreaResults;
    private JLabel labelResultText;
    private SGlobalTarget starget;

    public SShowResult(StartWindow startWindow, SGlobalTarget starget) {
        this.starget = starget;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(startWindow);

        setTitle("Результат");
        setSize(new Dimension(640, 480));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        showResult();
    }

    private void showResult() {
        double[] vector = starget.getResult();

        ArrayList<Double> listVector = new ArrayList<>();

        for (int i = 0; i < vector.length; i++) {
            listVector.add(vector[i]);
        }

        int maxPos = listVector.indexOf(Collections.max(listVector));

        ArrayList<String> scenarios = starget.getScenarios();

        StringBuilder allResult = new StringBuilder();
        for (int i = 0; i < scenarios.size(); i++) {
            allResult.append(String.format("%s -> %f\n", scenarios.get(i), listVector.get(i)));
        }

        textAreaResults.setText(allResult.toString());

        labelResultText.setText(String.format("Найкращий результат: %s (%f)", scenarios.get(maxPos), listVector.get(maxPos)));
    }

    public void display() {
        setVisible(true);
    }

    private void onOK() {
// add your code here
        dispose();
    }
}
