/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.image.algorithm;

import java.awt.Color;
import java.awt.image.BufferedImage; 
import java.util.Arrays; 
/**
 *
 * @author Samrand
 */
public class Threashold {
    
   private static final int BLACK = Color.BLACK.getRGB();
   private static final int WHITE = Color.WHITE.getRGB();
   private BufferedImage image;
   
   public Threashold(BufferedImage image){
       this.image = image;
   }
   
   public BufferedImage calculate(int t1,int t2){
       int h = image.getHeight();
       int w = image.getWidth();
       BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY); 
       
       for(int y=0;y<h;y++){
           for(int x=0;x<w;x++){
               int rgb = image.getRGB(x, y);
               if(rgb >= t1 && rgb <= t2)
                   img.setRGB(x, y, WHITE);
               else
                   img.setRGB(x, y, BLACK);
           }
       }
       return img;
   }
    
    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
    
}
