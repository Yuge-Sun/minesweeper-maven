import java.util.Objects;
import java.util.Random;


public class board {
    boolean[][] bombGrid = new boolean[11][11]; //bomb

    String [][] UIGrid = new String[11][11];

    int[][] adjacentNum = new int [11][11];

    int flagCount = 0;

    public void initialiseBoard() {
        flagCount = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                this.bombGrid[i][j] = false;
                if (i == 0 || i == 10 || j == 0 || j == 10) {
                    this.adjacentNum[i][j] = -1;
                    this.UIGrid[i][j] = " ";
                }
                else {
                    this.adjacentNum[i][j] = 0;
                    this.UIGrid[i][j] = "◻";
                }
            }
        }
    }

    public String printLand(int i, int j) {
        if (Objects.equals(UIGrid[i][j], "F") || Objects.equals(UIGrid[i][j], "B") ){
            return colours.ANSI_RED + UIGrid[i][j] + colours.ANSI_RESET;
        }
        else if (Objects.equals(UIGrid[i][j], "0")) {
            return UIGrid[i][j];
        }
        else {
            return colours.ANSI_GREEN + UIGrid[i][j] + colours.ANSI_RESET;
        }
    }

    public void printBoard() {
        for (int i = 0; i < 10; i++){
            if (i==0){
                System.out.print(colours.ANSI_CYAN+"\t1\t2\t3\t4\t5\t6\t7\t8\t9\n"+colours.ANSI_RESET );
            }
            else{
                for (int j = 0; j < 10; j++) {
                    if (j == 0) {
                        System.out.print(colours.ANSI_CYAN + i + colours.ANSI_RESET +"\t");
                    }
                    else {
                        System.out.print(printLand(i,j)+"\t");
                    }
                    if (j == 9) {
                        System.out.print("\n");
                    }
                }
            }
        }
    }

    public void fillBoard(int x, int y) {
        int numOfBombs = 0;
        while (numOfBombs <10) {
           for (int i = 1; i < 10; i++) {
                for (int j = 1; j < 10; j++) {
                    if (i != y && j != x && bombGrid[i][j] != true) {
                        Random rand = new Random();
                        int n = rand.nextInt(100);
                        if (n == 1 && numOfBombs != 10) {
                            bombGrid[i][j] = true;
                            numOfBombs++;
                        }
                    }
                }
            }
        }
    }

    public void showBombs () {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                if (bombGrid[i][j] == true) {
                    UIGrid[i][j] = "B";
                }
            }
        }
    }

    public void showAllNum () {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                UIGrid[i][j] = String.valueOf(adjacentNum[i][j]);
            }
        }
    }

    public void calcAdjacentNum () {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                for (int k = -1; k <2; k++) {
                    for (int l = -1; l <2; l++) {
                        if (k==0 && l==0) {
                            ;
                        }
                        else {
                            if (bombGrid[i+k][j+l] == true) {
                                adjacentNum[i][j] += 1;
                            }
                        }
                    }
                }
            }
        }
    }

    public void flagLand (int x, int y) {
        if (Objects.equals(UIGrid[y][x], "◻")) {
            UIGrid[y][x] = "F";
            flagCount++;
        }
        else if (Objects.equals(UIGrid[y][x], "F")) {
            UIGrid[y][x] = "◻";
            flagCount--;
        }
        else {
            System.out.println("Already dug, try again \n");
        }
    }

    public boolean isBomb (int x, int y) {
        if (bombGrid[y][x]== true) {
            return true;
        }
        else {
            return false;
        }
    }

    public void digLand (int x, int y) {
        if (UIGrid[y][x] == "◻" || UIGrid[y][x] == "F") {
            UIGrid[y][x] = String.valueOf(adjacentNum[y][x]);
        }
        else {
            System.out.println("Already dug, try again \n");
        }
    }

    public void revealZeros (int x, int y) {
        if (adjacentNum[y][x] == 0) {
            for (int k = -1; k < 2; k++) {
                for (int l = -1; l < 2; l++) {
                    if (!(k == 0 && l == 0)) {
                        if (UIGrid[y + k][x + l] == "◻") {
                            if (adjacentNum[y + k][x + l] == 0) {
                                UIGrid[y + k][x + l] = String.valueOf(adjacentNum[y + k][x + l]);
                                revealZeros(x + l, y + k);
                            }
                            else {
                                UIGrid[y + k][x + l] = String.valueOf(adjacentNum[y + k][x + l]);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean winCheck () {
        int correctFlag = 0;
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                if (UIGrid[i][j] == "◻") {
                    return false;
                }
                else if (UIGrid[i][j] == "F") {
                    if (bombGrid[i][j] == true) {
                        correctFlag++;
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        if (correctFlag == 10){
            return true;
        }
        else {
            return false;
        }
    }

    }