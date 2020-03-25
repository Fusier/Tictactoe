import java.util.*;

/**
 * A scalable Tic tac toe game. 
 *
 * One player tictactoe game versus an AI. This version of the game is played in command prompt. 
 *
 * @author Samu Willman
 * @version 1.0
 */
public class Tictactoe {
    
    /**
     * User input to declare the size of the gameboard.
     * 
     */
    static int size = 0;
    /**
     * A scanner which is needed in order to take user input.
     *
     */
    static Scanner input = new Scanner(System.in);
    /**
     * 2D array and the gameboard.
     *
     */
    static char [][] gameboard;
    /**
     * A character which player uses, computer will use O.
     *
     */
    static char playerChar = 'X';
    /**
     * Indicates the row in which user will place his marker.
     *
     */
    static int row = 0;
    /**
     * Indicates the column in which user will place his marker.
     *
     */
    static int column = 0;
    /**
     * Indicates the row in which computer will place his marker.
     *
     */
    static int computerplacerows = 0;
    /**
     * Indicates the column in which computer will place his marker.
     *
     */
    static int computerplacecolumns = 0;
    /**
     * isChar is shorter version of isThereChar();
     *
     */
    static boolean isChar;
    /**
     * This value is used in wincheckrows(), winCheckcolumns and winCheckdiagonal to help with checking 3 or more X's in a row
     *
     */
    static int winConditionX = 0;
    /**
     * This value is used in wincheckrows(), winCheckcolumns and winCheckdiagonal to help with checking 3 or more O's in a row
     *
     */
    static int winConditionO = 0;
    /**
     * Variable that user gives. This value changes how many X's or O's there has to be in a row
     *
     */
    static int neededToWin = 0;
    /**
     * This variable counts rounds, if enough rounds have been played it will be a tie.
     *
     */
    static int roundcounter = 0;
    /**
     * Acts as a counter for diagonal win method.
     *
     */
    static int counter = 0;
    /**
     * Main method. Initializes the game and has a while loop which keeps the game running until someone wins or it's a tie.
     * @param args Command line parameters.
     */
    public static void main(String[] args) {
        setBoard();
        printBoard();
        System.out.println();
     
        while (true) {
            System.out.println("Player X turn");
            System.out.println();
            playerPlace();
            printBoard(); 
            if (winCheckrows() == true || winCheckcolumns() == true || winCheckdiagonal() == true) {
            System.out.println("X WON!!");
            System.exit(1);
            }
            roundcounter++;
            isBoardFull();
            turn();
            System.out.println();

            System.out.println("Computer turn");
            System.out.println();
            computerMove();
            printBoard();
            if (winCheckrows() == true || winCheckcolumns() == true || winCheckdiagonal() == true) {
            System.out.println("O WON!!");
            System.exit(1);
            }
            roundcounter++;
            isBoardFull();
            turn();
            System.out.println();
        }
    }

