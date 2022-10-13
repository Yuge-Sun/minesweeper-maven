import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        board board01 = new board();
        Scanner userinput = new Scanner(System.in);
        while (true) {
            int uX;
            int uY;
            int maxInputX = 0;
            int maxInputY = 0;
            boolean tryAgain = true;
            boolean resetBoard = false;
            System.out.println("Welcome to Minesweeper! Please select a difficulty:\n (1) Easy 9x9, 10 mines\n (2) Medium 16x16, 40 mines\n (3) Hard 30x16, 99 mines");
            int difficultyNum = userinput.nextInt();
            userinput.nextLine();
            board01.difficultySelect (difficultyNum);
            while (true) {
                switch (difficultyNum) {
                    case 1 -> {
                        maxInputX = 9;
                        maxInputY = 9;
                    }
                    case 2 -> {
                        maxInputX = 16;
                        maxInputY = 16;
                    }
                    case 3 -> {
                        maxInputX = 30;
                        maxInputY = 16;
                    }
                    default -> System.out.println(wrongInput);
                }
                if (difficultyNum == 1 || difficultyNum == 2 ||difficultyNum == 3 ) {
                    break;
                }
            }

            board01.initialiseBoard();
            board01.printBoard();
            System.out.print("Please select a square to start digging \n"+enterX);
            uX = userinput.nextInt();
            userinput.nextLine();
            System.out.print(enterY);
            uY = userinput.nextInt();
            userinput.nextLine();
            board01.fillBoard(uX, uY);
            board01.calcAdjacentNum();
            board01.digLand(uX,uY);
            board01.revealZeros (uX,uY);
            while (true){
                if (resetBoard) {
                    break;
                }
                board01.printBoard();
                System.out.println("Select an action next: \n(F)lag  or  (D)ig");
                String input1 = userinput.nextLine();
                if (Objects.equals(input1, "F") || Objects.equals(input1, "f")) {
                    System.out.print("Please select a square to Flag \n" + enterX);
                    uX = userinput.nextInt();
                    userinput.nextLine();
                    System.out.print(enterY);
                    uY = userinput.nextInt();
                    userinput.nextLine();
                    if(uX<1 || uX > maxInputX || uY < 1 || uY >maxInputY){
                        System.out.println(outsideInput);
                    }
                    else {
                        board01.flagLand (uX, uY) ;
                        if (board01.winCheck()) {
                            while (true) {
                                System.out.println("You Won! \n Do you want to try again? Y/N");
                                input1 = userinput.nextLine();
                                if (Objects.equals(input1, "Y") || Objects.equals(input1, "y")) {
                                    tryAgain = true;
                                    resetBoard = true;
                                    break;
                                }
                                else if (Objects.equals(input1, "N") || Objects.equals(input1, "n")) {
                                    tryAgain = false;
                                    break;
                                }
                                else {
                                    System.out.println(wrongInput);
                                }
                            }
                        }
                    }
                }
                else if (Objects.equals(input1, "D") || Objects.equals(input1, "d")) {
                    System.out.print("Please select a square to dig \n"+enterX);
                    uX = userinput.nextInt();
                    userinput.nextLine();
                    System.out.print(enterY);
                    uY = userinput.nextInt();
                    userinput.nextLine();
                    if( uX < 1 || uX > maxInputX || uY < 1 || uY > maxInputY ){
                        System.out.println(outsideInput);
                    }
                    else {
                        if (board01.isBomb(uX, uY)) {
                            board01.showAllNum();
                            board01.showBombs ();
                            board01.printBoard();
                            while (true) {
                                System.out.println("You Lost! \nDo you want to try again? Y/N");
                                input1 = userinput.nextLine();
                                if (Objects.equals(input1, "Y") || Objects.equals(input1, "y")) {
                                    tryAgain = true;
                                    resetBoard = true;
                                    break;
                                }
                                else if (Objects.equals(input1, "N") || Objects.equals(input1, "n")) {
                                    tryAgain = false;
                                    break;
                                }
                                else {
                                    System.out.println(wrongInput);
                                }
                            }
                        }
                        else {
                            board01.digLand(uX, uY);
                            board01.revealZeros (uX,uY);
                            if (board01.winCheck()) {
                                while (true) {
                                    System.out.println("You Won! \nDo you want to try again? Y/N");
                                    input1 = userinput.nextLine();
                                    if (Objects.equals(input1, "Y") || Objects.equals(input1, "y")) {
                                        tryAgain = true;
                                        resetBoard = true;
                                        break;
                                    }
                                    else if (Objects.equals(input1, "N") || Objects.equals(input1, "n")) {
                                        tryAgain = false;
                                        break;
                                    }
                                    else {
                                        System.out.println(wrongInput);
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    System.out.println(wrongInput);
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

    private static final String wrongInput = "Wrong input, try again!";
    private static final String outsideInput = "Outside of area, try again!";
    private static final String enterX = "Enter X Coordinate: ";
    private static final String enterY = "Enter Y Coordinate: ";


}
