package model;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private List<Integer> armyAmounts = new ArrayList<>();
    private List<Player> playerOwner = new ArrayList<>();
	public List<Integer> getArmyAmounts() {
		return armyAmounts;
	}
	public void setArmyAmounts(List<Integer> armyAmounts) {
		this.armyAmounts = armyAmounts;
	}
	public List<Player> getPlayerOwner() {
		return playerOwner;
	}
	public void setPlayerOwner(List<Player> playerOwner) {
		this.playerOwner = playerOwner;
	}

}
