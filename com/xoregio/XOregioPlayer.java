package com.xoregio;

public interface XOregioPlayer
{
    int[] getNextMove(int[][] gameState, Object e);

    boolean isRobot();
}
