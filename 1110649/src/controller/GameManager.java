/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.MainFrame;

import java.awt.BorderLayout;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.io.IOException;

import view.Console;
import view.MapPanel;
import model.Click;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import model.Player;

import java.util.Set;

import model.Continent;
import model.GameState;
import model.Territory;
import network.Client;

/**
 *
 * @author lorenzosaraiva
 */
public class GameManager extends Observable implements Controller, Observer{

 
    public enum turnPhase {
        newArmyPhase, attackPhase, newAttackerPhase, moveArmyPhase
    }
    
    private static GameManager turnInstance = null;
    private static int playerQuantity;
    
    private MainFrame mainFrame;
    
    
    private List<Territory> lstTerritorios = new ArrayList<>();
    private Map<Territory,List<Territory>> neighbourMap;

    private Click click = null; 
 
    private GameplayController attackController;
    private CardsController cardsController;
    private ObjectivesController objController;
    private TurnController turnController;
    private MapController mapController;
    private ButtonsController buttonsController;
    private PlayerController playerController;
    private SerializationController serializationController;
    
    private Boolean hasConquered = false;
    private Boolean finishedAttacking = false;
    
    private int armiesAdded = 0;
    private int cardsChangeAmount = 0;
    
    public GameState currentState;


    //Implementing Singleton pattern
    protected GameManager() {
        
    }

    public static GameManager getInstance() {
        if (turnInstance == null) {
            turnInstance = new GameManager();
        }
        return turnInstance;
    }
    
     
     
    public void gameSetUp(int players){
        playerController = new PlayerController(players);
        serializationController = new SerializationController();
        turnController = new TurnController();
        mapController = new MapController();
        attackController = new GameplayController();
        cardsController = new CardsController();
        objController = new ObjectivesController();
        objController.randomizeObjectives();
        playerController.randomizeTerritories();
        getObjController().setContinentList(getMapController().getContinentList());
        buttonsController = new ButtonsController();
        currentState = new GameState();
    }
    
    
    //START turn and phase control
    
    public void goToNextPhase() {
    	
        turnController.goToNextPhase();
        setChanged();
        notifyObservers();
        clearChanged();
    }
   
    public void nextTurn() {
    	
    	serializationController.saveState();
    	Client.sendMessage("state_"+serializationController.parseIntoString());
    	
    	turnController.nextTurn();
        setChanged();
        notifyObservers();
        clearChanged();
    	hasConquered = false;
    }
      
    @Override
    public void update(Observable o, Object arg) {
        click = (Click)o;
        attackController.actionForClick(click.getValue());
        attackController.setCurrentTerritory(getMapPanel().getCurrentTerritory());
    }
    
    @Override
    public void consoleEvent(){
        String[] infoArray = null;
        String info = null;
        
        if(turnController.getCurrentPhase() == turnPhase.newArmyPhase){
            
            infoArray = Console.getInstance().getText().split("\\r?\\n");
	        info = infoArray[0].concat("\n");
	        info += infoArray[1].replaceFirst(".*","We are in the newArmyPhase\n");
	        info += infoArray[2].replaceFirst(".*","You have "+(playerController.getCurrentPlayer().newArmyAmount() - armiesAdded)+" armies left\n");
        }
        else if(turnController.getCurrentPhase() == turnPhase.attackPhase){
            info = Console.getInstance().getText().replaceAll("You have \\d+ armies left", "Who do you wish to attack?");
            info = info.replaceAll("We are in the .*","We are in the attackPhase");
        }
        else if(turnController.getCurrentPhase() == turnPhase.moveArmyPhase){            
        	infoArray = Console.getInstance().getText().split("\\r?\\n");
	        info = infoArray[0].concat("\n");
	        info += infoArray[1].replaceAll("We are in the .*","We are in the moveArmyPhase\n");
	        info += infoArray[2].replaceFirst(".*","Divide and Conquer\n");
        }
        
        Console.getInstance().setText(info);
        Console.getInstance().repaint();
    }
    
    
    //END turn and phase control
    
    //Getters and Setters

    public List<Player> getPlayers() {
        return Arrays.asList(playerController.getPlayerArray());
    }

    public Player getCurrentPlayer() {
        return playerController.getCurrentPlayer();
    }
    
    public void setCurrentPlayer(Player currentPlayer) {
        playerController.setCurrentPlayer(currentPlayer);
    }

    public turnPhase getTurnPhase() {
        return turnController.getCurrentPhase();
    }

    public void setLstTerritorios(List<Territory> lstTerritorios) {
        this.lstTerritorios = lstTerritorios;
    }

    public int getArmiesAdded() {
        return armiesAdded;
    }

    public void setArmiesAdded(int armiesAdded) {
        this.armiesAdded = armiesAdded;
        
        setChanged();
        notifyObservers();
    }
    
    public MapPanel getMapPanel() {
        return getMapController().getMapPanel();
    }

    
    public Boolean getHasConquered() {
        return hasConquered;
    }

    public void setHasConquered(Boolean hasConquered) {
        this.hasConquered = hasConquered;
    }
    
    public CardsController getCardsController() {
        return cardsController;
    }

    public void setCardsController(CardsController cardsController) {
        this.cardsController = cardsController;
    }

    public int getCardsChangeAmount() {
        return cardsChangeAmount;
    }

    public void setCardsChangeAmount(int cardsChangeAmount) {
        this.cardsChangeAmount = cardsChangeAmount;
    }
    /**
     * @return the finishedAttacking
     */
    public Boolean getFinishedAttacking() {
        return finishedAttacking;
    }

    /**
     * @param finishedAttacking the finishedAttacking to set
     */
    public void setFinishedAttacking(Boolean finishedAttacking) {
    	this.finishedAttacking = finishedAttacking;
    }
    
    public ObjectivesController getObjController() {
        return objController;
    }

    public void setObjController(ObjectivesController objController) {
        this.objController = objController;
    }
    
    public void setCurrentTerritory(Territory t){
        getMapPanel().setCurrentTerritory(t);
    }
    
    public Territory getCurrentTerritory(){
        return getMapPanel().getCurrentTerritory();
    }
    
    public void repaint(){
        getMapPanel().repaint();
    }
    

    public Map<Territory,List<Territory>> getNeighbourMap() {
        return getMapController().getNeighbourMap();
    }
    
    public List<Territory> getTerritoryList(){
        return getMapController().getTerritoryList();
    }
    
    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    
    public MapController getMapController() {
        return mapController;
    }

    public void setMapController(MapController mapController) {
        this.mapController = mapController;
    }
    
    public boolean checkPlayerHasContinent(String s,Player p){
        
        return playerController.checkPlayerHasContinent(s, p);
    }
    
    public void applyState() throws IOException{
    	serializationController.applyState();
    	
    }
    
    public Player getPlayerForId(int id){
    	return playerController.getPlayerForId(id);	
    }
}