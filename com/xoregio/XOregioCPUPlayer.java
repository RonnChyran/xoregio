package com.xoregio;

import java.awt.event.MouseEvent;

/**
 * Created by Ronny on 2016-05-20.
 */
import java.util.*;

public class XOregioCPUPlayer implements XOregioPlayer
{
    public XOregioCPUPlayer()
    {

    }

    @Override
    public boolean isRobot()
    {
      return true;
    }

    @Override
    public int[] getNextMove(int[][] gameState, int[] inputCoordinates)
    {
        // find coords of mouse click
        int row = getRandomRowWithSpaces(gameState);
        int col = getRandomEmpty(gameState[row]);
        return new int[] {row, col};
    }

    private int getRandomRowWithSpaces(int[][] array)
    {
        List<Integer> emptyRows = new ArrayList<>();
        for (int i = 0; i < array.length; i++)
        {
            for(int j : array[i])
            {
                if (j == 0)
                {
                    emptyRows.add(i);
                    break;
                }
            }
        }
        return emptyRows.get(new Random().nextInt(emptyRows.size()));
    }
    private int getRandomEmpty(int[] array)
    {
        List<Integer> emptyCol = new ArrayList<>();
        for (int i = 0; i < array.length; i++)
        {
            if(array[i] == 0)
                emptyCol.add(i);
        }
        return emptyCol.get(new Random().nextInt(emptyCol.size()));
    }
}
