/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Card;

/**
 *
 * @author lorenzosaraiva
 */
public class CardPanel extends JPanel{
    
    private List<Card> cards = new ArrayList();
    
    
    public CardPanel(List<Card> cards){
        this.cards = cards;
    }
    
    private void drawPanel(Graphics g){
        int offset = 0;
        for(Card card : cards){  
            BufferedImage before = card.getCardImage();
            int w = before.getWidth();
            int h = before.getHeight();
            BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            AffineTransform at = new AffineTransform();
            at.scale(0.5, 0.5);
            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            after = scaleOp.filter(before, after);
            g.drawImage(after, offset, 0, null);
            offset+=250;
        }
     
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        drawPanel(g);
    }
    
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(1250,375);
    }
    
    
    public void showsPanel(){
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(this);
                frame.pack();
                frame.setVisible(true);
                
    }

}
