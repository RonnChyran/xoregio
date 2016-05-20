package com.xoregio;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class XOregio
{
    public XOregio()
    {
        JFrame frame = new JFrame("XOregio");
        JButton button = new JButton("Hello World");
        frame.add(button);
        frame.setSize(400, 480);
        frame.setVisible(true);
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                new XOregioGame();
            }
        });
    }
    public static void main(String[] args)
    {
        new XOregio();
    }
}
