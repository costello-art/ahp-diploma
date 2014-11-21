package dialog;

import model.GlobalTarget;
import model.Scenario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowResultDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel labelResult;
    private GlobalTarget target;

    public ShowResultDialog(StartWindow startWindow, GlobalTarget target) {
        this.target = target;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(startWindow);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        setSize(new Dimension(640, 480));

        showResult();
    }

    private void showResult() {
        Scenario s = target.getScenarioList().get(0);
        double value = target.getFinalVector().get(0);

        labelResult.setText(String.format("Сценарій %s із результатом %f", s.getName(), value));
    }

    private void onOK() {
// add your code here
        dispose();
    }

    public void display() {
        setVisible(true);
    }
}
