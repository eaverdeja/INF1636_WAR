
package viewController;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DicePanel extends JPanel{
    
    private BufferedImage bgImage;
    private List<BufferedImage> attackDice;
    private List<BufferedImage> defenseDice;
    
    public DicePanel(){
        try{
            File bg = new File("images/mapas/war_tabuleiro_fundo.png");
            bgImage = ImageIO.read(bg);
            
            createDices();
        }
        catch(IOException e){
            System.out.println("Erro ao carregar imagem de fundo dos dados" + e.getMessage());
        }
    }
    
    private void createDices(){
      try{
        attackDice = new ArrayList<>();
        defenseDice = new ArrayList<>();
        for(int i = 1;i <= 6;i++){
            //Attack
            File dice = new File("images/Dados/dado_ataque_"+i+".png");
            attackDice.add(ImageIO.read(dice));
            
            //Defense
            dice = new File("images/Dados/dado_defesa_"+i+".png");
            defenseDice.add(ImageIO.read(dice));
        }
      }
      catch(IOException e){
          System.out.println("Erro ao carregar imagens dos dados" + e.getMessage());
      }
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.drawImage(bgImage, 0, 0, null);
        drawDices(g);
    }
    
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(400,200);
    }
    
    private void drawDices(Graphics g){
        int attackY = 20;
        int defenseY = 100;
        int xOffset = 80;
        
        BufferedImage dice;
        Random randomizer = new Random();
        
        for(int i = 0;i < 3;i++){
            //Choose a random attack dice and draw it
            dice = attackDice.get(randomizer.nextInt(attackDice.size()));
            g.drawImage(dice, (i*xOffset)+50, attackY, null);
            
            //Choose a random defense dice and draw it
            dice = defenseDice.get(randomizer.nextInt(defenseDice.size()));
            g.drawImage(dice, (i*xOffset)+50, defenseY, null);
        }
    }
}
