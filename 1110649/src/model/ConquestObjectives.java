/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import viewController.Turn;

/**
 *
 * @author lorenzosaraiva
 */
public class ConquestObjectives extends Objective {
    
    private Turn turnController = Turn.getInstance();

    public ConquestObjectives(int type){
     super(type);
    }
    
    @Override
    public boolean checkWin(){
        switch (getType()){
                case 0:
                    if (!checkPlayerHasContinent("EU",turnController.getCurrentPlayer()) || !checkPlayerHasContinent("OC",turnController.getCurrentPlayer()))
                        return false;
                    else{
                        if (checkPlayerHasContinent("ASI",turnController.getCurrentPlayer())||checkPlayerHasContinent("AS",turnController.getCurrentPlayer())||checkPlayerHasContinent("AF",turnController.getCurrentPlayer()))
                            return true;
                        else
                            return false;
                    }
                case 1:
                    if (!checkPlayerHasContinent("ASI",turnController.getCurrentPlayer()) || !checkPlayerHasContinent("AS",turnController.getCurrentPlayer()))
                        return false;
                    else
                        return true;
                case 2:
                    if (!checkPlayerHasContinent("EU",turnController.getCurrentPlayer()) || !checkPlayerHasContinent("AS",turnController.getCurrentPlayer()))
                        return false;
                    else{
                        if (checkPlayerHasContinent("ASI",turnController.getCurrentPlayer())||checkPlayerHasContinent("OC",turnController.getCurrentPlayer())||checkPlayerHasContinent("AF",turnController.getCurrentPlayer()))
                            return true;
                        else
                            return false;
                    }
                case 3:
                    int counter = 0;
                    for (Territory t: turnController.getMapPanel().getLstTerritorios()){
                        if (t.getOwnerPlayer() == turnController.getCurrentPlayer() && t.getQtdExercitos() >= 2)
                            counter++;
                    }
                    if (counter >= 18)
                        return true;
                    else
                        return false;
                case 4:
                    if (!checkPlayerHasContinent("ASI",turnController.getCurrentPlayer()) || !checkPlayerHasContinent("AF",turnController.getCurrentPlayer()))
                        return false;
                    else
                        return true;
                case 5:
                    if (!checkPlayerHasContinent("AN",turnController.getCurrentPlayer()) || !checkPlayerHasContinent("AF",turnController.getCurrentPlayer()))
                        return false;
                    else
                        return true;
                case 6:
                    int c = 0;
                    for (Territory t: turnController.getMapPanel().getLstTerritorios()){
                        if (t.getOwnerPlayer() == turnController.getCurrentPlayer())
                            c++;
                    }
                    if (c >= 24)
                        return true;
                    else
                        return false;
                case 7:
                    if (!checkPlayerHasContinent("AN",turnController.getCurrentPlayer()) || !checkPlayerHasContinent("OC",turnController.getCurrentPlayer()))
                        return false;
                    else
                        return true;
        }
        return false;
    }
    
    private boolean checkPlayerHasContinent(String s,Player p){
        
        for (Continent c:turnController.getObjController().getContinentList()){
            System.out.println(c.getName());
            if (c.getName() == s){
                return c.playerHasContinent(p);
            }
        }
        return false;
    }
}
