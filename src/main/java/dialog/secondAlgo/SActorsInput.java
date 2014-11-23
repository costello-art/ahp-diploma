package dialog.secondAlgo;

import dialog.StartWindow;
import model.GlobalTarget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SActorsInput extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextArea textAreaActors;
    private GlobalTarget target;

    public SActorsInput(StartWindow start, GlobalTarget target) {
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

        for (String line : textAreaActors.getText().split("\n")) {
            target.addActor(line, null);
        }
        dispose();
    }

    public GlobalTarget display() {
        setVisible(true);
        return target;
    }
}
