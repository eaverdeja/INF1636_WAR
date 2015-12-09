package controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.StringTokenizer;

import model.GameState;
import model.Territory;

public class SerializationController {
	
	private GameState currentState = new GameState();
	private String currentString = new String();
    private GameManager gameManager = GameManager.getInstance();
	
	public void applyState(String s) throws IOException{
		currentState = unparseFromString(s);
		List<Territory> territoryList = gameManager.getMapController().getTerritoryList();
		Territory t;
		for (int i = 0; i < territoryList.size(); i++) {
			t = territoryList.get(i);
			t.setQtdExercitos(currentState.getArmyAmounts().get(i));
			t.setOwnerPlayer(currentState.getPlayerOwner().get(i));
			
		}
		
		gameManager.getMapPanel().repaint();
	}
	
	public void saveState(){
		
    	Territory t;
    	GameState g = new GameState();
		for (int i = 0; i < gameManager.getMapController().getTerritoryList().size(); i++){
    		t = gameManager.getMapController().getTerritoryList().get(i);
    		g.getArmyAmounts().add(t.getQtdExercitos());
    		g.getPlayerOwner().add(t.getOwnerPlayer());
    	}

    	Integer s;
    	for (int i = 0; i < currentState.getArmyAmounts().size(); i++){
    		s = g.getArmyAmounts().get(i);
    	}
    	currentState = g;
    	currentString = parseIntoString();
	}
	
	public String parseIntoString(){
		StringBuilder string = new StringBuilder();
		Integer armies,player;
		for (int i = 0; i < currentState.getArmyAmounts().size();i++){
			armies = currentState.getArmyAmounts().get(i);
			player = currentState.getPlayerOwner().get(i).getPlayerId();
			string.append(String.format("%d %d ", armies, player));
		}
		
		return string.toString();
	}
	
	public GameState unparseFromString(String string) throws IOException{
		StringReader reader = new StringReader(string);
		GameState state = new GameState();
		
		StringTokenizer strToken = new StringTokenizer(string);
	    //Reads in the numbers to the array

	    
		for (int i = 0; i < gameManager.getMapController().getTerritoryList().size(); i++){
    		state.getArmyAmounts().add(i, Integer.parseInt((String)strToken.nextElement()));
    		state.getPlayerOwner().add(i, gameManager.getPlayerForId(Integer.parseInt((String)strToken.nextElement())));
    		
    	}
		currentState = state;
		return state;
	}
}
