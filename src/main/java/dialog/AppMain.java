package dialog;

import model.GlobalTarget;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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
    private JPanel panelNames;
    private JButton buttonNextActor;
    private JLabel labelActorName;
    private JSpinner spinnerActorTargetCount;
    private JPanel panelTargets;
    private JButton buttonShowInputActorsMatrix;
    private JPanel panelActorMatrixInput;
    private JPanel panelActorMatrix;
    private GlobalTarget globalTarget;
    private JTable table;
    private TableChangesListener tableModel;

    public static void main(String[] args) {
        AppMain main = new AppMain();
        main.setSize(800, 600);
        main.setTitle("Диплом Горошка");
        main.setContentPane(main.panelRoot);
        main.setVisible(true);

    }

    public AppMain() throws HeadlessException {
        buttonProcessData.addActionListener(new ProcessBasicUserInput());
        buttonNextActor.addActionListener(new ProcessNextActorTargetInput());
        buttonShowInputActorsMatrix.addActionListener(new ProcessActorsMatrixInput());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelNames.setLayout(migLayout);
        panelTargets.setLayout(migLayout);
        panelActorMatrix.setLayout(migLayout);

        //onActorsNamesCountChanged(2);

        panelBasicDataInput.setVisible(false);
        panelTargetInput.setVisible(false);
        panelActorMatrixInput.setVisible(true);

        initTable();
    }

    private void initTable() {
        table = new JTable();
        DefaultTableModel dataModel = new DefaultTableModel(4, 4);
        table.setModel(dataModel);
        panelActorMatrix.add(table);

        tableModel = new TableChangesListener();
        table.getModel().addTableModelListener(tableModel);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == j) {
                    dataModel.setValueAt(1, i, j);
                } else {
                    dataModel.setValueAt(0, i, j);
                }
            }
        }
        
        dataModel.setValueAt(1, 2, 2);

        panelActorMatrix.validate();
        panelActorMatrix.repaint();
    }
    
    private void createUIComponents() {
        spinnerActorCount = new JSpinner(new SpinnerNumberModel(2, 2, 9, 1));
        spinnerActorTargetCount = new JSpinner(new SpinnerNumberModel(2, 2, 9, 1));
        spinnerActorCount.addChangeListener(new SpinnerActionAddActorNamesFileds());
        spinnerActorTargetCount.addChangeListener(new SpinnerActionAddTargetNamesFileds());
    }

    private class SpinnerActionAddActorNamesFileds implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            Integer count = (Integer) (spinnerActorCount.getValue());
            onActorsNamesCountChanged(count);
        }
    }

    private void onActorsNamesCountChanged(Integer count) {
        panelNames.removeAll();

        for (int i = 0; i < count; i++) {
            JTextField textField = new JTextField();
            JLabel label = new JLabel();
            label.setName("label_" + i);
            label.setText("Назва актора #" + i);
            textField.setName("actorName" + i);
            textField.setPreferredSize(new Dimension(100, 25));
            textField.setVisible(true);
            label.setVisible(true);
            panelNames.add(label);
            panelNames.add(textField, "wrap");
            panelNames.validate();
            panelNames.repaint();
        }
    }

    private void onTargetNamesCountChanged(Integer count) {
        panelTargets.removeAll();

        for (int i = 0; i < count; i++) {
            JTextField textField = new JTextField();
            JLabel label = new JLabel();
            label.setName("label_" + i);
            label.setText("Назва цілі #" + i);
            textField.setName("targetName" + i);
            textField.setPreferredSize(new Dimension(100, 25));
            textField.setVisible(true);
            label.setVisible(true);
            panelTargets.add(label);
            panelTargets.add(textField, "wrap");
            panelTargets.validate();
            panelTargets.repaint();
        }
    }

    private class ProcessBasicUserInput implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            onTargetNamesCountChanged(2);

            Integer count = (Integer) spinnerActorCount.getValue();
            globalTarget = new GlobalTarget(textTargetName.getText(), count);

            for (int i = 0; i < count; i++) {
                for (Component c : panelNames.getComponents()) {
                    if (c.getName().equals("actorName" + i)) {
                        JTextField nameField = (JTextField) c;
                        globalTarget.addActor(nameField.getText());

                        System.out.println("added name " + nameField.getText());
                    }
                }
            }

            panelBasicDataInput.setVisible(false);
            panelTargetInput.setVisible(true);

            onTargetInputVisible(0);
        }
    }

    private void onTargetInputVisible(int actorIndex) {
        String firstActorName = globalTarget.getActors().get(actorIndex).getName();
        labelActorName.setText(firstActorName);
    }

    private class ProcessNextActorTargetInput implements ActionListener {
        int actorCount = (Integer) spinnerActorCount.getValue();
        int currentActor = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            Integer targetCount = (Integer) spinnerActorTargetCount.getValue();

            for (int currentTarget = 0; currentTarget < targetCount; currentTarget++) {
                for (Component c : panelTargets.getComponents()) {
                    if (c.getName().equals("targetName" + currentTarget)) {
                        JTextField targetField = (JTextField) c;
                        globalTarget.getActors().get(currentActor).addTarget(targetField.getText());
                        targetField.setText("");
                        System.out.println(globalTarget.getActors().get(currentActor).getTargets().get(0).getName());
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

    private class SpinnerActionAddTargetNamesFileds implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            Integer count = (Integer) (spinnerActorTargetCount.getValue());
            onTargetNamesCountChanged(count);
        }
    }

    private class ProcessActorsMatrixInput implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class TableChangesListener implements TableModelListener {
        /**
         *  нулів не можна вводити (ніде!)
         *  діагональні елементи та нижче не можна вводити
         * */

        @Override
        public void tableChanged(TableModelEvent e) {
            int row = table.getSelectedRow();
            int col = table.getSelectedColumn();

            if (row == -1 || col == -1) {
                return;
            }

            if (row == col) {
                System.out.println("error: you've changed diag element");
                return;
            }

            float value = Float.parseFloat((String) table.getModel().getValueAt(row, col));

            table.getModel().removeTableModelListener(tableModel);
            table.getModel().setValueAt(1 / value, col, row);
            table.getModel().addTableModelListener(tableModel);

            System.out.println("table has been changed!");
            System.out.println(String.format("%d:%d", row, col));
            
        }
    }
}