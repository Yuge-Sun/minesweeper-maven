import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    static Scanner userInput = new Scanner(System.in);
    public static void main(String[] args) {
        Board board01 = new Board();
        while (true) {
            int userInputX;
            int userInputY;
            int maxInputX = 0;
            int maxInputY = 0;
            boolean tryAgain = true;
            boolean resetBoard = false;
            System.out.println("""
                    Welcome to Minesweeper! Please select a difficulty:
                     (1) Easy 9x9, 10 mines
                     (2) Medium 16x16, 40 mines
                     (3) Hard 30x16, 99 mines""");
            while (true) {
                int difficultyNum = getInt();
                switch (difficultyNum) {
                    case 1 -> {
                        maxInputX = 9;
                        maxInputY = 9;
                        board01.difficultySelect (difficultyNum);
                    }
                    case 2 -> {
                        maxInputX = 16;
                        maxInputY = 16;
                        board01.difficultySelect (difficultyNum);
                    }
                    case 3 -> {
                        maxInputX = 30;
                        maxInputY = 16;
                        board01.difficultySelect (difficultyNum);
                    }
                    default -> System.out.println(WRONG_INPUT);
                }
                if (difficultyNum == 1 || difficultyNum == 2 ||difficultyNum == 3 ) {
                    break;
                }
            }
            board01.initialiseBoard();
            board01.printBoard();
            while (true) {
                System.out.print("Please select a square to start digging \n"+ ENTER_X);
                userInputX = getInt();
                System.out.print(ENTER_Y);
                userInputY = getInt();
                if (userInputX<1 || userInputX > maxInputX || userInputY < 1 || userInputY >maxInputY) {
                    System.out.println(OUTSIDE_INPUT);
                }
                else {
                    break;
                }
            }
            board01.fillBoard(userInputX, userInputY);
            board01.calcAdjacentNum();
            board01.digLand(userInputX,userInputY);
            board01.revealZeros (userInputX,userInputY);
            while (true){
                if (resetBoard) {
                    break;
                }
                board01.printBoard();
                System.out.println("Select an action next: \n(F)lag  or  (D)ig");
                String input1 = getString();
                if (Objects.equals(input1, "F") || Objects.equals(input1, "f")) {
                    System.out.print("Please select a square to Flag \n" + ENTER_X);
                    userInputX = getInt();
                    System.out.print(ENTER_Y);
                    userInputY = getInt();
                    if(userInputX<1 || userInputX > maxInputX || userInputY < 1 || userInputY >maxInputY){
                        System.out.println(OUTSIDE_INPUT);
                    }
                    else {
                        board01.flagLand (userInputX, userInputY) ;
                        if (board01.winCheck()) {
                            while (true) {
                                System.out.println("You Won! \n Do you want to try again? Y/N");
                                input1 = getString();
                                if (Objects.equals(input1, "Y") || Objects.equals(input1, "y")) {
                                    resetBoard = true;
                                    break;
                                }
                                else if (Objects.equals(input1, "N") || Objects.equals(input1, "n")) {
                                    tryAgain = false;
                                    break;
                                }
                                else {
                                    System.out.println(WRONG_INPUT);
                                }
                            }
                        }
                    }
                }
                else if (Objects.equals(input1, "D") || Objects.equals(input1, "d")) {
                    System.out.print("Please select a square to dig \n"+ ENTER_X);
                    userInputX = getInt();
                    System.out.print(ENTER_Y);
                    userInputY = getInt();
                    if( userInputX < 1 || userInputX > maxInputX || userInputY < 1 || userInputY > maxInputY ){
                        System.out.println(OUTSIDE_INPUT);
                    }
                    else {
                        if (board01.isBomb(userInputX, userInputY)) {
                            board01.showAllNum();
                            board01.showBombs ();
                            board01.printBoard();
                            while (true) {
                                System.out.println("You Lost! \nDo you want to try again? Y/N");
                                input1 = getString();
                                if (Objects.equals(input1, "Y") || Objects.equals(input1, "y")) {
                                    resetBoard = true;
                                    break;
                                }
                                else if (Objects.equals(input1, "N") || Objects.equals(input1, "n")) {
                                    tryAgain = false;
                                    break;
                                }
                                else {
                                    System.out.println(WRONG_INPUT);
                                }
                            }
                        }
                        else {
                            board01.digLand(userInputX, userInputY);
                            board01.revealZeros (userInputX,userInputY);
                            if (board01.winCheck()) {
                                while (true) {
                                    System.out.println("You Won! \nDo you want to try again? Y/N");
                                    input1 = getString();
                                    if (Objects.equals(input1, "Y") || Objects.equals(input1, "y")) {
                                        resetBoard = true;
                                        break;
                                    }
                                    else if (Objects.equals(input1, "N") || Objects.equals(input1, "n")) {
                                        tryAgain = false;
                                        break;
                                    }
                                    else {
                                        System.out.println(WRONG_INPUT);
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    System.out.println(WRONG_INPUT);
                }
                if (!tryAgain) {
                    break;
                }
            }
            if (!tryAgain) {
                break;
            }
        }
    }
    private static int getInt() {
        int i;
        do {
            try {
                i = userInput.nextInt();
                if (i > 0 && i <= 30) {
                    break;
                }
                else {
                    System.out.println(WRONG_INPUT);
                }

            }
            catch (InputMismatchException e) {
                System.out.println("Input must be a number, try again!");
            }
            finally {
                userInput.nextLine();
            }
        } while (true);
        return i;
    }
    private static String getString () {
        return userInput.nextLine();
    }

    private static final String WRONG_INPUT = "Wrong input, try again!";
    private static final String OUTSIDE_INPUT = "Outside of area, try again!";
    private static final String ENTER_X = "Enter X Coordinate: ";
    private static final String ENTER_Y = "Enter Y Coordinate: ";
}
