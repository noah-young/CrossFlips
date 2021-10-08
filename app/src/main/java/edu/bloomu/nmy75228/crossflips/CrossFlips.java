package edu.bloomu.nmy75228.crossflips;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Backend for CrossFlips game.
 * The three constructors give the option to initialize the game with the same or different values
 * for the rows and columns. It also gives the option to initialize the game with a random or
 * specified number of starting moves.
 *
 * @author Noah Young
 * @version 22 Feb 2020
 */
public class CrossFlips {
    private int rows;
    private int cols;
    int moves;
    List<String> points = new ArrayList<>();
    boolean addPoint;
    public int[][] crossFlips;

    /**
     * Basic constructor where rows and columns are both specified
     * by the same integer n and the number of moves are randomly
     * determined.
     * @param n the value of the rows and columns
     */
    public CrossFlips(int n)
    {
        rows = n;
        cols = n;
        crossFlips = new int[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                crossFlips[i][j] = 0;
            }
        }
        Random random = new Random();
        Random random2;
        int x, y;
        moves = random.nextInt(4) + 1; // Random number of moves between 1 and 4
        for(int i = 0; i < moves; i++) {
            random = new Random();
            random2 = new Random();
            x = random.nextInt(rows);
            y = random2.nextInt(cols);
            move(x, y);
        }
    }

    /**
     * Constructor where rows and columns are specified by two integers inputted
     * by the player and the number of moves are randomly determined.
     * @param r number of rows
     * @param c number of columns
     * */
    public CrossFlips(int r, int c)
    {
        rows = r;
        cols = c;
        crossFlips = new int[r][c];
        for (int i = 0; i < r; i++)
        {
            for (int j = 0; j < c; j++)
            {
                crossFlips[i][j] = 0;
            }
        }
        Random random = new Random();
        Random random2;
        int x, y;
        moves = random.nextInt(9) + 2; // Random number of moves between 2 and 10
        for(int i = 0; i < moves; i++) {
            random = new Random();
            random2 = new Random();
            x = random.nextInt(rows);
            y = random2.nextInt(cols);
            move(x, y);
        }
    }

    /**
     * Constructor where rows and columns are specified by two integers inputted
     * by the player and the number of moves are predetermined.
     * @param r number of rows
     * @param c number of columns
     * @param m number of starting moves
     * */
    public CrossFlips(int r, int c, int m)
    {
        rows = r;
        cols = c;
        crossFlips = new int[r][c];
        for (int i = 0; i < r; i++)
        {
            for (int j = 0; j < c; j++)
            {
                crossFlips[i][j] = 0;
            }
        }
        Random random;
        Random random2;
        int x, y;
        for(int i = 0; i < m; i++) {
            random = new Random();
            random2 = new Random();
            x = random.nextInt(rows);
            y = random2.nextInt(cols);
            move(x, y);
        }
    }

    /**
     * This method returns true if every disc on the board is yellow.
     * It searches through the crossFlips array for a red disc and
     * if one is found, the method returns false; otherwise the method
     * returns true once the whole array has been searched.
     */
    public boolean allYellow() {
        for (int i = 0; i < crossFlips.length; i++)
        {
            for (int j = 0; j < crossFlips[i].length; j++)
            {
                if(crossFlips[i][j] == 1)
                    return false;
            }
        }
        return true;
    }

    /**
     * Returns a random point from the points Array List to be used as a hint to
     * reach an allYellow state.
     */
    public String getHint() {
        Random random = new Random();
        return points.get(random.nextInt(points.size()));
    }

    /**
     * Makes a move on the board at the specified row and column
     * @param x The row of the move
     * @param y The column of the move
     */
    public void move(int x, int y) {
        addPoint = true;
        // Flip disc at the specified position
        if(crossFlips[x][y] == 0)
            crossFlips[x][y] = 1;
        else
            crossFlips[x][y] = 0;

        // Flip every disc in the row
        for (int i = 0; i < rows; i++)
        {
            if(crossFlips[i][y] == 0)
                crossFlips[i][y] = 1;
            else
                crossFlips[i][y] = 0;
        }
        // Flip every disc in the column
        for (int i = 0; i < cols; i++)
        {
            if(crossFlips[x][i] == 0)
                crossFlips[x][i] = 1;
            else
                crossFlips[x][i] = 0;
        }

        // Check to see if move is in move list
        // If it is remove it, otherwise add the new move
        String s = "(" + x + ", " + y + ")";
        for(int i = 0; i < points.size(); i++) {
            if(points.get(i).equals(s)) {
                points.remove(i);
                addPoint = false;
                break;
            }
        }
        if(addPoint)
            points.add(s);
    }

    /**
     * Returns the game as a string that represents game board
     */
    @Override
    public String toString() {
        String red = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        String disc = "O ";
        String redDisc = red + disc + reset;
        String yellowDisc = yellow + disc + reset;
        String board = "  ";
        for (int j = 0; j < cols; j++) {
            board += j + " ";
        }
        board += "\n";
        for (int i = 0; i < rows; i++)
        {
            board += i + " ";
            for (int j = 0; j < cols; j++)
            {
                if(crossFlips[i][j] == 0)
                    board += yellowDisc;
                else
                    board += redDisc;
            }
            board += "\n";
        }
        return board;
    }

    /**
     * Returns true if the move is valid otherwise returns false
     * if the move is out of bounds of the array.
     * @param x The row of the move
     * @param y The column of the move
     */
    public boolean validMove(int x, int y) {
        if(x < 0 || x >= rows || y < 0 || y >= cols)
            return false;
        return true;
    }

    /**
     * Returns true if the specified disc is yellow, false if
     * it's red
     * @param x The row of the disc
     * @param y The column of the disc
     */
    public boolean isYellow(int x, int y) {
        return crossFlips[x][y] == 0;
    }
}
