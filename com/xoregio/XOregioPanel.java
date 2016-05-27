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
 * Created by Ronny on 2016-05-27.
 */
public class XOregioPanel extends JPanel
{
    public XOregioBoard board = null;
    public XOregioBoardListener listener = null;
    private JLabel message;
    Clip backgroundMusic = null;
    public void startMusic()
    {
        try
        {
            File f = new File("Elevator_Music.wav");
            this.backgroundMusic = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(f)));
            backgroundMusic.open(inputStream);
            backgroundMusic.start();
        }
        catch(Exception e)
        {
            System.out.println("Unable to play music");
        }
    }

    public void stopMusic()
    {
        if(this.backgroundMusic != null)
            backgroundMusic.stop();
    }
    public XOregioPanel()
    {
        this.message = new JLabel("", SwingConstants.CENTER);
    }

    public void setMessage(String text)
    {
        this.message.setText(text);
    }
    public void setBoardListener(XOregioBoardListener boardListener)
    {
        this.listener = boardListener;
    }
    public void setupBoard(int rows, int cols, XOregioPlayer playerX, XOregioPlayer playerO, boolean playMusic, boolean startO)
    {
        if(this.board != null)
        {
            this.remove(board);
            this.remove(message);
        }
        if(this.backgroundMusic == null && playMusic)
        {
            this.startMusic();
        }
        this.board = new XOregioBoard(rows, cols, playerX, playerO, startO);
        if(this.listener != null) this.board.setBoardListener(this.listener);
        message.setText((board.isXTurn() ? "X" : "O")  + "'s Turn | Turn " + board.getTurnCount());
        this.add(board);
        this.add(message, BorderLayout.PAGE_END);
        this.revalidate();
    }
}
