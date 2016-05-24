package com.xoregio;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class XOregio
{
    public XOregio()
    {
        JFrame frame = new JFrame("XOregio");
        JButton spButton = new JButton("Single Player");
        JButton mpButton = new JButton("Multi Player");

        frame.add(spButton, BorderLayout.LINE_START);
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
        });
    }
    public static void main(String[] args)
    {
        new XOregio();
    }
}
