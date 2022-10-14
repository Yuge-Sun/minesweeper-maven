import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class BoardTest {
    Board testBoard = new Board();

    @Test
    public void testDifficultySelect() {
        testBoard.difficultySelect (1);
        Assertions.assertEquals(11, testBoard.getBoardSizeX(), "Wrong Board size X");
        Assertions.assertEquals(11, testBoard.getBoardSizeY(), "Wrong Board size Y");
        Assertions.assertEquals(10, testBoard.getBombNum(), "Wrong Bomb Number");
    }

    @Test
    public void testInitialiseBoard() {
        testBoard.difficultySelect (1);
        testBoard.initialiseBoard();
        for (int i = 0; i < testBoard.getBoardSizeY(); i++) {
            for (int j = 0; j < testBoard.getBoardSizeX(); j++) {
                Assertions.assertFalse(testBoard.getBombGrid(i,j));
                if (i == 0 || i == testBoard.getBoardSizeY() -1 || j == 0 || j == testBoard.getBoardSizeX() -1) {
                    Assertions.assertEquals(-1, testBoard.getAdjacentNum(i,j), "Wrong initial value of AdjacentNum");
                    Assertions.assertEquals(" ",testBoard.getUiGrid(i,j),"Wrong initial value of UiGrid");
                }
                else {
                    Assertions.assertEquals(0, testBoard.getAdjacentNum(i,j), "Wrong initial value of AdjacentNum");
                    Assertions.assertEquals("◻",testBoard.getUiGrid(i,j),"Wrong initial value of UiGrid");
                }
            }
        }
    }

    @Test
    public void testPrintLand() {
        testBoard.difficultySelect (1);
        testBoard.initialiseBoard();
        for (int i = 0; i < testBoard.getBoardSizeY(); i++) {
            for (int j = 0; j < testBoard.getBoardSizeX(); j++) {
                if (i == 0 || i == testBoard.getBoardSizeY() -1 || j == 0 || j == testBoard.getBoardSizeX() -1) {
                    Assertions.assertEquals("\u001B[32m \u001B[0m", testBoard.printLand(i, j), "Wrong printLand");
                }
                else {
                    Assertions.assertEquals("\u001B[32m◻\u001B[0m", testBoard.printLand(i, j), "Wrong printLand");
                }
            }
        }
    }

    @Test
    public void testFillBoard() {
        testBoard.difficultySelect (1);
        testBoard.initialiseBoard();
        testBoard.fillBoard(1, 1);
        int a=0;
        for (int i = 0; i < testBoard.getBoardSizeY(); i++) {
            for (int j = 0; j < testBoard.getBoardSizeX(); j++) {
                if (testBoard.getBombGrid(i,j)==true){
                    a++;
                }
            }
        }
        Assertions.assertTrue(a==10,"Number of bomb set is not equal to 10");
    }

    @Test
    public void testShowBombs() {
        testBoard.difficultySelect (1);
        testBoard.initialiseBoard();
        testBoard.testFillBoard1(1,1);
        testBoard.showBombs();
        for (int i = 1; i < testBoard.getBoardSizeY(); i++) {
            for (int j = 1; j < testBoard.getBoardSizeX(); j++) {
                if (testBoard.getBombGrid(i,j)==true){
                    Assertions.assertTrue(testBoard.getUiGrid(i,j)=="B","Bomb not showing in UIGrid");
                }
            }
        }
    }

    @Test
    public void calcAdjacentNum() {
        testBoard.difficultySelect (1);
        testBoard.initialiseBoard();
        testBoard.testFillBoard1(1,1);
        testBoard.calcAdjacentNum();
        testBoard.showAllNum();
        testBoard.showBombs();
        //testBoard.printBoard();
    }


    @Test
    public void testShowAllNum() {
        testBoard.difficultySelect (1);
        testBoard.initialiseBoard();
        testBoard.testFillBoard1(1,1);
        testBoard.calcAdjacentNum();
        testBoard.showAllNum();

    }



    @Test
    void flagLand() {
    }

    @Test
    void isBomb() {
    }

    @Test
    void digLand() {
    }

    @Test
    void revealZeros() {
    }

    @Test
    void winCheck() {
    }
}