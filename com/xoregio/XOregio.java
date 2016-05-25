package com.xoregio;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class XOregio
{
    public XOregio()
    {
        JFrame frame = new JFrame("XOregio");
        XOregioBoard board = new XOregioBoard();
        JButton spButton = new JButton("Single Player");
        spButton.setFont(Font.getFont("Courier New"));
        frame.setSize(400, 480);
        board.setSize(400, 480);
        frame.add(board, BorderLayout.CENTER);
        frame.add(spButton, BorderLayout.SOUTH);
        frame.setVisible(true);

        //   JButton spButton = new JButton("Single Player");
    //    JButton mpButton = new JButton("Multi Player");

     /*   frame.add(spButton, BorderLayout.LINE_START);
        frame.add(mpButton, BorderLayout.LINE_END);
        frame.setSize(400, 480);
        frame.setVisible(true);
        spButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                new XOregioGame(new XOregioHumanPlayer(), new XOregioCPUPlayer());
            }
        });
        mpButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                new XOregioGame(); //defaults to 2 human players
            }
        });*/
    }
    public static void main(String[] args)
    {
        new XOregio();
    }
}
