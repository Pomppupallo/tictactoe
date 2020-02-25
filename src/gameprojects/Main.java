package gameprojects;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String player = "X";
        String winner = "";
        boolean notFinished = true;
        String[][] gameBoard = createEmptyBoard();
        displayField(gameBoard);

        // Game loop
        while (notFinished) {
            getMoveFromInput(scanner, gameBoard, player);
            displayField(gameBoard);
            winner = checkStatus(gameBoard);
            switch (winner) {
                case "X wins":
                    winner = "X";
                    notFinished = false;
                    break;
                case "O wins":
                    winner = "O";
                    notFinished = false;
                    break;
                case "Draw":
                    notFinished = false;
                    winner = "draw";
                    break;
                case "Game not finished":
                    if (player.equals("X")) {
                        player = "O";
                    } else {
                        player = "X";
                    }
                    break;
            }
        }

        // Print game results
        if (winner.equals("draw")) {
            System.out.println("Draw");
        } else {
            System.out.println(winner + " wins");
        }
    }

    public static String[][] createEmptyBoard() {
        String[][] board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = " ";
            }
        }
        return board;
    }

    public static String checkStatus(String[][] symbols) {
        boolean boardFull = true; // Assume we have all X or O
        boolean xWins = false;
        boolean oWins = false;
        int counterX = 0;
        int counterO = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (symbols[i][j].equals(" ")) {
                    boardFull = false;
                }
                if (symbols[i][j].equals("X")) {
                    counterX++;
                }
                if (symbols[i][j].equals("O")) {
                    counterO++;
                }
            }
        }

        // Rows
        for (int i = 0; i < 3; i++) {
            if (symbols[i][0].equals(symbols[i][1]) && symbols[i][0].equals(symbols[i][2])) {
                if (symbols[i][0].equals("X")) {
                    xWins = true;
                }
                if (symbols[i][0].equals("O")) {
                    oWins = true;
                }
            }
        }

        // Cols
        for (int i = 0; i < 3; i++) {
            if (symbols[0][i].equals(symbols[1][i]) && symbols[0][i].equals(symbols[2][i])) {
                if (symbols[0][i].equals("X")) {
                    xWins = true;
                }
                if (symbols[0][i].equals("O")) {
                    oWins = true;
                }
            }
        }

        //Diagonal
        if (symbols[0][0].equals(symbols[1][1]) && symbols[0][0].equals(symbols[2][2])) {
            if (symbols[0][0].equals("X")) {
                xWins = true;
            }
            if (symbols[0][0].equals("O")) {
                oWins = true;
            }
        }

        // Other diagonal
        if (symbols[2][0].equals(symbols[1][1]) && symbols[2][0].equals(symbols[0][2])) {
            if (symbols[2][0].equals("X")) {
                xWins = true;
            }
            if (symbols[2][0].equals("O")) {
                oWins = true;
            }
        }

        // Check correct state of the game
        int difference = Math.abs(counterX - counterO);
        if (difference >= 2) {
            return "Impossible";
        }

        if (xWins && oWins) {
            return "Impossible";
        }

        if (xWins) {
            return "X wins";
        }

        if (oWins) {
            return "O wins";
        }

        if (!xWins && !oWins && boardFull) {
            return "Draw";
        }

        if (!xWins && !oWins && !boardFull) {
            return "Game not finished";
        }

        return "Missing condition";
    }

    public static void getMoveFromInput(Scanner scanner, String[][] gameBoard, String player) {

        while (true) {
            System.out.print("Enter the coordinates: ");
            if (!scanner.hasNextLine()) {
                break;
            }
            String readInput = scanner.nextLine();
            if (readInput.equals("")) {
                continue;
            }
            String[] input = readInput.split(" ");
            String regex = "\\d+";

            if (input.length <= 1) {
                System.out.println("Give two inputs!");
                continue;
            }

            if (!input[0].matches(regex) && !input[1].matches(regex)) {
                System.out.println("You should enter numbers!");
                continue;
            }

            int y = Integer.valueOf(input[0]);
            int x = Integer.valueOf(input[1]);

            if (y < 1 || y > 3 || x < 1 || x > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            int yLoc = y - 1;
            int xLoc = 3 - x;

            if (!gameBoard[xLoc][yLoc].equals(" ")) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            } else {
                gameBoard[xLoc][yLoc] = player;
                break;
            }
        }
    }

    public static String[][] readInput(Scanner scanner) {
        char[] symbols = new char[9];
        String[][] playField = new String[3][3];
        while (true) {
            System.out.println("Enter cells: ");
            String input = scanner.nextLine();
            input = input.toUpperCase();
            String[] letters = input.split("");

            // Check length of the input - has to be 9
            if (letters.length != 9) {
                System.out.println("Wrong input - give 9 characters");
                continue;
            }

            // Check inputs - only allow X or O or _
            for (int i = 0; i < 9; i++) {
                if (letters[i].equals("X") || letters[i].equals("O") || letters[i].equals("_")) {
                    symbols[i] = input.charAt(i);
                } else {
                    System.out.println("Wrong input - Give only (X - O - _) characters");
                }
            }

            int index = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    playField[i][j] = letters[index];
                    index++;
                }
            }

            // All is good - can break from loop
            break;
        }
        return playField;
    }

    public static void displayField(String[][] symbols) {
        int index = 0;
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.println("| " + symbols[i][0] + " " + symbols[i][1] + " " + symbols[i][2] + " |");
        }
        System.out.println("---------");
    }
}


