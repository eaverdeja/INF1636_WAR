/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import view.Console;
import view.DicePanel;
import java.awt.Font;
import java.awt.Window;
import java.util.Observable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import model.Player;
import model.Territory;

/**
 *
 * @author lorenzosaraiva
 */
public class GameplayController extends Observable implements Controller{
    
    private GameManager gameManager;
    private Player defensePlayer;
    private Territory currentTerritory;
    private Territory targetTerritory;
    private Territory lostTerritory;
    private int attackingArmies;
    private String attackMsg;
    private String moveMsg;
    
    public GameplayController(){
        this.gameManager = GameManager.getInstance();
    }
 
    public void actionForClick(Territory t){
        Player currentPlayer = gameManager.getCurrentPlayer();
        
        if (gameManager.getPlayer() != gameManager.getCurrentPlayer().getPlayerId()){
        	System.out.printf("Current %d This %d Not your turn!!", gameManager.getPlayer(),gameManager.getCurrentPlayer().getPlayerId() );
        	return;
        }
        //Add the console so it listens to bussiness
        addObserver(Console.getInstance());
        
        if (gameManager.getTurnPhase() == GameManager.turnPhase.newArmyPhase ){
            if (t.getOwnerPlayer() == currentPlayer){
                gameManager.setCurrentTerritory(t);
                setCurrentTerritory(t);
                gameManager.repaint();
            }
            else{
                gameManager.setCurrentTerritory(null);
                setCurrentTerritory(null);
            }
                
        }
        
        if (gameManager.getTurnPhase() == GameManager.turnPhase.newAttackerPhase ){
            if (t.getOwnerPlayer() == currentPlayer){
                gameManager.setCurrentTerritory(t);
                gameManager.repaint();
                gameManager.goToNextPhase();
            }
        }
        
        if (gameManager.getTurnPhase() == GameManager.turnPhase.moveArmyPhase){
            movePhase(t);
        }
        if (gameManager.getTurnPhase() == GameManager.turnPhase.attackPhase){
            attackPhase(t);
        }
        
        setCurrentTerritory(gameManager.getCurrentTerritory());
        gameManager.repaint();
    }
    
    private void movePhase(Territory t){
        Player currentPlayer = gameManager.getCurrentPlayer();
        if (currentTerritory == null){
            if (t.getOwnerPlayer() == currentPlayer && t.getAmountOfMovableArmies() > 0){
                gameManager.setCurrentTerritory(t);
                setCurrentTerritory(t);
                gameManager.repaint();
            }
        }
        else{
            if (t.getOwnerPlayer() == currentPlayer && gameManager.getNeighbourMap().get(currentTerritory).contains(t)){
                if (currentTerritory.getAmountOfMovableArmies() > 0){
                	moveMsg = "Make your move";
                	targetTerritory = t;
                    showInputForMove();
                }
            }
            else{
            	moveMsg = "Make your move";
            }
	        gameManager.setCurrentTerritory(null);
	        gameManager.repaint();
        }
    }
    
    private void attackPhase(Territory t){
        Player currentPlayer = gameManager.getCurrentPlayer();
        if (t.getOwnerPlayer() != currentPlayer){

            if (gameManager.getNeighbourMap().get(currentTerritory).contains(t) ){
                if (currentTerritory.getQtdExercitos() > 1){
                    defensePlayer = t.getOwnerPlayer();
                    if (currentTerritory.getQtdExercitos() - 1 > 3){
                        attackingArmies = 3;
                    }
                    else{
                        attackingArmies = currentTerritory.getQtdExercitos() - 1;
                    }
                    DicePanel dices = new DicePanel(currentTerritory.getQtdExercitos() - 1, t.getQtdExercitos());
                    dices.showDices();
                    if (dices.attackWins()){
                        attackMsg = "Choose a new victim!!!";
                        JOptionPane.showMessageDialog(null, "Attack wins!");
                        gameManager.setHasConquered(Boolean.TRUE);
                        t.setQtdExercitos(t.getQtdExercitos() - dices.getDefenseArmiesDead());
                        currentTerritory.setQtdExercitos(currentTerritory.getQtdExercitos() - dices.getAttackArmiesDead());
                        if (t.getQtdExercitos() == 0){
                            t.setOwnerPlayer(currentPlayer);
                            currentPlayer.setCurrentTerritories(currentPlayer.getCurrentTerritories()+1);
                            lostTerritory = t;
                            //pergunta ao jogador quantos exercitos quer mover
                            if(attackingArmies > 1) showInputForAttack();
                            else{
                                lostTerritory.setQtdExercitos(1);
                                currentTerritory.setQtdExercitos(currentTerritory.getQtdExercitos() - 1);
                            }
                        }
                    }
                    else{
                        attackMsg = "Be careful...";
                        JOptionPane.showMessageDialog(null, "Defense wins!");
                        currentTerritory.setQtdExercitos(currentTerritory.getQtdExercitos() - dices.getAttackArmiesDead());
                        t.setQtdExercitos(t.getQtdExercitos() - dices.getDefenseArmiesDead());
                    }
                    gameManager.setCurrentTerritory(null);
                    setCurrentTerritory(null);
                    gameManager.goToNextPhase(); 
                    gameManager.repaint();
                    
                    //Close the dices JDialog
                    Window w = SwingUtilities.getWindowAncestor(dices);
                    w.setVisible(false);
                }
            }
            else{
                attackMsg = "Attack thy neighbour!";
            }
        }
        else{
           gameManager.setCurrentTerritory(t);
           setCurrentTerritory(t);
           gameManager.repaint();
        }
    }
    
