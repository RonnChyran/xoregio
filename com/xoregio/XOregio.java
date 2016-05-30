/*	File Name:   XOregio.java
	Name:        Ronny Chan and Gerald Ma
	Class:       ICS3U1-01 (B)
	Date:        May 29, 2016
	Description: Contains the main user interface window for the XOregio game.
	             This file starts the game off and gives it a window.
*/

package com.xoregio;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Represents the main Swing UI that starts an XOregio game
 */
public class XOregio
{
    /**
     * Custom font for the UI
     * (loaded from Roboto.ttf)
     */
    public static Font ROBOTO_FONT = null;

    /**
     * The main frame (window) of the application
     */
    private JFrame frame;

    /**
     * The panel (extends JPanel) that contains our XOregio Game.
     */
    private XOregioGamePanel gameContainer;

    public static void main(String[] args)
    {
        //Attempt to set up look and feel of the UI
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //use the windows-style UI

            ROBOTO_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("resource/roboto.ttf")); //load Roboto font
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(ROBOTO_FONT); //register the roboto font for use.

        }
        catch (Exception e)
        {
            System.out.println("Error occcured when setting up Look and Feel.");
            ROBOTO_FONT = Font.decode(Font.SANS_SERIF); //fallback to the default sans-serif font if font loading fails
        }

        new XOregio(); //initialize the XOregio instance
    } // main

    public XOregio()
    {
        //component initialization
        this.frame = new JFrame("XOregio"); //initialize the window frame
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //make the process exit on closing the window
        this.gameContainer = new XOregioGamePanel(); /* initialize the game panel that contains the game.
                                                        This does not yet set up the board, it is lazy and only
                                                        sets up the board when the user starts the game */

        final JLabel winMessage = new JLabel(); /* The message displayed when someone wins.
                                                   We need a reference to it to be able to set it later */

        final JLabel winImage = new JLabel(); /* The picture of the winner (either X or O).
                                                 We need a reference to it to be able to set it later */

        JPanel mainMenuContainer = new JPanel(); //the main panel containing all the main menu components

        //component setup
        mainMenuContainer.setLayout(new GridLayout(2, 1)); //This grid layout gives it a layout of 2 rows and 1 column
        gameContainer.setLayout(new BorderLayout()); //Gives the game container the default layout
        frame.setLayout(new CardLayout()); /* A card layout allows the window to display different child panels
                                              in the same parent (the window). We have 4 different child panels,
                                              'MENU' for the main menu,
                                              'GAME' for the actual game,
                                              'WIN', for the win screen, and
                                              'INST' for the instruction screen*/

        /* set the logo of the game, scaled to the full width of the window (500), and half the height of
           the window (290)*/
        JLabel logo = new JLabel(getScaledImage("resource/logo.jpg", new Dimension(500, 290)));
        mainMenuContainer.add(logo); //add the logo to the main container
        mainMenuContainer.add(createMenuButtonsPanel(winMessage, winImage)); /* create the main menu buttons
                                                                                and add it to the container */

        //adding components to the main window
        frame.add(mainMenuContainer, "MENU"); //add the main menu container as 'MENU'
        frame.add(gameContainer, "GAME"); //add the game container as 'GAME'
        frame.add(createWinPanel(winMessage, winImage), "WIN"); //create and add the win screen as 'WIN'
        frame.add(createInstructionsPanel(), "INST"); //create and add the instruction screen as 'INST'

        //frame setup
        frame.setIconImage(XOregioBoard.PLAY_IMAGES[2].getImage()); /* set the taskbar icon as the 'O' from
                                                                       our game images */
        frame.setSize(500, 580); //set the size to 500 by 580
        frame.setLocationRelativeTo(null); //Setting location relative to null centers the window
        frame.setVisible(true); //set it visible
        frame.setResizable(false); //prevent resizing

    } // XOregio constructor

    /**
     * Creates a button that returns to the main menu panel 'MENU'
     * @return A button that, when clicked, returns to the main menu panel 'MENU'
     */
    private JButton createReturnToMenuButton()
    {
        JButton returnButton = createRobotoStyledButton("Return to Main Menu"); //create a button with the label text
        returnButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) //happens when the button is clicked
            {
                final CardLayout cl = (CardLayout) (frame.getContentPane().getLayout()); //get the layout of the window
                cl.show(frame.getContentPane(), "MENU"); //when the button is clicked, show the 'MENU' frame.
            }
        });
        return returnButton; //return the assembled button.
    } //createReturnToMenuButton

    /**
     * Creates a button that sets up the instance of XOregioGamePanel
     * @param settingsPanel The XOregioSettingsPanel that determines the parameters for this game instance
     * @param winMessage The label that shows the win message
     * @param winImage The label that shows the image of the player who won, either 'X' or 'O'
     * @param label The label of the start button
     * @param playerOne The XOregioPlayer instance for player one
     * @param playerTwo The XOregioPlayer instance for player two
     * @return A button that, when clicked, sets up the game container for a game of XOregio given the parameters the
     *         user gave from the settings panel.
     *
     * @see XOregioSettingsPanel The implementation of the settings panel
     * @see XOregioPlayer Represents a player of XOregio
     * @see XOregioHumanPlayer The implementation of a human player
     * @see XOregioCPUPlayer The implementation of a CPU player
     */
    private JButton createStartButton(final XOregioSettingsPanel settingsPanel,
                                      final JLabel winMessage,
                                      final JLabel winImage,
                                      String label,
                                      final XOregioPlayer playerOne,
                                      final XOregioPlayer playerTwo)
    {
        JButton startButton = createSwitchToGameButton(label); //creates a button that switches to the 'GAME' view
        startButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent) //happens when the button is clicked
            {
                final CardLayout cl = (CardLayout) (frame.getContentPane().getLayout()); //get the layout of the window
                gameContainer.setBoardListener(new XOregioBoardListener()
                {
                    @Override
                    public void turnChanged(XOregioPlayer player) //this happens when the turn is changed
                    {
                        //update the current turn message, and the turn count.
                        gameContainer.setStatusText((gameContainer.board.xTurn ? "X" : "O") + "'s Turn | Turn "
                                + gameContainer.board.turnCount);
                    }

                    @Override
                    public void gameWin(boolean isXWin) //this happens when the game is won.
                    {
                        winMessage.setText((isXWin ? "X" : "O") + " Wins!"); //set the text to the proper winner
                        winImage.setIcon(getScaledImage(isXWin ? XOregioBoard.PLAY_IMAGES[1].getImage()
                                        : XOregioBoard.PLAY_IMAGES[2].getImage(),
                                        new Dimension(150, 150)));  //display the image of the winning player

                        /* prepare for a restart by resetting the board in the background with the same
                           parameters */
                        gameContainer.setupBoard(settingsPanel.getRows(), settingsPanel.getColumns(),
                                playerOne, playerTwo, settingsPanel.isMusicEnabled(),
                                settingsPanel.isOStartsFirst());

                        //show the win screen
                        cl.show(frame.getContentPane(), "WIN");
                    }
                });
                /* Set up the board with the given parameters */
                gameContainer.setupBoard(settingsPanel.getRows(), settingsPanel.getColumns(),
                        playerOne, playerTwo, settingsPanel.isMusicEnabled(),
                        settingsPanel.isOStartsFirst());
            }
        });
        return startButton; //return the constructed start button
    } //createStartButton

    /**
     * Creates a panel that shows instructions on how to play the game
     * @return A JPanel that shows instructions on how to play the game
     */
    private JPanel createInstructionsPanel()
    {
        JPanel instructionsContainer = new JPanel(); //initialize the JPanel

        /* JLabel is very difficult to set the width of properly for multi-line text
           However, it supports rendering basic HTML. Because of that, we can use that ability
           to wrap the text with a fixed with using CSS styling.*/
        JLabel instructionsContent = new JLabel("<html>" +
                "        <div style=\"width:200px;\">In <b>XOregio</b>, the aim of the game is to have the last laugh!" +
                "            Either X or O goes first, and marks a square." +
                "            Any squares adjacent to it that aren't already marked are also marked." +
                "            The winner is the player that makes the last move." +
                "        </div>" +
                "</html>");
        instructionsContent.setFont(ROBOTO_FONT.deriveFont(14f)); //sets the font to roboto
        instructionsContainer.add(instructionsContent); //add the instructions to the panel
        instructionsContainer.add(createReturnToMenuButton()); //add a button to return to the main menu
        return instructionsContainer; //return the constructed panel
    } //createInstructionsPanel

    /**
     * Creates a button that switches to the 'GAME' panel in the frame CardLayout.
     * @param label The label to display on the button.
     * @return A button that switches to the game panel.
     */
    private JButton createSwitchToGameButton(String label)
    {
        JButton switchButton = createRobotoStyledButton(label); //create a Roboto styled button
        switchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent) //happens when the button is clicked
            {
                final CardLayout cl = (CardLayout) (frame.getContentPane().getLayout()); //get the cardlayout
                cl.show(frame.getContentPane(), "GAME"); //show the 'GAME' panel
            }
        });
        return switchButton; //return the constructed button
    } // createSwitchToGameButton

    /**
     * Creates a panel that contains the main menu buttons, including the 2 start buttons and
     * the settings buttons.
     * @param winMessage A label to display text when the game is won.
     * @param winImage A label to display a picture of the winner when the game is won.
     * @return The panel that contains the main menu buttons.
     */
    private JPanel createMenuButtonsPanel(JLabel winMessage, JLabel winImage)
    {
        //component initialization
        XOregioSettingsPanel settings = new XOregioSettingsPanel(); //initialize the settings panel
        JPanel settingsContainer = new JPanel(); //a container that contains the instructions and the settings
        JPanel menuContainer = new JPanel(); //a container that contains the menu.

        menuContainer.setLayout(new GridLayout(1, 3)); //set the layout of the menu container to 1 row, 3 columns

        JButton instructionsButton = createRobotoStyledButton("Show Instructions"); /* create a Roboto styled
                                                                                       button to show instructions */

        instructionsButton.addActionListener(new ActionListener() //happens when the button is clicked
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                final CardLayout cl = (CardLayout) (frame.getContentPane().getLayout()); //get the cardlayout
                cl.show(frame.getContentPane(), "INST"); //show the instruction panel.
            }
        });

        settingsContainer.add(instructionsButton); //add the instructions showing button to the settings container
        settingsContainer.add(settings); //add the settings panel to the settings container

        //For single player, the user plays with the CPU.
        menuContainer.add(createStartButton(settings, winMessage, winImage, "Single Player",
                                            new XOregioHumanPlayer(), new XOregioCPUPlayer()));

        //For multi-player, the user plays with another user
        menuContainer.add(createStartButton(settings, winMessage, winImage, "Multi-Player",
                                            new XOregioHumanPlayer(), new XOregioHumanPlayer()));

        menuContainer.add(settingsContainer); //add the settings to the menu

        return menuContainer; //return the constructed menu
    } // createMenuButtonsPanel

    /**
     * Create the panel that is shown when the game is won.
     * @param winMessage A label to display text when the game is won.
     * @param winImage A label to display a picture of the winner when the game is won.
     * @return The panel shown when the game is won.
     */
    private JPanel createWinPanel(JLabel winMessage, JLabel winImage)
    {
        JPanel winContainer = new JPanel(); //initialize the panel
        JPanel endButtonContainer = new JPanel(); /* initialize a container that contains the 2 buttons on what to do
                                                     after the game is won. */

        endButtonContainer.setLayout(new GridLayout(1, 2)); //set the layout to 1 row, 2 columns
        winContainer.setLayout(new GridLayout(0, 1)); //set the layout to single-column

        endButtonContainer.add(createSwitchToGameButton("Restart Game")); /* create a button that simply goes back
                                                                             to the game panel. Because the game state
                                                                             has already been set up for us before-hand,
                                                                             we do not need to worry about set up when
                                                                             returning to the game after it has been
                                                                             won. */

        final JButton menuReturnButton = createReturnToMenuButton(); //create a return to menu button
        menuReturnButton.addActionListener(new ActionListener() //happens when the button is clicked
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                gameContainer.stopMusic(); //stop the music in the main menu.
            }
        });
        endButtonContainer.add(menuReturnButton); //add the menu return button to the container

        winContainer.add(endButtonContainer); //add the button container
        winContainer.add(winImage); //add the image
        winContainer.add(winMessage); //add the message

        winImage.setHorizontalAlignment(JLabel.CENTER); //make the image centered
        winMessage.setHorizontalAlignment(JLabel.CENTER); //make the message centered
        winMessage.setFont(ROBOTO_FONT.deriveFont(Font.BOLD, 20f)); //make the win message font bigger and bold

        return winContainer; //return the constructed win screen
    } // createWinPanel

    /**
     * Creates a button with text in 14pt Roboto font
     * @param label The label of the button
     * @return A button with text in 14pt Roboto font
     */
    private static JButton createRobotoStyledButton(String label)
    {
        JButton button = new JButton(label); //create a new button with the specified lable
        button.setFont(ROBOTO_FONT.deriveFont(14f)); //set the font to 14pt Roboto
        return button; //return the button
    } // createRobotoStyledButton

    /**
     * Smoothy scales an image with high quality to the specified dimension
     * @param image The path to the image to scale
     * @param dimension The dimensions of the new image
     * @return The scaled image
     */
    public static ImageIcon getScaledImage(String image, Dimension dimension)
    {
        //http://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
        BufferedImage img = null; //assign a new BufferedImage to read the image data into
        try
        {
            img = ImageIO.read(new File(image)); //attempt to read the file into the image
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Unable to load image");
        }
        return XOregio.getScaledImage(img, dimension); //scale the image object
    } // getScaledImage

    /**
     * Smoothy scales an image with high quality to the specified dimension
     * @param image The Image object to scale
     * @param dimension The dimensions of the new image
     * @return The scaled image
     */
    public static ImageIcon getScaledImage(Image image, Dimension dimension)
    {
        Image scaledImage = image.getScaledInstance(dimension.width, dimension.height,
                Image.SCALE_SMOOTH); //scale the image with smooth scaling mode
        return new ImageIcon(scaledImage); //return the image in the form of an ImageIcon
    } // getScaledImage

} //XOregio class

