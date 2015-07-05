/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author lorenzosaraiva
 */
public class Objective {
    private String description;
    private int type;
    
    public Objective(int type){
        switch (type){
                case 0:
                    description = "Conquistar na totalidade a EUROPA, a OCEANIA e mais um terceiro.";
                    break;
                case 1:
                    description = "Conquistar na totalidade a ASIA, a AMERICA DO SUL.";
                    break;
                case 2:
                    description = "Conquistar na totalidade a EUROPA, a AMERICA DO SUL e mais um terceiro.";
                    break;
                case 3:
                    description = "Conquistar 18 TERRITÓRIOS e ocupar cada um deles com pelo menos dois exércitos.";
                    break;
                case 4:
                    description = "Conquistar na totalidade a ASIA e a ÁFRICA.";
                    break;
                case 5:
                    description = "Conquistar na totalidade a AMÉRICA DO NORTE e a ÁFRICA.";
                    break;
                case 6:
                    description = "Conquistar 24 TERRITÓRIOS à sua escolha.";
                    break;
                case 7:
                    description = "Conquistar na totalidade a AMÉRICA DO NORTE e a OCEANIA.";
                    break;
                case 8:
                    description = "Destruir totalmente OS EXÉRCITOS AZUIS.";
                    break;
                case 9:
                    description = "Destruir totalmente OS EXÉRCITOS BRANCOS.";
                    break;
                case 10:
                    description = "Destruir totalmente OS EXÉRCITOS VERMELHOS.";
                    break;
                case 11:
                    description = "Destruir totalmente OS EXÉRCITOS ROSAS.";
                    break;
                case 12:
                    description = "Destruir totalmente OS EXÉRCITOS AMARELOS.";
                    break;
                case 13:
                    description = "Destruir totalmente OS EXÉRCITOS VERDES.";
                    break;    
        }
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }
    
    public boolean checkWin(){
        return false;
    }
}