package com.xoregio;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/*
todo: Single Player/Multi Player selection
todo: Start as X and y (checkbox)
todo: Amount of Rows and Columns
todo: Turn message and turn number keeper
 */
public class XOregio
{
    public XOregio()
    {
        final JFrame frame = new JFrame("XOregio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JPanel menuContainer =  new JPanel();
        final JPanel gameContainer = new JPanel();
        final JPanel menuButtons = new JPanel();

        frame.setLayout(new CardLayout());
        menuContainer.setLayout(new GridLayout(2,1));
        menuButtons.setLayout(new GridLayout(1, 2));
        gameContainer.setLayout(new GridLayout(0,1));

        JButton spButton = new JButton("Start Single Player");
        JButton mpButton = new JButton("Start Multi Player");
        menuButtons.add(spButton);
        menuButtons.add(mpButton);


        JLabel logo = new JLabel(new ImageIcon("logo.png"));
        logo.setMinimumSize(new Dimension(400, 240));
        menuContainer.add(logo);
        menuContainer.add(menuButtons);

        frame.add(menuContainer, "MENU");
        frame.add(gameContainer, "GAME");

        frame.setSize(400, 480);
        frame.setVisible(true);
        spButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                CardLayout cl = (CardLayout)(frame.getContentPane().getLayout());
                gameContainer.add(new XOregioBoard(5, 5, new XOregioHumanPlayer(), new XOregioCPUPlayer()));
                gameContainer.add(new JLabel("Hello World"));
                cl.show(frame.getContentPane(), "GAME");
            }
        });
        mpButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                CardLayout cl = (CardLayout)(frame.getContentPane().getLayout());
                gameContainer.add(new XOregioBoard(5, 5, new XOregioHumanPlayer(), new XOregioHumanPlayer()));
                cl.show(frame.getContentPane(), "GAME");
            }
        });
    }
    public static void main(String[] args)
    {
        new XOregio();
    }
}