/**
 * Represents the menu panel with settings options
 */
class XOregioSettingsPanel extends JPanel
{

    /**
     * A check box that determines whether music is to be played
     */
    private JCheckBox playMusic;

    /**
     * A check box that determines whether O starts the game or not
     */
    private JCheckBox startO;

    /**
     * A dropdown box that determines the number of rows in the board
     */
    private JComboBox<Integer> rows;

    /**
     * A dropdown box that determines the number of columns in the board
     */
    private JComboBox<Integer> cols;

    /**
     * A finite list of grid sizes to display in the rows and columns drop down.
     * We use the boxed int type Integer instead of primitive ints because JComboBox does not
     * support primitive arrays.
     */
    private final Integer[] GRID_SIZES = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public XOregioSettingsPanel()
    {
        JPanel comboBoxContainer = new JPanel(); //the container that contains the rows and columns dropdown together
        comboBoxContainer.setLayout(new GridLayout(0, 2)); /* give it a grid layout with 2 columns, one for the label
                                                              and one for the actual dropdown */

        this.setLayout(new GridLayout(0, 1)); // give the settings panel a single-column layout.

        //component initialization
        this.playMusic = new JCheckBox("Play Music"); //the checkbox that determines if music is played
        JLabel rowLabel = new JLabel("Rows: "); //label for rows dropdown
        JLabel columnLabel = new JLabel("Columns: "); //label for columns dropdown

        /* initialize the rows and columns dropdowns using grid sizes using
           as generic Integer combo boxes */
        this.rows = new JComboBox<>(GRID_SIZES);
        this.cols = new JComboBox<>(GRID_SIZES);

        this.startO = new JCheckBox("O goes First"); //the checkbox that determines if O goes first

        //Setting the fonts of all components to 14pt Roboto
        rowLabel.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));
        columnLabel.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));
        this.playMusic.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));
        this.rows.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));
        this.cols.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));
        this.startO.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));

        //Sets the default options for columns and rows
        this.cols.setSelectedIndex(3); //index 3 is 4 columns
        this.rows.setSelectedIndex(3); //index 3 is 4 rows
        this.playMusic.setSelected(true); //enable music by default

        comboBoxContainer.add(rowLabel); //add the row label to the dropdown container
        comboBoxContainer.add(rows); //add the row dropdown to the dropdown container
        comboBoxContainer.add(columnLabel); //add the column label to the dropdown container
        comboBoxContainer.add(cols); //add the column dropdown to the dropdown container

        this.add(playMusic); //add the play music checkbox to the settings panel
        this.add(startO); //add the 'O' starts first checkbox to the settings panel
        this.add(comboBoxContainer); //add the dropdown container to the settings panel
    } // XOregioSettingsPanel constructor

    /**
     * Gets whether or not music is enabled by getting the state of the playMusic check box.
     * @return Whether or not music is enabled
     */
    public boolean isMusicEnabled()
    {
        return this.playMusic.isSelected();
    } //isMusicEnabled

    /**
     * Gets whether or not O starts first by getting the state of the startO check box
     * @return Whether or not O starts first
     */
    public boolean isOStartsFirst()
    {
        return this.startO.isSelected();
    } //isOStartsFirst

    /**
     * Gets the amount of rows the user selected
     * @return The amount of rows the user selected
     */
    public int getRows()
    {
        return (int)this.rows.getSelectedItem(); //unbox the object into a primitive int
    } //getRows

    /**
     * Gets the amount of columns the user selected
     * @return The amount of columns the user selected
     */
    public int getColumns()
    {
        return (int)this.cols.getSelectedItem(); //unbox the object into a primitive int
    } //getColumns
} //XOregioSettingsPanel class

