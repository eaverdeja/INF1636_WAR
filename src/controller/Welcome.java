package controller;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Welcome extends JPanel{
    
    private Image image;
    
    public Welcome(){
        try{
            File welcome = new File("images/cartas/war_carta_verso.png");
            image = ImageIO.read(welcome);
        }
        catch(IOException e){
            System.out.print("Erro ao carregar a imagem" + e.getMessage());
        } 
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image,0,0,null);
    }

}
