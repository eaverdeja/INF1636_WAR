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
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.*;
import model.Territory;
import viewController.Turn.turnPhase;

public class MainFrame extends JFrame{
    public final int DEF_WIDTH = 960;
    public final int DEF_HEIGHT = 760;
    
    private MapPanel mapPanel;
    private JButton finishAttacks;
    private JButton finishMoves;
    private JButton nextTurn;
    private JButton addArmy;
    
    private Turn turnController;
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
        
        //Create dices and next turn buttons
        createNextTurn();
        createFinishAttacksButton();
        createFinishMovesButton();
        createAddArmy();
        
        //Console with turn/player/territory infos
        createConsole();
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
    
    private void createNextTurn(){
        nextTurn = new JButton("Next turn!");
        nextTurn.setAlignmentY(TOP_ALIGNMENT);
        getMapPanel().setLayout(null);
        getMapPanel().add(nextTurn);
        nextTurn.setBounds(DEF_WIDTH - 120, 30, 100, 30);
        
        nextTurn.addActionListener((ActionEvent e) -> {
            try {
                turnController.nextTurn();
                repaint();
            }
            catch (Exception ex){
                System.out.println("Erro ao passar de turno" + ex.getMessage());   
            }
        });
    }
    
    private void createAddArmy(){
        addArmy = new JButton("Add Army");
        addArmy.setAlignmentY(TOP_ALIGNMENT);
        addArmy.setPreferredSize(new Dimension(20,20));
        getMapPanel().setLayout(null);
        getMapPanel().add(addArmy);
        addArmy.setBounds(DEF_WIDTH - 250, 30, 100, 30);
        
        addArmy.addActionListener((ActionEvent e) -> {
            
            try {
                if (turnController.getTurnPhase() == turnPhase.newArmyPhase){
                    if (getMapPanel().getCurrentTerritory().getOwnerPlayer() == turnController.getCurrentPlayer()){
                        if (turnController.getCurrentPlayer().newArmyAmout() > turnController.getArmiesAdded()){
                            getMapPanel().getCurrentTerritory().addArmy();
                            turnController.setArmiesAdded(turnController.getArmiesAdded()+1);
                            System.out.print("You have " + (turnController.getCurrentPlayer().newArmyAmout() - turnController.getArmiesAdded()) + " armies left \n" );
                        }else{
                            turnController.setArmiesAdded(0);
                            turnController.goToNextPhase();
                            finishAttacks.setVisible(true);
                            addArmy.setVisible(false);
                            System.out.print("Already added all armies! \n" );
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
        finishAttacks.setBounds(DEF_WIDTH - 250, 30, 100, 30);
        finishAttacks.setVisible(false);
        finishAttacks.addActionListener((ActionEvent e) -> {
            try {
                turnController.goToMovePhase();
                finishAttacks.setVisible(false);
                finishMoves.setVisible(true);
                getMapPanel().setCurrentTerritory(null);
                repaint();
            }
            catch (Exception ex){
                System.out.println("Erro ao rolar os dados" + ex.getMessage());   
            }
        });
    }
    
    private void createFinishMovesButton(){
        finishMoves = new JButton("End Moves");
        
        finishMoves.setAlignmentY(TOP_ALIGNMENT);
        finishMoves.setPreferredSize(new Dimension(20,20));
        getMapPanel().setLayout(null);
        getMapPanel().add(finishMoves);
        finishMoves.setBounds(DEF_WIDTH - 250, 30, 100, 30);
        finishMoves.setVisible(false);
        finishMoves.addActionListener((ActionEvent e) -> {
            try {
                turnController.nextTurn();
                addArmy.setVisible(true);
                finishMoves.setVisible(false);
                repaint();
                
            }
            catch (Exception ex){
                System.out.println("Erro ao rolar os dados" + ex.getMessage());   
            }
        });
    }
    
    private void createConsole(){
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
