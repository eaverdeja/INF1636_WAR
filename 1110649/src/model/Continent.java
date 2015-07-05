/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lorenzosaraiva
 */
public class Continent {
    
    private List<Territory> territoryList = new ArrayList();
    private String name;
    
    public Continent(String s, List<Territory> list){
        name = s;
        territoryList = list;
    }
    
    public boolean playerHasContinent(Player p){
        for (Territory t:getTerritoryList()){
            System.out.println("vez de " + t.getNome());
            if (t.getOwnerPlayer() != p){
                System.out.println("o errado eh" + t.getNome());
                return false;
            }
        }
        return true;
    }

    public List<Territory> getTerritoryList() {
        return territoryList;
    }

    public String getName() {
        return name;
    }
}
