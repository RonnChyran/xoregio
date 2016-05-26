package com.xoregio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.*;
import java.io.*;
//import sun.audio.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.*;



/**
 *	Created by Ronny on 2016-05-24.
 *	todo:	End Game	Restart
 *	todo:	Re-implement AI (XOregioPlayer abstraction)
 *	todo:	Music done? not really
 *	todo:	Roboto
 *	todo:	Win condition.
 *	todo:	Borders
 */
public class XOregioBoard extends JComponent
{
	 private	int rows;
	 private	int columns;
	 private	boolean xTurn = true;
	 private	boolean win	= false;
	 public int	[][] board;
	 private	final	ImageIcon[]	icons	=	new ImageIcon[] {	new ImageIcon("0.jpg"),	new ImageIcon("1.jpg"),	new ImageIcon("2.jpg") };

	 public XOregioBoard(int rows, int columns)
	 {
		  this.rows	= rows;
		  this.columns	= columns;
		  this.board =	new int[rows][columns];
		  this.addMouseListener(new XOregioMouseListener(this));
	 }
	 public XOregioBoard()
	 {
		  this(4, 4);
	 }

	 /**
	  * Gets	the spacing	between columns by dividing the width of the	board	by	the
	  * number of columns.
	  * @return	The spacing	between the	columns
	  */
	 private	int getColSpacing()
	 {
		  return	this.getBounds().width / this.columns;
	 }

	 /**
	  * Gets	the spacing	between rows by dividing the height	of	the board by the
	  * number of rows.
	  * @return	The spacing	between the	rows
	  */
	 private	int getRowSpacing()
	 {
		  return	this.getBounds().height	/ this.rows;
	 }

	 /**
	  * Checks if the	board	is	full by checking each cell	in	the board.
	  * If no cells have	'0' (unmarked), then	it	will return	true.
	  * @return	true if the	board	is	full,	false	otherwise
	  */
	 public boolean fullBoard()
	 {
		  for	(int[] row : board)
		  {
				for (int	col :	row)
				{
					 if (col	==	0)
						  return	false;
				}
		  }
		  return	true;
	 }	//	fullBoard


	 /**
	  * Marks the board and	adjacent	squares.	It	checks for valid adjacent cells vertically and horizontally
	  * and also excludes already-marked cells, then sets	the valid cells to either 1 or 2, depending on
	  * whose turn	it	is	currently.
	  * @param row	The row of the	cell to mark
	  * @param col	The column of the	cell to mark.
	  */
	 public void markBoard(int	row, int	col)
	 {
		  int	mark = xTurn ?	1 : 2;
		  board[row][col]	= mark;
		  if(row	!=	0 && board[row	- 1][col] == 0)
				board[row -	1][col] = mark;

		  if(row	!=	board.length -	1 && board[row	+ 1][col] == 0)
				board[row +	1][col] = mark;

		  if(col	!=	0 && board[row][col - 1] == 0)
				board[row][col	- 1] = mark;

		  if(col	!=	board[0].length -	1 && board[row][col + 1] == 0)
				board[row][col	+ 1] = mark;
	 }	//	markBoard

	 /**
	  * Determines	if	the chosen square	is	unmarked, then	chooses
	  * the square	and adjacent cells to be marked by markBoard.
	  * Also	switches	the turn	counter to the	next player, and determines if the game has been won.
	  * @param row
	  * @param col
	  */
	 public void choseSquare(int row, int col)
	 {
		  if(board[row][col]	==	0)
				markBoard(row,	col);
		  this.xTurn =	!xTurn;
		  win	= fullBoard();
	 }	//	choseSquare

	 /**
	  * Paints the	board
	  * @param g
	  */
	 public void paint(Graphics g)
	 {
		  Rectangle	r = this.getBounds(); //gets the	bounds of the board
		  g.setColor(Color.DARK_GRAY);
		  g.fillRect(r.x,	r.y, r.width, r.height); //draw a background
		  g.setColor(Color.DARK_GRAY);
		  int	colSpacing = this.getColSpacing();
		  int	rowSpacing = this.getRowSpacing();
		  for(int i	= 0; i <	this.rows +	1;	i++)
		  {
				g.fillRect(0, rowSpacing *	i,	r.width,	5); //draw horizontal lines for every row
		  }
		  for(int i	= 0; i <	this.columns +	1;	i++)
		  {
				g.fillRect(colSpacing *	i,	0,	5,	r.height); //draw	vertical	lines	for every column
		  }
		  for	(int row	= 0; row	< this.rows; row++)
		  {
				for (int	col =	0;	col <	this.columns; col++)
				{
					 //fill in the	appropriate	image
					 g.drawImage(icons[this.board[row][col]].getImage(),
								col *	colSpacing + 5, row * rowSpacing	+ 5,
								colSpacing - 10, rowSpacing -	10, this);
				}
		  }
	 }

	 /**
	  * Implements	a MouseListener that	updates an XOregioBoard.
	  */
	 class XOregioMouseListener implements	MouseListener
	 {
		  private XOregioBoard board;
		  XOregioMouseListener(XOregioBoard	board)
		  {
				this.board = board;
		  }

		  @Override
		  public	void mouseClicked(MouseEvent e) {

		  }

		  @Override
		  public	void mousePressed(MouseEvent e) {

		  }

		  @Override
		  public	void mouseReleased(MouseEvent	e)	{
				//Gets the valid indices by dividing the MouseEvent coordinates
				//by the	spacing of the	row or column.
				int x	= e.getX() / board.getColSpacing();
				int y	= e.getY() / board.getRowSpacing();
				board.choseSquare(y,	x);
				board.repaint();
		  }

		  @Override
		  public	void mouseEntered(MouseEvent e) {

		  }

		  @Override
		  public	void mouseExited(MouseEvent e) {

		  }
	 }
	 
	 public static class AL implements ActionListener{
		public final void actionPerformed(ActionEvent e){
			try{
			
			music();
		}catch(Throwable y){
		y.printStackTrace();
		}
		}
	 }
	 
	 public static void music() throws Throwable
	 {
	     Clip clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream("E:\\School Stuff\\Summative\\xoregio\\com\\xoregio\\Elevator_Music.wav")));
        clip.open(inputStream);                                                                                    //have to change file path every time. Fix
        clip.start(); 

	 }
	 
	 public static	void main(String[] args)
	 {
		  JFrame	frame	= new	JFrame("XOregio");
		  frame.add(new XOregioBoard());
		  frame.setSize(450,	500);
		  frame.setVisible(true);
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  
		  JButton button = new JButton("Music");
		  frame.add(button, BorderLayout.SOUTH);
		  button.addActionListener(new AL());
		  frame.show(true);

	 }

}
