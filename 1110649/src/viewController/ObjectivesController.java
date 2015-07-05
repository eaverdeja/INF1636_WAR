/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewController;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.ConquestObjectives;
import model.Continent;
import model.DestroyObjectives;
import model.Objective;
import model.Player;

/**
 *
 * @author lorenzosaraiva
 */
public class ObjectivesController {
    
    private List<Continent> continentList = new ArrayList();
    private List<Objective> objectiveList = new ArrayList();
    private Turn turnController = Turn.getInstance();
    int playersLacking;
    
    public ObjectivesController(){
        playersLacking = 6 - turnController.getPlayers().size(); 
        for (int i = 0; i< 14 - playersLacking; i++){
            Objective o = objFactory(i);
            objectiveList.add(o);
            
        }
        
    }
    
    private Objective objFactory(int type){
        String description;
        Objective o;
        switch (type){
                case 0:
                    o = new ConquestObjectives(type);
                    return o;
                case 1:
                    o = new ConquestObjectives(type);
                    return o;
                case 2:
                    o = new ConquestObjectives(type);
                    return o;
                case 3:
                    o = new ConquestObjectives(type);
                    return o;
                case 4:
                    o = new ConquestObjectives(type);
                    return o;
                case 5:
                    o = new ConquestObjectives(type);
                    return o;
                case 6:
                    o = new ConquestObjectives(type);
                    return o;
                case 7:
                    o = new ConquestObjectives(type);
                    return o;
                case 8:
                    o = new DestroyObjectives(type);
                    return o;
                case 9:
                    o = new DestroyObjectives(type);
                    return o;
                case 10:
                    o = new DestroyObjectives(type);
                    return o;
                case 11:
                    o = new DestroyObjectives(type);
                    return o;
                case 12:
                    o = new DestroyObjectives(type);
                    return o;
                case 13:
                    o = new DestroyObjectives(type);
                    return o;   
        }
        return null;
    }
    
    public void randomizeObjectives(){
        Random random = new Random();
        for (Player p :turnController.getPlayers()){
            int randValue = random.nextInt(objectiveList.size());
            Objective o = objectiveList.get(randValue);
            while (o.getType() == 8 && p.getColor() == Color.BLUE){
                randValue = random.nextInt(objectiveList.size());
                o = objectiveList.get(randValue);
            }
            while (o.getType() == 9 && p.getColor() == Color.WHITE){
                randValue = random.nextInt(objectiveList.size());
                o = objectiveList.get(randValue);
            }
            while (o.getType() == 10 && p.getColor() == Color.RED){
                randValue = random.nextInt(objectiveList.size());
                o = objectiveList.get(randValue);
            }
            while (o.getType() == 11 && p.getColor() == Color.MAGENTA){
                randValue = random.nextInt(objectiveList.size());
                o = objectiveList.get(randValue);
            }
            while (o.getType() == 12 && p.getColor() == Color.YELLOW){
                randValue = random.nextInt(objectiveList.size());
                o = objectiveList.get(randValue);
            }
            while (o.getType() == 13 && p.getColor() == Color.GREEN){
                randValue = random.nextInt(objectiveList.size());
                o = objectiveList.get(randValue);
            }
            System.out.println(o.getDescription());
            
            p.setObjective(o);
            objectiveList.remove(randValue);
        }
                    System.out.println("--------------------------------");

    }


    public List<Continent> getContinentList() {
        return continentList;
    }

    public void setContinentList(List<Continent> continentList) {
        this.continentList = continentList;
    }
}
