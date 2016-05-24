package com.xoregio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
    private final ImageIcon icon =  new ImageIcon("1.jpg");
    public XOregioBoard(int rows, int columns, XOregioPlayer playerOne, XOregioPlayer playerTwo)
    {
        this.rows = rows;
        this.columns = columns;
        this.state  = new int[rows][columns];
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.addMouseListener(new XOregioMouseListener(this));
    }
    public XOregioBoard()
    {
        this(5, 5, new XOregioHumanPlayer(), new XOregioHumanPlayer());
    }

    public int getColSpacing()
    {
        return this.getBounds().width / this.columns;
    }
    public int getRowSpacing()
    {
        return this.getBounds().height / this.rows;
    }

    public void paint(Graphics g)
    {
        Rectangle r = this.getBounds();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(r.x, r.y, r.width, r.height);
        g.setColor(Color.BLUE);
        int colSpacing = this.getColSpacing();
        int rowSpacing = this.getRowSpacing();
        for(int i = 0; i < this.rows + 1; i++)
        {
            g.fillRect(0, rowSpacing * i, r.width, 5); //draw horizontal lines
        }
        for(int i = 0; i < this.columns + 1; i++)
        {
            g.fillRect(colSpacing * i, 0, 5, r.height); //draw vertical lines
        }
        for (int row = 0; row < this.rows; row++)
        {
            for (int col = 0; col < this.columns; col++)
            {
                g.drawImage(icon.getImage(), col * colSpacing + 5, row * rowSpacing + 5, colSpacing - 10, rowSpacing - 10, this);
            }
        }
    }

    class XOregioMouseListener implements MouseListener
    {
        private XOregioBoard board;
        XOregioMouseListener(XOregioBoard board)
        {
            this.board = board;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int x = e.getX() / board.getColSpacing();
            int y = e.getY() / board.getRowSpacing();
            board.state[y][x] = 3;
            System.out.print(x + ", " + y);
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
