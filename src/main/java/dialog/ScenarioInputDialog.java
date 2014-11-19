package dialog;

import model.GlobalTarget;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Sviat on 18.11.14.
 */
public class ScenarioInputDialog extends JDialog {
    final static Logger log = Logger.getLogger(ScenarioInputDialog.class);
    private static MigLayout migLayout = new MigLayout("ins 0, hidemode 3", "", "[][][]");

    private JLabel labelScenarioTitle;
    private JButton buttonSaveScenarioMatrix;
    private JLabel labelCurrentScenario;
    private JPanel panelScenarioInput;
    private JButton buttonScenarioInputDone;
    private JPanel panelRoot;
    private JTextArea textAreaScenario;
    private JButton buttonSaveScenarioList;
    private GlobalTarget target;
    private RTable table;
    private int currentScenarioAndList = 0;
    private Map<String, Float> bestTargets;
    private ArrayList<String> bestTargetNames;

    public ScenarioInputDialog(StartWindow startWindow, GlobalTarget target) {
        this.target = target;
        setSize(new Dimension(640, 480));

        setResizable(false);
        setLocationRelativeTo(startWindow);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Введення акторів та їх цілей");
        setContentPane(panelRoot);
        setModal(true);

        panelScenarioInput.setLayout(migLayout);

        buttonSaveScenarioList.addActionListener(new SaveScenarioList());
        buttonSaveScenarioMatrix.addActionListener(new SaveScenarioMatrix());
        buttonScenarioInputDone.addActionListener(new InputHasBeenDone());
    }

    private void buildMatrixForScenario(int currentScenario) {
        panelScenarioInput.removeAll();
        panelScenarioInput.validate();
        panelScenarioInput.repaint();

        table = new RTable(target.getScenarioList().size());
        table.setHeaders(target.getScenarioListNames());

        panelScenarioInput.add(table, "wrap");

        panelScenarioInput.validate();
        panelScenarioInput.repaint();
    }

    public GlobalTarget display() {
        setVisible(true);
        return target;
    }

    private class SaveScenarioList implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<String> scenarios = new ArrayList<>();

            for (String line : textAreaScenario.getText().split("\n")) {
                scenarios.add(line);
            }

            target.setScenarioListNames(scenarios);

            bestTargetNames = new ArrayList<>();

            bestTargets = target.getBestTargetsForActors();

            for (String t : bestTargets.keySet()) {
                bestTargetNames.add(t);
            }

            labelCurrentScenario.setText(
                    String.format("<html>Матриця сценарія для цілі %s</html>",
                            bestTargetNames.get(currentScenarioAndList)));
            buildMatrixForScenario(currentScenarioAndList);
        }
    }

    private class SaveScenarioMatrix implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            target.getScenarioList().get(currentScenarioAndList).setMatrix(table.getMatrix());
            currentScenarioAndList++;

            if (currentScenarioAndList >= bestTargets.size()) {
                labelScenarioTitle.setText("Всі сценарії введено");
                buttonSaveScenarioMatrix.setEnabled(false);
            } else {
                labelCurrentScenario.setText(
                        String.format("<html>Матриця сценарія для цілі %s</html>",
                                bestTargetNames.get(currentScenarioAndList)));
                buildMatrixForScenario(currentScenarioAndList);
            }
        }
    }

    private class InputHasBeenDone implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}
