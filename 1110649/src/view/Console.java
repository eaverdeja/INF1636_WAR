package view;

import controller.Controller;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import model.Click;
import model.Player;


/**
 *
 * @author Verdeja
 */
public class Console extends JTextArea implements Observer {

    private Click click = null;
    private static Console consoleInstance = null;
    private static Font font;
    private static String defaultMessage;
    private static String info;
    
    //Implementing singleton pattern
    
    protected Console(){
        super();
        setOpaque(false);
        setEditable(false);
        setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.LIGHT_GRAY)));
        
        font = new Font("TimesRoman",Font.BOLD,14);
        defaultMessage = "Hello world?";
        
        setFont(font);
        setText(defaultMessage);
    }
    
    public static Console getInstance(){
        if(consoleInstance == null){
            consoleInstance = new Console();
            return consoleInstance;
        }
        else return consoleInstance;
    }
    
    @Override
    public boolean isOpaque(){
        return false;
    }
    
    @Override
    protected void paintComponent(Graphics g){
        g.setColor(new Color(225,225,225,32));
        Insets insets = getInsets();
        int x = insets.left;
        int y = insets.top;
        int width = getWidth() - (insets.left + insets.right);
        int height = getHeight() - (insets.top + insets.bottom);
        g.fillRect(x, y, width, height);
        super.paintComponent(g);
    }

    @Override
    public void update(Observable o, Object arg) {
        Controller c = (Controller)o;
        c.consoleEvent();
    }
    
    
    /**
     * @return the defaultMessage
     */
    public static String getDefaultMessage() {
        return defaultMessage;
    }

    /**
     * @param aDefaultMessage the defaultMessage to set
     */
    public static void setDefaultMessage(String aDefaultMessage) {
        defaultMessage = aDefaultMessage;
    }
}
