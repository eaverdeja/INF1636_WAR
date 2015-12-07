package network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

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
        		System.out.println(in.nextLine());
            }
        	
        	in.close();
            
        } catch (IOException e) {
            e.getMessage();
        }
	}

}
