//	File Name:   XOregioSettingsPanel.java
//	Name:        Ronny Chan and Gerald Ma
//	Class:       ICS3U1-01 (B)
//	Date:        May 29, 2016
//	Description: 

package com.xoregio;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ronny on 2016-05-29.
 */
public class XOregioSettingsPanel extends JPanel
{

    private JCheckBox playMusic;
    private JCheckBox startO;
    private JComboBox<Integer> rows;
    private JComboBox<Integer> cols;
    private final Integer[] GRID_SIZES = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    public XOregioSettingsPanel()
    {
        final JPanel comboBoxContainer = new JPanel();
        comboBoxContainer.setLayout(new GridLayout(3, 2));
        this.setLayout(new GridLayout(0, 1));

        this.playMusic = new JCheckBox("Play Music");
        this.playMusic.setSelected(true);
        JLabel rowLabel = new JLabel("Rows: ");
        JLabel columnLabel = new JLabel("Columns: ");
        this.rows = new JComboBox<>(GRID_SIZES);
        this.cols = new JComboBox<>(GRID_SIZES);
        this.startO = new JCheckBox("O goes First");
        rowLabel.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));
        columnLabel.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));
        this.playMusic.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));
        this.rows.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));
        this.cols.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));
        this.startO.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));
        this.cols.setSelectedIndex(3);
        this.rows.setSelectedIndex(3);

        comboBoxContainer.add(rowLabel);
        comboBoxContainer.add(rows);
        comboBoxContainer.add(columnLabel);
        comboBoxContainer.add(cols);

        this.add(playMusic);
        this.add(startO);
        this.add(comboBoxContainer);
    }

    public boolean isPlayMusic()
    {
        return this.playMusic.isSelected();
    }

    public boolean isStartO()
    {
        return this.startO.isSelected();
    }

    public int getRows()
    {
        return (int)this.rows.getSelectedItem();
    }

    public int getColumns()
    {
        return (int)this.cols.getSelectedItem();
    }
}
