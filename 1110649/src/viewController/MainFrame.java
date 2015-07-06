package viewController;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;

import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.*;
import model.Card;
import model.Territory;
import viewController.Turn.turnPhase;

public class MainFrame extends JFrame{
    public final int DEF_WIDTH = 960;
    public final int DEF_HEIGHT = 760;
    public final int nextPhaseOffset = 130;
    public final int cardsOffset = 360;
    public final int objectiveOffset = 460;    
    private MapPanel mapPanel;
    private JButton finishAttacks;
    private JButton finishMoves;
    private JButton addArmy;
    private JButton changeCards;
    private JButton showCards;
    public  JButton showObjective;
    private Turn turnController;
    private Console consoleController;
    private final int players;
    
    private Map<Territory,List<Territory>> neighbourMap;
    
    public MainFrame(int players){
        
        setSize(DEF_WIDTH,DEF_HEIGHT);
        setResizable(false);
        setLocation(0,0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Define number of players and turn scheme
        this.players = players;
        turnController = Turn.getInstance();
        turnController.createAndRandomizePlayers(players);
        
        //Create map
        createMap();
        createNeighbourMap();
        mapPanel.setNeighbourMap(neighbourMap);
        turnController.setLstTerritorios(mapPanel.getLstTerritorios());
        turnController.randomizeTerritories();
        turnController.setMapPanel(mapPanel);
        turnController.getObjController().setContinentList(mapPanel.getContinentList());
        
        //Create dices and next turn buttons
        createFinishAttacksButton();
        createFinishMovesButton();
        createAddArmy();
        createChangeCards();
        createShowCards();
        createShowObjective();
        //Console with turn/player/territory infos
        createConsole();
        consoleController = Console.getInstance();
    }
    
    private void createMap(){
        
        //Load map
        mapPanel = new MapPanel(turnController.getPlayers());
        getMapPanel().setLayout(new BorderLayout());
        
        //Define territories
        getMapPanel().defineTerritories();
        
        //Add map to pane
        getContentPane().add(getMapPanel());
    }
    
    private void createAddArmy(){
        addArmy = new JButton("Add Army");
        addArmy.setAlignmentY(TOP_ALIGNMENT);
        addArmy.setPreferredSize(new Dimension(20,20));
        getMapPanel().setLayout(null);
        getMapPanel().add(addArmy);
        addArmy.setBounds(DEF_WIDTH - nextPhaseOffset, 30, 100, 30);
        
        addArmy.addActionListener((ActionEvent e) -> {
            
            try {
                if (turnController.getTurnPhase() == turnPhase.newArmyPhase){
                    if (getMapPanel().getCurrentTerritory().getOwnerPlayer() == turnController.getCurrentPlayer()){
                        if (turnController.getCurrentPlayer().newArmyAmount() > turnController.getArmiesAdded()){
                            getMapPanel().getCurrentTerritory().addArmy();
                            turnController.setArmiesAdded(turnController.getArmiesAdded()+1);
                            System.out.print("You have " + (turnController.getCurrentPlayer().newArmyAmount() - turnController.getArmiesAdded()) + " armies left \n" );
                            if(turnController.getCurrentPlayer().newArmyAmount() == turnController.getArmiesAdded()){
                                turnController.goToNextPhase();
                                turnController.setArmiesAdded(0);
                                finishAttacks.setVisible(true);
                                addArmy.setVisible(false);
                            }
                        }
                    }else{
                        System.out.print("Not your territory");
                    }
                    repaint();
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
        getMapPanel().setLayout(null);
        getMapPanel().add(finishAttacks);
        finishAttacks.setBounds(DEF_WIDTH - nextPhaseOffset, 30, 100, 30);
        finishAttacks.setVisible(false);
        finishAttacks.addActionListener((ActionEvent e) -> {
            try {
                turnController.setFinishedAttacking(true);
                turnController.goToNextPhase();
                finishAttacks.setVisible(false);
                finishMoves.setVisible(true);
                repaint();
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
        getMapPanel().setLayout(null);
        getMapPanel().add(finishMoves);
        finishMoves.setBounds(DEF_WIDTH - nextPhaseOffset, 30, 100, 30);
        finishMoves.setVisible(false);
        finishMoves.addActionListener((ActionEvent e) -> {
            try {
                if (turnController.getHasConquered()){
                    Card card = turnController.getCardsController().getRandomCard();
                    turnController.getCurrentPlayer().giveCard(card);                   
                    card.showCard();
                }
                addArmy.setVisible(true);
                finishMoves.setVisible(false);
                repaint();
                turnController.nextTurn();
                
            }
            catch (Exception ex){
                System.out.println("Erro ao terminar os movimentos: " + ex.getMessage());   
            }
        });
    }
    
    private void createChangeCards(){
    
        changeCards = new JButton("Change Cards");
        
        changeCards.setAlignmentY(TOP_ALIGNMENT);
        changeCards.setPreferredSize(new Dimension(20,20));
        getMapPanel().setLayout(null);
        getMapPanel().add(changeCards);
        changeCards.setBounds(DEF_WIDTH - cardsOffset, 30, 100, 30);
        changeCards.setVisible(true);
        changeCards.addActionListener((ActionEvent e) -> {
            try {
                if (turnController.getCurrentPlayer().canChangeCards()){
                    System.out.println("TROCOU");
                    turnController.getCurrentPlayer().setHasChanged(true);
                }else{
                    System.out.println("NAO TROCOU");
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
        getMapPanel().setLayout(null);
        getMapPanel().add(showCards);
        showCards.setBounds(DEF_WIDTH - cardsOffset + 110, 30, 100, 30);
        showCards.setVisible(true);
        showCards.addActionListener((ActionEvent e) -> {
            try {
                CardPanel panel = new CardPanel(turnController.getCurrentPlayer().getCurrentCards());
                panel.showsPanel();
            }
            catch (Exception ex){
                System.out.println("Erro ao trocar as cartas: " + ex.getMessage());   
            }
        });
    }
    
    private void createShowObjective(){
    
        showObjective = new JButton("Show Objective");
        
        showObjective.setAlignmentY(TOP_ALIGNMENT);
        showObjective.setPreferredSize(new Dimension(20,20));
        getMapPanel().setLayout(null);
        getMapPanel().add(showObjective);
        showObjective.setBounds(DEF_WIDTH - objectiveOffset, 30, 100, 30);
        showObjective.setVisible(true);
        showObjective.addActionListener((ActionEvent e) -> {
            try {
                JOptionPane.showMessageDialog(null, turnController.getCurrentPlayer().getObjective().getDescription());
            }
            catch (Exception ex){
                System.out.println("Erro ao trocar as cartas: " + ex.getMessage());   
            }
        });
    }
    
    
    private void createConsole(){
        //Add the console to the turnController obsList
        turnController.addObserver(Console.getInstance());
        
        Console.getInstance().setBounds(124, 614, 266, 82);
        getMapPanel().setLayout(null);
        getMapPanel().add(Console.getInstance());
        repaint();
    }
    
    private void createNeighbourMap(){
        List<Territory> territoryList = getMapPanel().getLstTerritorios();
        List<Territory> neighbourList = new ArrayList<>();
        neighbourMap = new HashMap<>();
        
        ArrayList<Line2D.Double> tLines = new ArrayList<>();
        ArrayList<Line2D.Double> nLines = new ArrayList<>();
        
        //Search for neighbours(n) around territory(t)
        for(Territory t : territoryList){
            tLines = getLineSegments(t.getPoligono());
            for(Territory n : territoryList){
              if(!t.getNome().equals(n.getNome())){
                    //Try to intersect territory lines with neighbour lines
                    nLines = getLineSegments(n.getPoligono());
                    for(Line2D.Double tLine : tLines){
                        for(Line2D.Double nLine : nLines){
                            if(tLine.intersectsLine(nLine)){
                                neighbourList.add(n);
                            }
                        }
                    }
                }
            }
         
            //Removing duplicates
            Set<Territory> neighbourSet = new HashSet<>(neighbourList);
            neighbourMap.put(t, new ArrayList<>(neighbourSet));
            
           // New neighbours are coming
            neighbourList.clear();
        }
        
        addSeaNeighbours();
    }
    
    private void addSeaNeighbours(){
        
        List<Territory> seaNeighbours = new ArrayList<>();
        List<Territory> territoryList = getMapPanel().getLstTerritorios();
        Territory t = null;
        Territory n = null;
        
        for (Iterator<Territory> it = territoryList.iterator(); it.hasNext();) {
            t = it.next();
            
            //BRASIL
            if(t.getNome().equals("Brasil")){
                n = null;
                for (Iterator<Territory> neighbours = territoryList.iterator(); neighbours.hasNext();) {
                    n = neighbours.next();
                    if(n != null && n.getNome().equals("Nigeria")){
                        seaNeighbours.add(n);
                    }
                }
                seaNeighbour(t,seaNeighbours);
            }
            
            //ALASCA
            if(t.getNome().equals("Alasca")){
                n = null;
                for (Iterator<Territory> neighbours = territoryList.iterator(); neighbours.hasNext();) {
                    n = neighbours.next();
                    if(n != null && n.getNome().equals("Siberia")){
                        seaNeighbours.add(n);
                    }
                }
                seaNeighbour(t,seaNeighbours);
            }
            
            //GROELANDIA
            if(t.getNome().equals("Groelandia")){
                n = null;
                for (Iterator<Territory> neighbours = territoryList.iterator(); neighbours.hasNext();) {
                    n = neighbours.next();
                    if(n != null && n.getNome().equals("Quebec")){
                        seaNeighbours.add(n);
                    }
                    else if(n != null && n.getNome().equals("Reino Unido")){
                        seaNeighbours.add(n);
                    }
                }
                seaNeighbour(t,seaNeighbours);
            }
            
            //FRANÇA
            if(t.getNome().equals("Franca")){
                n = null;
                for (Iterator<Territory> neighbours = territoryList.iterator(); neighbours.hasNext();) {
                    n = neighbours.next();
                    if(n != null && n.getNome().equals("Suecia")){
                        seaNeighbours.add(n);
                    }
                    else if(n != null && n.getNome().equals("Reino Unido")){
                        seaNeighbours.add(n);
                    }
                }
                seaNeighbour(t,seaNeighbours);
            }
            
            //ITALIA
            if(t.getNome().equals("Italia")){
                n = null;
                for (Iterator<Territory> neighbours = territoryList.iterator(); neighbours.hasNext();) {
                    n = neighbours.next();
                    if(n != null && n.getNome().equals("Suecia")){
                        seaNeighbours.add(n);
                    }
                    else if(n != null && n.getNome().equals("Argelia")){
                        seaNeighbours.add(n);
                    }
                }
                seaNeighbour(t,seaNeighbours);
            }
            
            //ARGELIA
            if(t.getNome().equals("Argelia")){
                n = null;
                for (Iterator<Territory> neighbours = territoryList.iterator(); neighbours.hasNext();) {
                    n = neighbours.next();
                    if(n != null && n.getNome().equals("Espanha")){
                        seaNeighbours.add(n);
                    }
                }
                seaNeighbour(t,seaNeighbours);
            }
            
            //EGITO
            if(t.getNome().equals("Egito")){
                n = null;
                for (Iterator<Territory> neighbours = territoryList.iterator(); neighbours.hasNext();) {
                    n = neighbours.next();
                    if(n != null && n.getNome().equals("Romania")){
                        seaNeighbours.add(n);
                    }
                    else if(n != null && n.getNome().equals("Jordania")){
                        seaNeighbours.add(n);
                    }
                }
                seaNeighbour(t,seaNeighbours);
            }
            
            //SOMALIA
            if(t.getNome().equals("Somalia")){
                n = null;
                for (Iterator<Territory> neighbours = territoryList.iterator(); neighbours.hasNext();) {
                    n = neighbours.next();
                    if(n != null && n.getNome().equals("Arabia Saudita")){
                        seaNeighbours.add(n);
                    }
                }
                seaNeighbour(t,seaNeighbours);
            }
            
            //INDONESIA
            if(t.getNome().equals("Indonesia")){
                n = null;
                for (Iterator<Territory> neighbours = territoryList.iterator(); neighbours.hasNext();) {
                    n = neighbours.next();
                    if(n != null && n.getNome().equals("India")){
                        seaNeighbours.add(n);
                    }
                    else if(n != null && n.getNome().equals("Bangladesh")){
                        seaNeighbours.add(n);
                    }
                    else if(n != null && n.getNome().equals("Australia")){
                        seaNeighbours.add(n);
                    }
                    else if(n != null && n.getNome().equals("Nova Zelandia")){
                        seaNeighbours.add(n);
                    }
                }
                seaNeighbour(t,seaNeighbours);
            }
            
            //NOVA ZELANDIA
            if(t.getNome().equals("Nova Zelandia")){
                n = null;
                for (Iterator<Territory> neighbours = territoryList.iterator(); neighbours.hasNext();) {
                    n = neighbours.next();
                    if(n != null && n.getNome().equals("Australia")){
                        seaNeighbours.add(n);
                    }
                }
                seaNeighbour(t,seaNeighbours);
            }
            
            //JAPAO
            if(t.getNome().equals("Japao")){
                n = null;
                for (Iterator<Territory> neighbours = territoryList.iterator(); neighbours.hasNext();) {
                    n = neighbours.next();
                    if(n != null && n.getNome().equals("Cazaquistao")){
                        seaNeighbours.add(n);
                    }
                    else if(n != null && n.getNome().equals("Mongolia")){
                        seaNeighbours.add(n);
                    }
                    else if(n != null && n.getNome().equals("Coreia do Norte")){
                        seaNeighbours.add(n);
                    }
                }
                seaNeighbour(t,seaNeighbours);
            }
        }
    }
    
    private void seaNeighbour(Territory t, List<Territory> nList){
        
        List<Territory> neighbourList = null;
        
        //Append the seaNeighbours
        neighbourList = neighbourMap.get(t);
        neighbourList.addAll(nList);
        
        //Reflect it!
        for(Territory n : nList){
            neighbourList = neighbourMap.get(n);
            neighbourList.add(t);
            neighbourMap.put(n, neighbourList);
        }
        
        //Clear list for next country
        nList.clear();
    }
    
    //getLineSegments's body was written by Peter http://stackoverflow.com/users/559415/peter
    private ArrayList<Line2D.Double> getLineSegments(GeneralPath p){
        
        ArrayList<double[]> linePoints = new ArrayList<>();
        ArrayList<Line2D.Double> lineSegments = new ArrayList<>();
              
        double[] coords = new double[6];    
        
        for(PathIterator pi = p.getPathIterator(null); !pi.isDone(); pi.next()){
            // The type will be SEG_LINETO, SEG_MOVETO, or SEG_CLOSE
            // since p is composed of straight lines
            int type = pi.currentSegment(coords);
            
            // We record a double array of {segment type, x coord, y coord}
            double[] pathIteratorCoords = {type,coords[0],coords[1]};
            linePoints.add(pathIteratorCoords);
        }
        
        double[] start = new double[3]; // To record where each polygon starts

        for (int i = 0; i < linePoints.size(); i++) {
            // If we're not on the last point, return a line from this point to the next
            double[] currentElement = linePoints.get(i);

            // We need a default value in case we've reached the end of the ArrayList
            double[] nextElement = {-1, -1, -1};
            if (i < linePoints.size() - 1) {
                nextElement = linePoints.get(i + 1);
            }

            // Make the lines
            if (currentElement[0] == PathIterator.SEG_MOVETO) {
                start = currentElement; // Record where the polygon started to close it later
            } 

            if (nextElement[0] == PathIterator.SEG_LINETO) {
                lineSegments.add(
                        new Line2D.Double(
                            currentElement[1], currentElement[2],
                            nextElement[1], nextElement[2]
                        )
                    );
            } else if (nextElement[0] == PathIterator.SEG_CLOSE) {
                lineSegments.add(
                        new Line2D.Double(
                            currentElement[1], currentElement[2],
                            start[1], start[2]
                        )
                    );
            }
        }
        return lineSegments;
    }

    public Map<Territory,List<Territory>> getNeighbourMap() {
        return neighbourMap;
    }

    public MapPanel getMapPanel() {
        return mapPanel;
    }
}