    private void showInputForAttack(){
        
        int val = 0;
        String[] options = {"OK"};
        JPanel panel = new JPanel();
        JLabel lbl = new JLabel("Quantos exercitos deseja passar? (1-" + attackingArmies + ")" );
        JTextField txt = new JTextField(10);
        panel.add(lbl);
        panel.add(txt);
        do{
            int nome = JOptionPane.showOptionDialog(null, panel, "Atenção", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);


            if (nome == 0){
                String num = txt.getText();
                try {
                    val = Integer.parseInt(num);
                } catch (NumberFormatException e) {
                    val = -1;
                }
                if (val > 0 && val <=attackingArmies)
                    break;
                else
                    JOptionPane.showMessageDialog(null, "Escolha um numero valido!");
            }
        }while(true);

        JOptionPane.showMessageDialog(null, "Voce passou " + val + " exercitos.");
        
        

        
        lostTerritory.setQtdExercitos(val);
        currentTerritory.setQtdExercitos(currentTerritory.getQtdExercitos() - val);
        
//        gameManager.currentPlay.getOwnerChanged().add(lostTerritory);
//        gameManager.currentPlay.getPlayer().add(gameManager.getCurrentPlayer());
//        gameManager.currentPlay.getNewTerritoryArmies().add(val);
//
//        gameManager.currentPlay.getArmyChanged().add(currentTerritory);
//        gameManager.currentPlay.getNewArmyAmount().add(-val);
    }
    
    private void showInputForMove(){
        
        int val = 0;
        String[] options = {"OK"};
        JPanel panel = new JPanel();
        JLabel lbl = new JLabel("Quantos exercitos deseja passar? (0-" + currentTerritory.getAmountOfMovableArmies() + ")" );
        JTextField txt = new JTextField(10);
        panel.add(lbl);
        panel.add(txt);
        do{
        int nome = JOptionPane.showOptionDialog(null, panel, "Atenção", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
         
        
        if (nome == 0){
            String num = txt.getText();
            try {
                val = Integer.parseInt(num);
            } catch (NumberFormatException e) {
                val = -1;
            }
            if (val >= 0 && val <=currentTerritory.getAmountOfMovableArmies())
                break;
            else
                JOptionPane.showMessageDialog(null, "Escolha um numero valido!");
            }
        }while(true);

        JOptionPane.showMessageDialog(null, "Voce passou " + val + " exércitos.");
        
        currentTerritory.removeArmies(val);
        targetTerritory.addMovedArmies(val);
        setCurrentTerritory(null);
    }

    public void setCurrentTerritory(Territory currentTerritory) {
        this.currentTerritory = currentTerritory;
        
        setChanged();
        notifyObservers();
        clearChanged();
    }
    
    public void consoleEvent(){
        
        Console c = Console.getInstance();
        
        String phase = GameManager.getInstance().getTurnPhase().name();
        Player currentPlayer = GameManager.getInstance().getCurrentPlayer();
        
        String info = null;
        String phaseMsg = null;
        
        if(phase.equals("newArmyPhase")){
            phaseMsg = "You have "+(currentPlayer.newArmyAmount()+" armies left");
            if(currentTerritory == null){
                phaseMsg = "Thats not yours!";
            }
        }
        else if(phase.equals("attackPhase")){   
            phaseMsg = attackMsg;
            if(currentTerritory == null){
                phaseMsg = "Choose a territory to command";
            }
        }
        else if(phase.equals("newAttackerPhase")){
            phaseMsg = "Seek and destroy";
        }
        else if(phase.equals("moveArmyPhase")){
            phaseMsg = "Make your move";
        }
        
        if(currentTerritory != null){
            info = "Country = "+currentTerritory.getNome()
                    +"\nWe are in the "+GameManager.getInstance().getTurnPhase().name()
                    +"\n"+phaseMsg;
        }
        else{
            info = "Country = none"
                    +"\nWe are in the "+GameManager.getInstance().getTurnPhase().name()
                    +"\n"+phaseMsg;
        }
        
        c.setText(info);
        c.setFont(new Font("TimesRoman",Font.BOLD,14));
        c.repaint();
    }
}