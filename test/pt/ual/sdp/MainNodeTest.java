package pt.ual.sdp;

import org.junit.jupiter.api.Test;
import pt.ual.sdp.views.MainNode;

import static org.junit.jupiter.api.Assertions.*;

class MainNodeTest {

    @Test
    void shouldHaveAnHashValueForDifferentKeys() {
        MainNode mainNode = new MainNode();
        assertEquals(1, mainNode.hash("A", 2));
        assertEquals(0, mainNode.hash("B", 2));
        assertEquals(1, mainNode.hash("C", 2));
    }
}