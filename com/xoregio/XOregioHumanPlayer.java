package com.xoregio;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * Created by Ronny on 2016-05-20.
 */
public class XOregioHumanPlayer implements XOregioPlayer
{
    private ImageIcon imageIcon;
    private String label;
    public XOregioHumanPlayer(String label)
    {
        this.label = label;
    }
    @Override
    public String getLabel()
    {
        return this.label;
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
    public int[] getNextMove(XOregioBoard board, int[] inputCoordinates)
    {
      return new int[] {inputCoordinates[0] / board.getColSpacing(), inputCoordinates[1] / board.getRowSpacing()};
    }
}
