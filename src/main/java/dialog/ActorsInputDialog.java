package dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sviat on 18.11.14.
 */
public class ActorsInputDialog extends JDialog {
    private JLabel panelActorInput;
    private JButton buttonAddNewActor;
    private JButton buttonInputHasBeenDone;
    private JTextField textField1;
    private JTextArea textAreaActorTargets;
    private JLabel labelActorCount;
    private JLabel labelIsActorAdded;
    private JPanel panelRoot;

    public ActorsInputDialog(StartWindow startWindow) {
        setSize(new Dimension(640, 480));
        setResizable(false);
        setLocationRelativeTo(startWindow);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Введення акторів та їх цілей");
        setContentPane(panelRoot);
        setModal(true);

        buttonAddNewActor.addActionListener(new AddActorListener());
    }

    public void display() {
        setVisible(true);
    }

    private class AddActorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("new actor!");
        }
    }
}
