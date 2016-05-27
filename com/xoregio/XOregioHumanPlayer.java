package com.xoregio;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * Created by Ronny on 2016-05-20.
 */
public class XOregioHumanPlayer implements XOregioPlayer
{
    public XOregioHumanPlayer()
    {

    }
    @Override
    public boolean isRobot()
    {
        return false;
    }

    @Override
    public int[] getNextMove(XOregioBoard board, int[] inputCoordinates)
    {
      return new int[] {inputCoordinates[0] / board.getColSpacing(), inputCoordinates[1] / board.getRowSpacing()};
    }
}
