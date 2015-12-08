package network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import view.ClientWelcome;

public class Reader implements Runnable {
	Socket cliente;
		
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
        		System.out.println(msg);
            }
        	
        	in.close();
            
        } catch (IOException e) {
            e.getMessage();
        }
	}

}
