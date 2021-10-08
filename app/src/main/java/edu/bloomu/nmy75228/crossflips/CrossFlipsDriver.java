/**
 * CrossFlipsDriver included in the project for Console I/O if needed.
 * One change was made in the form of an if-statement to check to see if
 * the move made by the player was valid by way of the validMove method
 */
/* package edu.bloomu.nmy75228.crossflips;

import java.util.Scanner;

/**
 * Front end for Cross Flips with console I/O.
 *
 * @author Drue Coles


public class CrossFlipsDriver {

    public static void main(String[] args) {
        System.out.println("Welcome to Cross Flips!");
        Scanner in = new Scanner(System.in);
        
        System.out.print("Enter the board dimensions: ");
        String[] dim = in.nextLine().split(" ");
        int rows = Integer.parseInt(dim[0]);
        int cols = Integer.parseInt(dim[1]);
        System.out.println();

        // Initialize the game with just three random moves for testing purposes.
        CrossFlips game = new CrossFlips(rows, cols, 3);

        System.out.println(game);

        // Continue until the game is over or the user quits.
        while (!game.allYellow()) {

            System.out.print("Enter your move, h for a hint, or q to quit. ");
            String input = in.nextLine().toLowerCase().trim();

            // Check if user wants a hint or wants to quit.
            if (input.startsWith("h")) {
                System.out.printf("%nHint: %s%n%n", game.getHint());
            } else if (input.startsWith("q")) {
                System.out.println("OK, goodbye. Try again tomorrow.");
                return;
            } else {
                // User has entered a move, which should be a row index and a column index
                // separated by a space, so extract the two numbers. Warning: if the input
                // contains any other text, parseInt will throw an exception.
                String[] move = input.split(" ");
                int r = Integer.parseInt(move[0]);
                int c = Integer.parseInt(move[1]);

                // Added this if statement to check if the move is valid
                if (game.validMove(r, c))
                    game.move(r, c);
                else
                    System.out.println("\nInvalid move, try again");

                System.out.println();
                System.out.println(game);
            }
        }
        System.out.println("YOU WIN!");
    }
} */