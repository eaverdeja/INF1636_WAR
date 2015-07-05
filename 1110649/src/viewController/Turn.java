/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewController;

import model.Click;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import model.Player;
import java.util.Random;
import javax.swing.JOptionPane;
import model.Continent;
import model.Territory;

/**
 *
 * @author lorenzosaraiva
 */
public class Turn extends Observable implements Controller, Observer{

    

    public enum turnPhase {
        newArmyPhase, attackPhase, newAttackerPhase, moveArmyPhase
    }

    private Player[] playerArray;
    private static int playerQuantity;
    private Player currentPlayer;
    private static Turn turnInstance = null;
    private turnPhase currentPhase;
    private List<Territory> lstTerritorios = new ArrayList<>();
    private int armiesAdded = 0;
    private Click click = null;
    private MapPanel mapPanel;
    private GameplayController attackController;
    private CardsController cardsController;
    private ObjectivesController objController;
    private Boolean hasConquered = false;
    private Boolean finishedAttacking = false;
    private int cardsChangeAmount = 4;

    //Implementing Singleton pattern
    protected Turn() {
    }

    public static Turn getInstance() {
        if (turnInstance == null) {
            turnInstance = new Turn();
        }
        return turnInstance;
    }
    
    //START Creating players and distributing territories 
    public void createAndRandomizePlayers(int players) {
        playerArray = new Player[players];
        playerQuantity = players;
        currentPhase = turnPhase.newArmyPhase;
        attackController = new GameplayController();
        cardsController = new CardsController();
        objController = new ObjectivesController();
        for (int i = 0; i < players; i++) {
            Player newPlayer = new Player();
            playerArray[i] = newPlayer;
        }
        objController.randomizeObjectives();
        currentPlayer = this.playerArray[0];
    }

    private void shuffleArray(Player[] array, int size) {
        int index;
        Player temp;
        Random random = new Random();
        for (int i = size - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    public void randomizeTerritories() {
        int counter = 0;
        long seed = System.nanoTime();
        Collections.shuffle(lstTerritorios, new Random(seed));
        for (Territory t : lstTerritorios) {
            t.setOwnerPlayer(this.playerArray[counter]);
            t.setQtdExercitos(1);
            this.playerArray[counter].setCurrentTerritories(this.playerArray[counter].getCurrentTerritories() + 1);
            counter++;
            if (counter == playerQuantity) {
                counter = 0;
            }
        }
    }
    //END Creating players and distributing territories 
    private boolean checkVictory(){
        return currentPlayer.getObjective().checkWin();
    }
    //START turn and phase control
    
    public void goToNextPhase() {
        if (currentPhase == turnPhase.newArmyPhase) {
            currentPhase = turnPhase.attackPhase;
        }
        else if (currentPhase == turnPhase.attackPhase && finishedAttacking){
            currentPhase = turnPhase.moveArmyPhase;
        }
        else if (currentPhase == turnPhase.attackPhase) {
            currentPhase = turnPhase.newAttackerPhase;
        }
        else if (currentPhase == turnPhase.newAttackerPhase) {
            currentPhase = turnPhase.attackPhase;
        }
        setChanged();
        notifyObservers();
        clearChanged();
    }
    
    public void goToMovePhase(){
        currentPhase = turnPhase.moveArmyPhase;
    }

    public void nextTurn() {
        int index = 0;
        currentPhase = turnPhase.newArmyPhase;
        for (Player p : playerArray) {
            if (p.getPlayerId() == getCurrentPlayer().getPlayerId()) {
                break;
            } else {
                index++;
            }
        }
        
        updateCardsChangedAmount();
        hasConquered = false;
        finishedAttacking = false;
        
        if (checkVictory()){
            JOptionPane.showMessageDialog(null,"O Player " + currentPlayer.getColor() + " ganhou!");
        }
        
        for (Territory t: lstTerritorios){
            t.resetMoves();
        }
        if (index == playerQuantity - 1) {
            currentPlayer = playerArray[0];
        } else {
            currentPlayer = playerArray[index + 1];
        }
        currentPlayer.setHasChanged(false);
        updatePlayerContinentBonus();
        if (currentPlayer.hasFullHand()){
            currentPlayer.canChangeCards();
            currentPlayer.setHasChanged(true);
        }
    }
    
    private void updateCardsChangedAmount(){
        if (currentPlayer.hasChanged()){
            if (cardsChangeAmount < 12){
            cardsChangeAmount += 2;
            }
            else{
                if (cardsChangeAmount == 12)
                    cardsChangeAmount = 15;
                else
                    cardsChangeAmount+=5;
            }     
        }
    
    }
    
    private void updatePlayerContinentBonus(){
        currentPlayer.setContinentBonus(0);
        if (checkPlayerHasContinent("AN",currentPlayer))
            currentPlayer.setContinentBonus(currentPlayer.getContinentBonus()+5);
        if (checkPlayerHasContinent("AF",currentPlayer))
            currentPlayer.setContinentBonus(currentPlayer.getContinentBonus()+3);
        if (checkPlayerHasContinent("AS",currentPlayer))
            currentPlayer.setContinentBonus(currentPlayer.getContinentBonus()+2);
        if (checkPlayerHasContinent("ASI",currentPlayer))
            currentPlayer.setContinentBonus(currentPlayer.getContinentBonus()+7);
        if (checkPlayerHasContinent("OC",currentPlayer))
            currentPlayer.setContinentBonus(currentPlayer.getContinentBonus()+2);
        if (checkPlayerHasContinent("EU",currentPlayer))
            currentPlayer.setContinentBonus(currentPlayer.getContinentBonus()+5);
    }

    
    @Override
    public void update(Observable o, Object arg) {
        click = (Click)o;
        attackController.actionForClick(click.getValue());
        attackController.setCurrentTerritory(mapPanel.getCurrentTerritory());
    }
    
    @Override
    public void consoleEvent(){
        String info = null;
        if(currentPhase == turnPhase.newArmyPhase){
            info = Console.getInstance().getText().replaceAll("\\d+", Integer.toString(currentPlayer.newArmyAmount()-armiesAdded));    
            info = info.replaceAll("We are in the .*","We are in the newArmyPhase");
        }
        else if(currentPhase == turnPhase.attackPhase){
            info = Console.getInstance().getText().replaceAll("You have \\d+ armies left", "Who do you wish to attack?");
            info = info.replaceAll("We are in the .*","We are in the attackPhase");
        }
        else if(currentPhase == turnPhase.moveArmyPhase){
            info = Console.getInstance().getText();
            info = Console.getInstance().getText().replaceAll("Who do you wish to attack\\?", "Make your move");
            info = info.replaceAll("We are in the .*","We are in the moveArmyPhase");
        }
        
        Console.getInstance().setText(info);
        Console.getInstance().repaint();
    }
    
    private boolean checkPlayerHasContinent(String s,Player p){
        
        for (Continent c:getObjController().getContinentList()){
            if (c.getName() == s){
                return c.playerHasContinent(p);
            }
        }
        return false;
    }
    //END turn and phase control
    
    //Getters and Setters

    public List<Player> getPlayers() {
        return Arrays.asList(playerArray);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public turnPhase getTurnPhase() {
        return currentPhase;
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
        return mapPanel;
    }

    public void setMapPanel(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
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

    
}
