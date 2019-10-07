/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.image.UI;

import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Samrand
 */
public class TabbedPanel extends JPanel{
    private BufferedImage image;
    private String name;

    TabbedPanel(BufferedImage image,String name) {
        super(new BorderLayout());
        this.image = image;
        add(new JLabel(name, SwingConstants.CENTER),"North");
        add(new JLabel(new ImageIcon(image)),"Center");
        this.name = name;
    }
    
    public BufferedImage getImage(){
        return image;
    }
    
    public String getName(){
        return name;
    }
    
    public int getRGB(int x,int y){
        return 0;
    }
    
}
