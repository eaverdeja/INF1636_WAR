package network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import controller.GameManager;
import view.ClientWelcome;

public class Reader implements Runnable {
	Socket cliente;
	private GameManager gameManager = GameManager.getInstance();
		
	public Reader(Socket cliente) throws IOException {
		this.cliente = cliente;
    }
	
	@Override
	public void run() {
        try {
        	Scanner in = new Scanner(cliente.getInputStream());
            
        	while (in.hasNextLine()) {
        		String msg = in.nextLine();
        		if(msg.equals("start_game")) {
        			Client.getInstance().start();
        		}
        		if(msg.equals("0"))
        			gameManager.setPlayer(0);
        		if(msg.equals("1"))
       				gameManager.setPlayer(1);
       			if(msg.equals("2"))
        			gameManager.setPlayer(2);
        		if (msg.equals("nextTurn"))
        			gameManager.nextTurn();
        		if (msg.length() >= 10){
        			String temp = msg.substring(0,10);
        			if (temp.equals("firststate")){
        					gameManager.applyState(msg.substring(11));
              				System.out.println(msg.substring(6));
        			}
        			temp = msg.substring(0,5);
        			if (temp.equals("state")){
        				char c = msg.charAt(5);
        				
        				if (Character.getNumericValue(c) == gameManager.getPlayer()){
        					System.out.println("EH IGUAL");
        					return;
        				}
    					gameManager.applyState(msg.substring(6));
    					gameManager.nextTurn();
        			}
        		}
            }
        	
        	in.close();
            
        } catch (IOException e) {
            e.getMessage();
        }
	}

}