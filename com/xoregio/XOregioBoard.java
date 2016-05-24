package com.xoregio;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ronny on 2016-05-24.
 */
public class XOregioBoard extends JComponent
{
    private int rows;
    private int columns;
    private int [][] state;
    private XOregioPlayer playerOne;
    private XOregioPlayer playerTwo;

    public XOregioBoard(int rows, int columns, XOregioPlayer playerOne, XOregioPlayer playerTwo)
    {
        this.rows = rows;
        this.columns = columns;
        this.state  = new int[rows][columns];
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }
    public XOregioBoard()
    {
        this(5, 5, new XOregioHumanPlayer(), new XOregioHumanPlayer());
    }

    public void paint(Graphics g)
    {
        Rectangle r = this.getBounds();
        for(int i = 0; i < this.rows; i++)
        {
            g.fillRect(100 * (i+1), 5, 5, 395);
        }
        g.fillRect(r.x, r.y, r.width, r.height);
        System.out.print("redrawn");
    }
}
