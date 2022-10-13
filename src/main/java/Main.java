import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        board board01 = new board();
        Scanner userinput = new Scanner(System.in);
        while (true) {
            int uX = 0;
            int uY = 0;
            boolean tryAgain = true;
            boolean resetBoard = false;
            board01.initialiseBoard();
            board01.printBoard();
            System.out.println("Welcome to Minesweeper! \nPlease select a square to start digging \n Enter X Coordinate: ");
            uX = userinput.nextInt();
            userinput.nextLine();
            System.out.println("Enter Y Coordinate: ");
            uY = userinput.nextInt();
            userinput.nextLine();
            board01.fillBoard(uX, uY);
            board01.calcAdjacentNum();
            board01.digLand(uX,uY);
            board01.revealZeros (uX,uY);
            while (true){
                if (resetBoard == true) {
                    break;
                }
                board01.printBoard();
                System.out.println("Select an action next: \n(F)lag  or  (D)ig");
                String input1 = userinput.nextLine();
                if (Objects.equals(input1, "F") || Objects.equals(input1, "f")) {
                    System.out.println("Please select a square to Flag \n Enter X Coordinate: ");
                    uX = userinput.nextInt();
                    userinput.nextLine();
                    System.out.println("Enter Y Coordinate: ");
                    uY = userinput.nextInt();
                    userinput.nextLine();
                    if(uX<1 || uX > 9 || uY < 1 || uY >9){
                        System.out.println("Outside of area, try again");
                    }
                    else {
                        board01.flagLand (uX, uY) ;
                        if (board01.winCheck() == true) {
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
                                    System.out.println("Wrong input, try again!");
                                }
                            }
                        }
                    }
                }
                else if (Objects.equals(input1, "D") || Objects.equals(input1, "d")) {
                    System.out.println("Please select a square to dig \n Enter X Coordinate: ");
                    uX = userinput.nextInt();
                    userinput.nextLine();
                    System.out.println("Enter Y Coordinate: ");
                    uY = userinput.nextInt();
                    userinput.nextLine();
                    if( uX < 1 || uX > 9 || uY < 1 || uY >9 ){
                        System.out.println("Outside of area, try again");
                    }
                    else {
                        if (board01.isBomb(uX, uY) == true) {
                            board01.showAllNum();
                            board01.showBombs ();
                            board01.printBoard();
                            while (true) {
                                System.out.println("You Lost! \n Do you want to try again? Y/N");
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
                                    System.out.println("Wrong input, try again!");
                                }
                            }
                        }
                        else {
                            board01.digLand(uX, uY);
                            board01.revealZeros (uX,uY);
                            if (board01.winCheck() == true) {
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
                                        System.out.println("Wrong input, try again!");
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    System.out.println("Wrong input, try again\n");
                }
                if (tryAgain == false) {
                    break;
                }
            }
            if (tryAgain == false) {
                break;
            }
        }
    }
}