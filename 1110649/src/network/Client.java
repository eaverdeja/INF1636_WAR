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

public class Client {
	private static Client client;
	private Socket cli;
	private Scanner teclado;
    private InputStream in;
    private OutputStream out;
    BufferedWriter bw;
	private String address;
	private String msg;
	
    //Implementing Singleton pattern
    protected Client() {
    }

    public static Client getInstance() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }
    
    public Boolean connect(String address) throws IOException {
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
    
    public void sendMessage(String msg) throws UnknownHostException, IOException {
		bw.write(msg); 
		bw.newLine();
		bw.flush();
	}
}
