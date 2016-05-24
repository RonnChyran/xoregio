package com.xoregio;

public interface XOregioPlayer
{
    int[] getNextMove(int[][] gameState, int[] inputCoordinates);

    boolean isRobot();
}
