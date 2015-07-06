package view;

import controller.MapController;
import controller.GameManager;
import model.Click;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

import javax.swing.*;
import model.Continent;

import model.Player;
import model.Ponto;
import model.Territory;
import controller.GameManager.turnPhase;

@SuppressWarnings("serial")
public class MapPanel extends JPanel {

    // Lista de territorios
    
    
    private Territory currentTerritory = null;
    
    
    private BufferedImage mapImage;
    private BufferedImage lineImage;
    private BufferedImage infosImage;
    private List<Color> playerColors;
    private List<Territory> territoryList;
    private Map<Territory,List<Territory>> neighbourMap;

    private GameManager gameManager;
    private Console consoleController;


    public MapPanel(List<Player> playerList, List<Territory> territoryList, Map<Territory,List<Territory>> neighbourMap) {
        try{
            File map = new File("images/Mapas/war_tabuleiro_fundo.png");
            mapImage = ImageIO.read(map);
            
            File line = new File("images/Mapas/war_tabuleiro_linhas.png");
            lineImage = ImageIO.read(line);
            
            File infos = new File("images/mapas/war_tabuleiro.png");
            infosImage = ImageIO.read(infos);
            
            playerColors = new ArrayList<>();
            playerList.forEach((Player) -> playerColors.add(Player.getColor()));
            
            this.territoryList = territoryList;
            this.neighbourMap = neighbourMap;
            gameManager = GameManager.getInstance();
            consoleController = Console.getInstance();
        } 
        catch(IOException e){
            System.out.print("Erro ao carregar a imagem" + e.getMessage());
        }   

        this.addMouseListener(new MouseListener() {

            // Evento de click para detectar se o ponto clicado esta dentro da area do teritorio.
            @Override
            public void mouseClicked(MouseEvent e) {
                // Para cada territorio da lista de territorios
                System.out.println("mouseX = "+e.getX()+" mouseY = "+e.getY()+"\n");
                for(Territory t : territoryList) {

                    // Se o ponto clicado for contido pelo poligono do territorio	
                    if(t.getPoligono().contains(e.getX(), e.getY())) {   
                        Click click = new Click();
                        
                        click.addObserver(gameManager);
                        click.setValue(t);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
         
        //Paint the sea
        g.drawImage(mapImage,0,0,null);
       
        //Paint infos
        g.drawImage(infosImage,0,0,null);
        
        //Paint Territories/names
        paintTerritories(g);
        
        //Paint territory lines
        g.drawImage(lineImage,0,0,null);
        
        //Paint players
        paintPlayers(g);
        
        //Paint armies
        paintArmyInfo(g);
    }
    
    
    
    private void paintTerritories(Graphics g){
        //Draw the different territories
        Graphics2D g2 = (Graphics2D)g;
        
        List<Territory> neighbourList = new ArrayList();
        //Turn Antialiasing on
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
        for(Territory t : territoryList){
           
            //Filling
            switch(t.getContinente()){
                case("AN"): g2.setColor(new Color(238,64,54)); break;
                case("AS"): g2.setColor(new Color(0,104,56)); break;
                case("AF"): g2.setColor(new Color(101,45,144)); break;
                case("EU"): g2.setColor(new Color(43,56,143)); break;
                case("ASI"): g2.setColor(new Color(246,146,30)); break;
                case("OC"): g2.setColor(new Color(38,169,224)); break;
            }
            
            if(!(currentTerritory == null)){
                
                //Are we filling the current country?
                if(getCurrentTerritory().equals(t)){
                    g2.setColor(g2.getColor().brighter().brighter());
                }
                
                //Get neighbours of chosen territory
                neighbourList = getNeighbourMap().get(currentTerritory);
                for(Territory n : neighbourList){
                    
                    //Are we filling(t) a neighbour(n)?
                    if (gameManager.getTurnPhase() == turnPhase.attackPhase){
                        if(t.equals(n) && (t.getOwnerPlayer() != currentTerritory.getOwnerPlayer())){
                            g2.setColor(g2.getColor().darker().darker());
                        }
                    }else if(gameManager.getTurnPhase() == turnPhase.moveArmyPhase){
                        if(t.equals(n) && (t.getOwnerPlayer() == currentTerritory.getOwnerPlayer())){
                            g2.setColor(g2.getColor().darker().darker());
                        }
                    }else{
                        if(t.equals(n)){
                            g2.setColor(g2.getColor().darker().darker());
                        }
                    }
                }
            }
            
            g2.fill((Shape)t.getPoligono());
           
            //Drawing borders
            g2.setStroke(new BasicStroke(1.5f));
            g2.setColor(Color.BLACK);
            g2.draw(t.getPoligono());
        }
    }
    
    private void paintPlayers(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        
        int xCoord = 20;
        int diameter = 50;
        Player currentPlayer = gameManager.getCurrentPlayer();
        
        for(Color c : playerColors){
           
           g2.setPaint(c);
           g2.fillOval(xCoord,20,diameter,diameter);
           
           g2.setColor(Color.BLACK);
           g2.drawOval(xCoord,20,diameter,diameter);
           
           if(currentPlayer.getColor().equals(c)){
               g2.setPaint(Color.BLACK);
               g2.fillOval(xCoord+diameter/4,20+diameter/4,diameter/2,diameter/2);
           }
           
           xCoord += 70;
        }
    }
    
    private void paintArmyInfo(Graphics g){
        Graphics2D g2 = (Graphics2D)g;           
        //Draw army infos
        int width = 30;
        int height = 20;
        
        g2.setFont(new Font("TimesRoman",Font.BOLD,15));
        
        for(Territory t : territoryList){
            g2.setPaint(t.getOwnerPlayer().getColor());
            g2.fillRect(t.getCenterX(),t.getCenterY(),width,height);
       
            //Are we painting the current territory?
            if(t.equals(currentTerritory)){
                g2.setColor(t.getOwnerPlayer().getColor());
                g2.setStroke(new BasicStroke(2.5f));
                g2.drawRect(t.getCenterX()-1,t.getCenterY()-1,width+2,height+2);
            }
            
            g2.setStroke(new BasicStroke(1.5f));
            g2.setColor(Color.black);
            g2.drawRect(t.getCenterX(),t.getCenterY(),width,height);
            
            g2.drawString(Integer.toString(t.getQtdExercitos()),t.getCenterX()+height/2,t.getCenterY()+width/2);
        }
    }
	
    
    // Bloco de inicialização dos territ�rios
    // Estou assumindo que a classe territorio tem um nome e um poligono definindo sua área de clique.
   

    /**
     * @param territoryList the lstTerritorios to set
     */

    /**
     * @param neighbourMap the neighbourMap to set
     */
    public void setNeighbourMap(Map<Territory,List<Territory>> neighbourMap) {
        this.neighbourMap = neighbourMap;
    }
    
    public Territory getCurrentTerritory() {
        return currentTerritory;
    }
    
    public void setCurrentTerritory(Territory currentTerritory) {
        this.currentTerritory = currentTerritory;
    }   

    public Map<Territory,List<Territory>> getNeighbourMap() {
        return neighbourMap;
    }
    
    public BufferedImage getMapImage(){
        return mapImage;
    }

}