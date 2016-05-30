/*	File Name:   XOregioCPUPlayer.java
	Name:        Ronny Chan and Gerald Ma
	Class:       ICS3U1-01 (B)
	Date:        May 29, 2016
	Description: Implements the computer playing XOregio, picking the next move randomly from
	             a set of valid moves on the board.
*/

package com.xoregio;

import java.util.*;

/**
 *  Represents a computer controlled player that randomly selects the next move from a set of valid
 *  moves on the XOregioBoard.
 */
public class XOregioCPUPlayer implements XOregioPlayer
{
    /**
     * This player is computer controlled
     * @return true, the CPU player is computer controlled.
     */
    @Override
    public boolean isCpuPlayer()
    {
      return true;
    } // isCpuPlayer

    /**
     * Gets the next move selected randomly from a set of valid moves determined from the current state of the board.
     * @param board The XOregioBoard on which play is taking place
     * @param inputCoordinates Can be null, the computer does not click on the XOregioBoard and does not need coordinates
     *                         to determine the next move.
     * @return An integer array where index 0 is the row, and index 1 is the column chosen randomly by the CPU.
     */
    @Override
    public int[] getNextMove(XOregioBoard board, int[] inputCoordinates)
    {
        System.out.println("Robot's Turn");
        int row = getRandomRowContainingZero(board.board); //gets a random row with free space
        System.out.println("Got Row: " + (row + 1));
        int col = getRandomZeroIndex(board.board[row]); //get a random empty space within the row.
        System.out.println("Got Col: " + (col +1));
        return new int[] {row, col};
    } // getNextMove

    /**
     * Gets the index of a random row from a 2-dimensional integer array where
     * one of the rows contain the value '0' (representing a free space on the board)
     * @param array The array to search for
     * @return The index of the random row containing zero
     */
    private int getRandomRowContainingZero(int[][] array)
    {
        List<Integer> emptyRows = new ArrayList<>();
        /* We can't use an array for an unknown length, so we'll use a generic list instead.
           Because Java generics do not support primitive types, we have to use the Boxed int type Integer*/
        for (int i = 0; i < array.length; i++)
        {
            for(int j : array[i])
            {
                if (j == 0)
                {
                    emptyRows.add(i); //add the index of the row if it contains a 0
                    break; /* if a row contains one zero, we don't care if it contains more,
                              so we break out of the inner loop */
                }
            }
        }
        return emptyRows.get(new Random().nextInt(emptyRows.size())); //return a random index
    } // getRandomRowContainingZero

    /**
     * Gets a random index from an array that contains the value of '0'.
     * If there is only one space that contains zero, it will always return the one
     * @param array The array to look for
     * @return The random index containing zero
     */
    private int getRandomZeroIndex(int[] array)
    {
        List<Integer> emptyCol = new ArrayList<>();
        for (int i = 0; i < array.length; i++)
        {
            if(array[i] == 0)
                emptyCol.add(i);
        }
        return emptyCol.get(new Random().nextInt(emptyCol.size()));
    } // getRandomZeroIndex
} // XOregioCPUPlayer class
