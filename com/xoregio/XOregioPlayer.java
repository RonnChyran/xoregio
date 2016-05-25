package com.xoregio;

import javax.swing.*;

public interface XOregioPlayer
{
    int[] getNextMove(int[][] gameState, int[] inputCoordinates);

    boolean isRobot();

    ImageIcon getImageIcon();

    void setImageIcon(ImageIcon imageIcon);
}
