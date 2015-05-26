package model;

import com.sun.glass.ui.Size;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.Map;

public class Territory {
    private String Name;
    private List<Rectangle> Borders;

    public Territory(String Name){
        this.Name = Name;
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

    /**
     * @return the Borders
     */
    public List<Rectangle> getBorders() {
        return Borders;
    }

    /**
     * @param Borders the Borders to set
     */
    public void setBorders(List<Rectangle> Borders) {
        this.Borders = Borders;
    }
}
