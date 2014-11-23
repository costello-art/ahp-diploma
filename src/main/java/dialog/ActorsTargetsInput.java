package dialog;

import model.DConfig;
import model.GlobalTarget;
import util.Calculate;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Created by Sviat on 19.11.14.
 */
public class ActorsTargetsInput extends JDialog {
    final static Logger log = Logger.getLogger(ActorsTargetsInput.class);
    private static MigLayout migLayout = new MigLayout("ins 0, hidemode 3", "", "[][][]");

    private JLabel labelActorsTargetsInput;
    private JButton buttonNextActorTargetsInput;
    private JPanel panelActorsTargets;
    private JPanel panelRoot;
    private JLabel labelMatrixForActor;
    private JButton buttonActorsTargetsInputDone;
    private GlobalTarget target;

    private RTable table;

    private int actorMax = 2;

    public ActorsTargetsInput(StartWindow startWindow, GlobalTarget target) {
        this.target = target;
        setLocationRelativeTo(startWindow);
        setModal(true);
        setTitle("Матриця цілей для акторів");
        setSize(new Dimension(640, 480));
        setContentPane(panelRoot);

        panelActorsTargets.setLayout(migLayout);

        initTitleAndLimits();

        labelMatrixForActor.setText("Матриця цілей для актора " + target.getActorsList().get(0).getName());
        buttonNextActorTargetsInput.addActionListener(new OnActorsMatrixEntered());
        buttonActorsTargetsInputDone.addActionListener(new OnActorsMatrixEnterDone());
        buildMatrixForCurrentActor(0);
    }

    private void initTitleAndLimits() {
        if (!DConfig.isSecondAlgo) {
            labelActorsTargetsInput.setText(
                    String.format("<html>Потрібно ввести матрицю цілей для двох кращих акторів: <b>%s</b> та <b>%s</b></html>",
                            target.getActorsList().get(0).getName(),
                            target.getActorsList().get(1).getName()));
            actorMax = 2;
        } else {
            labelActorsTargetsInput.setText(
                    String.format("<html>Потрібно ввести матрицю цілей для всіх акторів</html>"));
            actorMax = target.getActorsList().size();
        }
    }

    public GlobalTarget display() {
        setVisible(true);
        return target;
    }

    private class OnActorsMatrixEntered implements ActionListener {
        private int currentActor = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            log.debug("Current actor index: " + currentActor);
            target.getActorsList().get(currentActor).setMatrix(table.getMatrix());

            //лічильник збільшується тут, бо на початку вручну обробили 0
            currentActor++;

            if (currentActor >= actorMax) {
                buttonNextActorTargetsInput.setText("Матриця цілей для всіх акторів введена.");
                buttonNextActorTargetsInput.setEnabled(false);
                return;
            }

            labelMatrixForActor.setText("Матриця цілей для актора " + target.getActorsList().get(currentActor).getName());
            buildMatrixForCurrentActor(currentActor);
        }
    }

    private void buildMatrixForCurrentActor(int currentActor) {
        panelActorsTargets.removeAll();
        panelActorsTargets.validate();
        panelActorsTargets.repaint();

        table = new RTable(target.getActorsList().get(currentActor).getTargets());

        panelActorsTargets.add(table, "w 100%, h 100%");
        panelActorsTargets.validate();
        panelActorsTargets.repaint();
    }

    private class OnActorsMatrixEnterDone implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!DConfig.isSecondAlgo) {
                Map<String, Double> v1 = target.getActorsList().get(0).getBestActorsWeightValues();
                Map<String, Double> v2 = target.getActorsList().get(1).getBestActorsWeightValues();
                Map<String, Double> bestVector = Calculate.buildAndNormalizeVectorOfBestTargets(v1, v2);

                target.setBestTargetsForActors(bestVector);
            }

            dispose();
        }
    }
}