package dialog.secondAlgo;

import dialog.StartWindow;
import model.GlobalTarget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SScenarioInput extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextArea textAreaScenario;
    private GlobalTarget target;

    public SScenarioInput(StartWindow startWindow, GlobalTarget target) {
        this.target = target;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(startWindow);

        setSize(new Dimension(320, 240));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
        for (String line : textAreaScenario.getText().split("\n")) {
            target.addScenario(line);
        }
        dispose();
    }

    public GlobalTarget display() {
        setVisible(true);
        return target;
    }
}
