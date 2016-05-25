package com.xoregio;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * Created by Ronny on 2016-05-20.
 */
public class XOregioHumanPlayer implements XOregioPlayer
{
    private ImageIcon imageIcon;
    public XOregioHumanPlayer()
    {

    }
    @Override
    public boolean isRobot()
    {
        return false;
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.imageIcon;
    }

    @Override
    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    @Override
    public int[] getNextMove(int[][] gameState, int[] inputCoordinates)
    {
        // find coords of mouse click
        int row = inputCoordinates[0]/ 100;
        int col = inputCoordinates[1]/ 100;
        return new int[] {row, col};
    }
}
