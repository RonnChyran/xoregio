package com.xoregio;

import javax.swing.*;

public interface XOregioPlayer
{
    int[] getNextMove(XOregioBoard board, int[] inputCoordinates);

    boolean isRobot();

    ImageIcon getImageIcon();

    void setImageIcon(ImageIcon imageIcon);
}
