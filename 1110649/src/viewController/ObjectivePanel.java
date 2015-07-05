/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewController;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Card;
import model.Objective;

/**
 *
 * @author lorenzosaraiva
 */
public class ObjectivePanel extends JPanel{
    
    private Objective obj;
    
    
    public ObjectivePanel(){
    }
    
    private void drawPanel(Graphics g){
        try {
            File image = new File("images/Cartas/war_carta_objetivo.png");
            BufferedImage objectiveBackground  = ImageIO.read(image);
            g.drawImage(objectiveBackground, 0, 0, null);
        } catch (IOException ex) {
            Logger.getLogger(ObjectivePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        drawPanel(g);
    }
    
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(440,725);
    }
    
    
    public void showsPanel(){
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(this);
                frame.pack();
                frame.setVisible(true);
                
    }

}
