package com.xoregio;

/**
 * Represents a player playing a game of XOregio.
 */
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
