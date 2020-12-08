package pt.ual.sdp;

import static org.junit.jupiter.api.Assertions.*;

class MainNodeTest {

    @org.junit.jupiter.api.Test
    void hash() {
        MainNode mainNode = new MainNode();
        assertEquals(2, mainNode.hash("A", 2));
        assertEquals(1, mainNode.hash("B", 2));
        assertEquals(2, mainNode.hash("C", 2));
    }
}