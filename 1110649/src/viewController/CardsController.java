/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Card;
import model.Player;

/**
 *
 * @author lorenzosaraiva
 */
public class CardsController implements Controller {
    
    private List<Card> cardDeck = new ArrayList<>();
    private Turn turnController = Turn.getInstance();
    public Card getRandomCard(){
            Random randomGenerator = new Random();
            int randValue = randomGenerator.nextInt(cardDeck.size());
            System.out.println(randValue);
            Card card = cardDeck.get(randValue);
            cardDeck.remove(randValue);
            if (cardDeck.size()==0){
                for (Player p : turnController.getPlayers()){
                p.removeCards();
                createAndRandomizeCards();
                }
            }
        return card;      
    }
    
    public CardsController() {
        createAndRandomizeCards();
    }
    
    public void consoleEvent(){
        
    }
    
    private void createAndRandomizeCards(){
    
        try {
            Card card;
            card = new Card("Alasca");
            cardDeck.add(card);
            
            card = new Card("Calgary");
            cardDeck.add(card);
            
            card = new Card("Groelandia");
            cardDeck.add(card);
            
            card = new Card("Vancouver");
            cardDeck.add(card);
            
            card = new Card("Quebec");
            cardDeck.add(card);
            
            card = new Card("California");
            cardDeck.add(card);
            
            card = new Card("Texas");
            cardDeck.add(card);
            
            card = new Card("NovaYork");
            cardDeck.add(card);
            
            card = new Card("Mexico");
            cardDeck.add(card);
            
            card = new Card("Venezuela");
            cardDeck.add(card);
            
            card = new Card("Peru");
            cardDeck.add(card);
            
            card = new Card("Brasil");
            cardDeck.add(card);
            
            card = new Card("Argentina");
            cardDeck.add(card);
            
            card = new Card("Angola");
            cardDeck.add(card);
            
            card = new Card("Africa do Sul");
            cardDeck.add(card);
            
            card = new Card("Argelia");
            cardDeck.add(card);
            
            card = new Card("Egito");
            cardDeck.add(card);
            
            card = new Card("Nigeria");
            cardDeck.add(card);
            
            card = new Card("Somalia");
            cardDeck.add(card);
            
            card = new Card("Espanha");
            cardDeck.add(card);
            
            card = new Card("Franca");
            cardDeck.add(card);
            
            card = new Card("Italia");
            cardDeck.add(card);
            
            card = new Card("Polonia");
            cardDeck.add(card);
            
            card = new Card("Reino Unido");
            cardDeck.add(card);
            
            card = new Card("Romania");
            cardDeck.add(card);
            
            card = new Card("Suecia");
            cardDeck.add(card);
            
            card = new Card("Ucrania");
            cardDeck.add(card);
            
            card = new Card("Arabia Saudita");
            cardDeck.add(card);
            
            card = new Card("Bangladesh");
            cardDeck.add(card);
            
            card = new Card("Cazaquistao");
            cardDeck.add(card);
            
            card = new Card("Mongolia");
            cardDeck.add(card);
            
            card = new Card("China");
            cardDeck.add(card);
            
            card = new Card("Coreia do Norte");
            cardDeck.add(card);
            
            card = new Card("Estonia");
            cardDeck.add(card);
            
            card = new Card("India");
            cardDeck.add(card);
            
            card = new Card("Ira");
            cardDeck.add(card);
            
            card = new Card("Iraque");
            cardDeck.add(card);
            
            card = new Card("Japao");
            cardDeck.add(card);
            
            card = new Card("Jordania");
            cardDeck.add(card);
            
            card = new Card("Letonia");
            cardDeck.add(card);
            
            card = new Card("Paquistao");
            cardDeck.add(card);
            
            card = new Card("Russia");
            cardDeck.add(card);
            
            card = new Card("Siberia");
            cardDeck.add(card);
                      
            card = new Card("Siria");
            cardDeck.add(card);
            
            card = new Card("Tailandia");
            cardDeck.add(card);
            
            card = new Card("Turquia");
            cardDeck.add(card);
            
            card = new Card("Autralia");
            cardDeck.add(card);
            
            card = new Card("Indonesia");
            cardDeck.add(card);
            
            card = new Card("Nova Zelandia");
            cardDeck.add(card);
            
            card = new Card("Perth");
            cardDeck.add(card);
            
            card = new Card("Coringa");
            cardDeck.add(card);
            
            card = new Card("Coringa");
            cardDeck.add(card);
            
            
            shuffleCardDeck();
            
        } catch (IOException ex) {
            Logger.getLogger(CardsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void shuffleCardDeck(){
        
        long seed = System.nanoTime();
        Collections.shuffle(cardDeck, new Random(seed));
        
    }
}
