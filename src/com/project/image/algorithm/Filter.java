/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.image.algorithm;

import java.awt.image.BufferedImage;

/**
 *
 * @author Samrand
 */
public class Filter {
    
    public static BufferedImage grayScale(BufferedImage image){
        return grayScale(image, "l");
    }
    
    public static BufferedImage grayScale(BufferedImage image,String type){
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height,  
                                    BufferedImage.TYPE_BYTE_GRAY); 
        for( int y = 0 ; y < height ; y++ ) {
            for( int x = 0 ; x < width ; x++ ){
                int rgb = image.getRGB(x, y);
                resultImage.setRGB(x, y, findGray(rgb,type));
            }
        }
        return resultImage;
    }
    
    private static int findGray(int rgb,String type){
        int Red   = rgb>>16&0x000000FF;  
        int Green = rgb>> 8&0x000000FF;  
        int Blue  = rgb>> 0&0x000000FF;
        int Gray  = 0;
        if(type.equals("averaging") || type.equals("a")){
            Gray = (Red + Green + Blue) / 3;
        }else if(type.equals("luminance")||type.equals("luma") || type.equals("l")){
            Gray = (int) (Red * 0.299 + Green * 0.587 + Blue * 0.114);
        }else if(type.equals("desaturation") || type.equals("d")){
            Gray = (int) (( Math.max(Math.max(Red, Green), Blue) + Math.min(Math.min(Red, Green), Blue) ) / 2);
        }else if(type.equals("decomposition")){
            Gray = Math.min(Math.min(Red, Green), Blue);
        }
        return 0xff000000|Gray<<16|Gray<<8|Gray;
    }
}
