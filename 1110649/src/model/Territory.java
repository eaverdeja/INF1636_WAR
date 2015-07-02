package model;

import com.sun.javafx.geom.Point2D;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import viewController.Turn;

public class Territory {

        private String Continente;
	private String Nome;
        private Player ownerPlayer;
        private int qtdExercitos;
	private GeneralPath poligono;
        private int centerX;
        private int centerY;

	public Territory(String continente, String nome, Ponto p[], float x, float y) {
		super();
		
                this.Continente = continente;
		this.Nome = nome;
                
		GeneralPath gp = new GeneralPath(GeneralPath.WIND_EVEN_ODD);				
		boolean first = true;
                this.centerX = 0;
                this.centerY = 0;
                
                for (Ponto p1 : p) {
                    if (first) {
                        gp.moveTo(p[0].get("x") + (x),p[0].get("y") + (y));
                        first = false;
                    } else {
                        gp.lineTo(p1.get("x") + (x), p1.get("y") + (y));
                    }
                    
                    this.centerX += p1.get("x");
                    this.centerY += p1.get("y");
                }
                
                this.centerX /= p.length;
                this.centerY /= p.length;
                
		gp.closePath();
		
		this.poligono = gp;
	}
	
    public GeneralPath getPoligono() {
            return this.poligono;
    }

    public String getNome(){
        return this.Nome;
    }

    /**
     * @return the Continente
     */
    public String getContinente() {
        return Continente;
    }

    /**
     * @return the qtdExercitos
     */
    public int getQtdExercitos() {
        return qtdExercitos;
    }

    /**
     * @param qtdExercitos the qtdExercitos to set
     */
    public void setQtdExercitos(int qtdExercitos) {
        this.qtdExercitos = qtdExercitos;
    }

    /**
     * @return the ownerPlayer
     */
    public Player getOwnerPlayer() {
        return ownerPlayer;
    }

    /**
     * @param ownerPlayer the ownerPlayer to set
     */
    public void setOwnerPlayer(Player ownerPlayer) {
        this.ownerPlayer = ownerPlayer;
    }
    
    public void addArmy(){
    
        this.qtdExercitos++;
    }

    /**
     * @return the centerX
     */
    public int getCenterX() {
        return centerX;
    }

    /**
     * @return the centerY
     */
    public int getCenterY() {
        return centerY;
    }

    /**
     * @param centerX the centerX to set
     */
    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    /**
     * @param centerY the centerY to set
     */
    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

}
