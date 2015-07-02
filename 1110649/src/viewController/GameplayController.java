/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewController;


import javax.swing.JOptionPane;
import model.Player;
import model.Territory;

/**
 *
 * @author lorenzosaraiva
 */
public class GameplayController {
    
    private Player currentPlayer;
    private Player defensePlayer;
    private Turn turnController;
    private Territory currentTerritory;
    private Territory targetTerritory;
    private Territory lostTerritory;
    private int attackingArmies;
    
    public GameplayController(){
        this.turnController = Turn.getInstance();
    }
 
    public void actionForClick(Territory t){
        currentPlayer = turnController.getCurrentPlayer();
        
        System.out.print(turnController.getTurnPhase() + "fase do turno\n");
        System.out.print("O territorio " + t.getNome() + " do jogador "+ t.getOwnerPlayer().getPlayerId() + " tem " + t.getQtdExercitos() + " exercitos \n");

        if (turnController.getTurnPhase() == Turn.turnPhase.newArmyPhase ){
            if (t.getOwnerPlayer() == currentPlayer){
            turnController.getMapPanel().setCurrentTerritory(t);
            setCurrentTerritory(t);
            turnController.getMapPanel().repaint();
                    System.out.print(currentTerritory.getNome() + "eh o current!\n");
            }
        }
        
        if (turnController.getTurnPhase() == Turn.turnPhase.chooseNewAttacker ){
            if (t.getOwnerPlayer() == currentPlayer){
            turnController.getMapPanel().setCurrentTerritory(t);
            setCurrentTerritory(t);
            turnController.getMapPanel().repaint();
            turnController.goToNextPhase();
            System.out.print(currentTerritory.getNome() + "eh o current!\n");
            }
        }
        
        if (turnController.getTurnPhase() == Turn.turnPhase.moveArmyPhase){
            movePhase(t);
        }
        if (turnController.getTurnPhase() == Turn.turnPhase.attackPhase){
            attackPhase(t);
        }     
    }
    
    private void movePhase(Territory t){
    
        if (currentTerritory == null){
            if (t.getOwnerPlayer() == currentPlayer && t.getQtdExercitos() > 1){
                turnController.getMapPanel().setCurrentTerritory(t);
                setCurrentTerritory(t);
                turnController.getMapPanel().repaint();
            }
        }
        else{
            if (t.getOwnerPlayer() == currentPlayer && currentTerritory != t && turnController.getMapPanel().getNeighbourMap().get(currentTerritory).contains(t)){
                if (currentTerritory.getQtdExercitos() > 1){
                    targetTerritory = t;
                    showInputForMove();
                    turnController.getMapPanel().setCurrentTerritory(null);
                    turnController.getMapPanel().repaint();
                }
            }
            if (currentTerritory == t){
                turnController.getMapPanel().setCurrentTerritory(null);
                turnController.getMapPanel().repaint();
            }
        }
    }
    
    private void attackPhase(Territory t){
        
        if (t.getOwnerPlayer() != currentPlayer){

            if (turnController.getMapPanel().getNeighbourMap().get(currentTerritory).contains(t) ){
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
                        JOptionPane.showMessageDialog(null, "ATTACK wins");
                        t.setQtdExercitos(t.getQtdExercitos() - dices.getDefenseArmiesDead());
                        currentTerritory.setQtdExercitos(currentTerritory.getQtdExercitos() - dices.getAttackArmiesDead());
                        if (t.getQtdExercitos() == 0){
                            t.setOwnerPlayer(currentPlayer);
                            currentPlayer.setCurrentTerritories(currentPlayer.getCurrentTerritories()+1);
                            lostTerritory = t;
                            //pergunta ao jogador quantos exercitos quer mover
                            showInputForAttack();
                        }
                    }
                    else{
                        currentTerritory.setQtdExercitos(currentTerritory.getQtdExercitos() - dices.getAttackArmiesDead());
                        t.setQtdExercitos(t.getQtdExercitos() - dices.getDefenseArmiesDead());
                    }
                    turnController.getMapPanel().setCurrentTerritory(null);
                    setCurrentTerritory(null);
                    turnController.goToNextPhase(); 
                    turnController.getMapPanel().repaint();
                }
            }
        }
        else{
           turnController.getMapPanel().setCurrentTerritory(t);
            setCurrentTerritory(t);
           turnController.getMapPanel().repaint();
        }
    }
    
    private void showInputForAttack(){
        
        String nome = null;
        do {
            nome = JOptionPane.showInputDialog("Quantos exercitos deseja passar? (1-" + attackingArmies + ")" );
            if (Integer.parseInt(nome) != 1 && Integer.parseInt(nome) != 2 && Integer.parseInt(nome) != 3) {
                JOptionPane.showMessageDialog(null,"Escolha um dos numeros possíveis.");
            }
        } while (Integer.parseInt(nome) != 1 && Integer.parseInt(nome) != 2 && Integer.parseInt(nome) != 3);

        JOptionPane.showMessageDialog(null, "Voce passou " + nome + " exércitos.");
        lostTerritory.setQtdExercitos(Integer.parseInt(nome));
        currentTerritory.setQtdExercitos(currentTerritory.getQtdExercitos() - Integer.parseInt(nome));
    }
    
    private void showInputForMove(){
        String nome = null;
        
        do {
            nome = JOptionPane.showInputDialog("Quantos exercitos deseja passar? (1-" + (currentTerritory.getQtdExercitos() - 1) + ")" );
            if (Integer.parseInt(nome) < currentTerritory.getQtdExercitos() - 1 && Integer.parseInt(nome) > 0) {
                JOptionPane.showMessageDialog(null,"Escolha um dos numeros possíveis.");
            }
        } while (Integer.parseInt(nome) < currentTerritory.getQtdExercitos() - 1 && Integer.parseInt(nome) > 0);
        
        JOptionPane.showMessageDialog(null, "Voce passou " + nome + " exércitos.");
        currentTerritory.setQtdExercitos(currentTerritory.getQtdExercitos() - Integer.parseInt(nome));
        targetTerritory.setQtdExercitos(targetTerritory.getQtdExercitos() + Integer.parseInt(nome));
        setCurrentTerritory(null);
    }

    public void setCurrentTerritory(Territory currentTerritory) {
        this.currentTerritory = currentTerritory;
    }
}
