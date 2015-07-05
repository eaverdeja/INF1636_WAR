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
import model.Territory;

/**
 *
 * @author lorenzosaraiva
 */
public class Turn extends Observable implements Controller, Observer{

    public enum turnPhase {
        newArmyPhase, attackPhase, chooseNewAttacker, moveArmyPhase
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
    private Boolean hasConquered = false;
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
        for (int i = 0; i < players; i++) {
            Player newPlayer = new Player();
            playerArray[i] = newPlayer;
        }

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
    
    //START turn and phase control
    
    public void goToNextPhase() {
        if (currentPhase == turnPhase.newArmyPhase) {
            currentPhase = turnPhase.attackPhase;
            return;
        }
        if (currentPhase == turnPhase.attackPhase) {
            currentPhase = turnPhase.chooseNewAttacker;
            return;
        }
        if (currentPhase == turnPhase.chooseNewAttacker) {
            currentPhase = turnPhase.attackPhase;
            return;
        }
        setChanged();
        notifyObservers();
        clearChanged();
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
        if (currentPlayer.hasChanged()){
            cardsChangeAmount += 2;
        }
        hasConquered = false;
        
        for (Territory t: lstTerritorios){
            t.resetMoves();
        }
        if (index == playerQuantity - 1) {
            currentPlayer = playerArray[0];
        } else {
            currentPlayer = playerArray[index + 1];
        }
        currentPlayer.setHasChanged(false);
        if (currentPlayer.hasFullHand()){
            currentPlayer.canChangeCards();
            currentPlayer.setHasChanged(true);
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        attackController.setCurrentTerritory(mapPanel.getCurrentTerritory());
        click = (Click)o;
        attackController.actionForClick(click.getValue());
    }
    
    @Override
    public void consoleEvent(){
        String info = null;
        if(currentPlayer.newArmyAmount() == armiesAdded){
            info = Console.getInstance().getText().replaceAll("You have \\d+", "You have no more");
        }
        else{
            info = Console.getInstance().getText().replaceAll("\\d+", Integer.toString(currentPlayer.newArmyAmount()-armiesAdded));
        }
        
        Console.getInstance().setText(info);
        Console.getInstance().repaint();
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

    public void goToMovePhase() {
        this.currentPhase = turnPhase.moveArmyPhase;
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
}
