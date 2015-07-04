package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 *
 * @author lorenzosaraiva
 */
public class Player {
    
    private Color color;
    private int playerId;
    private static int playerQuantity = 0;
    private int currentTerritories = 0;
    private List<Card> currentCards = new ArrayList<>();
    
    public Player(){
        this.playerId = playerQuantity;
        playerQuantity++;
        this.color = getPlayerColor(this.playerId);  
    }
    
    private Color getPlayerColor(int id){
        switch(id){
            case 0:
                return Color.WHITE;
            case 1:
                return Color.RED;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.MAGENTA;
            case 4:
                return Color.YELLOW;
            case 5:
                return Color.GREEN; 
        }
        return null;
    
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public boolean changeCards(){
        
        int matches = 0,check[] = {0,0,0,0};
     
        for ( Card i : currentCards){
            
            if (check[i.getCardSymbol()] == 0 && i.getCardSymbol() != 3){
                check[i.getCardSymbol()] = 1;
            }else{
                matches++;
            }
        }
        return matches >= 2;
    }

  
    public int getPlayerId() {
        return playerId;
    }


    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    
    public static int getPlayerQuantity() {
        return playerQuantity;
    }
    
    public int newArmyAmount(){
        int ret = (int) Math.floor(this.getCurrentTerritories()/2);
        if (ret > 3)
            return ret;
        return 3;
    }

    public int getCurrentTerritories() {
        return currentTerritories;
    }

    public void setCurrentTerritories(int currentTerritories) {
        this.currentTerritories = currentTerritories;
    }
    
    public void giveCard(Card card){
        currentCards.add(card);
    }
}
