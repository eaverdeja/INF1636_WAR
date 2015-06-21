/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Player;
import java.util.Random;
import model.Territory;
/**
 *
 * @author lorenzosaraiva
 */
public class Turn {

    public enum turnPhase{
        newArmyPhase,attackPhase,retreatPhase, choseNewAttacker,moveArmyPhase
      
    }
    private Player[] playerArray;
    private static int playerQuantity;
    private Player currentPlayer;
    private static Turn turnInstance = null;
    private turnPhase currentPhase;
    private List<Territory> lstTerritorios = new ArrayList<>();
    private int armiesAdded = 0;

    
    //Implementing Singleton pattern
    protected Turn(){}
    
    public static Turn getInstance(){
        if (turnInstance == null ){
            turnInstance = new Turn();
        }
        return turnInstance;
    }
    
    public void createAndRadomizePlayers(int players){
        this.playerArray = new Player[players];
        this.playerQuantity = players;
        this.currentPhase = turnPhase.newArmyPhase;
        createPlayers(players);
    
        /* DEBUG
        for (int i = 0; i < this.playerQuantity; i++){
            System.out.print(playerArray[i].getPlayerId() + " ");
        }

        System.out.print("\n");

        shuffleArray(this.playerArray, this.playerQuantity);

        for (int i = 0; i < playerQuantity; i++){
           System.out.print(playerArray[i].getPlayerId() + " ");
        }
        */

        currentPlayer = this.playerArray[0];
    
    }
    
    private void createPlayers (int players){
        for (int i = 0; i < players; i++){
            Player newPlayer = new Player();
            playerArray[i] = newPlayer;

            System.out.println("A new player has been created!");
        }
    }
   
    private void shuffleArray(Player[] array, int size){
        int index;
        Player temp;
        Random random = new Random();
        for (int i = size - 1; i > 0; i--)
        {
         index = random.nextInt(i + 1);
         temp = array[index];
         array[index] = array[i];
         array[i] = temp;
        }
    }
    
    public void nextTurn(){
        int index = 0;
        currentPhase = turnPhase.newArmyPhase;
        for(Player p : playerArray){ 
            if (p.getPlayerId() == getCurrentPlayer().getPlayerId()) break;
            else index++;
        }
        if(index == playerQuantity-1) currentPlayer = playerArray[0];
        else currentPlayer = playerArray[index+1];
    }
    
    public List<Player> getPlayers(){
        return Arrays.asList(playerArray);
    }
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public turnPhase getTurnPhase() {
        return currentPhase;
    }
    
    public void goToNextPhase(){
        if (currentPhase == turnPhase.newArmyPhase){
            currentPhase = turnPhase.attackPhase;
            return;
        }
        if (currentPhase == turnPhase.attackPhase){
            currentPhase = turnPhase.choseNewAttacker;
            return;
        }
        if (currentPhase == turnPhase.retreatPhase){
            currentPhase = turnPhase.choseNewAttacker;
            return;
        }
        
    }
       
    /**
     * @param lstTerritorios the lstTerritorios to set
     */
    public void setLstTerritorios(List<Territory> lstTerritorios) {
        this.lstTerritorios = lstTerritorios;
    }
    
    public void randomizeTerritories(){
        int counter = 0;
        for (Territory t:lstTerritorios){
            t.setOwnerPlayer(this.playerArray[counter]);
            t.setQtdExercitos(1);
            this.playerArray[counter].setCurrentTerritories(this.playerArray[counter].getCurrentTerritories()+1);
            counter++;
            if (counter == playerQuantity)
                counter = 0;
        }
    }
    
    public int getArmiesAdded() {
        return armiesAdded;
    }
    
     public void setArmiesAdded(int armiesAdded) {
        this.armiesAdded = armiesAdded;
    }
    
     public void goToMovePhase(){
         this.currentPhase = turnPhase.moveArmyPhase;
     }
    
}
