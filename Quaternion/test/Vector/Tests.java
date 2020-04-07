package Vector;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Tests {
    @Test
    public void equals() {
        assertTrue(new Vector(1, 1, 1).equals(new Vector(1, 1, 1)));
        assertTrue(new Vector(0, 0, 0).equals(new Vector()));
        assertFalse(new Vector(1, 2, 3).equals(new Vector(1, 2, 4)));
    }

    @Test
    public void toStringTest() {
        assertEquals("1.0i+1.0j+1.0k", new Vector(1, 1, 1).toString());
        assertEquals("-1.1i-1000.0j+12.0k", new Vector(-1.1, -1000, 12).toString());
        assertEquals("0.0i+0.0j+0.0k", new Vector(0, 0, 0).toString());
        assertEquals("0.0i+0.0j+0.0k", new Vector().toString());
    }
}
