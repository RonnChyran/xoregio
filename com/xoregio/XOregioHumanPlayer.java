package com.xoregio;

import java.awt.event.MouseEvent;

/**
 * Created by Ronny on 2016-05-20.
 */
public class XOregioHumanPlayer implements XOregioPlayer
{
    @Override
    public boolean isRobot()
    {
        return false;
    }

    @Override
    public int[] getNextMove(int[][] gameState, Object o)
    {
        // find coords of mouse click
        MouseEvent e = (MouseEvent)o;
        int row = e.getY() / 100;
        int col = e.getX() / 100;
        return new int[] {row, col};
    }
}
