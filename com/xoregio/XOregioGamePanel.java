//	File Name:   XOregioGamePanel.java
//	Name:        Ronny Chan and Gerald Ma
//	Class:       ICS3U1-01 (B)
//	Date:        May 29, 2016
//	Description: 

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
public class XOregioGamePanel extends JPanel
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
    }

    /**
     * Starts the background music.
     *
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
    }

    /**
     * Stops any currently playing background music.
     */
    public void stopMusic()
    {
        /* if the background music was previously loaded, and is currently running, only then will we
        attempt to stop it */
        if(this.backgroundMusic != null && this.backgroundMusic.isRunning())
            backgroundMusic.stop();
    }

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
}
