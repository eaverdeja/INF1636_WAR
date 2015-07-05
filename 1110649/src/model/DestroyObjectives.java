/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import viewController.Turn;

/**
 *
 * @author lorenzosaraiva
 */
public class DestroyObjectives extends Objective {
    private Color targetColor;
    private Turn turnController = Turn.getInstance();
    public DestroyObjectives(int type) {
        super(type);
        switch(type){
                case 8:
                    targetColor = Color.BLUE;
                    break;
                case 9:
                    targetColor = Color.WHITE;
                    break;
                case 10:
                    targetColor = Color.RED;
                    break;
                case 11:
                    targetColor = Color.MAGENTA;
                    break;
                case 12:
                    targetColor = Color.YELLOW;
                    break;
                case 13:
                    targetColor = Color.GREEN;
                    break;    
        }
    }   

    public Color getTargetColor() {
        return targetColor;
    }
    
    @Override
    public boolean checkWin(){
        for (Territory t: turnController.getMapPanel().getLstTerritorios()){
            if (t.getOwnerPlayer().getColor() == this.targetColor)
                return false;
        }
        return true;
    }
}
