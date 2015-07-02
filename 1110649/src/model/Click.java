/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Observable;
import model.Territory;

/**
 *
 * @author lorenzosaraiva
 */
public class Click extends Observable{
    
    private Territory t;
    
    public Click()
   {
       
   }
   public void setValue(Territory t)
   {
      this.t = t;
      setChanged();
      notifyObservers();
   }
   public Territory getValue()
   {
      return t;
   }
}
