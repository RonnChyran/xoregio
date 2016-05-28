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
    private Clip backgroundMusic;
    public XOregioPanel()
    {
        this.message = new JLabel("", SwingConstants.CENTER);
        this.message.setFont(RobotoFont.ROBOTO_FONT.deriveFont(14f));
    }

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

    public void stopMusic()
    {
        if(this.backgroundMusic != null && this.backgroundMusic.isRunning())
            backgroundMusic.stop();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);// Draw the background image.
        Rectangle r = this.getBounds();
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
        if((this.backgroundMusic == null || !this.backgroundMusic.isRunning()) && playMusic)
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
