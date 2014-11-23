package dialog.secondAlgo;

import dialog.StartWindow;
import model.SGlobalTarget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SScenarioInput extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextArea textAreaScenario;
    private SGlobalTarget target;

    public SScenarioInput(StartWindow startWindow, SGlobalTarget target) {
        this.target = target;
        setContentPane(contentPane);
        setSize(new Dimension(320, 240));
        setModal(true);
        setLocationRelativeTo(startWindow);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Політики");

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
        for (String scenario : textAreaScenario.getText().split("\n")) {
            target.addScenario(scenario);
        }

        dispose();
    }

    public SGlobalTarget display() {
        setVisible(true);
        return target;
    }
}