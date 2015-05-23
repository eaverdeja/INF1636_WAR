import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;

public class MainFrame extends JFrame{
    public final int DEF_WIDTH = 1000;
    public final int DEF_HEIGHT = 1000;
    
    private BufferedImage image;
    private List<Territory> paises;
    
    public MainFrame(){
        setSize(DEF_WIDTH,DEF_HEIGHT);
        setLocation(100,100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        createMap();
    }
    
    private void createMap(){
        
        //Load map
        MapPanel Map = new MapPanel();
        
        defineTerritories();
        mapCountries(Map);
        
        //Add map to pane
        getContentPane().add(Map);
        
    }
    
    private void mapCountries(MapPanel Map){
        for(Territory t : paises){

            //Listen for click
            Map.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e){
                    float Xcoord = e.getPoint().x;
                    float Ycoord = e.getPoint().y;

                    //DEBUG
                    System.out.println(e.getPoint());

                    for(Point p : t.getBorders()){
                        //Longitude?
                        if(Xcoord - p.x > 0 && Xcoord - p.x < 40){
                            //Latitude?
                            if(Ycoord - p.y > 0 && Ycoord - p.y < 40){
                                System.out.println("Brasil");
                            }
                        }   
                    }
                }
            });
        }
    }
    
    public void defineTerritories(){
        paises = new ArrayList<>();
        
        //Brasil
        Territory Brasil = new Territory("Brasil");

        //Defining borders
        List<Point> borders = new ArrayList<>();
        borders.add(new Point(225,330));
        borders.add(new Point(270,375));
        Brasil.setCoordinates(borders);
        
        paises.add(Brasil);
    }
}
