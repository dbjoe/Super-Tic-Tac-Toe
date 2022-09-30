package project2;

import org.junit.Test;
import static org.junit.Assert.*;

public class SuperTicTacToeGameTest {

    @Test
    public void testConstructor1() {
        SuperTicTacToeGame game = new SuperTicTacToeGame(3, 3, true);
        assertEquals("EMPTY", game.getCell(0, 0));
    }

    @Test
    public void testConstructor2() {
        SuperTicTacToeGame game = new SuperTicTacToeGame(3, 3, true);
        assertEquals("EMPTY", game.getCell(0, 1));
    }

    @Test
    public void testConstructor3() {
        SuperTicTacToeGame game = new SuperTicTacToeGame(3, 3, true);
        assertEquals("EMPTY", game.getCell(0, 2));
    }

    @Test
    public void testConstructor4() {
        SuperTicTacToeGame game = new SuperTicTacToeGame(3, 3, true);
        assertEquals("EMPTY", game.getCell(1, 0));
    }

    @Test
    public void testConstructor5() {
        SuperTicTacToeGame game = new SuperTicTacToeGame(3, 3, true);
        assertEquals("EMPTY", game.getCell(1, 1));
    }

    @Test
    public void testConstructor6() {
        SuperTicTacToeGame game = new SuperTicTacToeGame(3, 3, true);
        assertEquals("EMPTY", game.getCell(1, 2));
    }

    @Test
    public void testConstructor7() {
        SuperTicTacToeGame game = new SuperTicTacToeGame(3, 3, true);
        assertEquals("EMPTY", game.getCell(2, 0));
    }

    @Test
    public void testConstructor8() {
        SuperTicTacToeGame game = new SuperTicTacToeGame(3, 3, true);
        assertEquals("EMPTY", game.getCell(2, 1));
    }

    @Test
    public void testConstructor9() {
        SuperTicTacToeGame game = new SuperTicTacToeGame(3, 3, true);
        assertEquals("EMPTY", game.getCell(2, 2));
    }
}
