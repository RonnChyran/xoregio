package com.xoregio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.*;

/**
 * Represents an XOregioBoard implementation as a JComponent,
 * that supports player switching, multiple rows and columns,
 * and embedding within a LayoutManager.
 */
public class XOregioBoard extends JComponent
{
    /**
     * The number of rows in the board
     */
    private int rows;

    /**
     * The number of columns in the board
     */
    private int columns;

    /**
     * Keeps track of whether or not it is X's turn
     */
    public boolean xTurn = true;

    /**
     * Keeps track of the win state
     */
    private boolean win = false;

    /**
     * Keeps the state of the board in the form of a 2D integer array
     * A value of '0' represents empty
     * A value of '1' represents that it is marked by 'X'
     * A value of '2' represents that it is marked by 'O'
     */
    public int[][] board;

    /**
     * The player instance representing 'X'
     */
    private XOregioPlayer playerX;

    /**
     * The player instance representing 'O'
     */
    private XOregioPlayer playerO;

    /**
     * A Listener that listens for events on the board
     * @see XOregioBoardListener
     */
    private XOregioBoardListener boardListener = null;

    /**
     * A constant of images for respective cell state
     * Empty, X, or O.
     */
    public static final ImageIcon[] PLAY_IMAGES = new ImageIcon[]{new ImageIcon("0.png"), new ImageIcon("1.png"), new ImageIcon("2.png")};

    /**
     * A counter that keeps track of the current turn
     */
    public int turnCount = 1;

    /**
     * The default constructor for an XOregio Board
     * @param rows The number of rows in the play area
     * @param columns The number of columns in the play area
     * @param playerX The player instance representing 'X'
     * @param playerO The player instance representing 'O'
     * @param startAsO Whether or not O goes first.
     */
    public XOregioBoard(int rows, int columns, XOregioPlayer playerX, XOregioPlayer playerO, boolean startAsO)
    {
        this.xTurn = !startAsO; //swap the turn counter if 'O' goes first
        this.playerX = playerX; //assign X to instance
        this.playerO = playerO; //assign O to instance
        this.rows = rows; //assign rows to instance
        this.columns = columns; //assign columns to instance
        this.board = new int[rows][columns]; //initialize board
        this.addMouseListener(new XOregioMouseListener(this)); //add default mouse listener
        if(playerO.isCpuPlayer() && startAsO) //if O is a robot, and should go first, we ask it to make a move
        {
            int[] move = playerO.getNextMove(this, null);
            this.choseSquare(move[1], move[0]);
        }
    }

    /**
     * Sets the write-only board listener for this instance
     * @param boardListener The board listener to set
     */
    public void setBoardListener(XOregioBoardListener boardListener)
    {
        this.boardListener = boardListener;
    }

    /**
     * Gets the spacing between columns by dividing the width of the board by the
     * number of columns.
     *
     * @return The spacing between the columns
     */
    public int getColSpacing()
    {
        return this.getBounds().width / this.columns;
    }

    /**
     * Gets the spacing between rows by dividing the height of the board by the
     * number of rows.
     *
     * @return The spacing between the rows
     */
    public int getRowSpacing()
    {
        return this.getBounds().height / this.rows;
    }

    /**
     * Checks if the board is full by checking each cell in the board.
     * If no cells have '0' (unmarked), then it will return true.
     *
     * @return true if the board is full, false otherwise
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
    }    // fullBoard


    /**
     * Marks the board and adjacent squares. It checks for valid adjacent cells vertically and horizontally
     * and also excludes already-marked cells, then sets the valid cells to either 1 or 2, depending on
     * whose turn it is currently.
     *
     * @param row The row of the cell to mark
     * @param col The column of the cell to mark.
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
    }    // markBoard

    /**
     * Determines if the chosen square is unmarked, then chooses
     * the square and adjacent cells to be marked by markBoard.
     * Also switches the turn counter to the next player, and determines if the game has been won.
     *
     * @param row The row of the cell to choose
     * @param col The col of the cell to mark
     */
    public void choseSquare(int row, int col)
    {
        if (board[row][col] == 0)
        {
            markBoard(row, col);
            this.xTurn = !xTurn;
        }
        win = fullBoard();
    }    // choseSquare

