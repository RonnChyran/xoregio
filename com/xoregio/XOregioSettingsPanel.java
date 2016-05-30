//	File Name:   XOregioSettingsPanel.java
//	Name:        Ronny Chan and Gerald Ma
//	Class:       ICS3U1-01 (B)
//	Date:        May 29, 2016
//	Description: 

package com.xoregio;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a JPanel with various components that determine the parameters used
 * when setting the game up.
 */
public class XOregioSettingsPanel extends JPanel
{

    /**
     * A check box that determines whether music is to be played
     */
    private JCheckBox playMusic;

    /**
     * A check box that determines whether O starts the game or not
     */
    private JCheckBox startO;

    /**
     * A dropdown box that determines the number of rows in the board
     */
    private JComboBox<Integer> rows;

    /**
     * A dropdown box that determines the number of columns in the board
     */
    private JComboBox<Integer> cols;

    /**
     * A finite list of grid sizes to display in the rows and columns drop down.
     * We use the boxed int type Integer instead of primitive ints because JComboBox does not
     * support primitive arrays.
     */
    private final Integer[] GRID_SIZES = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public XOregioSettingsPanel()
    {
        JPanel comboBoxContainer = new JPanel(); //the container that contains the rows and columns dropdown together
        comboBoxContainer.setLayout(new GridLayout(0, 2)); /* give it a grid layout with 2 columns, one for the label
                                                              and one for the actual dropdown */

        this.setLayout(new GridLayout(0, 1)); // give the settings panel a single-column layout.

        //component initialization
        this.playMusic = new JCheckBox("Play Music"); //the checkbox that determines if music is played
        JLabel rowLabel = new JLabel("Rows: "); //label for rows dropdown
        JLabel columnLabel = new JLabel("Columns: "); //label for columns dropdown

        //initialize the rows and columns dropdowns using grid sizes
        this.rows = new JComboBox(GRID_SIZES);
        this.cols = new JComboBox(GRID_SIZES);

        this.startO = new JCheckBox("O goes First"); //the checkbox that determines if O goes first

        //Setting the fonts of all components to 14pt Roboto
        rowLabel.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));
        columnLabel.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));
        this.playMusic.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));
        this.rows.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));
        this.cols.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));
        this.startO.setFont(XOregio.ROBOTO_FONT.deriveFont(14f));

        //Sets the default options for columns and rows
        this.cols.setSelectedIndex(3); //index 3 is 4 columns
        this.rows.setSelectedIndex(3); //index 3 is 4 rows
        this.playMusic.setSelected(true); //enable music by default

        comboBoxContainer.add(rowLabel); //add the row label to the dropdown container
        comboBoxContainer.add(rows); //add the row dropdown to the dropdown container
        comboBoxContainer.add(columnLabel); //add the column label to the dropdown container
        comboBoxContainer.add(cols); //add the column dropdown to the dropdown container

        this.add(playMusic); //add the play music checkbox to the settings panel
        this.add(startO); //add the 'O' starts first checkbox to the settings panel
        this.add(comboBoxContainer); //add the dropdown container to the settings panel
    }

    /**
     * Gets whether or not music is enabled by getting the state of the playMusic check box.
     * @return Whether or not music is enabled
     */
    public boolean isMusicEnabled()
    {
        return this.playMusic.isSelected();
    }

    /**
     * Gets whether or not O starts first by getting the state of the startO check box
     * @return Whether or not O starts first
     */
    public boolean isOStartsFirst()
    {
        return this.startO.isSelected();
    }

    /**
     * Gets the amount of rows the user selected
     * @return The amount of rows the user selected
     */
    public int getRows()
    {
        return (int)this.rows.getSelectedItem(); //unbox the object into a primitive int
    }

    /**
     * Gets the amount of columns the user selected
     * @return The amount of columns the user selected
     */
    public int getColumns()
    {
        return (int)this.cols.getSelectedItem(); //unbox the object into a primitive int
    }
}
