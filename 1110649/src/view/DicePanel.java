
package view;

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
    private	BufferedImage diceImg;
    private int firstAttackDice = 0;
    private int secondAttackDice = 0;
    private int thirdAttackDice = 0;
    private int firstDefenseDice = 0;
    private int secondDefenseDice = 0;
    private int thirdDefenseDice = 0;
    private int defenseArmiesDead = 0;
    private int attackArmiesDead = 0;
    private int numAttackDices = 0;
    private int numDefenseDices = 0;
    
    
    public DicePanel(int attack, int defense){
      
        try{
            bgImage = ImageIO.read(getClass().getResource("/images/Mapas/war_tabuleiro_fundo.png"));
        }
        catch(IOException e){
            System.out.println("Erro ao carregar imagem de fundo dos dados" + e.getMessage());
        }
        numAttackDices = attack;
        numDefenseDices = defense;
        createDices();
        rollValues();
        
    }
    
    private void createDices(){
      try{
        attackDice = new ArrayList<>();
        defenseDice = new ArrayList<>();
        for(int i = 1;i <= 6;i++){
            //Attack
            diceImg = ImageIO.read(getClass().getResource("/images/Dados/dado_ataque_"+i+".png"));
            attackDice.add(diceImg);
            
            //Defense
            //diceImg.flush();
            diceImg = ImageIO.read(getClass().getResource("/images/Dados/dado_defesa_"+i+".png"));
            defenseDice.add(diceImg);
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
    
    private void rollValues(){
        Random randomizer = new Random();
        
        firstAttackDice = randomizer.nextInt(6);
            
        firstDefenseDice = randomizer.nextInt(6);
        
        if (numAttackDices > 1)
            secondAttackDice = randomizer.nextInt(6);
        
        if (numDefenseDices > 1)
            secondDefenseDice = randomizer.nextInt(6);
        
        if (numAttackDices > 2)
            thirdAttackDice = randomizer.nextInt(6);
        
        if (numDefenseDices > 2)    
            thirdDefenseDice = randomizer.nextInt(6);
                
    }
    private void drawDices(Graphics g){
        int attackY = 20;
        int defenseY = 100;
        int xOffset = 80;
        
        BufferedImage dice;
        Random randomizer = new Random();
        
        
            for(int i = 0;i < 3;i++){
                switch(i){
            
                    case 0:
                        dice = attackDice.get(firstAttackDice);
                        g.drawImage(dice, (i*xOffset)+50, attackY, null);
                        
                        dice = defenseDice.get(firstDefenseDice);
                        g.drawImage(dice, (i*xOffset)+50, defenseY, null);
                        break;
                    case 1:
                        if (numAttackDices >= 2){
                            dice = attackDice.get(secondAttackDice);
                            g.drawImage(dice, (i*xOffset)+50, attackY, null);
                        }
                        
                        if (numDefenseDices >= 2){
                            dice = defenseDice.get(secondDefenseDice);
                            g.drawImage(dice, (i*xOffset)+50, defenseY, null);
                        }
                        break;
                        
                    case 2:
                        if (numAttackDices >= 3){
                            dice = attackDice.get(thirdAttackDice);
                            g.drawImage(dice, (i*xOffset)+50, attackY, null);
                        }
                        if (numDefenseDices >= 3){
                            dice = defenseDice.get(thirdDefenseDice);
                            g.drawImage(dice, (i*xOffset)+50, defenseY, null);
                        }
                        break;
                        
                 }
        
            }
        
    }
    
    public void showDices(){
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(this);
                frame.pack();
                frame.setVisible(true);
                
    }

    public boolean attackWins(){
        if (Math.min(numDefenseDices, numAttackDices) == 1){
            
            if (getBiggerDice(false) < getBiggerDice(true)){
                defenseArmiesDead++;
                return true;
            }
            else{
                attackArmiesDead++;
                return false;
            }
        }
        
        if (Math.min(numDefenseDices, numAttackDices) == 2){
            if (getBiggerDice(false) < getBiggerDice(true)){
                defenseArmiesDead++;
                if (getSmallerDice(false) < getSmallerDice(true)){
                    defenseArmiesDead++;
                    return true;
                }
                else{
                    attackArmiesDead++;
                    return false;
                }
            }
            else{
                attackArmiesDead++;
                if (getSmallerDice(false) < getSmallerDice(true)){
                    defenseArmiesDead++;
                    return false;
                }
                else{
                    attackArmiesDead++;
                    return false;
                }
                
            }
        }
        if (Math.min(numDefenseDices, numAttackDices) >= 3){
            if (getBiggerDice(false) < getBiggerDice(true)){
                defenseArmiesDead++;
                if (getSmallerDice(false) < getSmallerDice(true)){
                    defenseArmiesDead++;
                    if (getMiddleDice(false) < getMiddleDice(true)){
                        defenseArmiesDead++;
                        return true;
                    }else{
                        attackArmiesDead++;
                        return true;
                    }
                }
                else{
                    attackArmiesDead++;
                    if (getMiddleDice(false) < getMiddleDice(true)){
                        defenseArmiesDead++;
                        return true;
                    }else{
                        attackArmiesDead++;
                        return false;
                    }
                }
            }
            else{
                attackArmiesDead++;
                if (getSmallerDice(false) < getSmallerDice(true)){
                    defenseArmiesDead++;
                    if (getMiddleDice(false) < getMiddleDice(true)){
                        defenseArmiesDead++;
                        return true;
                    }else{
                        attackArmiesDead++;
                        return false;
                    }
                }
                else{
                    attackArmiesDead++;
                    if (getMiddleDice(false) < getMiddleDice(true)){
                        defenseArmiesDead++;
                        return false;
                    }else{
                        attackArmiesDead++;
                        return false;
                    }
                }
                
            }
        }
        return false;
    }
    
    private int getBiggerDice(boolean isAttack){
        if (isAttack){
            return Math.max(Math.max(firstAttackDice, thirdAttackDice), Math.max(firstAttackDice,secondAttackDice)); 
        }else{
            return Math.max(Math.max(firstDefenseDice, thirdDefenseDice), Math.max(firstDefenseDice,secondDefenseDice)); 
        }
    }
    
    private int getMiddleDice(boolean isAttack){
        if (isAttack){
            int ret;
            
            if (( firstAttackDice >= secondAttackDice && firstAttackDice <= thirdAttackDice)||(firstAttackDice <= secondAttackDice && firstAttackDice >= thirdAttackDice))
                return firstAttackDice; 
            
            if (( secondAttackDice >= firstAttackDice && secondAttackDice <= thirdAttackDice)||(secondAttackDice <= firstAttackDice && secondAttackDice >= thirdAttackDice))
                return secondAttackDice;
            
            if (( thirdAttackDice >= firstAttackDice && thirdAttackDice <= secondAttackDice)||(thirdAttackDice <= firstAttackDice && thirdAttackDice >= secondAttackDice))
                return thirdAttackDice;
                
            
        }else{
            if (( firstDefenseDice >= secondDefenseDice && firstDefenseDice <= thirdDefenseDice)||(firstDefenseDice <= secondDefenseDice && firstDefenseDice >= thirdAttackDice))
                return firstDefenseDice; 
            
            if (( secondDefenseDice >= firstDefenseDice && secondDefenseDice <= thirdDefenseDice)||(secondDefenseDice <= firstDefenseDice && secondDefenseDice >= thirdDefenseDice))
                return secondDefenseDice;
            
            if (( thirdDefenseDice >= firstDefenseDice && thirdDefenseDice <= secondDefenseDice)||(thirdDefenseDice <= firstDefenseDice && thirdDefenseDice >= secondDefenseDice))
                return thirdDefenseDice; 
        }
        return 0;
    }
    
    private int getSmallerDice(boolean isAttack){
        if (isAttack){
            return Math.min(Math.min(firstAttackDice, thirdAttackDice), Math.min(firstAttackDice,secondAttackDice)); 
        }else{
            return Math.min(Math.min(firstDefenseDice, thirdDefenseDice), Math.min(firstDefenseDice,secondDefenseDice)); 
        }
    }

    public int getDefenseArmiesDead() {
        return defenseArmiesDead;
    }

    public int getAttackArmiesDead() {
        return attackArmiesDead;
    }
}
