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
                File image = new File("images/Cartas/war_carta_an_alasca.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Alasca";
                break;
            }
            case "Calgary":{
                File image = new File("images/Cartas/war_carta_an_calgary.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Calgary";
                break;
            }
            case "Groelandia":{
                File image = new File("images/Cartas/war_carta_an_groelandia.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Groelandia";
                break;
            }
            case "Vancouver":{
                File image = new File("images/Cartas/war_carta_an_vancouver.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Vancouver";
                break;
            }
            case "Quebec":{
                File image = new File("images/Cartas/war_carta_an_quebec.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Quebec";
                break;
            }
            case "California":{
                File image = new File("images/Cartas/war_carta_an_california.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "California";
                break;
            }
            case "Texas":{
                File image = new File("images/Cartas/war_carta_an_texas.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Texas";
                break;
            }
            case "NovaYork":{
                File image = new File("images/Cartas/war_carta_an_novayork.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "NovaYork";
                break;
            }
            case "Mexico":{
                File image = new File("images/Cartas/war_carta_an_mexico.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Mexico";
                break;
            }
            case "Venezuela":{
                File image = new File("images/Cartas/war_carta_asl_venezuela.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Venezuela";
                break;
            }
            case "Peru":{
                File image = new File("images/Cartas/war_carta_asl_peru.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Peru";
                break;
            }
            case "Brasil":{
                File image = new File("images/Cartas/war_carta_asl_brasil.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Brasil";
                break;
            }
            case "Argentina":{
                File image = new File("images/Cartas/war_carta_asl_argentina.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Argentina";
                break;
            }
            case "Angola":{
                File image = new File("images/Cartas/war_carta_af_angola.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Angola";
                break;
            }
            case "Africa do Sul":{
                File image = new File("images/Cartas/war_carta_af_africadosul.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Africa do Sul";
                break;
            }
            case "Argelia":{
                File image = new File("images/Cartas/war_carta_af_argelia.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Argelia";
                break;
            }
            case "Egito":{
                File image = new File("images/Cartas/war_carta_af_egito.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Egito";
                break;
            }
            case "Nigeria":{
                File image = new File("images/Cartas/war_carta_af_nigeria.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Nigeria";
                break;
            }
            case "Somalia":{
                File image = new File("images/Cartas/war_carta_af_somalia.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Somalia";
                break;
            }
            case "Espanha":{
                File image = new File("images/Cartas/war_carta_eu_espanha.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Espanha";
                break;
            }
            case "Franca":{
                File image = new File("images/Cartas/war_carta_eu_franca.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Franca";
                break;
            }
            case "Italia":{
                File image = new File("images/Cartas/war_carta_eu_italia.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Italia";
                break;
            }
            case "Polonia":{
                File image = new File("images/Cartas/war_carta_eu_polonia.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Polonia";
                break;
            }
            case "Reino Unido":{
                File image = new File("images/Cartas/war_carta_eu_reinounido.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Reino Unido";
                break;
            }
            case "Romania":{
                File image = new File("images/Cartas/war_carta_eu_romenia.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Romania";
                break;
            }
            case "Suecia":{
                File image = new File("images/Cartas/war_carta_eu_suecia.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Suecia";
                break;
            }
            case "Ucrania":{
                File image = new File("images/Cartas/war_carta_eu_ucrania.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Ucrania";
                break;
            }
            case "Arabia Saudita":{
                File image = new File("images/Cartas/war_carta_as_arabiasaudita.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Arabia Saudita";
                break;
            }
            case "Bangladesh":{
                File image = new File("images/Cartas/war_carta_as_bangladesh.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Bangladesh";
                break;
            }
            case "Cazaquistao":{
                File image = new File("images/Cartas/war_carta_as_cazaquistao.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Cazaquistao";
                break;
            }
            case "Mongolia":{
                File image = new File("images/Cartas/war_carta_as_mongolia.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Mongolia";
                break;
            }
            case "China":{
                File image = new File("images/Cartas/war_carta_as_china.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "China";
                break;
            }
            case "Coreia do Norte":{
                File image = new File("images/Cartas/war_carta_as_coreiadonorte.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Coreia do Norte";
                break;
            }
            case "Estonia":{
                File image = new File("images/Cartas/war_carta_as_estonia.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Estonia";
                break;
            }
            case "India":{
                File image = new File("images/Cartas/war_carta_as_india.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "India";
                break;
            }
            case "Ira":{
                File image = new File("images/Cartas/war_carta_as_ira.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Ira";
                break;
            }
            case "Iraque":{
                File image = new File("images/Cartas/war_carta_as_iraque.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Romania";
                break;
            }
            case "Japao":{
                File image = new File("images/Cartas/war_carta_as_japao.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Japao";
                break;
            }
            case "Jordania":{
                File image = new File("images/Cartas/war_carta_as_jordania.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Jordania";
                break;
            }
            case "Letonia":{
                File image = new File("images/Cartas/war_carta_as_letonia.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Letonia";
                break;
            }
            case "Paquistao":{
                File image = new File("images/Cartas/war_carta_as_paquistao.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Paquistao";
                break;
            }
            case "Russia":{
                File image = new File("images/Cartas/war_carta_as_russia.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Russia";
                break;
            }
            case "Siberia":{
                File image = new File("images/Cartas/war_carta_as_siberia.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Siberia";
                break;
            }
            case "Siria":{
                File image = new File("images/Cartas/war_carta_as_siria.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Siria";
                break;
            }
            case "Tailandia":{
                File image = new File("images/Cartas/war_carta_as_tailandia.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Tailandia";
                break;
            }
            case "Turquia":{
                File image = new File("images/Cartas/war_carta_as_turquia.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Turquia";
                break;
            }
            case "Autralia":{
                File image = new File("images/Cartas/war_carta_oc_australia.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Autralia";
                break;
            }
            case "Indonesia":{
                File image = new File("images/Cartas/war_carta_oc_indonesia.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Indonesia";
                break;
            }
            case "Nova Zelandia":{
                File image = new File("images/Cartas/war_carta_oc_novazelandia.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Nova Zelandia";
                break;
            }
            case "Perth":{
                File image = new File("images/Cartas/war_carta_oc_perth.png");
                cardImage = ImageIO.read(image);
                cardSymbol = 0;
                name = "Perth";
                break;
            }
            case "Coringa":{
                File image = new File("images/Cartas/war_carta_coringa.png");
                cardImage = ImageIO.read(image);
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
        g.drawImage(cardImage, 0, 0, null);
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
}
