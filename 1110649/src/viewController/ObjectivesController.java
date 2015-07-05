/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewController;

import java.util.ArrayList;
import java.util.List;
import model.Continent;
import model.Objective;
import model.Player;

/**
 *
 * @author lorenzosaraiva
 */
public class ObjectivesController {
    
    private List<Continent> continentList = new ArrayList();
    private Objective[] objCheckList;
    
    public ObjectivesController(){
        this.objCheckList> = new Objective[40];
    
    }
    
    public void addContinent(Continent c){
        getContinentList().add(c);
    }
    
    public boolean checkPlayerHasContinent(String s,Player p){
        
        for (Continent c:continentList){
            System.out.println(c.getName());
            if (c.getName() == s){
                return c.playerHasContinent(p);
            }
        }
        return false;
    }

    public List<Continent> getContinentList() {
        return continentList;
    }

    public void setContinentList(List<Continent> continentList) {
        this.continentList = continentList;
    }
}
