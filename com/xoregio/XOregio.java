package com.xoregio;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
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
        final JPanel menuContainer = new JPanel();
        final XOregioPanel gameContainer = new XOregioPanel();
        final JPanel settingsContainer = new JPanel();
        final JPanel comboBoxContainer = new JPanel();
        final JPanel menuButtons = new JPanel();
        final JPanel winContainer = new JPanel();
        final JLabel winMessage = new JLabel();
        final JPanel endButtonContainer = new JPanel();
        final JLabel winTile = new JLabel();

        gameContainer.setLayout(new BorderLayout());
        frame.setLayout(new CardLayout());
        menuContainer.setLayout(new GridLayout(2, 1));
        menuButtons.setLayout(new GridLayout(1, 3));
        settingsContainer.setLayout(new GridLayout(0, 1));
        comboBoxContainer.setLayout(new GridLayout(3, 2));
        endButtonContainer.setLayout(new GridLayout(1, 2));
        winContainer.setLayout(new GridLayout(0, 1));

        JButton restartButton = new JButton("Restart Game");
        JButton returnButton = new JButton("Return to Main Menu");
        JButton spButton = new JButton("<html>Start<br/>Single Player</html>");
        JButton mpButton = new JButton("<html>Start<br/>Multi Player</html>");
        spButton.setFont(RobotoFont.ROBOTO_FONT.deriveFont(14f));
        mpButton.setFont(RobotoFont.ROBOTO_FONT.deriveFont(14f));
        restartButton.setFont(RobotoFont.ROBOTO_FONT.deriveFont(14f));
        returnButton.setFont(RobotoFont.ROBOTO_FONT.deriveFont(14f));
        winMessage.setFont(RobotoFont.ROBOTO_FONT.deriveFont(Font.BOLD, 20f));
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

        restartButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                final CardLayout cl = (CardLayout) (frame.getContentPane().getLayout());
                cl.show(frame.getContentPane(), "GAME");
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final CardLayout cl = (CardLayout) (frame.getContentPane().getLayout());
                gameContainer.stopMusic();
                cl.show(frame.getContentPane(), "MENU");
            }
        });
        endButtonContainer.add(restartButton);
        endButtonContainer.add(returnButton);
        winContainer.add(endButtonContainer);
        winContainer.add(winTile, Component.CENTER_ALIGNMENT);
        winTile.setHorizontalAlignment(JLabel.CENTER);
        winMessage.setHorizontalAlignment(JLabel.CENTER);
        winContainer.add(winMessage);


        JLabel logo = new JLabel(getScaledImage("logo.jpg", new Dimension(400, 240)));
        menuContainer.add(logo);
        menuContainer.add(menuButtons);

        frame.add(menuContainer, "MENU");
        frame.add(gameContainer, "GAME");
        frame.add(winContainer, "WIN");

        frame.setSize(400, 480);
        frame.setVisible(true);
        frame.setResizable(false);

        spButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                final CardLayout cl = (CardLayout) (frame.getContentPane().getLayout());
                gameContainer.setBoardListener(new XOregioBoardListener()
                {
                    @Override
                    public void turnChanged(XOregioPlayer player)
                    {
                        gameContainer.setMessage((gameContainer.board.isXTurn() ? "X" : "O") + "'s Turn | Turn " + gameContainer.board.getTurnCount());
                    }

                    @Override
                    public void gameWin(boolean winningPlayer)
                    {
                        winMessage.setText((winningPlayer ? "X" : "O") + " Wins!");
                        winTile.setIcon(getScaledImage(winningPlayer ? "1.jpg" : "2.jpg", new Dimension(100, 100)));
                        gameContainer.setupBoard((int) rows.getSelectedItem(), (int) cols.getSelectedItem(),
                                new XOregioHumanPlayer(), new XOregioCPUPlayer(), playMusic.isSelected(),
                                startO.isSelected());
                        cl.show(frame.getContentPane(), "WIN");
                    }
                });
                gameContainer.setupBoard((int) rows.getSelectedItem(), (int) cols.getSelectedItem(),
                        new XOregioHumanPlayer(), new XOregioCPUPlayer(), playMusic.isSelected(),
                        startO.isSelected()
                );
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

    private static ImageIcon getScaledImage(String image, Dimension dimension)
    {
        //http://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(new File(image));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(dimension.width, dimension.height,
                Image.SCALE_SMOOTH);
        return new ImageIcon(dimg);
    }

    public static void main(String[] args)
    {

        try
        {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e)
        {
            // handle exception
        } catch (ClassNotFoundException e)
        {
            // handle exception
        } catch (InstantiationException e)
        {
            // handle exception
        } catch (IllegalAccessException e)
        {
            // handle exception
        }
        new XOregio();
    }

}
