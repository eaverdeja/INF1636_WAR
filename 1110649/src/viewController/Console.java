package viewController;

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
import model.Territory;

/**
 *
 * @author Verdeja
 */
public class Console extends JTextArea implements Observer {
    
    private Click click = null;
    private static Console consoleInstance = null;
    private static Font font;
    private static String defaultMessage;
    
    //Implementing singleton pattern
    
    protected Console(){
        super();
        setOpaque(false);
        setEditable(false);
        setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.LIGHT_GRAY)));
        
        font = new Font("TimesRoman",Font.BOLD,14);
        defaultMessage = "Which country do you desire?";
        
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
        click = (Click)o; //Territory
        String phase = Turn.getInstance().getTurnPhase().name();
        String phaseMsg = null;
        if(phase.equals("newArmyPhase")){
            phaseMsg = "You have "+(10-Turn.getInstance().getArmiesAdded()+" armies left");
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
        
        if(click.getValue() != null){
            String info = "Country = "+click.getValue().getNome()
                        +"\nWe are in the "+Turn.getInstance().getTurnPhase().name()
                        +"\n"+phaseMsg;
            consoleInstance.setText(info);
            consoleInstance.setFont(new Font("TimesRoman",Font.BOLD,14));
        }
        else consoleInstance.setText(defaultMessage);
    }
}
