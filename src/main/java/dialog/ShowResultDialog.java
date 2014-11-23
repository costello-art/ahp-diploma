package dialog;

import model.DConfig;
import model.GlobalTarget;
import model.Scenario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

public class ShowResultDialog extends JDialog {
    private JPanel contentPane;
    private JLabel labelResult;
    private JLabel labelVector;
    private JTextArea textAreaScenarioVector;
    private JButton buttonContinueModdeling;
    private JButton buttonClose;
    private GlobalTarget target;

    public ShowResultDialog(StartWindow startWindow, GlobalTarget target) {
        this.target = target;

        setContentPane(contentPane);
        setModal(true);
        setLocationRelativeTo(startWindow);

        setSize(new Dimension(640, 480));

        buttonContinueModdeling.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DConfig.isSecondAlgo = true;
                dispose();
            }
        });

        buttonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        showResult();
    }

    private void showResult() {
        double max = Collections.max(target.getFinalVector());
        int index = target.getFinalVector().indexOf(max);
        Scenario s = target.getScenarioList().get(index);
        double value = target.getFinalVector().get(index);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < target.getFinalVector().size(); i++) {
            sb.append(target.getScenarioList().get(i).getName() + " -> " + target.getFinalVector().get(i) + "\n");
        }

        textAreaScenarioVector.setText(sb.toString());
        labelResult.setText(String.format("Сценарій %s із результатом %f", s.getName(), value));
    }

    public void display() {
        setVisible(true);
    }
}