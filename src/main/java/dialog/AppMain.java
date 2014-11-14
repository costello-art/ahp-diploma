package dialog;

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
    private JPanel panelBasicDataInput;
    private JPanel panelTargetInput;
    private JTextField textTargetName;
    private JButton buttonProcessData;
    private JSpinner spinnerActorCount;
    private JPanel panelActors;
    private JButton buttonNextActor;
    private JLabel labelActorName;

    private JPanel panelTargets;
    private JButton buttonShowInputActorsMatrix;
    private JLabel labelTargetInputTitle;
    private JPanel panelActorTargetInput;
    private JButton buttonNextActorInput;
    private JPanel panelActorTargetMatrixInput;
    private JButton buttonDoCalculations;
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
        buttonProcessData.addActionListener(new ProcessActorInput());
        buttonNextActor.addActionListener(new ProcessNextActorTargetInput());
        buttonShowInputActorsMatrix.addActionListener(new ProcessActorsMatrixInput());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelActors.setLayout(migLayout);
        panelTargets.setLayout(migLayout);

        onActorsCountChanged(2);

        panelBasicDataInput.setVisible(false);
        panelTargetInput.setVisible(false);
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
            panelActors.add(atb, "wrap");
        }

        panelActors.validate();
        panelActors.repaint();
    }

    private class ProcessActorInput implements ActionListener {
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

            panelBasicDataInput.setVisible(false);
            moveToTargetMatrixInput();
        }
    }

    private void moveToTargetMatrixInput() {
        panelTargetInput.setVisible(true);

        labelTargetInputTitle.setText("Введіть матрицю цілей для всіх акторів. Матриця має розмір " + globalTarget.getActorCount());

        tableWithTargets = new RTable(globalTarget.getActorCount());
        tableWithTargets.setHeaders(globalTarget.getActorsNames());
        panelTargets.add(tableWithTargets, "wrap");

        panelTargets.validate();
        panelTargets.repaint();
    }

    private void onTargetInputVisible(int actorIndex) {
        String firstActorName = globalTarget.getActors().get(actorIndex).getName();
        labelActorName.setText(firstActorName);
    }

    private class ProcessNextActorTargetInput implements ActionListener {
        int actorCount = globalTarget.getActorCount();
        int currentActor = 1;

        @Override
        public void actionPerformed(ActionEvent e) {
            Integer targetCount = 0;//(Integer) spinnerActorTargetCount.getValue();

            for (int currentTarget = 0; currentTarget < targetCount; currentTarget++) {
                for (Component c : panelTargets.getComponents()) {
                    if (c.getName().equals("targetName" + currentTarget)) {
                        JTextField targetField = (JTextField) c;
                        globalTarget.getActors().get(currentActor).addTarget(targetField.getText());
                        targetField.setText("");
                    }
                }
            }

            currentActor++;
            if (currentActor >= actorCount) {
                buttonNextActor.setText("Всі дані введено");
                return;
            }

            onTargetInputVisible(currentActor);
        }
    }

    private class ProcessActorsMatrixInput implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            panelTargets.removeAll();
            labelTargetInputTitle.setText(String.format("<html>Введіть матрицю цілей для актора<b>\"%s\"</b></html>", globalTarget.getActorsNames().get(0)));
            tableWithTargets = new RTable(globalTarget.getActors().get(0).getTargets().size());
            panelTargets.add(tableWithTargets);
            panelTargets.validate();
            panelTargets.repaint();
        }
    }
}