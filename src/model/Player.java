/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;

/**
 *
 * @author lorenzosaraiva
 */
public class Player {
    
    private Color color;
    private int playerId;
    static int playerQuantity = 0;
    
    public Player(){
        this.playerId = playerQuantity;
        playerQuantity++;
        this.color = getPlayerColor(this.playerId);
        System.out.println(playerQuantity);
    }
    
    private Color getPlayerColor(int id){
        switch(id){
            case 0:
                return Color.WHITE;
            case 1:
                return Color.BLACK;
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

  
    public int getPlayerId() {
        return playerId;
    }


    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
   
}
