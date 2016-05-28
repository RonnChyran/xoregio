package com.xoregio;

/**
 * Represents a listener that reacts on the state of the
 * XOregioBoard changing
 * @see XOregioBoard
 */
public interface XOregioBoardListener
{
    /**
     * Called when the active player is changed from 'X' to 'O' and vice-versa
     * @param player The player instance the turn changed to.
     *               If 'X' plays, then this will be called with 'O' after X plays.
     *               If 'O' plays, then this will be called with 'X' after O plays
     */
    void turnChanged(XOregioPlayer player);

    /**
     * Called when the game is won.
     * @param xWon Whether or not 'X' was the player who won.
     */
    void gameWin(boolean xWon);
}
