import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MapPanel extends JPanel{
    private Image imageResized;
    
    public MapPanel(){
        try{
            File map = new File("images/war_tabuleiro_com_nomes.png");
            BufferedImage image = ImageIO.read(map);
            imageResized = image.getScaledInstance((int)(image.getHeight()*0.6),(int)(image.getWidth()*0.3), Image.SCALE_DEFAULT);
            
        }
        catch(IOException e){
            System.out.print("Erro ao carregar a imagem" + e.getMessage());
        }   
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(imageResized,0,0,null);
    }
    
}
