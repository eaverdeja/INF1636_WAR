
import java.awt.Point;
import java.util.List;

public class Territory {
    private String Name;
    private List<Point> Borders;

    public Territory(String Name){
        this.Name = Name;
    }
    
    /**
     * @return the borders
     */
    public List<Point> getBorders() {
        return Borders;
    }

    /**
     * @param borders the borders to set
     */
    public void setCoordinates(List<Point> Borders) {
        this.Borders = Borders;
    }

    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
    }

}
