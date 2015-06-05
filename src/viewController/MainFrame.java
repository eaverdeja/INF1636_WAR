package viewController;

import java.awt.BorderLayout;
import java.awt.Color;
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

public class MainFrame extends JFrame{
    public final int DEF_WIDTH = 960;
    public final int DEF_HEIGHT = 760;
    
    private MapPanel mapPanel;
    private BufferedImage image;
    private JButton rollButton;
    private JLabel diceOne;
    private JLabel diceTwo;
    private JLabel diceThree;
    private JButton nextTurn;
    private Turn turnController;
    private final int players;
    private JPanel box;
    
    private Map<Territory,List<Territory>> neighbourMap;
    
    public MainFrame(int players){
        
        setSize(DEF_WIDTH,DEF_HEIGHT);
        setResizable(false);
        setLocation(0,0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Define number of players and turn scheme
        this.players = players;
        turnController = Turn.getInstance();
        turnController.createAndRadomizePlayers(players);
        
        //Create map
        createMap();
        createNeighbourMap();
        createRollButton();
        createNextTurn();
        
    }
    
    private void createMap(){
        
        //Load map
        mapPanel = new MapPanel(turnController.getPlayers());
        mapPanel.setLayout(new BorderLayout());
        
        //Define territories
        mapPanel.defineTerritories();
        
        //Add map to pane
        getContentPane().add(mapPanel);
    }
    
    private void createNextTurn(){
        nextTurn = new JButton("Next turn!");
        nextTurn.setAlignmentY(TOP_ALIGNMENT);
        nextTurn.setPreferredSize(new Dimension(20,20));
        mapPanel.setLayout(null);
        mapPanel.add(nextTurn);
        nextTurn.setBounds(DEF_WIDTH - 120, 30, 100, 30);
        
        nextTurn.addActionListener((ActionEvent e) -> {
            try {
                Turn.getInstance().nextTurn();
                repaint();
            }
            catch (Exception ex){
                System.out.println("Erro ao passar de turno" + ex.getMessage());   
            }
        });
    }

    private void createRollButton(){
        rollButton = new JButton("Roll Dices!");
        
        rollButton.setAlignmentY(TOP_ALIGNMENT);
        rollButton.setPreferredSize(new Dimension(20,20));
        mapPanel.setLayout(null);
        mapPanel.add(rollButton);
        rollButton.setBounds(DEF_WIDTH - 250, 30, 100, 30);
        
        rollButton.addActionListener((ActionEvent e) -> {
            try {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(new DicePanel());
                frame.pack();
                frame.setVisible(true);
            }
            catch (Exception ex){
                System.out.println("Erro ao rolar os dados" + ex.getMessage());   
            }
        });
    }
    
    private void createNeighbourMap(){
        List<Territory> territoryList = mapPanel.getLstTerritorios();
        List<Territory> neighbourList = new ArrayList<>();
        neighbourMap = new HashMap<>();
        
        ArrayList<Line2D.Double> tLines = new ArrayList<>();
        ArrayList<Line2D.Double> nLines = new ArrayList<>();
        
        //Search for neighbours(n) around territory(t)
        //For each t
        for(Territory t : territoryList){
            tLines = getLineSegments(t.getPoligono());
                
            //With n's lines
            boolean alreadyFriends = false;
            for(Territory n : territoryList){
                
                if(!t.getNome().equals(n.getNome())){
                    //Try to intersect t's lines
                    for(Line2D.Double tLine : tLines){

                        nLines = getLineSegments(n.getPoligono());
                        for(Line2D.Double nLine : nLines){
                            if(tLine.intersectsLine(nLine)){
                                neighbourList.add(n);
                                //After adding a neighbour theres no need to intersect
                                //remaining line segments
                                alreadyFriends = true;
                            }
                        }
                    }
                }
            }
            
            //Removing possible duplicates
            Set<Territory> neighbourSet = new HashSet<>(neighbourList);
            
            neighbourMap.put(t, new ArrayList<>(neighbourSet));
            neighbourList.clear();
        }
    }
    
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
}
