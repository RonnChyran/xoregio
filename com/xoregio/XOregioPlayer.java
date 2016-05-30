//	File Name:   XOregioPlayer.java
//	Name:        Ronny Chan and Gerald Ma
//	Class:       ICS3U1-01 (B)
//	Date:        May 29, 2016
//	Description: Represents a player playing a game of XOregio.
//              An XOregioPlayer should be immutable and never represent any
//              information about the game state. A single instance of XOregioPlayer
//              should be re-usable across instances of games.

package com.xoregio;

public interface XOregioPlayer
{
    /**
     * Gets the next move from the player
     * @param board The XOregioBoard on which play is taking place
     * @param inputCoordinates The input coordinates on where the player clicked the XOregioBoard
     *                         or null, if computer-controlled
     * @return The next cell to choose in the form of an integer array where the first index is the row
     * @see XOregioBoard
     */
    int[] getNextMove(XOregioBoard board, int[] inputCoordinates);

    /**
     * Gets whether or not the player is computer-controlled
     * @return Whether or not the player is computer controlled
     */
    boolean isCpuPlayer();

}
