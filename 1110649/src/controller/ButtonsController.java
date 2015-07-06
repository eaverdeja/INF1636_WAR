/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import model.Card;
import view.CardPanel;

/**
 *
 * @author lorenzosaraiva
 */
public class ButtonsController {
    
    private final int DEF_WIDTH = 960;
    private final int DEF_HEIGHT = 760;
    private final int nextPhaseOffset = 130;
    private final int cardsOffset = 360;
    
    private JButton finishAttacks;
    private JButton finishMoves;
    private JButton addArmy;
    private JButton changeCards;
    private JButton showCards;
    private JButton showObjective;
    private GameManager gameManager = GameManager.getInstance();
    
    public ButtonsController(){
    
        createFinishAttacksButton();
        createFinishMovesButton();
        createAddArmy();
        createChangeCards();
        createShowCards();
        createShowObjective();
    }
    
    private void createAddArmy(){
        
        addArmy = new JButton("Add Army");
        addArmy.setAlignmentY(TOP_ALIGNMENT);
        addArmy.setPreferredSize(new Dimension(20,20));
        gameManager.getMapPanel().setLayout(null);
        gameManager.getMapPanel().add(addArmy);
        addArmy.setBounds(DEF_WIDTH - nextPhaseOffset, 30, 100, 30);
        
        addArmy.addActionListener((ActionEvent e) -> {
            
            try {
                if (gameManager.getTurnPhase() == GameManager.turnPhase.newArmyPhase){
                    if (gameManager.getMapPanel().getCurrentTerritory().getOwnerPlayer() == gameManager.getCurrentPlayer()){
                        if (gameManager.getCurrentPlayer().newArmyAmount() > gameManager.getArmiesAdded()){
                            gameManager.getMapPanel().getCurrentTerritory().addArmy();
                            gameManager.setArmiesAdded(gameManager.getArmiesAdded()+1);
                            System.out.print("You have " + (gameManager.getCurrentPlayer().newArmyAmount() - gameManager.getArmiesAdded()) + " armies left \n" );
                            if(gameManager.getCurrentPlayer().newArmyAmount() == gameManager.getArmiesAdded()){
                                gameManager.goToNextPhase();
                                gameManager.setArmiesAdded(0);
                                finishAttacks.setVisible(true);
                                addArmy.setVisible(false);
                            }
                        }
                    }else{
                        System.out.print("Not your territory");
                    }
                    gameManager.repaint();
                }else{
                    System.out.print("Not the right turnPhase\n");
                }
            }
            catch (Exception ex){
                System.out.println("Nenhum pais selecionado!");   
            }
        });
    }

    private void createFinishAttacksButton(){
        finishAttacks = new JButton("End Attacks");
        
        finishAttacks.setAlignmentY(TOP_ALIGNMENT);
        finishAttacks.setPreferredSize(new Dimension(20,20));
        gameManager.getMapPanel().setLayout(null);
        gameManager.getMapPanel().add(finishAttacks);
        finishAttacks.setBounds(DEF_WIDTH - nextPhaseOffset, 30, 100, 30);
        finishAttacks.setVisible(false);
        finishAttacks.addActionListener((ActionEvent e) -> {
            try {
            	gameManager.setFinishedAttacking(true);
                gameManager.goToNextPhase();
                finishAttacks.setVisible(false);
                finishMoves.setVisible(true);
                gameManager.setCurrentTerritory(null);
                gameManager.repaint();
            }
            catch (Exception ex){
                System.out.println("Erro ao terminar os ataques!" + ex.getMessage());   
            }
        });
    }
    
    private void createFinishMovesButton(){
        finishMoves = new JButton("End Moves");
        
        finishMoves.setAlignmentY(TOP_ALIGNMENT);
        finishMoves.setPreferredSize(new Dimension(20,20));
        gameManager.getMapPanel().setLayout(null);
        gameManager.getMapPanel().add(finishMoves);
        finishMoves.setBounds(DEF_WIDTH - nextPhaseOffset, 30, 100, 30);
        finishMoves.setVisible(false);
        finishMoves.addActionListener((ActionEvent e) -> {
            try {
                if (gameManager.getHasConquered()){
                    Card card = gameManager.getCardsController().getRandomCard();
                    gameManager.getCurrentPlayer().giveCard(card);                   
                    card.showCard();
                }
                GameManager.getInstance().nextTurn();
                addArmy.setVisible(true);
                finishMoves.setVisible(false);
                gameManager.nextTurn();
                gameManager.repaint();
                
            }
            catch (Exception ex){
                System.out.println("Erro ao terminar os movimentos: " + ex.getMessage());   
            }
        });
    }
    
    private void createChangeCards(){
    
        changeCards = new JButton("Change");
        
        changeCards.setAlignmentY(TOP_ALIGNMENT);
        changeCards.setPreferredSize(new Dimension(20,20));
        gameManager.getMapPanel().setLayout(null);
        gameManager.getMapPanel().add(changeCards);
        changeCards.setBounds(DEF_WIDTH - cardsOffset, 30, 100, 30);
        changeCards.setVisible(true);
        changeCards.addActionListener((ActionEvent e) -> {
            try {
                gameManager.repaint();
                if (gameManager.getCurrentPlayer().canChangeCards()){
                    JOptionPane.showMessageDialog(null, "Troca efetuada!");
                    gameManager.getCurrentPlayer().setHasChanged(true);
                }else{
                    JOptionPane.showMessageDialog(null, "Você não pode trocar!");
                }
            }
            catch (Exception ex){
                System.out.println("Erro ao trocar as cartas: " + ex.getMessage());   
            }
        });
    }
    
    private void createShowCards(){
    
        showCards = new JButton("Show Cards");
        
        showCards.setAlignmentY(TOP_ALIGNMENT);
        showCards.setPreferredSize(new Dimension(20,20));
        gameManager.getMapPanel().setLayout(null);
        gameManager.getMapPanel().add(showCards);
        showCards.setBounds(DEF_WIDTH - cardsOffset + 110, 30, 100, 30);
        showCards.setVisible(true);
        showCards.addActionListener((ActionEvent e) -> {
            try {
                CardPanel panel = new CardPanel(gameManager.getCurrentPlayer().getCurrentCards());
                panel.showsPanel();
            }
            catch (Exception ex){
                System.out.println("Erro ao trocar as cartas: " + ex.getMessage());   
            }
        });
    }
    
    private void createShowObjective(){
    
        showObjective = new JButton("Objective");
        
        showObjective.setAlignmentY(TOP_ALIGNMENT);
        showObjective.setPreferredSize(new Dimension(20,20));
        gameManager.getMapPanel().setLayout(null);
        gameManager.getMapPanel().add(showObjective);
        showObjective.setBounds(DEF_WIDTH - cardsOffset - 110, 30, 100, 30);
        showObjective.setVisible(true);
        showObjective.addActionListener((ActionEvent e) -> {
            try {
                JOptionPane.showMessageDialog(null, gameManager.getCurrentPlayer().getObjective().getDescription());
            }
            catch (Exception ex){
                System.out.println("Erro ao trocar as cartas: " + ex.getMessage());   
            }
        });
    }
}
