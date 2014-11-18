package dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sviat on 18.11.14.
 */
public class StartWindow extends JFrame {
    private JTextField textFieldTarget;
    private JButton buttonAddActors;
    private JButton buttonActorTargetMatrixInput;
    private JPanel panelRoot;

    public static void main(String[] args) {
        StartWindow start = new StartWindow();
        start.setTitle("Диплом Горошка!");

        start.setSize(800, 600);
        start.setTitle("Диплом Горошка");
        start.setContentPane(start.panelRoot);
        start.setVisible(true);

        start.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public StartWindow() throws HeadlessException {
        buttonAddActors.addActionListener(new OpenActorInputDialog(this));
    }

    private class OpenActorInputDialog implements ActionListener {
        private StartWindow startWindow;

        public OpenActorInputDialog(StartWindow startWindow) {

            this.startWindow = startWindow;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ActorsInputDialog aDialog = new ActorsInputDialog(startWindow);
            aDialog.display();
        }
    }
}