    /**
     * Paints the board
     *
     * @param g The Graphics object passed to this component
     */
    public void paint(Graphics g)
    {
        Rectangle r = this.getBounds();
        g.setColor(Color.DARK_GRAY);
        int colSpacing = this.getColSpacing();
        int rowSpacing = this.getRowSpacing();
        g.drawImage(XOregio.getScaledImage("background.jpg", new Dimension(r.width, r.height)).getImage(), 0, 0, this);

        for (int i = 1; i < this.rows; i++)
        {
            g.fillRect(0, rowSpacing * i, r.width, 7); //draw horizontal lines for every row
        }
        for (int i = 1; i < this.columns; i++)
        {
            g.fillRect(colSpacing * i, 0, 7, r.height); //draw vertical lines for every column
        }
        for (int row = 0; row < this.rows; row++)
        {
            for (int col = 0; col < this.columns; col++)
            {
                //fill in the appropriate image
                g.drawImage(PLAY_IMAGES[this.board[row][col]].getImage(),
                        col * colSpacing + 5, row * rowSpacing + 5,
                        colSpacing - 5, rowSpacing - 5, this);
            }
        }
    }

    /**
     * Gets the current player instance according to xTurn
     * @return The current player instance
     */
    public XOregioPlayer getCurrentPlayer()
    {
        return xTurn ? this.playerX : this.playerO;
    }

    /**
     * Implements a MouseListener that updates an XOregioBoard.
     */
    class XOregioMouseListener implements MouseListener
    {
        /**
         * A reference to the XOregioBoard instance of this mouse listener
         */
        private XOregioBoard board;
        /**
         * The ding that plays on a valid move
         */
        Clip goodDing = this.loadSound("goodDing.wav");
        /**
         * The ding that plays on an invalid move
         */
        Clip badDing = this.loadSound("badDing.wav");

        XOregioMouseListener(XOregioBoard board)
        {
            this.board = board;
        }

        /**
         * Loads a sound from a filename and returns the Clip instance
         * Also reduces the volume for safer listening.
         * @param fileName The filename of the audio clip
         * @return The loaded Clip instance. Null if an exception occurs
         */
        private Clip loadSound(String fileName)
        {
            try {
                File click = new File(fileName);
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(click)));
                Clip clip = AudioSystem.getClip();
                clip.open(inputStream);
                FloatControl gainControl =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-10.0f); // turn down the volume
                return clip;
            } catch (Exception g) {
                g.printStackTrace();
                return null;
            }
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
            //by the spacing of the row or column.
            int[] coordinates = board.getCurrentPlayer().getNextMove(board, new int[]{e.getY(), e.getX()});
            if(board.board[coordinates[0]][coordinates[1]] == 0)
            {
                turnCount++;
                goodDing.setFramePosition(0);
                goodDing.start();
            }
            else
            {
                badDing.setFramePosition(0);
                badDing.start();
            }
            board.choseSquare(coordinates[0], coordinates[1]);
            if (board.boardListener != null) board.boardListener.turnChanged(board.getCurrentPlayer());
            if (board.getCurrentPlayer().isCpuPlayer() && !board.win)
            {
                int[] robotCoordinates = board.getCurrentPlayer().getNextMove(board, null);
                board.choseSquare(robotCoordinates[0], robotCoordinates[1]);
                turnCount++;
                if (board.boardListener != null) board.boardListener.turnChanged(board.getCurrentPlayer());
            }
            board.repaint();
            if (board.win && board.boardListener != null)
            {
                boardListener.gameWin(!xTurn);
            }
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
}
