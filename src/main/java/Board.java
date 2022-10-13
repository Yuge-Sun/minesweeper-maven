import java.security.SecureRandom;
import java.util.Objects;


public class Board {

    int difficultyX ;
    int difficultyY;
    int bombNum;
    boolean[][] bombGrid = new boolean[difficultyY][difficultyX]; //bomb
    String [][] uiGrid = new String[difficultyY][difficultyX];
    int[][] adjacentNum = new int [difficultyY][difficultyX];
    int flagCount = 0;
    SecureRandom rand = new SecureRandom();

    public void difficultySelect (int num) {
        switch (num) {
            case 1 -> {
                this.difficultyX = 11;
                this.difficultyY = 11;
                this.bombNum = 10;
            }
            case 2 -> {
                this.difficultyX = 18;
                this.difficultyY = 18;
                this.bombNum = 40;
            }
            case 3 -> {
                this.difficultyX = 32;
                this.difficultyY = 18;
                this.bombNum = 99;
            }
            default -> System.out.println("Wrong input, try again!");
        }
    }


    public void initialiseBoard() {
        flagCount = 0;
        bombGrid = new boolean[difficultyY][difficultyX]; //bomb
        uiGrid = new String[difficultyY][difficultyX];
        adjacentNum = new int [difficultyY][difficultyX];
        for (int i = 0; i < difficultyY; i++) {
            for (int j = 0; j < difficultyX; j++) {
                this.bombGrid[i][j] = false;
                if (i == 0 || i == difficultyY-1 || j == 0 || j == difficultyX-1) {
                    this.adjacentNum[i][j] = -1;
                    this.uiGrid[i][j] = " ";
                }
                else {
                    this.adjacentNum[i][j] = 0;
                    this.uiGrid[i][j] = "◻";
                }
            }
        }
    }

    public String printLand(int i, int j) {
        if (Objects.equals(uiGrid[i][j], "F") || Objects.equals(uiGrid[i][j], "B") ){
            return ANSI_RED + uiGrid[i][j] + ANSI_RESET;
        }
        else if (Objects.equals(uiGrid[i][j], "0")) {
            return uiGrid[i][j];
        }
        else {
            return ANSI_GREEN + uiGrid[i][j] + ANSI_RESET;
        }
    }

    public void printBoard() {
        for  (int x = 1 ; x < difficultyX-1 ; x++) {
            System.out.print(ANSI_CYAN+"\t"+x+ANSI_RESET );
        }
        System.out.print("\n");
        for (int i = 1; i < difficultyY-1 ; i++){
            for (int j = 0; j < difficultyX-1 ; j++) {
                if (j == 0) {
                    System.out.print(ANSI_CYAN + i + ANSI_RESET +"\t");
                }
                else {
                    System.out.print(printLand(i,j)+"\t");
                }
                if (j == difficultyX-2 ) {
                    System.out.print("\n");
                }
            }
        }
    }

    public void fillBoard(int x, int y) {
        int numOfBombs = 0;
        while (numOfBombs < bombNum) {
            for (int i = 1; i < difficultyY-1 ; i++) {
                for (int j = 1; j < difficultyX-1 ; j++) {
                    if (i != y && j != x && !bombGrid[i][j]) {
                        int n = rand.nextInt(100);
                        if (n == 1 && numOfBombs != bombNum) {
                            bombGrid[i][j] = true;
                            numOfBombs++;
                        }
                    }
                }
            }
        }
    }

    public void showBombs () {
        for (int i = 1; i < difficultyY; i++) {
            for (int j = 1; j < difficultyX; j++) {
                if (bombGrid[i][j]) {
                    uiGrid[i][j] = "B";
                }
            }
        }
    }

    public void showAllNum () {
        for (int i = 1; i < difficultyY-1 ; i++) {
            for (int j = 1; j < difficultyX-1 ; j++) {
                uiGrid[i][j] = String.valueOf(adjacentNum[i][j]);
            }
        }
    }

    public void calcAdjacentNum () {
        for (int i = 1; i < difficultyY-1 ; i++) {
            for (int j = 1; j < difficultyX-1 ; j++) {
                for (int k = -1; k <2; k++) {
                    for (int l = -1; l <2; l++) {
                        if (!(k==0 && l==0)) {
                            if (bombGrid[i + k][j + l]) {
                                adjacentNum[i][j] += 1;
                            }
                        }
                    }
                }
            }
        }
    }

    public void flagLand (int x, int y) {
        if (Objects.equals(uiGrid[y][x], "◻")) {
            uiGrid[y][x] = "F";
            flagCount++;
        }
        else if (Objects.equals(uiGrid[y][x], "F")) {
            uiGrid[y][x] = "◻";
            flagCount--;
        }
        else {
            System.out.println("Already dug, try again \n");
        }
    }

    public boolean isBomb (int x, int y) {
        return bombGrid[y][x];
    }

    public void digLand (int x, int y) {
        if (Objects.equals(uiGrid[y][x], "◻") || Objects.equals(uiGrid[y][x], "F")) {
            uiGrid[y][x] = String.valueOf(adjacentNum[y][x]);
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
                        if (Objects.equals(uiGrid[y + k][x + l], "◻")) {
                            if (adjacentNum[y + k][x + l] == 0) {
                                uiGrid[y + k][x + l] = String.valueOf(adjacentNum[y + k][x + l]);
                                revealZeros(x + l, y + k);
                            }
                            else {
                                uiGrid[y + k][x + l] = String.valueOf(adjacentNum[y + k][x + l]);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean winCheck () {
        int correctFlag = 0;
        for (int i = 1; i < difficultyY-1; i++) {
            for (int j = 1; j < difficultyX-1; j++) {
                if (Objects.equals(uiGrid[i][j], "◻")) {
                    return false;
                }
                else if (Objects.equals(uiGrid[i][j], "F")) {
                    if (bombGrid[i][j]) {
                        correctFlag++;
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        return correctFlag == bombNum;
    }


    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_CYAN = "\u001B[36m";

}