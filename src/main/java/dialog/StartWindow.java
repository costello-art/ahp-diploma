package dialog;

import dialog.secondAlgo.SActorsInput;
import dialog.secondAlgo.SScenarioInput;
import dialog.secondAlgo.SScenarioMatrix;
import dialog.secondAlgo.STargetInput;
import model.GlobalTarget;
import model.SGlobalTarget;
import util.Calculate;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Sviat on 18.11.14.
 */
public class StartWindow extends JFrame {
    final static Logger log = Logger.getLogger(StartWindow.class);
    private static MigLayout migLayout = new MigLayout("ins 0, hidemode 3", "", "[][][]");
    private SGlobalTarget starget;

    private JTextField textFieldTarget;
    private JButton buttonAddActors;
    private JButton buttonActorTargetMatrixInputDone;
    private JPanel panelRoot;
    private JPanel panelMatrix;
    private JButton buttonStartScenarioInput;
    private JButton buttonRunSecond;
    private GlobalTarget target;
    private RTable actorTargetMatrix;

    public static void main(String[] args) {
        log.debug("ok");
        StartWindow start = new StartWindow();
        start.setTitle("Магістерська робота Горошка Є.М., ІСМм-21");

        start.setSize(800, 400);
        start.setContentPane(start.panelRoot);
        start.setVisible(true);

        start.setLocationRelativeTo(null);

        start.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public StartWindow() throws HeadlessException {
        buttonAddActors.addActionListener(new OpenActorInputDialog(this));
        buttonActorTargetMatrixInputDone.addActionListener(new OpenActorsTargetsInput(this));
        buttonStartScenarioInput.addActionListener(new StartScenarioInput(this));

        buttonRunSecond.addActionListener(new RunSecond(this));
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
        actorTargetMatrix = new RTable(target.getActorsNames());

        panelMatrix.setLayout(migLayout);
        panelMatrix.add(actorTargetMatrix, "w 100%, h 100%");

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
            target.setMatrix(actorTargetMatrix.getMatrix());
            ActorsTargetsInput aDialog = new ActorsTargetsInput(startWindow, target);
            target = aDialog.display();
        }
    }

    private class StartScenarioInput implements ActionListener {
        private StartWindow startWindow;

        public StartScenarioInput(StartWindow startWindow) {

            this.startWindow = startWindow;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ScenarioInputDialog sDialog = new ScenarioInputDialog(startWindow, target);
            target = sDialog.display();

            log.debug("scenario input has been done");

            ArrayList<Double> finalVector = Calculate.getResultVector(target.getScenarioMatrixForBestTarget(), target.getBestTargetsAsArray());
            target.setFinalVector(finalVector);

            new ShowResultDialog(startWindow, target).display();

          //  target.printResult(finalVector);
        }
    }

    private class RunSecond implements ActionListener {
        private StartWindow startWindow;

        public RunSecond(StartWindow startWindow) {
            this.startWindow = startWindow;
            starget = new SGlobalTarget("name");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            starget = new SActorsInput(startWindow, starget).display();
            starget = new STargetInput(startWindow, starget).display();
            starget = new SScenarioInput(startWindow, starget).display();
            starget = new SScenarioMatrix(startWindow, starget).display();
        }
    }
}