/**
 * Represents a game panel with options
 */
class XOregioGamePanel extends JPanel
{
    /**
     * The XOregioBoard instance that represents the board
     */
    public XOregioBoard board = null;

    /**
     * The board listener that will persist across multiple instances of XOregioBoard
     */
    public XOregioBoardListener listener = null;

    /**
     * The message displayed in the bottom of the game screen
     */
    private JLabel message;

    /**
     * The background music played
     */
    private Clip backgroundMusic;

    public XOregioGamePanel()
    {
        this.message = new JLabel("", SwingConstants.CENTER);
        this.message.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));
    } // XOregioGamePanel constructor

    /**
     * Starts the background music.
     */
    public void startMusic()
    {
        try
        {
            File soundFile = new File("resource/Elevator_Music.wav"); //load the music from file
            this.backgroundMusic = AudioSystem.getClip(); //get an audio clip from the OS
            AudioInputStream inputStream = AudioSystem //load the file as an audio stream
                    .getAudioInputStream(new BufferedInputStream(new FileInputStream(soundFile)));
            backgroundMusic.open(inputStream); //load the audio steam into the clip
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY); //loop forever
            backgroundMusic.start(); //start te clip
        }
        catch(Exception e)
        {
            System.out.println("Unable to play music");
        }
    } // startMusic

    /**
     * Stops any currently playing background music.
     */
    public void stopMusic()
    {
        /* if the background music was previously loaded, and is currently running, only then will we
        attempt to stop it */
        if(this.backgroundMusic != null && this.backgroundMusic.isRunning())
            backgroundMusic.stop();
    } // stopMusic

    /**
     * Sets the status message at the bottom of the board
     * @param text The message to set
     */
    public void setStatusText(String text)
    {
        this.message.setText(text); /* instead of exposing the entire JLabel, we will just expose this one method
                                       that changes the status text */
    }

    /**
     * Sets the instance of the listener that will be re-attached to
     * all instances of XOregioBoard initialized by this instance of XOregioGamePanel
     * @param boardListener
     */
    public void setBoardListener(XOregioBoardListener boardListener)
    {
        this.listener = boardListener;
    }

    /**
     * Sets up and displays an XOregioBoard
     * @param rows The number of rows in the play area
     * @param cols The number of columns in the play area
     * @param playerX The player instance representing 'X'
     * @param playerO The player instance representing 'O'
     * @param startAsO Whether or not O goes first.
     * @param playMusic Whether or not to play background music
     */
    public void setupBoard(int rows, int cols, XOregioPlayer playerX, XOregioPlayer playerO,
                           boolean playMusic, boolean startAsO)
    {
        if (this.board != null) //if the board was previously initialized
        {
            this.remove(board); //remove the previous board instance from the panel
            this.remove(message); //remove the previous status message instance from the pannel.
        }

        if ((this.backgroundMusic == null || !this.backgroundMusic.isRunning()) && playMusic)
        {
            this.startMusic(); /* Start the audio if it was previously loaded,
                                  only if the music is not already running (preventing duplicate audio) and only if
                                  the user chose to play music. */
        }
        this.board = new XOregioBoard(rows, cols, playerX, playerO, startAsO); //initialize a new board

        if (this.listener != null)
            this.board.setBoardListener(this.listener); /* re-attach the board listener to the new instance of the
                                                           XOregioBoard */

        message.setText((board.xTurn ? "X" : "O")  + "'s Turn | Turn " + board.turnCount); //set the default message
        this.add(board); //add the board to the panel
        this.add(message, BorderLayout.PAGE_END); //add the message to the end of the panel
        this.revalidate(); //revalidate must be called if the panel has been shown previously
    }
} //XOregioGamePanel class
