package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import controller.GameManager;
/**
 *
 * @author lorenzosaraiva
 */
public class Player {
    
    private Color color;
    private int playerId;
    private static int playerQuantity = 0;
    private int currentTerritories = 0;
    private int continentBonus = 0;
    private List<Card> currentCards = new ArrayList<>();
    private boolean hasChanged = false;
    private Objective objective;
    
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
    
    public boolean canChangeCards(){
        
        int matches = 0,check[] = {0,0,0,0};
     
        for ( Card i : getCurrentCards()){
            
            if (check[i.getCardSymbol()] == 0 && i.getCardSymbol() != 3){
                check[i.getCardSymbol()]++;
            }else{
                matches++;
                check[i.getCardSymbol()]++;
            }
        }
        if (matches >= 2){
            int swapIndex = 0, comp = 0;
            for (int i = 0; i < 4; i++){
                if (comp < check[i]){
                    comp = check[i];
                    swapIndex = i;
                }
            }
            for (Iterator<Card> iterator = currentCards.iterator(); iterator.hasNext();) {
                Card card = iterator.next();
                        if (card.getCardSymbol() == swapIndex || card.getCardSymbol() == 3) {
                            iterator.remove();
                        }
                }
            return true;
        }else{
            int diffs = 0;
            for (int i = 0; i < 4;i++){
                if (check[i]>0){
                    diffs++;
                }
            }
            if (diffs > 2){
                boolean firstSymbol = false, secondSymbol = false, thirdSymbol = false;
                for (Iterator<Card> iterator = currentCards.iterator(); iterator.hasNext();) {
                    Card card = iterator.next();
                    if (card.getCardSymbol() == 0){
                        if (!firstSymbol){
                            firstSymbol = true;
                            iterator.remove();
                        }
                    }
                    if (card.getCardSymbol() == 1){
                        if (!secondSymbol){
                            secondSymbol = true;
                            iterator.remove();
                        }
                    }
                    if (card.getCardSymbol() == 2){
                        if (!thirdSymbol){
                            thirdSymbol = true;
                            iterator.remove();
                        }
                    }
                    if (card.getCardSymbol() == 3){                       
                            iterator.remove();
                    }
                }
                return true;
            }else{
            return false;
            }
        }
    }
    
    public void removeCards(){
        for (Card i : getCurrentCards()){
            getCurrentCards().remove(i);
        }
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
        if (hasChanged()){
            ret += GameManager.getInstance().getCardsChangeAmount();
        }
        ret += getContinentBonus();
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
        getCurrentCards().add(card);
    }

    public List<Card> getCurrentCards() {
        return currentCards;
    }
    
    public boolean hasFullHand(){
        if (getCurrentCards().size() == 5){
            return true;
        }
        return false;
    }

    public boolean hasChanged() {
        return hasChanged;
    }

    public void setHasChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }

    public Objective getObjective() {
        return objective;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    public int getContinentBonus() {
        return continentBonus;
    }

    public void setContinentBonus(int continentBonus) {
        this.continentBonus = continentBonus;
    }
    
}
