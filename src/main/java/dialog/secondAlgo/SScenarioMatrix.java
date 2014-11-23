package dialog.secondAlgo;

import dialog.RTable;
import dialog.StartWindow;
import model.SGlobalTarget;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SScenarioMatrix extends JDialog {
    private static MigLayout migLayout = new MigLayout("ins 0, hidemode 3", "", "[][][]");
    final static Logger log = Logger.getLogger(SScenarioMatrix.class);

    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel labelMatrixForActor;
    private JButton buttonNext;
    private JPanel panelMatrix;
    private SGlobalTarget target;
    private RTable table;

    public SScenarioMatrix(StartWindow start, SGlobalTarget target) {

        this.target = target;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(start);

        setSize(new Dimension(320, 240));

        panelMatrix.setLayout(migLayout);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonNext.addActionListener(new NextMatrixInput());

        initMatrixInputForActor(0);
    }

    private void onOK() {
        dispose();
    }

    public SGlobalTarget display() {
        setVisible(true);
        return target;
    }

    private class NextMatrixInput implements ActionListener {
        int current = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            current++;

            if (current >= target.getActors().size()) {
                labelMatrixForActor.setText("Всі матриці введено");
                buttonNext.setEnabled(false);
                return;
            }

            target.addScenarioMatrix(table.getMatrix());

            initMatrixInputForActor(current);
        }
    }

    private void initMatrixInputForActor(int current) {
        labelMatrixForActor.setText("Введіть матрицю для цілі: " + target.getTargets().get(current));

        panelMatrix.removeAll();
        panelMatrix.validate();
        panelMatrix.repaint();

        table = new RTable(target.getActors());
        panelMatrix.add(table, "wrap");

        panelMatrix.validate();
        panelMatrix.repaint();
    }
}
