package model;

import com.sun.javafx.geom.Point2D;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;
import controller.GameManager;

public class Territory {

        private static final int maxArmies = 100;
        private String Continente;
	private String Nome;
        private Player ownerPlayer;
        private int qtdExercitos;
        private int[] movedList = new int[maxArmies];
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
                initMovedList();
	}
    
    private void initMovedList(){
    
        for (int i = 0;i<maxArmies;i++){
            movedList[i] = 0;
        }
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
        for (int i = 0; i < qtdExercitos; i++){
            if (movedList[i] == 0){
                movedList[i] = 1;
            }
        }
    }
    
    public void removeArmies(int qtd){
        int toChange = 0;
        this.qtdExercitos -= qtd;
        for (int i = qtdExercitos; i < qtdExercitos + qtd; i++){
            if (movedList[i] == 2)
                toChange++;
            movedList[i] = 0;
        for (int n = 0; n < qtdExercitos; n++){
            if (toChange>0){
                if (movedList[n] == 1){
                        movedList[n] = 2;
                        toChange--;
                    }
                }
            }
        }
    
    }
    
    public void addMovedArmies(int qtd){
        for (int i = this.qtdExercitos; i < this.qtdExercitos + qtd;i++){
            movedList[i] = 2;
        }
        this.qtdExercitos+= qtd;
    }
    
    public int getAmountOfMovableArmies(){
        int ret = 0, moved = 0;
            for (int i = 0; i < this.qtdExercitos;i++){
                if (movedList[i] == 1){
                    ret++;
                }
                if (movedList[i] == 2){
                    moved++;
                }
            }
            if (moved == 0)
                return ret - 1;
            else
                return ret;
    }
    
    public void resetMoves(){
        for (int i = 0; i < this.qtdExercitos;i++){
            movedList[i] = 1;
        }
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
    
        
        movedList[qtdExercitos] = 1;
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
