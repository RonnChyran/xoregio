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
        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(1, 2));
        JLabel logo = new JLabel(new ImageIcon("logo.png"));

        JButton spButton = new JButton("Start Single Player");
        JButton mpButton = new JButton("Start Multi Player");
        logo.setPreferredSize(new Dimension(400, 240));

        frame.setLayout(new GridLayout(2,1));
        frame.add(logo);

        menu.add(spButton);
        menu.add(mpButton);

        frame.add(menu);



        //
        //
        //
      /*  JButton b = new JButton("Just fake button");
        Dimension buttonSize = b.getPreferredSize();
        frame.setPreferredSize(new Dimension(400,480));
        frame.add(new JButton("Button 1"));
        frame.add(new JButton("Button 2"));
        frame.add(new JButton("Button 3"));
        frame.add(new JButton("Long-Named Button 4"));
        frame.add(new JButton("5"));*/
        frame.setSize(400, 480);
        frame.setVisible(true);
      /*  JPanel gamePanel = new JPanel(new BorderLayout());
        XOregioBoard board = new XOregioBoard();
        JLabel message = new JLabel();
        message.setSize(400, 30);
        message.setText("Hello World");
        JButton spButton = new JButton("Single Player");
        spButton.setFont(Font.getFont("Courier New"));
        board.setSize(400, 480);
        board.setMaximumSize(new Dimension(400, 480));
        gamePanel.add(board, BorderLayout.CENTER);
        gamePanel.add(message, BorderLayout.CENTER);
        gamePanel.setSize(400, 480);
        frame.setMinimumSize(new Dimension(gamePanel.getWidth(), gamePanel.getHeight() + 50));
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(spButton, BorderLayout.SOUTH);
        frame.add(message, BorderLayout.SOUTH);
        gamePanel.setVisible(false);
        frame.pack();
        frame.setVisible(true);

*/
    }
    public static void main(String[] args)
    {
        new XOregio();
    }
}
