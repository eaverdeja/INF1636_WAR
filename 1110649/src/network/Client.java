package network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import view.ClientWelcome;

public class Client {
	private static Client client;
	private Socket cli;
	private Scanner teclado;
    private InputStream in;
    private OutputStream out;
    private static BufferedWriter bw;
	private String address;
	private String msg;
	ClientWelcome welcomePanel;
	
    //Implementing Singleton pattern
    protected Client() {
    }

    public static Client getInstance() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }
    
    public Boolean connect(String address, ClientWelcome welcomePanel) throws IOException {
    	this.welcomePanel = welcomePanel;
    	try {
			cli = new Socket(address, 12345);
			teclado = new Scanner(System.in);
	        in = cli.getInputStream();
	        out = cli.getOutputStream();
	        bw = new BufferedWriter(new OutputStreamWriter(out));
	        
	        //Reader usado para ouvir mensagens do servidor
	        new Thread(new Reader(cli)).start();
			return true;
		} catch (UnknownHostException e) {
			e.getMessage();
			return false;
		} catch (IOException e) {
			e.getMessage();
			return false;
		}
    }
    
    public static void sendMessage(String msg) {
    	try { 
			bw.write(msg); 
			bw.newLine();
			bw.flush();
    	} catch(IOException ex) {
    		ex.getMessage();
    	}
	}
    
    public void start() {
		System.out.println("hi");
    	welcomePanel.start();
    }
}
