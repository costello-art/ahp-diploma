package dialog;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Created by Sviat on 13.11.14.
 */
public class RTable extends JTable {
    private int size;

    private double[][] matrix;

    /**
     * Таблиця оцінок
     *
     * @param elementHeaders кількість акторів/цілей тощо
     */
    public RTable(ArrayList<String> elementHeaders) {
        super();
        size = elementHeaders.size() + 1;

        setModel(new DefaultTableModel(size, size));

        matrix = new double[size - 1][size - 1];

        reset();

        setHeaders(elementHeaders);

        getModel().addTableModelListener(new ModelListener());
    }

    private void setHeaders(ArrayList<String> headers) {
        //set top and left header
        for (int i = 1; i < size; i++) {
            getModel().setValueAt(headers.get(i - 1), i, 0);
            getModel().setValueAt(headers.get(i - 1), 0, i);
        }
    }

    public void reset() {
        for (int i = 1; i < size; i++) {
            for (int j = 1; j < size; j++) {
                if (i == j) {
                    getModel().setValueAt(1, i, j);
                } else {
                    getModel().setValueAt(j, i, j); //TODO: remove this init
                }

                getColumnModel().getColumn(i).setPreferredWidth(60);
                matrix[i - 1][j - 1] = Float.parseFloat(getModel().getValueAt(i, j).toString());
            }
        }
    }

    public double[][] getMatrix() {
        return matrix;
    }

    private class ModelListener implements TableModelListener {
        private boolean kostyl;

        @Override
        public void tableChanged(TableModelEvent e) {
            if (kostyl) {
                kostyl = false;
                return;
            }
            kostyl = true;

            int col = getSelectedColumn();
            int row = getSelectedRow();

            if (col < 1 || row < 1) {
                return;
            }

            if (row == col) {
                System.out.println("error: you've changed diag element");
                return;
            }

            if (col < 1 || row > size - 1) {
                System.out.println("error: you've edited wrong element");
                return;
            }

            float value = Float.parseFloat((String) getModel().getValueAt(row, col));

            getModel().setValueAt(1 / value, col, row);

            matrix[row-1][col-1] = value;
            matrix[col-1][row-1] = 1 / value;
        }
    }
}
