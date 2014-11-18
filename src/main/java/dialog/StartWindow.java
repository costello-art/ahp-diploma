package dialog;

import model.GlobalTarget;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sviat on 18.11.14.
 */
public class StartWindow extends JFrame {
    final static Logger log = Logger.getLogger(StartWindow.class);
    private static MigLayout migLayout = new MigLayout("ins 0, hidemode 3", "", "[][][]");

    private JTextField textFieldTarget;
    private JButton buttonAddActors;
    private JButton buttonActorTargetMatrixInputDone;
    private JPanel panelRoot;
    private JPanel panelMatrix;
    private GlobalTarget target;

    public static void main(String[] args) {
        log.debug("ok");
        StartWindow start = new StartWindow();
        start.setTitle("Диплом Горошка!");

        start.setSize(800, 400);
        start.setTitle("Диплом Горошка");
        start.setContentPane(start.panelRoot);
        start.setVisible(true);

        start.setLocationRelativeTo(null);

        start.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public StartWindow() throws HeadlessException {
        buttonAddActors.addActionListener(new OpenActorInputDialog(this));
        buttonActorTargetMatrixInputDone.addActionListener(new OpenActorsTargetsInput(this));
    }

    private class OpenActorInputDialog implements ActionListener {
        private StartWindow startWindow;

        public OpenActorInputDialog(StartWindow startWindow) {

            this.startWindow = startWindow;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            target = new GlobalTarget(textFieldTarget.getText());
            ActorsInputDialog aDialog = new ActorsInputDialog(startWindow, target);
            target = aDialog.display();

            buttonAddActors.setEnabled(false);
            addMatrix();
        }
    }

    private void addMatrix() {
        RTable table = new RTable(target.getActorCount());
        table.setHeaders(target.getActorsNames());

        panelMatrix.setLayout(migLayout);
        panelMatrix.add(table, "w 100%, h 100%");

        panelMatrix.validate();
        panelMatrix.repaint();
    }

    private class OpenActorsTargetsInput implements ActionListener {
        private StartWindow startWindow;

        public OpenActorsTargetsInput(StartWindow startWindow) {
            this.startWindow = startWindow;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ActorsTargetsInput aDialog = new ActorsTargetsInput(startWindow, target);
            target = aDialog.display();

        }
    }
}