/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.image.UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;
import javax.swing.filechooser.*;
import com.project.image.algorithm.*;
/**
 *
 * @author Samrand
 */

public class Frame extends JFrame implements ActionListener{
    
    private JMenuItem Histogram,New,Open,Save,Exit,Kmeans,Threasholding,Edge,GrayScale;
    private JLabel redValue,greenValue,blueValue,grayValue,pixelX,pixelY;
    private JPanel buttomPanelColor,buttomPanelGray;
    private JTabbedPane tp;
    private TabbedPanel[] tabbedPanel = new TabbedPanel[25];
    private int tabs = 0;
    private String fileName,filePath;
    
    
    public Frame(){
        setTitle("Image Segmentation");
        setSize(750,500);
        setResizable(true);
        setVisible(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JMenuBar jmb = initJMenuBar();
        setJMenuBar(jmb);
        
       
        
        
        tp=new JTabbedPane(); 
        
        buttomPanelColor = initButtomPanelColor();
        buttomPanelGray = initButtomPanelGray();
        
        add(tp,"Center");
        //add(buttomPanelColor,"South");

        
        show();
    }

    private JMenuBar initJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        
        JMenu File = new JMenu("File");
        JMenu Filters = new JMenu("Filters");
        JMenu Tools = new JMenu("Tools");
        
        Histogram = new JMenuItem("Histogram");
        New = new JMenuItem("New");
        Open = new JMenuItem("Open");
        Save = new JMenuItem("Save");
        Exit = new JMenuItem("Exit");
        Kmeans = new JMenuItem("KMeans");
        Threasholding = new JMenuItem("Threasholding");
        Edge = new JMenuItem("Edge Detection");
        GrayScale = new JMenuItem("GrayScale");
        
        Tools.add(Histogram);
        
        File.add(New);
        File.add(Open);
        File.addSeparator();
        File.add(Save);
        File.addSeparator();
        File.add(Exit);
        
        Filters.add(Kmeans);
        Filters.add(Threasholding);
        Filters.add(Edge);
        Filters.add(GrayScale);
        
        jMenuBar.add(File);
        jMenuBar.add(Filters);
        jMenuBar.add(Tools);
        
        New.addActionListener(this);
        Open.addActionListener(this);
        Save.addActionListener(this);
        Exit.addActionListener(this);
        
        Kmeans.addActionListener(this);
        Threasholding.addActionListener(this);
        GrayScale.addActionListener(this);
        Edge.addActionListener(this);
        
        Histogram.addActionListener(this);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());            
        }catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex){
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return jMenuBar;
    }

    private JPanel initButtomPanelColor() {
        JPanel tmp = new JPanel(new GridLayout(1,10));
        tmp.add(new JLabel("Red : "));
        tmp.add(redValue = new JLabel("0"));
        tmp.add(new JLabel("Green : "));
        tmp.add(greenValue = new JLabel("0"));
        tmp.add(new JLabel("Blue : "));
        tmp.add(blueValue = new JLabel("0"));
        tmp.add(new JLabel("X : "));
        tmp.add(pixelX = new JLabel("0"));
        tmp.add(new JLabel("Y : "));
        tmp.add(pixelY = new JLabel("0"));
        
        return tmp;
    }
    
    private JPanel initButtomPanelGray() {
        JPanel tmp = new JPanel(new GridLayout(1,6));
        tmp.add(new JLabel("Gray : "));
        tmp.add(grayValue = new JLabel("0"));
        tmp.add(new JLabel("X : "));
        tmp.add(pixelX = new JLabel("0"));
        tmp.add(new JLabel("Y : "));
        tmp.add(pixelY = new JLabel("0"));
        
        return tmp;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        
        if(source.equals(New)){
            tp.removeAll();
            tabs = 0;
            
        }else if(source.equals(Open)){
            
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG Images", "jpg");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    String tmp = chooser.getSelectedFile().getName();
                    fileName = tmp.substring(0,tmp.indexOf("."));
                    filePath = chooser.getSelectedFile().getAbsolutePath();
                    tabbedPanel[tabs] = new TabbedPanel(ImageIO.read(chooser.getSelectedFile()),"Original");
                    tp.add(tabbedPanel[tabs].getName(),tabbedPanel[tabs]);
                } catch (IOException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
            
        }else if(source.equals(Save)){
            
            String Name = tabbedPanel[tp.getSelectedIndex()].getName();
            BufferedImage imgTemp = (tabbedPanel[tp.getSelectedIndex()].getImage());
            saveImage(filePath+fileName+"-"+Name+".jpg", imgTemp);
            
        }else if(source.equals(Exit)){
            
            System.exit(0);
            
        }else if(source.equals(Kmeans)){
            
            String Name = tabbedPanel[tp.getSelectedIndex()].getName();
            KMeans kmeans = new KMeans(tabbedPanel[tp.getSelectedIndex()].getImage());
            String temp = JOptionPane.showInputDialog(null, "Enter K-Value of Clustering:- ", "0");
            if(!temp.equals("")){ 
                int k = Integer.parseInt(temp);
                BufferedImage imgTemp = kmeans.calculate(k);
                tabbedPanel[++tabs] = new TabbedPanel(imgTemp,Name+"-"+"KMeans-" + k);
                tp.add(tabbedPanel[tabs].getName(),tabbedPanel[tabs]);
            }
            
        }else if(source.equals(Threasholding)){
           String temp = JOptionPane.showInputDialog(null, "Enter K-Value of Clustering:- ", "0");
           

        }else if(source.equals(GrayScale)){
            
            String Name = tabbedPanel[tp.getSelectedIndex()].getName();
            BufferedImage imgTemp = Filter.grayScale(tabbedPanel[tp.getSelectedIndex()].getImage());
            tabbedPanel[++tabs] = new TabbedPanel(imgTemp,Name+"-"+"GrayScale");
            tp.add(tabbedPanel[tabs].getName(),tabbedPanel[tabs]);
            
        }else if(source.equals(Edge)){
            
            String Name = tabbedPanel[tp.getSelectedIndex()].getName();
            CannyEdgeDetector edge = new CannyEdgeDetector();
            edge.setSourceImage(tabbedPanel[tp.getSelectedIndex()].getImage());
            edge.process();
            BufferedImage imgTemp = edge.getEdgesImage();
            tabbedPanel[++tabs] = new TabbedPanel(imgTemp,Name+"-"+"Edge");
            tp.add(tabbedPanel[tabs].getName(),tabbedPanel[tabs]);
            
        }else if(source.equals(Histogram)){
            Histogram histo = new Histogram(tabbedPanel[tp.getSelectedIndex()].getImage());
            String name = tabbedPanel[tp.getSelectedIndex()].getName() + "-Histogram";
            tp.add(name,histo.getPanel());
            tabs++;
               
        }
    }
    
    public static void saveImage(String filename,BufferedImage image) { 
        File file = new File(filename); 
        try { 
            ImageIO.write(image, "png", file); 
        } catch (Exception e) { 
            System.out.println(e.toString()+" Image '"+filename 
                                +"' saving failed."); 
        } 
    } 
     
    public static BufferedImage loadImage(String filename) { 
        BufferedImage result = null; 
        try { 
            result = ImageIO.read(new File(filename)); 
        } catch (Exception e) { 
            System.out.println(e.toString()+" Image '" 
                                +filename+"' not found."); 
        } 
        return result; 
    } 
}
