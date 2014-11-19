package dialog;

import model.GlobalTarget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sviat on 18.11.14.
 */
public class ActorsInputDialog extends JDialog {
    private JLabel panelActorInput;
    private JButton buttonAddNewActor;
    private JButton buttonInputHasBeenDone;
    private JTextField textFieldActorName;
    private JTextArea textAreaActorTargets;
    private JLabel labelActorCount;
    private JLabel labelIsActorAdded;
    private JPanel panelRoot;
    private GlobalTarget target;

    public ActorsInputDialog(StartWindow startWindow, GlobalTarget target) {
        this.target = target;
        setSize(new Dimension(640, 480));
        setResizable(false);
        setLocationRelativeTo(startWindow);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Введення акторів та їх цілей");
        setContentPane(panelRoot);
        setModal(true);

        buttonAddNewActor.addActionListener(new AddActorListener());
        buttonInputHasBeenDone.addActionListener(new InputHasBennDoneListener());
    }

    public GlobalTarget display() {
        setVisible(true);
        return target;
    }

    private class AddActorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<String> targets = new ArrayList<>();
            Collections.addAll(targets, textAreaActorTargets.getText().split("\n"));

            target.addActor(textFieldActorName.getText(), targets);
            labelIsActorAdded.setText("<html>Актор <b>"+textFieldActorName.getText()+"</b> доданий. Кількість цілей: "+targets.size()+"</html>");
            labelActorCount.setText("Всього акторів: " + target.getActorsList().size());
        }
    }

    private class InputHasBennDoneListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}
