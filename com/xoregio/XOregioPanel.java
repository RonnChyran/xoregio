package com.xoregio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Represents a JPanel that contains a XOregioBoard.
 * Allows reinitialization of the board (reset) by
 * recreating the instance of XOregioBoard (which is difficult to reset to default state).
 */
public class XOregioPanel extends JPanel
{
    public XOregioBoard board = null;
    public XOregioBoardListener listener = null;
    private JLabel message;
    private Clip backgroundMusic;
    public XOregioPanel()
    {
        this.message = new JLabel("", SwingConstants.CENTER);
        this.message.setFont(RobotoFont.ROBOTO_FONT.deriveFont(14f));
    }

    /**
     * Starts the background music.
     */
    public void startMusic()
    {
        try
        {
            File f = new File("Elevator_Music.wav");
            this.backgroundMusic = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(f)));
            backgroundMusic.open(inputStream);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY); //loop forever
            backgroundMusic.start();
        }
        catch(Exception e)
        {
            System.out.println("Unable to play music");
        }
    }

    /**
     * Stops the background music.
     */
    public void stopMusic()
    {
        if(this.backgroundMusic != null && this.backgroundMusic.isRunning())
            backgroundMusic.stop();
    }

    /**
     * Sets the status message at the bottom of the board
     * @param text The message to set
     */
    public void setMessage(String text)
    {
        this.message.setText(text);
    }

    /**
     * Sets the instance of the listener that will be re-attached to
     * all instances of XOregioBoard initialized by this instance of XOregioPanel
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
                           boolean playMusic,
                           boolean startAsO)
    {
        if(this.board != null) //remove the instance currently attached to the panel
        {
            this.remove(board);
            this.remove(message);
        }
        if((this.backgroundMusic == null || !this.backgroundMusic.isRunning()) && playMusic)
        {
            this.startMusic(); //start the music if playMusic is specified, and only if the music is not already running
        }
        this.board = new XOregioBoard(rows, cols, playerX, playerO, startAsO); //initialize a new board
        if(this.listener != null) this.board.setBoardListener(this.listener); //re-attach the board listener
        message.setText((board.xTurn ? "X" : "O")  + "'s Turn | Turn " + board.turnCount); //set the default message
        this.add(board); //add the board to the panel
        this.add(message, BorderLayout.PAGE_END); //add the message to the end of the panel
        this.revalidate(); //revalidate must be called if the panel has been shown previously
    }
}
