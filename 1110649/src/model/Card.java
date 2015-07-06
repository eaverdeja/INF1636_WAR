/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author lorenzosaraiva
 */
public class Card extends JPanel {
    
     private BufferedImage cardImage;
     private int cardSymbol;
     private String name;
     
     public Card(String s) throws IOException{
         switch (s){
            case "Alasca":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_an_alasca.png"));
                
                cardSymbol = 0;
                name = "Alasca";
                break;
            }
            case "Calgary":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_an_calgary.png"));
                
                cardSymbol = 2;
                name = "Calgary";
                break;
            }
            case "Groelandia":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_an_groelandia.png"));
                
                cardSymbol = 2;
                name = "Groelandia";
                break;
            }
            case "Vancouver":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_an_vancouver.png"));
                
                cardSymbol = 0;
                name = "Vancouver";
                break;
            }
            case "Quebec":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_an_quebec.png"));
                
                cardSymbol = 2;
                name = "Quebec";
                break;
            }
            case "California":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_an_california.png"));
                
                cardSymbol = 1;
                name = "California";
                break;
            }
            case "Texas":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_an_texas.png"));
                
                cardSymbol = 0;
                name = "Texas";
                break;
            }
            case "NovaYork":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_an_novayork.png"));
                
                cardSymbol = 1;
                name = "NovaYork";
                break;
            }
            case "Mexico":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_an_mexico.png"));
                
                cardSymbol = 1;
                name = "Mexico";
                break;
            }
            case "Venezuela":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_asl_venezuela.png"));
                
                cardSymbol = 0;
                name = "Venezuela";
                break;
            }
            case "Peru":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_asl_peru.png"));
                
                cardSymbol = 0;
                name = "Peru";
                break;
            }
            case "Brasil":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_asl_brasil.png"));
                
                cardSymbol = 2;
                name = "Brasil";
                break;
            }
            case "Argentina":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_asl_argentina.png"));
                
                cardSymbol = 1;
                name = "Argentina";
                break;
            }
            case "Angola":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_af_angola.png"));
                
                cardSymbol = 1;
                name = "Angola";
                break;
            }
            case "Africa do Sul":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_af_africadosul.png"));
                
                cardSymbol = 0;
                name = "Africa do Sul";
                break;
            }
            case "Argelia":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_af_argelia.png"));
                
                cardSymbol = 2;
                name = "Argelia";
                break;
            }
            case "Egito":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_af_egito.png"));
                
                cardSymbol = 1;
                name = "Egito";
                break;
            }
            case "Nigeria":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_af_nigeria.png"));
                
                cardSymbol = 2;
                name = "Nigeria";
                break;
            }
            case "Somalia":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_af_somalia.png"));
                
                cardSymbol = 1;
                name = "Somalia";
                break;
            }
            case "Espanha":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_eu_espanha.png"));
                
                cardSymbol = 2;
                name = "Espanha";
                break;
            }
            case "Franca":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_eu_franca.png"));
                
                cardSymbol = 0;
                name = "Franca";
                break;
            }
            case "Italia":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_eu_italia.png"));
                
                cardSymbol = 1;
                name = "Italia";
                break;
            }
            case "Polonia":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_eu_polonia.png"));
                
                cardSymbol = 0;
                name = "Polonia";
                break;
            }
            case "Reino Unido":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_eu_reinounido.png"));
                
                cardSymbol = 2;
                name = "Reino Unido";
                break;
            }
            case "Romania":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_eu_romenia.png"));
                
                cardSymbol = 0;
                name = "Romania";
                break;
            }
            case "Suecia":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_eu_suecia.png"));
                
                cardSymbol = 1;
                name = "Suecia";
                break;
            }
            case "Ucrania":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_eu_ucrania.png"));
                
                cardSymbol = 2;
                name = "Ucrania";
                break;
            }
            case "Arabia Saudita":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_arabiasaudita.png"));
                
                cardSymbol = 2;
                name = "Arabia Saudita";
                break;
            }
            case "Bangladesh":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_bangladesh.png"));
                
                cardSymbol = 2;
                name = "Bangladesh";
                break;
            }
            case "Cazaquistao":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_cazaquistao.png"));
                
                cardSymbol = 2;
                name = "Cazaquistao";
                break;
            }
            case "Mongolia":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_mongolia.png"));
                
                cardSymbol = 0;
                name = "Mongolia";
                break;
            }
            case "China":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_china.png"));
                
                cardSymbol = 1;
                name = "China";
                break;
            }
            case "Coreia do Norte":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_coreiadonorte.png"));
                
                cardSymbol = 1;
                name = "Coreia do Norte";
                break;
            }
            case "Coreia do Sul":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_coreiadosul.png"));
                
                cardSymbol = 0;
                name = "Coreia do Sul";
                break;
            }
            case "Estonia":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_estonia.png"));
                
                cardSymbol = 2;
                name = "Estonia";
                break;
            }
            case "India":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_india.png"));
                
                cardSymbol = 0;
                name = "India";
                break;
            }
            case "Ira":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_ira.png"));
                
                cardSymbol = 1;
                name = "Ira";
                break;
            }
            case "Iraque":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_iraque.png"));
                
                cardSymbol = 0;
                name = "Romania";
                break;
            }
            case "Japao":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_japao.png"));
                
                cardSymbol = 2;
                name = "Japao";
                break;
            }
            case "Jordania":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_jordania.png"));
                
                cardSymbol = 1;
                name = "Jordania";
                break;
            }
            case "Letonia":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_letonia.png"));
                
                cardSymbol = 1;
                name = "Letonia";
                break;
            }
            case "Paquistao":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_paquistao.png"));
                
                cardSymbol = 2;
                name = "Paquistao";
                break;
            }
            case "Russia":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_russia.png"));
                
                cardSymbol = 0;
                name = "Russia";
                break;
            }
            case "Siberia":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_siberia.png"));
                
                cardSymbol = 1;
                name = "Siberia";
                break;
            }
            case "Siria":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_siria.png"));
                
                cardSymbol = 1;
                name = "Siria";
                break;
            }
            case "Tailandia":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_tailandia.png"));
                
                cardSymbol = 0;
                name = "Tailandia";
                break;
            }
            case "Turquia":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_as_turquia.png"));
                
                cardSymbol = 0;
                name = "Turquia";
                break;
            }
            case "Autralia":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_oc_australia.png"));
                
                cardSymbol = 0;
                name = "Autralia";
                break;
            }
            case "Indonesia":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_oc_indonesia.png"));
                
                cardSymbol = 0;
                name = "Indonesia";
                break;
            }
            case "Nova Zelandia":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_oc_novazelandia.png"));
                
                cardSymbol = 0;
                name = "Nova Zelandia";
                break;
            }
            case "Perth":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_oc_perth.png"));
                
                cardSymbol = 0;
                name = "Perth";
                break;
            }
            case "Coringa":{
                cardImage = ImageIO.read(getClass().getResource("/images/Cartas/war_carta_coringa.png"));
                
                cardSymbol = 3;
                name = "Coringa";
                break;
            }
         }
     }
     
    public void showCard(){
         
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
        
     }
     
    public void drawCard(Graphics g){
        g.drawImage(getCardImage(), 0, 0, null);
    }
    
     
     @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        drawCard(g);
    }
    
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(440,725);
    }

    public int getCardSymbol() {
        return cardSymbol;
    }

    public BufferedImage getCardImage() {
        return cardImage;
    }
}
