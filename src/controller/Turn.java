/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Player;
import java.util.Random;

/**
 *
 * @author lorenzosaraiva
 */
public class Turn {

    private Player[] playerArray;
    private final int playerQuantity;
    private static Player currentPlayer;

    public Turn(int players) {
        this.playerArray = new Player[players];
        this.playerQuantity = players;
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
    
    public static Player getCurrentPlayer() {
        return currentPlayer;
    }
       
}
