package com.xoregio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.*;

/**
 * Created by Ronny on 2016-05-24.
 * todo:	End Game	Restart
 * todo:	Roboto
 * todo:	Borders
 */
public class XOregioBoard extends JComponent
{
    private int rows;
    private int columns;
    private boolean xTurn = true;
    private boolean win = false;
    public int[][] board;
    private XOregioPlayer player1;
    private XOregioPlayer player2;
    private XOregioBoardListener boardListener = null;
    private final ImageIcon[] icons = new ImageIcon[]{new ImageIcon("0.jpg"), new ImageIcon("1.jpg"), new ImageIcon("2.jpg")};
	 private int turnCount = 0;

    public XOregioBoard(int rows, int columns, XOregioPlayer player1, XOregioPlayer player2)
    {
        this.player1 = player1;
        this.player2 = player2;
        this.rows = rows;
        this.columns = columns;
        this.board = new int[rows][columns];
        this.addMouseListener(new XOregioMouseListener(this));
    }

    public XOregioBoard()
    {
        this(5, 5, new XOregioHumanPlayer("X"), new XOregioHumanPlayer("O"));
    }

    public void setBoardListener(XOregioBoardListener boardListener)
    {
        this.boardListener = boardListener;
    }
    /**
     * Gets	the spacing	between columns by dividing the width of the	board	by	the
     * number of columns.
     *
     * @return The spacing	between the	columns
     */
    public int getColSpacing()
    {
        return this.getBounds().width / this.columns;
    }

    /**
     * Gets	the spacing	between rows by dividing the height	of	the board by the
     * number of rows.
     *
     * @return The spacing	between the	rows
     */
    public int getRowSpacing()
    {
        return this.getBounds().height / this.rows;
    }

    /**
     * Checks if the	board	is	full by checking each cell	in	the board.
     * If no cells have	'0' (unmarked), then	it	will return	true.
     *
     * @return true if the	board	is	full,	false	otherwise
     */
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
    }    //	fullBoard


    /**
     * Marks the board and	adjacent	squares.	It	checks for valid adjacent cells vertically and horizontally
     * and also excludes already-marked cells, then sets	the valid cells to either 1 or 2, depending on
     * whose turn	it	is	currently.
     *
     * @param row The row of the	cell to mark
     * @param col The column of the	cell to mark.
     */
    public void markBoard(int row, int col)
    {
        int mark = xTurn ? 1 : 2;
        board[row][col] = mark;
        if (row != 0 && board[row - 1][col] == 0)
            board[row - 1][col] = mark;

        if (row != board.length - 1 && board[row + 1][col] == 0)
            board[row + 1][col] = mark;

        if (col != 0 && board[row][col - 1] == 0)
            board[row][col - 1] = mark;

        if (col != board[0].length - 1 && board[row][col + 1] == 0)
            board[row][col + 1] = mark;
    }    //	markBoard

    /**
     * Determines	if	the chosen square	is	unmarked, then	chooses
     * the square	and adjacent cells to be marked by markBoard.
     * Also	switches	the turn	counter to the	next player, and determines if the game has been won.
     *
     * @param row
     * @param col
     */
    public void choseSquare(int row, int col)
    {
        if (board[row][col] == 0)
        {
            markBoard(row, col);
            this.xTurn = !xTurn;
        }
        win = fullBoard();
    }    //	choseSquare

    /**
     * Paints the	board
     *
     * @param g
     */
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Rectangle r = this.getBounds(); //gets the	bounds of the board
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(r.x, r.y, r.width, r.height); //draw a background
        g2.setColor(Color.DARK_GRAY);
        int colSpacing = this.getColSpacing();
        int rowSpacing = this.getRowSpacing();
        for (int i = 0; i < this.rows + 1; i++)
        {
            g2.fillRect(0, rowSpacing * i, r.width, 5); //draw horizontal lines for every row
        }
        for (int i = 0; i < this.columns + 1; i++)
        {
            g2.fillRect(colSpacing * i, 0, 5, r.height); //draw vertical lines for every column
        }
        for (int row = 0; row < this.rows; row++)
        {
            for (int col = 0; col < this.columns; col++)
            {
                //fill in the	appropriate	image
                g2.drawImage(icons[this.board[row][col]].getImage(),
                        col * colSpacing + 5, row * rowSpacing + 5,
                        colSpacing - 10, rowSpacing - 10, this);
            }
        }
    }

    public XOregioPlayer getCurrentPlayer()
    {
        return xTurn ? this.player1 : this.player2;
    }

    /**
     * Implements	a MouseListener that	updates an XOregioBoard.
     */
    class XOregioMouseListener implements MouseListener
    {
        private XOregioBoard board;

        XOregioMouseListener(XOregioBoard board)
        {
            this.board = board;
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {

        }

        @Override
        public void mousePressed(MouseEvent e)
        {

        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            //Gets the valid indices by dividing the MouseEvent coordinates
            //by the spacing of the	row or column.
            XOregioPlayer previousPlayer;
            int[] coordinates = board.getCurrentPlayer().getNextMove(board, new int[]{e.getX(), e.getY()});
            previousPlayer = board.getCurrentPlayer();
            board.choseSquare(coordinates[1], coordinates[0]);
            if(board.boardListener != null) board.boardListener.turnChanged(board.getCurrentPlayer());
            if (board.getCurrentPlayer().isRobot() && !board.win)
            {
                int[] robotCoordinates = board.getCurrentPlayer().getNextMove(board, null);
                previousPlayer = board.getCurrentPlayer();
                board.choseSquare(robotCoordinates[1], robotCoordinates[0]);
                if(board.boardListener != null) board.boardListener.turnChanged(board.getCurrentPlayer());
            }
            board.repaint();
            if(board.win && board.boardListener != null)
            {
                boardListener.gameWin(previousPlayer);
            }
				
				turnCount++;
				System.out.print(turnCount);
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {

        }

        @Override
        public void mouseExited(MouseEvent e)
        {

        }
    }

    public static class AL implements ActionListener
    {
        public final void actionPerformed(ActionEvent e)
        {
            try
            {

                music();
            } catch (Throwable y)
            {
                y.printStackTrace();
            }
        }
    }

    public static void music() throws Throwable
    {
	 	  File f = new File("Elevator_Music.wav");
	 
        Clip clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(f)));
        clip.open(inputStream);                                                                                    
        clip.start();
    }
	 
	 private static final Font ROBOTO_FONT = new Font("roboto", Font.PLAIN, 24);

    private static Font getFont(String name)
	 {
   	  Font font = null;
    	  if (name == null)
		  {
        return ROBOTO_FONT;
        }

   	  try {
            File fontFile = new File("roboto.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();

            ge.registerFont(font);

   	 } catch (Exception ex) 
		   {
         font = ROBOTO_FONT;
         }
    return font;
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("XOregio");
        frame.add(new XOregioBoard());
        frame.setSize(450, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.setResizable(false);

        JButton button = new JButton("Music");
		  button.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        frame.add(button, BorderLayout.SOUTH);
        button.addActionListener(new AL());
        frame.show(true);

    }

}
