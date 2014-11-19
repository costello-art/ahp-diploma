package dialog;

import model.Actor;
import model.GlobalTarget;
import model.math.Calculate;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Sviat on 19.11.14.
 */
public class ActorsTargetsInput extends JDialog {
    private static MigLayout migLayout = new MigLayout("ins 0, hidemode 3", "", "[][][]");

    private JLabel labelActorsTargetsInput;
    private JButton buttonNextActorTargetsInput;
    private JPanel panelActorsTargets;
    private JPanel panelRoot;
    private JLabel labelMatrixForActor;
    private JButton buttonActorsTargetsInputDone;
    private GlobalTarget target;
    private final ArrayList<Actor> bestActorsList;
    private RTable table;


    public ActorsTargetsInput(StartWindow startWindow, GlobalTarget target) {
        this.target = target;
        setLocationRelativeTo(startWindow);
        setModal(true);
        setSize(new Dimension(640, 480));
        setContentPane(panelRoot);

        panelActorsTargets.setLayout(migLayout);

        bestActorsList = target.getBestActors();

        labelActorsTargetsInput.setText(
                String.format("<html>Потрібно ввести матрицю цілей для двох кращих акторів: <b>%s</b> та <b>%s</b></html>",
                        bestActorsList.get(0).getName(),
                        bestActorsList.get(1).getName())
        );

        labelMatrixForActor.setText("Матриця цілей для актора " + bestActorsList.get(0));
        buttonNextActorTargetsInput.addActionListener(new OnActorsMatrixEntered());
        buttonActorsTargetsInputDone.addActionListener(new OnActorsMatrixEnterDone());
        buildMatrixForCurrentActor(0);
    }

    public GlobalTarget display() {
        setVisible(true);
        return target;
    }

    private class OnActorsMatrixEntered implements ActionListener {
        private int currentActor = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            target.getBestActors().get(currentActor).setMatrix(table.getMatrix());
           // bestActorsList.get(currentActor).setMatrix(table.getMatrix());

            //лічильник збільшується тут, бо на початку вручну обробили 0
            currentActor++;

            if (currentActor >= 2) {
                buttonNextActorTargetsInput.setText("Матриця цілей для всіх акторів введена.");
                buttonNextActorTargetsInput.setEnabled(false);
                return;
            }

            labelActorsTargetsInput.setText("Матриця цілей для актора " + bestActorsList.get(currentActor).getName());
            buildMatrixForCurrentActor(currentActor);
        }
    }

    private void buildMatrixForCurrentActor(int currentActor) {
        panelActorsTargets.removeAll();
        panelActorsTargets.validate();
        panelActorsTargets.repaint();

        ArrayList<String> targets = bestActorsList.get(currentActor).getTargets();
        table = new RTable(targets.size());
        table.setHeaders(targets);

        panelActorsTargets.add(table, "w 100%, h 100%");
        panelActorsTargets.validate();
        panelActorsTargets.repaint();
    }

    private class OnActorsMatrixEnterDone implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}