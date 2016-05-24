//package com.xoregio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

public class XOregioGame implements MouseListener
{
    Drawing draw = new Drawing();
    int[][] board = new int[4][4];    // 0 = empty; 1 = X; 2 = O
    boolean xTurn = true;
    boolean win = false;
    JLabel message = new JLabel("X's turn");
    ImageIcon[] boardPictures = new ImageIcon[3];
    XOregioPlayer player = new XOregioCPUPlayer();
    public XOregioGame()      // constructor
    {
        for (int i = 0; i < boardPictures.length; i++)
            boardPictures[i] = new ImageIcon(i + ".jpg");
        JFrame frame = new JFrame("XOregioGame");
        frame.add(draw);
        draw.addMouseListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 480);
        message.setFont(new Font("Serif", Font.BOLD, 20));
        message.setForeground(Color.blue);
        message.setHorizontalAlignment(SwingConstants.CENTER);
        JButton jButton = new JButton("Test Button");
        frame.add(message, "South");
        frame.setVisible(true);
    }


    // Marks chosen square (as indicated by parameters row and col), and any adjacent empty squares.
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
        xTurn = !xTurn;

    } // markBoard


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


    // Updates game board and checks for win after a player has chosen a square (as indicated by parameters row and col).
    // choseSquare should call methods markBoard and fullBoard.
    public void choseSquare(int row, int col)
    {
       
		  if(board[row][col] == 0)
            markBoard(row, col);
 	
        win = fullBoard();
    } // choseSquare


    class Drawing extends JComponent
    {
        public void paint(Graphics g)
        {
            // draw the content of the board
            for (int row = 0; row < 4; row++)
                for (int col = 0; col < 4; col++)
                    g.drawImage(boardPictures[board[row][col]].getImage(), col * 100, row * 100, 100, 100, this);
            // draw grid
            g.fillRect(100, 5, 5, 395);
            g.fillRect(200, 5, 5, 395);
            g.fillRect(300, 5, 5, 395);

            g.fillRect(5, 100, 395, 5);
            g.fillRect(5, 200, 395, 5);
            g.fillRect(5, 300, 395, 5);

        }
    }

    // --> starting implementing MouseListener - it has 5 methods
    public void mousePressed(MouseEvent e)
    {
    }

    public void mouseReleased(MouseEvent e)
    {
        if (!win)
        {
            int[] markedSquare = player.getNextMove(board, e);
            choseSquare(markedSquare[0], markedSquare[1]);
            // get paint to be called to reflect your mouse click
            draw.repaint();
        }
    }

    public void mouseClicked(MouseEvent e)
    {
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    // finishing implementing MouseListener  <---
    public static void main(String[] args)
    {
        new XOregioGame();
    }
}