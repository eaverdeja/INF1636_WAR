import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class ClienteTask implements Runnable {
    private final Socket clienteSocket;
    private Boolean startGame = false;
    String currentMsg;
    BufferedWriter bw;
    static ArrayList<Socket> listaClientes;

    public ClienteTask(Socket clientSocket) throws IOException {
        this.clienteSocket = clientSocket;
        bw = new BufferedWriter(new OutputStreamWriter(clienteSocket.getOutputStream()));
    }
    
    @Override
    public void run() {
        try {
        	Scanner in = new Scanner(clienteSocket.getInputStream());
            
        	while (in.hasNextLine()) {
        		currentMsg = in.nextLine();
        		if(currentMsg.compareTo("StartGame") == 0) {
        			startGame = true;
        			//Warn server
        			Server.isGameReady();
        		}
        		broadcast(currentMsg);
            }
            
            in.close();
            clienteSocket.close();
            
        } catch (IOException e) {
            e.getMessage();
        }
    }
    
    private void broadcast(String msg) throws IOException {
    	listaClientes = Server.getClientes();
		
    	for (Socket cliente : listaClientes) {
    		try {
    	    	System.out.println("Broadcasting: " + msg + " to: " + cliente.getInetAddress());
    	        bw.write(msg);
    	        bw.newLine();
    	        bw.flush();
    					
    		} catch (IOException e) {
                e.getMessage();
            }
		}
    }
    
    public Boolean isReady() {
    	return startGame;
    }
    
    public void startGame() {
    	System.out.println("I, " + clienteSocket.getInetAddress() + ", WILL START!");
    	
    }
}