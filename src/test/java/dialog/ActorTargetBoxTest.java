package dialog;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ActorTargetBoxTest {

    private ActorTargetBox atb;

    @Before
    public void setUp() throws Exception {
        atb = new ActorTargetBox(0);
    }

    @Test
    public void testGetActorName() throws Exception {
        String name = "name";
        atb.setActorName(name);

        assertEquals(name, atb.getActorName());
    }

    @Test
    public void testGetTargetCount() throws Exception {
        int count = 5;
        atb.setTargetsCount(count);

        assertEquals(count, atb.getTargetCount());
    }

    @Test
    public void testGetTargets() throws Exception {
        String targets = "tar0, tar1, tar2";
        atb.setTargetsText(targets);

        ArrayList<String> expected = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            expected.add("tar" + i);
        }

        assertEquals(expected, atb.getTargets());
    }
}