package dialog;

import model.Actor;
import model.GlobalTarget;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sviat on 12.11.14.
 */
public class AppMain extends JFrame {

    private static MigLayout migLayout = new MigLayout("ins 0, hidemode 3", "", "[][][]");


    private JPanel panelRoot;
    private JPanel panelStep1;
    private JPanel panelStep2;
    private JTextField textTargetName;
    private JButton buttonMoveToStep2;
    private JSpinner spinnerActorCount;
    private JPanel panelActors;
    private JButton buttonNextActor;
    private JLabel labelActorName;

    private JPanel panelGlobalTargets;
    private JButton buttonMoveToStep3;
    private JLabel labelGlobalTarget;
    private JPanel panelStep3;
    private JPanel panelActorTargets;
    private JButton buttonDoCalculations;
    private JLabel labelActorsTargetsInput;
    private GlobalTarget globalTarget;
    private RTable tableWithTargets;

    public static void main(String[] args) {
        AppMain main = new AppMain();
        main.setSize(800, 600);
        main.setTitle("Диплом Горошка");
        main.setContentPane(main.panelRoot);
        main.setVisible(true);

    }

    public AppMain() throws HeadlessException {
        buttonMoveToStep2.addActionListener(new ProcessActorNamesInput());
        buttonMoveToStep3.addActionListener(new ProcessGlobalMatrixInput());
        buttonDoCalculations.addActionListener(new DoCalculations());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelActors.setLayout(migLayout);
        panelGlobalTargets.setLayout(migLayout);
        panelActorTargets.setLayout(migLayout);

        onActorsCountChanged(2);

        panelStep1.setVisible(true);
        panelStep2.setVisible(false);
        panelStep3.setVisible(false);
    }

    private void createUIComponents() {
        spinnerActorCount = new JSpinner(new SpinnerNumberModel(2, 2, 9, 1));
        spinnerActorCount.addChangeListener(new SpinnerActionAddActorNamesFileds());
    }

    private class SpinnerActionAddActorNamesFileds implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            Integer count = (Integer) (spinnerActorCount.getValue());
            onActorsCountChanged(count);
        }
    }

    private void onActorsCountChanged(Integer count) {
        panelActors.removeAll();

        for (int i = 0; i < count; i++) {
            ActorTargetBox atb = new ActorTargetBox(i);
            atb.setActorName("act" + i);
            atb.setTargetsText("t1,t2");
            panelActors.add(atb, "wrap");
        }

        panelActors.validate();
        panelActors.repaint();
    }

    private class ProcessActorNamesInput implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Integer count = (Integer) spinnerActorCount.getValue();
            globalTarget = new GlobalTarget(textTargetName.getText(), count);

            for (int i = 0; i < count; i++) {
                for (Component c : panelActors.getComponents()) {
                    if (c.getName().equals("actorTargetBox" + i)) {
                        ActorTargetBox box = (ActorTargetBox) c;
                        globalTarget.addActor(box.getActorName(), box.getTargets());
                    }
                }
            }

            panelStep1.setVisible(false);
            moveToStep2();
        }
    }

    private void moveToStep2() {
        panelStep2.setVisible(true);

        labelGlobalTarget.setText("Введіть матрицю цілей для всіх акторів. Матриця має розмір " + globalTarget.getActorCount());

        tableWithTargets = new RTable(globalTarget.getActorCount());
        tableWithTargets.setHeaders(globalTarget.getActorsNames());
        panelGlobalTargets.add(tableWithTargets, "wrap");

        panelGlobalTargets.validate();
        panelGlobalTargets.repaint();
    }

    private class ProcessNextActorTargetInput implements ActionListener {
        int count = globalTarget.getActorCount();
        int currentActor = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentActor + 1 >= count) {
                labelActorsTargetsInput.setText("Цілі для всіх акторів вже введено.");
                buttonNextActor.setEnabled(false);
                return;
            }
            currentActor++;

            labelActorsTargetsInput.setText(String.format("<html>Введіть матрицю цілей для актора<b>\"%s\"</b></html>", globalTarget.getActorsNames().get(currentActor)));
            globalTarget.getActors().get(currentActor).setMatrix(tableWithTargets.getMatrix());

            remove(tableWithTargets);
            panelActorTargets.validate();
            panelActorTargets.repaint();

            Actor actor = globalTarget.getActors().get(currentActor);
            tableWithTargets = new RTable(actor.getTargets().size());
            tableWithTargets.setHeaders(actor.getTargets());
            panelActorTargets.add(tableWithTargets, "wrap");
        }
    }

    private class ProcessGlobalMatrixInput implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            globalTarget.setMatrix(tableWithTargets.getMatrix());
            panelStep2.setVisible(false);
            moveToStep3();
        }
    }

    private void moveToStep3() {
        buttonNextActor.addActionListener(new ProcessNextActorTargetInput());

        panelStep3.setVisible(true);
        labelActorsTargetsInput.setText(String.format("<html>Введіть матрицю цілей для актора<b>\"%s\"</b></html>", globalTarget.getActorsNames().get(0)));
        tableWithTargets = new RTable(globalTarget.getActors().get(0).getTargets().size());
        tableWithTargets.setHeaders(globalTarget.getActors().get(0).getTargets());

        panelActorTargets.add(tableWithTargets, "wrap");

        panelActorTargets.validate();
        panelActorTargets.repaint();
    }

    private class DoCalculations implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}