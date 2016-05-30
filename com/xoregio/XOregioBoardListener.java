//	File Name:   XOregioBoardListener.java
//	Name:        Ronny Chan and Gerald Ma
//	Class:       ICS3U1-01 (B)
//	Date:        May 29, 2016
//	Description: 

package com.xoregio;

/**
 * Represents a listener that reacts on the state of the XOregioBoard changing.
 * Because we separated the board from the UI, this 'listener' is notified on the board state changing
 * rather than checking the state on a mouse-click of the board from the main Swing UI.
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
     * @param isXWin Whether or not 'X' was the player who won.
     */
    void gameWin(boolean isXWin);
}
