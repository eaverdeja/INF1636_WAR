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
    public int id;
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
        		read(in.nextLine());
            }
            
            in.close();
            clienteSocket.close();
            
        } catch (IOException e) {
            e.getMessage();
        }
    }
    
    public Boolean isReady() {
    	return startGame;
    }
    
    private void read(String msg) {
		if(msg.compareTo("start_game") == 0) {
			startGame = true;
			//Warn server
			try {
				getWriter().write(String.format("%d",this.id));
				getWriter().newLine();
    	        getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Server.isGameReady();
		}
		
		if (msg.length() >= 10){
			String temp = msg.substring(0,10);
			if (temp.equals("firststate")){
				
				Server.broadcast(msg,null);
			}
			
			temp = msg.substring(0,5);
			if (temp.equals("state")){
				Server.broadcast(msg,this);
			}
		}

    }
     
    public BufferedWriter getWriter() {
    	return bw;
    }
}