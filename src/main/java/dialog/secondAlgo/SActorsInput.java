package dialog.secondAlgo;

import dialog.StartWindow;
import model.SGlobalTarget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SActorsInput extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextArea textAreaActors;
    private SGlobalTarget target;

    public SActorsInput(StartWindow start, SGlobalTarget target) {
        this.target = target;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(start);

        setTitle("Актори");
        setSize(new Dimension(320, 240));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
        for (String actor : textAreaActors.getText().split("\n")) {
            target.addActor(actor);
        }

        dispose();
    }

    public SGlobalTarget display() {
        setVisible(true);
        return target;
    }
}