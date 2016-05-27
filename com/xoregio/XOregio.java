package com.xoregio;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*
todo: Start as X and y (checkbox)
todo: Turn message and turn number keeper
todo:	End Game Restart
todo:	Roboto
todo:	Borders
 */
public class XOregio
{
    public XOregio()
    {

        final JFrame frame = new JFrame("XOregio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JPanel menuContainer =  new JPanel();
        final JPanel gameContainer = new JPanel();
        final JPanel settingsContainer = new JPanel();
        final JPanel comboBoxContainer = new JPanel();
        final JPanel menuButtons = new JPanel();
        final JPanel winContainer = new JPanel();

        gameContainer.setLayout(new BorderLayout());
        frame.setLayout(new CardLayout());
        menuContainer.setLayout(new GridLayout(2,1));
        menuButtons.setLayout(new GridLayout(1, 3));
        settingsContainer.setLayout(new GridLayout(0, 1));
        comboBoxContainer.setLayout(new GridLayout(3, 2));


        JButton spButton = new JButton("<html>Start<br/>Single Player</html>");
        JButton mpButton = new JButton("<html>Start<br/>Multi Player</html>");
        final JCheckBox playMusic = new JCheckBox("Play Music");
        playMusic.setSelected(true);
        JLabel rowLabel = new JLabel("Rows");
        JLabel columnLabel = new JLabel("Columns");
        final JComboBox<Integer> rows = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        final JComboBox<Integer> cols = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        final JCheckBox startO = new JCheckBox("O goes First");


        cols.setSelectedIndex(3);
        rows.setSelectedIndex(3);

        comboBoxContainer.add(rowLabel);
        comboBoxContainer.add(rows);
        comboBoxContainer.add(columnLabel);
        comboBoxContainer.add(cols);

        menuButtons.add(spButton);
        menuButtons.add(mpButton);
        settingsContainer.add(playMusic);
        settingsContainer.add(startO);
        settingsContainer.add(comboBoxContainer);
        menuButtons.add(settingsContainer);

        JLabel logo = getScaledImage("logo.jpg", new Dimension(400, 240));
        menuContainer.add(logo);
        menuContainer.add(menuButtons);

        frame.add(menuContainer, "MENU");
        frame.add(gameContainer, "GAME");

        frame.setSize(400, 480);
        frame.setVisible(true);
        frame.setResizable(false);
        spButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                CardLayout cl = (CardLayout)(frame.getContentPane().getLayout());
                final XOregioBoard board = new XOregioBoard((int)rows.getSelectedItem(), (int)cols.getSelectedItem(),
                        new XOregioHumanPlayer(),
                        new XOregioCPUPlayer(),
                        startO.isSelected());
                if(playMusic.isSelected()) board.startMusic();
                final JLabel message = new JLabel((board.isXTurn() ? "X" : "O")  + "'s Turn | Turn " + board.getTurnCount(), SwingConstants.CENTER);
                board.setBoardListener(new XOregioBoardListener()
                {
                    @Override
                    public void turnChanged(XOregioPlayer player)
                    {
                        message.setText((board.isXTurn() ? "X" : "O") + "'s Turn | Turn " + board.getTurnCount());
                    }
                    @Override
                    public void gameWin(XOregioPlayer winningPlayer)
                    {
                        message.setText((board.isXTurn() ? "X" : "O") + " wins!");
                    }
                });
                gameContainer.add(board);
                gameContainer.add(message, BorderLayout.PAGE_END);

                cl.show(frame.getContentPane(), "GAME");
            }
        });
        mpButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {

            }
        });
    }
    private JLabel getScaledImage(String image, Dimension dimension)
    {
        //http://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
        BufferedImage img = null;
        JLabel label = new JLabel();
        label.setSize(dimension);
        try {
            img = ImageIO.read(new File(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(label.getWidth(), label.getHeight(),
                Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(dimg));
        return label;
    }

    public static void main(String[] args)
    {

        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }
        new XOregio();
    }
}
