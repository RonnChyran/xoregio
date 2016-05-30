//	File Name:   XOregioHumanPlayer.java
//	Name:        Ronny Chan and Gerald Ma
//	Class:       ICS3U1-01 (B)
//	Date:        May 29, 2016
//	Description: 

package com.xoregio;

/**
 * Represents a Human XOregioPlayer with an actual person behind the screen
 * clicking on the XOregioBoard, sending coordinates to the player.
 */
public class XOregioHumanPlayer implements XOregioPlayer
{
    public XOregioHumanPlayer()
    {

    }

    /**
     * Humans are not CPU players.
     * @return false - Humans are not computer-controlled
     */
    @Override
    public boolean isCpuPlayer()
    {
        return false;
    }

    /**
     * Gets the next move by divinding the input coordinates by the spacing of the respective row or column,
     * to get the correct cell and return it in the form of an integer array.
     * @param board The XOregioBoard on which play is taking place
     * @param inputCoordinates The input coordinates on where the player clicked the XOregioBoard
     * @return An integer array where index 0 is the row, and index 1 is the column chosen by the player.
     */
    @Override
    public int[] getNextMove(XOregioBoard board, int[] inputCoordinates)
    {
      return new int[] {inputCoordinates[0] / board.getRowSpacing(), inputCoordinates[1] / board.getColSpacing()};
    }
}
