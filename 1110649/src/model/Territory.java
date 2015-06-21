package model;

import java.awt.geom.GeneralPath;
import viewController.Turn;

public class Territory {

        private String Continente;
	private String Nome;
        private Player ownerPlayer;
        private int qtdExercitos;
	private GeneralPath poligono;

	public Territory(String continente, String nome, Ponto p[], float x, float y) {
		super();
		
                this.Continente = continente;
		this.Nome = nome;
		
		GeneralPath gp = new GeneralPath(GeneralPath.WIND_EVEN_ODD);				
		
		gp.moveTo(p[0].get("x") + (x),p[0].get("y") + (y));

		for (int i = 1; i < p.length; i++) {
			gp.lineTo(p[i].get("x") + (x), p[i].get("y") + (y));
		}

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

}
