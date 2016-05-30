//	File Name:   XOregio.java
//	Name:        Ronny Chan and Gerald Ma
//	Class:       ICS3U1-01 (B)
//	Date:        May 29, 2016
//	Description: 

package com.xoregio;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class XOregio
{
    public static Font ROBOTO_FONT;
    private JFrame frame;
    private XOregioGamePanel gameContainer;

    private JButton createReturnToMenuButton()
    {
        JButton returnButton = createRobotoStyledButton("Return to Main Menu");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final CardLayout cl = (CardLayout) (frame.getContentPane().getLayout());
                cl.show(frame.getContentPane(), "MENU");
            }
        });
        return returnButton;
    }

    private JButton createStartButton(final XOregioSettingsPanel settingsPanel,
                                      final JLabel winMessage,
                                      final JLabel winTile,
                                      String label,
                                      final XOregioPlayer playerOne,
                                      final XOregioPlayer playerTwo)
    {
        JButton startButton = createSwitchToGameButton(label);
        startButton.addActionListener(new ActionListener()
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
                        gameContainer.setMessage((gameContainer.board.xTurn ? "X" : "O") + "'s Turn | Turn "
                                + gameContainer.board.turnCount);
                    }

                    @Override
                    public void gameWin(boolean winningPlayer)
                    {
                        winMessage.setText((winningPlayer ? "X" : "O") + " Wins!");
                        winTile.setIcon(getScaledImage(winningPlayer ? XOregioBoard.PLAY_IMAGES[1].getImage()
                                        : XOregioBoard.PLAY_IMAGES[2].getImage(),
                                new Dimension(150, 150)));
                        gameContainer.setupBoard(settingsPanel.getRows(), settingsPanel.getColumns(),
                                playerOne, playerTwo, settingsPanel.isPlayMusic(),
                                settingsPanel.isStartO());
                        cl.show(frame.getContentPane(), "WIN");
                    }
                });
                gameContainer.setupBoard(settingsPanel.getRows(), settingsPanel.getColumns(),
                        playerOne, playerTwo, settingsPanel.isPlayMusic(),
                        settingsPanel.isStartO());
            }
        });
        return startButton;
    }

    private JPanel createInstructionsPanel()
    {
        JPanel instructionsContainer = new JPanel();
        JLabel instructionsContent = new JLabel("<html>" +
                "        <div style=\"width:200px;\">In <b>XOregio</b>, the aim of the game is to have the last laugh!" +
                "            Either X or O goes first, and marks a square." +
                "            Any squares adjacent to it that aren't already marked are also marked." +
                "            The winner is the player that makes the last move." +
                "        </div>" +
                "</html>");
        instructionsContent.setFont(ROBOTO_FONT.deriveFont(14f));
        instructionsContainer.add(instructionsContent);
        instructionsContainer.add(createReturnToMenuButton());
        return instructionsContainer;
    }

    private JButton createSwitchToGameButton(String label)
    {
        JButton switchButton = createRobotoStyledButton(label);
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                final CardLayout cl = (CardLayout) (frame.getContentPane().getLayout());
                cl.show(frame.getContentPane(), "GAME");
            }
        });
        return switchButton;

    }

    private JPanel createMenuButtonsPanel(JLabel winMessage, JLabel winTile)
    {
        XOregioSettingsPanel settings = new XOregioSettingsPanel();
        JPanel settingsContainer = new JPanel();
        JPanel menuButtons = new JPanel();
        menuButtons.setLayout(new GridLayout(1, 3));
        JButton instructionsButton = createRobotoStyledButton("Show Instructions");

        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final CardLayout cl = (CardLayout) (frame.getContentPane().getLayout());
                cl.show(frame.getContentPane(), "INST");
            }
        });

        settingsContainer.add(instructionsButton);
        settingsContainer.add(settings);

        menuButtons.add(createStartButton(settings, winMessage, winTile, "Single Player", new XOregioHumanPlayer(), new XOregioCPUPlayer()));
        menuButtons.add(createStartButton(settings, winMessage, winTile, "Multi-Player", new XOregioHumanPlayer(), new XOregioHumanPlayer()));
        menuButtons.add(settingsContainer);
        return menuButtons;
    }
    private JPanel createWinPanel(JLabel winMessage, JLabel winTile, final XOregioGamePanel gameContainer)
    {
        JPanel winContainer = new JPanel();
        JPanel endButtonContainer = new JPanel();

        endButtonContainer.setLayout(new GridLayout(1, 2));
        winContainer.setLayout(new GridLayout(0, 1));

        winContainer.add(endButtonContainer);
        winContainer.add(winTile, Component.CENTER_ALIGNMENT);
        winContainer.add(winMessage);

        winTile.setHorizontalAlignment(JLabel.CENTER);
        winMessage.setHorizontalAlignment(JLabel.CENTER);
        winMessage.setFont(ROBOTO_FONT.deriveFont(Font.BOLD, 20f));

        endButtonContainer.add(createSwitchToGameButton("Restart Game"));

        final JButton gameReturnButton = createReturnToMenuButton();
        gameReturnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameContainer.stopMusic();
            }
        });
        endButtonContainer.add(gameReturnButton);
        return winContainer;
    }
    private static JButton createRobotoStyledButton(String label)
    {
        JButton button = new JButton(label);
        button.setFont(ROBOTO_FONT.deriveFont(14f));
        return button;
    }

    public XOregio()
    {
        this.frame = new JFrame("XOregio");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameContainer = new XOregioGamePanel();

        frame.setIconImage(XOregioBoard.PLAY_IMAGES[2].getImage());

        final JLabel winMessage = new JLabel();
        final JLabel winTile = new JLabel();
        
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new GridLayout(2, 1));
        gameContainer.setLayout(new BorderLayout());

        frame.setLayout(new CardLayout());

        JLabel logo = new JLabel(getScaledImage("resource/logo.jpg", new Dimension(500, 350)));
        mainContainer.add(logo);
        mainContainer.add(createMenuButtonsPanel(winMessage, winTile));

        frame.add(mainContainer, "MENU");
        frame.add(gameContainer, "GAME");
        frame.add(createWinPanel(winMessage, winTile, gameContainer), "WIN");
        frame.add(createInstructionsPanel(), "INST");

        frame.setSize(500, 580);
        frame.setLocationRelativeTo(null); //this centers the screen
        frame.setVisible(true);
        frame.setResizable(false);

    }

    public static ImageIcon getScaledImage(String image, Dimension dimension)
    {
        //http://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(new File(image));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return XOregio.getScaledImage(img, dimension);
    }

    public static ImageIcon getScaledImage(Image image, Dimension dimension)
    {
        //http://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
        Image dimg = image.getScaledInstance(dimension.width, dimension.height,
                Image.SCALE_SMOOTH);
        return new ImageIcon(dimg);
    }

    public static void main(String[] args)
    {

        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            ROBOTO_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("resource/roboto.ttf"));
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(ROBOTO_FONT);
        } catch (Exception e) {
            System.out.println("Error occcured when setting up Look and Feel.");
        }

        new XOregio();
    }

}
