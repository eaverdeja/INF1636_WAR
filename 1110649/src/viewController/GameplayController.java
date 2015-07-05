/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewController;


import java.awt.Font;
import java.util.Observable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Player;
import model.Territory;

/**
 *
 * @author lorenzosaraiva
 */
public class GameplayController extends Observable implements Controller{
    
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
        
        System.out.print(turnController.getTurnPhase() + " fase do turno\n");
        System.out.print("O territorio " + t.getNome() + " do jogador "+ t.getOwnerPlayer().getPlayerId() + " tem " + t.getQtdExercitos() + " exercitos \n");

        //Add the console so it listens to bussiness
        addObserver(Console.getInstance());
        
        if (turnController.getTurnPhase() == Turn.turnPhase.newArmyPhase ){
            if (t.getOwnerPlayer() == currentPlayer){
                turnController.getMapPanel().setCurrentTerritory(t);
                setCurrentTerritory(t);
                turnController.getMapPanel().repaint();
                System.out.print(currentTerritory.getNome() + "eh o current!\n");
            }
            else{
                turnController.getMapPanel().setCurrentTerritory(null);
            }
                
        }
        
        if (turnController.getTurnPhase() == Turn.turnPhase.chooseNewAttacker ){
            if (t.getOwnerPlayer() == currentPlayer){
                turnController.getMapPanel().setCurrentTerritory(t);
                turnController.getMapPanel().repaint();
                turnController.goToNextPhase();
            }
        }
        
        if (turnController.getTurnPhase() == Turn.turnPhase.moveArmyPhase){
            movePhase(t);
        }
        if (turnController.getTurnPhase() == Turn.turnPhase.attackPhase){
            attackPhase(t);

        }
        
        setCurrentTerritory(turnController.getMapPanel().getCurrentTerritory());
        turnController.getMapPanel().repaint();
    }
    
    private void movePhase(Territory t){
    
        if (currentTerritory == null){
            if (t.getOwnerPlayer() == currentPlayer && t.getAmountOfMovableArmies() > 0){
                turnController.getMapPanel().setCurrentTerritory(t);
                setCurrentTerritory(t);
                turnController.getMapPanel().repaint();
            }
        }
        else{
            if (t.getOwnerPlayer() == currentPlayer && currentTerritory != t && turnController.getMapPanel().getNeighbourMap().get(currentTerritory).contains(t)){
                if (currentTerritory.getAmountOfMovableArmies() > 0){
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
                        JOptionPane.showMessageDialog(null, "Attack wins!");
                        turnController.setHasConquered(Boolean.TRUE);
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
                        JOptionPane.showMessageDialog(null, "Defense wins!");
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

        JOptionPane.showMessageDialog(null, "Voce passou " + val + " exércitos.");
        lostTerritory.setQtdExercitos(val);
        currentTerritory.setQtdExercitos(currentTerritory.getQtdExercitos() - val);
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
        
        String phase = Turn.getInstance().getTurnPhase().name();
        Player currentPlayer = Turn.getInstance().getCurrentPlayer();
        
        String info = null;
        String phaseMsg = null;
        
        if(phase.equals("newArmyPhase")){
            phaseMsg = "You have "+(currentPlayer.newArmyAmount()+" armies left");
        }
        else if(phase.equals("attackPhase")){   
            phaseMsg = "AttackMsg";
        }
        else if(phase.equals("chooseNewAttacker")){
            phaseMsg = "ChooseNewMsg";
        }
        else if(phase.equals("movePhase")){
            phaseMsg = "MoveMsg";
        }

        if(currentTerritory != null){
            info = "Country = "+currentTerritory.getNome()
                        +"\nWe are in the "+Turn.getInstance().getTurnPhase().name()
                        +"\n"+phaseMsg;
            c.setText(info);
            c.setFont(new Font("TimesRoman",Font.BOLD,14));
        }
        else c.setText("Thats not yours!");
        c.repaint();
    }
}
