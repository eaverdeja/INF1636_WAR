/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.GameManager.turnPhase;
import java.util.Collections;
import java.util.Random;
import javax.swing.JOptionPane;
import model.Continent;
import model.Player;
import model.Territory;

/**
 *
 * @author lorenzosaraiva
 */
public class TurnController {
    
    private turnPhase currentPhase;
    private GameManager gameManager = GameManager.getInstance();
    private int cardsChangeAmount;
    private boolean finishedAttacking = false;
    private boolean hasConquered = false;
    
    
    
    public TurnController (){
            currentPhase = GameManager.turnPhase.newArmyPhase;
    }
    
        


    public void goToNextPhase() {
        if (getCurrentPhase() == GameManager.turnPhase.newArmyPhase) {
            setCurrentPhase(GameManager.turnPhase.attackPhase);
        }
        else if (getCurrentPhase() == GameManager.turnPhase.attackPhase && finishedAttacking){
            setCurrentPhase(GameManager.turnPhase.moveArmyPhase);
        }
        else if (getCurrentPhase() == GameManager.turnPhase.attackPhase) {
            setCurrentPhase(GameManager.turnPhase.newAttackerPhase);
        }
        else if (getCurrentPhase() == GameManager.turnPhase.newAttackerPhase) {
            setCurrentPhase(GameManager.turnPhase.attackPhase);
        }
    }

    public turnPhase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(turnPhase currentPhase) {
        this.currentPhase = currentPhase;
    }
    
    public void goToMovePhase(){
        currentPhase = turnPhase.moveArmyPhase;
    }

    public void nextTurn() {
        int index = 0;
        currentPhase = turnPhase.newArmyPhase;
        for (Player p : gameManager.getPlayers()) {
            if (p.getPlayerId() == gameManager.getCurrentPlayer().getPlayerId()) {
                break;
            } else {
                index++;
            }
        }
        
        updateCardsChangedAmount();
        hasConquered = false;
        finishedAttacking = false;
        
        if (checkVictory()){
            JOptionPane.showMessageDialog(null,"O Player " + gameManager.getCurrentPlayer().getColor() + " ganhou!");
        }
        
        for (Territory t: gameManager.getTerritoryList()){
            t.resetMoves();
        }
        if (index == gameManager.getPlayers().size() - 1) {
            gameManager.setCurrentPlayer(gameManager.getPlayers().get(0));
            
        } else {
            gameManager.setCurrentPlayer(gameManager.getPlayers().get(index + 1));
        }
        gameManager.getCurrentPlayer().setHasChanged(false);
        updatePlayerContinentBonus();
        if (gameManager.getCurrentPlayer().hasFullHand()){
            gameManager.getCurrentPlayer().canChangeCards();
            gameManager.getCurrentPlayer().setHasChanged(true);
        }
    }
    
    private void updateCardsChangedAmount(){
        if (gameManager.getCurrentPlayer().hasChanged()){
            if (cardsChangeAmount < 12){
            cardsChangeAmount += 2;
            }
            else{
                if (cardsChangeAmount == 12)
                    cardsChangeAmount = 15;
                else
                    cardsChangeAmount+=5;
            }     
        }
    
    }
    
    private void updatePlayerContinentBonus(){
        gameManager.getCurrentPlayer().setContinentBonus(0);
        if (gameManager.checkPlayerHasContinent("AN", gameManager.getCurrentPlayer()))
            gameManager.getCurrentPlayer().setContinentBonus(gameManager.getCurrentPlayer().getContinentBonus()+5);
        if (gameManager.checkPlayerHasContinent("AF", gameManager.getCurrentPlayer()))
            gameManager.getCurrentPlayer().setContinentBonus(gameManager.getCurrentPlayer().getContinentBonus()+3);
        if (gameManager.checkPlayerHasContinent("AS", gameManager.getCurrentPlayer()))
            gameManager.getCurrentPlayer().setContinentBonus(gameManager.getCurrentPlayer().getContinentBonus()+2);
        if (gameManager.checkPlayerHasContinent("ASI", gameManager.getCurrentPlayer()))
            gameManager.getCurrentPlayer().setContinentBonus(gameManager.getCurrentPlayer().getContinentBonus()+7);
        if (gameManager.checkPlayerHasContinent("OC", gameManager.getCurrentPlayer()))
            gameManager.getCurrentPlayer().setContinentBonus(gameManager.getCurrentPlayer().getContinentBonus()+2);
        if (gameManager.checkPlayerHasContinent("EU", gameManager.getCurrentPlayer()))
            gameManager.getCurrentPlayer().setContinentBonus(gameManager.getCurrentPlayer().getContinentBonus()+5);
    }
    
    private boolean checkVictory(){
        return gameManager.getCurrentPlayer().getObjective().checkWin();
    }
    
}
