package dialog.secondAlgo;

import dialog.StartWindow;
import model.GlobalTarget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class STargetInput extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextArea textAreaTarget;
    private GlobalTarget target;

    public STargetInput(StartWindow start, GlobalTarget target) {
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
        for(String line : textAreaTarget.getText().split("\n")) {
            target.addTargetForAll(line);
        }

        dispose();
    }

    public GlobalTarget display() {
        return target;
    }
}
