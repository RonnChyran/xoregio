   import javax.swing.*;
   import java.awt.*;
   import java.awt.event.*;
	import java.lang.*;
	import java.util.*;
	
    public class XOregio implements MouseListener
   {
      Drawing draw = new Drawing();
      int [][] board = new int [4][4];    // 0 = empty; 1 = X; 2 = O
      boolean xTurn = true;
      boolean win = false;
      JLabel message = new JLabel("X's turn");
      ImageIcon[] boardPictures = new ImageIcon[3];
   
       public XOregio()      // constructor
      {
         for (int i = 0; i < boardPictures.length; i++)
            boardPictures[i] = new ImageIcon(i + ".jpg"); 
         JFrame frame = new JFrame("XOregio");
         frame.add(draw);
         draw.addMouseListener(this);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setSize(420, 480);
         message.setFont(new Font("Serif",Font.BOLD,20));
         message.setForeground(Color.blue);
         message.setHorizontalAlignment(SwingConstants.CENTER);
         frame.add(message, "South");
         frame.setVisible(true);
		}
   
            		
   // Marks chosen square (as indicated by parameters row and col), and any adjacent empty squares.
   public void markBoard(int row, int col)
   {
   } // markBoard
	
   
   // Checks if the board is full.  If it is, return true; otherwise, return false.
   public boolean fullBoard()
   {
		int count = 0;
	
		for (int r = 0; r < 4; r++)
		{
			for (int c = 0; c < 4; c++)
			{
				if (board[r][c] == 1 || board[r][c] == 2)
					count++;
					//just check if there are any zeroes, you don't need to add up everything.
			}
		}
		
		if (count == 16)
		{
			return true;
		}
		else
			return false;
   } // fullBoard
  
  
   // Updates game board and checks for win after a player has chosen a square (as indicated by parameters row and col).
   // choseSquare should call methods markBoard and fullBoard.
    public void choseSquare(int row, int col)
   {
   } // choseSquare
   
   
       class Drawing extends JComponent
      {
          public void paint(Graphics g)
         {
         // draw the content of the board
            for (int row = 0; row < 4; row++)
               for (int col = 0; col < 4; col++)
                  g.drawImage(boardPictures[board[row][col]].getImage(),col * 100, row * 100,100,100,this);
         // draw grid
            g.fillRect(100,5,5,395);
            g.fillRect(200,5,5,395);
            g.fillRect(300,5,5,395);
         
            g.fillRect(5,100,395,5);
            g.fillRect(5,200,395,5);
            g.fillRect(5,300,395,5);
            //so basically after you save it
            //and you want to test
            //since this runs in the cloud idk how the ui will work
            //so jsut click "bash" and then
            //copy 'git add *.java'
            //git commit -m "whatever mssgae here"
            //git push 
            //and then you can sync to your deksotp
            System.out.println("Hello World");
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
         // find coords of mouse click
            int row = e.getY()/100;
            int col = e.getX()/100;
         // handle the move that the player has made on the game board
            choseSquare(row, col);
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
         XOregio xoRegio = new XOregio();
      }
   }