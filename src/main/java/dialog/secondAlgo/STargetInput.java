package dialog.secondAlgo;

import dialog.StartWindow;
import model.SGlobalTarget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class STargetInput extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextArea textAreaTarget;
    private SGlobalTarget target;

    public STargetInput(StartWindow start, SGlobalTarget target) {
        this.target = target;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(start);
        setSize(new Dimension(320, 240));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
        for(String targetName : textAreaTarget.getText().split("\n")) {
            target.addTarget(targetName);
        }

        dispose();
    }

    public SGlobalTarget display() {
        setVisible(true);
        return target;
    }
}
