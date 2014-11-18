package dialog;

import model.Actor;
import model.GlobalTarget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Sviat on 19.11.14.
 */
public class ActorsTargetsInput extends JDialog {
    private JLabel labelActorsTargetsInput;
    private JButton buttonNextActorTargetsInput;
    private JPanel panelActorsTargets;
    private GlobalTarget target;
    private final ArrayList<Actor> bestActorsList;


    public ActorsTargetsInput(StartWindow startWindow, GlobalTarget target) {
        this.target = target;
        setLocationRelativeTo(startWindow);
        setModal(true);

        setSize(new Dimension(640, 480));

        //setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        bestActorsList = target.getBestActors();

        labelActorsTargetsInput.setText(
                String.format("<html>Потрібно ввести матрицю цілей для двох кращих акторів: <b>%s</b> та <b>%s</b></html>",
                        bestActorsList.get(0).getName(),
                        bestActorsList.get(1).getName())
        );

        buttonNextActorTargetsInput.addActionListener(new OnActorsMatrixEntered());

    }


    public GlobalTarget display() {
        setVisible(true);
        return target;
    }

    private class OnActorsMatrixEntered implements ActionListener {
        private int currentActor = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            buildMatrixForCurrentActor(currentActor);
        }
    }

    private void buildMatrixForCurrentActor(int currentActor) {
        ArrayList<String> targets = bestActorsList.get(currentActor).getTargets();
        RTable table = new RTable(targets.size());
        table.setHeaders(targets);

        panelActorsTargets.add(table, "w: 50%, h:50% wrap");
        panelActorsTargets.validate();
        panelActorsTargets.repaint();
    }
}
