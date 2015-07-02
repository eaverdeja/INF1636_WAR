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
        private int firstVertexX;
        private int firstVertexY;

	public Territory(String continente, String nome, Ponto p[], float x, float y) {
		super();
		
                this.Continente = continente;
		this.Nome = nome;
                
		GeneralPath gp = new GeneralPath(GeneralPath.WIND_EVEN_ODD);				
		boolean first = true;
                this.firstVertexX = 0;
                this.firstVertexY = 0;
                
                for (Ponto p1 : p) {
                    if (first) {
                        gp.moveTo(p[0].get("x") + (x),p[0].get("y") + (y));
                        first = false;
                    } else {
                        gp.lineTo(p1.get("x") + (x), p1.get("y") + (y));
                    }
                    
                    this.firstVertexX += p1.get("x");
                    this.firstVertexY += p1.get("y");
                }
                
                this.firstVertexX /= p.length;
                this.firstVertexY /= p.length;
                
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
     * @return the firstVertexX
     */
    public int getFirstVertexX() {
        return firstVertexX;
    }

    /**
     * @return the firstVertexY
     */
    public int getFirstVertexY() {
        return firstVertexY;
    }

    /**
     * @param firstVertexX the firstVertexX to set
     */
    public void setFirstVertexX(int firstVertexX) {
        this.firstVertexX = firstVertexX;
    }

    /**
     * @param firstVertexY the firstVertexY to set
     */
    public void setFirstVertexY(int firstVertexY) {
        this.firstVertexY = firstVertexY;
    }

}
