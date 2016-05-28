package com.xoregio;

/**
 * Created by Ronny on 2016-05-26.
 */
public interface XOregioBoardListener
{
    void turnChanged(XOregioPlayer player);
    void gameWin(boolean xWon);
}