    /**
     * Asks user the size of the gameboard and initializes the gameboard.
     *
     */
    public static void setBoard() {
        do {
            System.out.println("Please give board size (min 3x3, max 8x8) (Please note: Give it in the form of a single number!)");
            try { 
                size = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("I only accept numbers!");
                continue;
            }
        } while (size < 3 || size > 8);
            do {
                System.out.println("How many marks in a row are required in order to win the game?");
                try { 
                    neededToWin = Integer.parseInt(input.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("I only accept numbers!");
                    continue;
                }
            } while (neededToWin < 3 || neededToWin > size);
        System.out.println();
        gameboard = new char [size][size];
    }
    
    /**
     * Prints gameboard and creates coordinate numbers which makes placing marks easier.
     *
     */
    public static void printBoard() {
            System.out.print("   ");
            for(int i=0; i<gameboard.length; i++) {
                System.out.print(i+1 + " ");
            }
            System.out.println();
            for (int i = 0; i < gameboard.length; i++) {
                if(i < 9) {
                    System.out.print(" " + (i+1) + " ");
                } else {
                    System.out.print(i+1 + " ");
                }
                for (int j = 0;j < gameboard[i].length; j++) {
                    if(gameboard[i][j] != 'X' && gameboard[i][j] != 'O') {
                        System.out.print("- ");
                    } else {
                        System.out.print(gameboard[i][j] + " ");
                    }
                }
                System.out.println();
            }
    }
    
    /**
     * Asks the user two numbers which will be used for placing a mark. 
     * Method also leaves the mark after checking there is no other character in the way.
     */
    public static void playerPlace() {
        isChar = true;
        while(isChar) {
            do {
                System.out.println("Please give a row where you want to place. (Example: 3)");
                try { 
                    row = Integer.parseInt(input.nextLine().trim());                         
                } catch (NumberFormatException e) {
                    System.out.println("I only accept numbers!");
                }
            } while (row < 1 || row > gameboard[0].length);
            do {
                System.out.println("Please give a column where you want to place. (Example: 3)");
                try { 
                    column = Integer.parseInt(input.nextLine().trim());                         
                } catch (NumberFormatException e) {
                    System.out.println("I only accept numbers!");
                }
            } while (column < 1 || column > gameboard[0].length);
            isChar = isThereChar();
            if(isChar == false) {
                gameboard[row-1][column-1] = playerChar;
                break;
            }
        }
        System.out.println();
    } 
    
    /**
     * This method is used to check if either user or computer has already placed a marker on the wanted position.
     * @return if there is a character or not
     */
    public static boolean isThereChar() {
        if(gameboard[row-1][column-1] != 'X' && gameboard[row-1][column-1] != 'O') {
            return false;
        } else {
            if(playerChar == 'X') {
                System.out.println("That spot is occupied already");
            }
            return true; 
        }
    }

    /**
     * Computer takes a random number between 1 and the size of the gameboard. 
     * After picking a number it checks if there is a character in its way before it can place its own character,
     */
    public static void computerMove() {
        isChar = true;
        while(isChar) {
            row = (int) (Math.random() * gameboard.length) + 1;
            column = (int) (Math.random() * gameboard.length) + 1;
            isChar = isThereChar();
            if(isChar == false) {
                gameboard[row-1][column-1] = playerChar;
                break;
            }
        }
    }
    
    /**
     *Switches the user's and computer's character back and forth which is used for altering between user's and computer's turns.
     *
     */
    public static void turn() {
        if (playerChar == 'X') {
            playerChar = 'O';
            } else {
            playerChar = 'X';
            }
        }
    
    /**
     * Checks gameboard for vertical wins.
     * @return true if the game is won
    */
    public static boolean winCheckrows()  {
        for(int i = 0; i < gameboard.length; i++){
            winConditionO = 0;
            winConditionX = 0;
            for(int j = 0; j < gameboard[i].length; j++){
                if(gameboard[i][j] == 'X') {
                    winConditionX++;
            }  else {
                winConditionX = 0;
            }
            if (gameboard[i][j] == 'O') {
                winConditionO++;
            } else {
                winConditionO = 0;
            }
            if (winConditionO == neededToWin) {
                return true;
            }
            if (winConditionX == neededToWin) {
               return true;
        }
    }
}
return false;
}

    /**
     * Checks gameboard for horizontal wins.
     * @return true if the game is won
    */
    public static boolean winCheckcolumns()  {
        for(int i = 0; i < gameboard.length; i++){
            winConditionO = 0;
            winConditionX = 0;
            for(int j = 0; j < gameboard[i].length; j++){
                if(gameboard[j][i] == 'X') {
                    winConditionX++;
            }  else {
                winConditionX = 0;
            }
            if (gameboard[j][i] == 'O') {
                winConditionO++;
            } else {
                winConditionO = 0;
            }
            if (winConditionO == neededToWin) {
                return true;
            }
            if (winConditionX == neededToWin) {
               return true;
        }
    }
}
return false;
}

    /**
     * Checks gameboard for diagonal wins.
     * @return true If the game is won
    */
public static boolean winCheckdiagonal()  { 
    for(int i = 0; i < gameboard.length; i++){
        for(int j = 0; j < gameboard[i].length; j++){
                if(gameboard[i][j] == playerChar) {
                counter=1;
                for(int a=1; a <=neededToWin && i+a<gameboard.length && j + a< gameboard[i].length;a++) { 
                    if(playerChar == gameboard[i+a][j+a]) {
                        counter++;
                        if(counter >= neededToWin) {
                            return true;
                        }
                    } else {
                        break;
                    }
                }
                counter = 1;
                for(int a = 1; a<=neededToWin && i+a <gameboard.length && j-a>=0; a++) {
                    if(playerChar == gameboard[i+a][j-a]) {
                    counter++;
                    if(counter >= neededToWin) {
                        return true;
                    }
                } else {
                    break;
                }
            }
        }
    }

}
return false;
}

    /**         
     * Checks gameboard for a draw.
     * 
    */
    public static void isBoardFull () {
        if(roundcounter == (Math.pow(size, 2) - 1)) {
            System.out.println("It's a tie!");
            System.exit(1);
        }
    }
}