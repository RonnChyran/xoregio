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
    private boolean xTurn = false;
    private boolean win = false;
    public int [][] board;
    private final ImageIcon[] icons =  new ImageIcon[] { new ImageIcon("0.jpg"), new ImageIcon("1.jpg"), new ImageIcon("2.jpg") };
    public XOregioBoard(int rows, int columns)
    {
        this.rows = rows;
        this.columns = columns;
        this.board = new int[rows][columns];
        this.addMouseListener(new XOregioMouseListener(this));
    }
    public XOregioBoard()
    {
        this(4, 4);
    }

    private int getColSpacing()
    {
        return this.getBounds().width / this.columns;
    }

    private int getRowSpacing()
    {
        return this.getBounds().height / this.rows;
    }

    // Checks if the board is full.  If it is, return true; otherwise, return false.
    public boolean fullBoard()
    {
        for (int[] row : board)
        {
            for (int col : row)
            {
                if (col == 0)
                    return false;
            }
        }
        return true;
    } // fullBoard


    public void markBoard(int row, int col)
    {
        int mark = xTurn ? 1 : 2;
        board[row][col] = mark;
        if(row != 0 && board[row - 1][col] == 0)
            board[row - 1][col] = mark;

        if(row != board.length - 1 && board[row + 1][col] == 0)
            board[row + 1][col] = mark;

        if(col != 0 && board[row][col - 1] == 0)
            board[row][col - 1] = mark;

        if(col != board[0].length - 1 && board[row][col + 1] == 0)
            board[row][col + 1] = mark;
    } // markBoard

    public void choseSquare(int row, int col)
    {
        if(board[row][col] == 0)
            markBoard(row, col);
        this.xTurn = !xTurn;
        win = fullBoard();
    } // choseSquare

    public void paint(Graphics g)
    {
        Rectangle r = this.getBounds();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(r.x, r.y, r.width, r.height);
        g.setColor(Color.DARK_GRAY);
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
                g.drawImage(icons[this.board[row][col]].getImage(), col * colSpacing + 5, row * rowSpacing + 5, colSpacing - 10, rowSpacing - 10, this);
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
            board.choseSquare(y, x);
            board.repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("XOregio");
        frame.add(new XOregioBoard());
        frame.setSize(480, 400);
        frame.setVisible(true);
    }

}
