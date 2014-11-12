package dialog;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Sviat on 13.11.14.
 */
public class ActorTargetBox extends JPanel {
    private JLabel labelForActor;
    private JLabel labelForTargets;
    private JLabel labelForTargetCount;

    private JTextField textActorName;
    private JTextField textTargets;
    private JSpinner targetCount;

    private int index;

    public ActorTargetBox(int currentIndex) {
        super();
        this.index = currentIndex;

        setName("actorTargetBox" + currentIndex);
        setLayout(new FlowLayout());

        labelForActor = new JLabel();
        labelForTargetCount = new JLabel();
        labelForTargets = new JLabel();

        textActorName = new JTextField();
        textTargets = new JTextField();

        textActorName.setPreferredSize(new Dimension(150, 28));
        textTargets.setPreferredSize(new Dimension(400, 28));

        targetCount = new JSpinner(new SpinnerNumberModel(2, 2, 9, 1));

        setLabelText();

        add(labelForActor);
        add(textActorName);
        add(labelForTargetCount);
        add(targetCount);
        add(labelForTargets);
        add(textTargets);
    }

    private void setLabelText() {
        labelForActor.setText("Актор #" + index);
        labelForTargetCount.setText("цілей");
        labelForTargets.setText("назви цілей");
    }

    public String getActorName() {
        return textActorName.getText();
    }

    public int getTargetCount() {
        return (int) targetCount.getValue();
    }

    public ArrayList<String> getTargets() {
        ArrayList<String> targetList = new ArrayList<>();

        String[] targets = textTargets.getText().split(",");

        for (String target : targets) {
            targetList.add(target);
        }

        return targetList;
    }
}