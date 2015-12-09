import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class Server {
	static Boolean boot;
	static int players;
	static HashMap<Socket, ClienteTask> listaClientes;
	
	public static void main(String[] args) throws IOException {
		
		//Wait for customers
		Runnable serverTask = new Runnable() {
			@Override
			public void run() {
				Socket cliente = null;
				listaClientes = new HashMap<>();
				do {
					boot = setup();
				} while(!boot);
					
				try{
					ServerSocket servidor = new ServerSocket(12345);
					System.out.println("Porta 12345 aberta!");
					do {
						cliente = servidor.accept();
						System.out.println("Nova conexao com o cliente " + cliente.getInetAddress().getHostAddress());
						ClienteTask worker = new ClienteTask(cliente);
						new Thread(worker).start();
						
						listaClientes.put(cliente, worker);
						worker.id = listaClientes.size() - 1;
					} while(true);
					
				} catch(IOException e) {
                    System.err.println("Unable to process client request");
                    e.printStackTrace();
				}	
			}
		};
		
		Thread serverThread = new Thread(serverTask);
		serverThread.start();
	}
	
	public static void isGameReady() {
		Boolean gameReady = false;
		for(ClienteTask cliente : listaClientes.values()) {
			gameReady = (cliente.isReady() && (listaClientes.size() == players));
			if (!gameReady)
				return;
		}

		if(gameReady) {
			broadcast("start_game", null);
		}
	}
    
    public static void broadcast(String msg, ClienteTask sender) {
    	for (ClienteTask cliente : listaClientes.values()) {
    		if(cliente != sender) {
	    		try {
	    	        cliente.getWriter().write(msg);
	    	        cliente.getWriter().newLine();
	    	        cliente.getWriter().flush();
	    					
	    		} catch (IOException e) {
	                e.getMessage();
	            }
    		}
		}
    }
	
	private static boolean setup() {
		try {
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        System.out.print("Quantos jogadores irao jogar?\n");
	        try{
	            players = Integer.parseInt(br.readLine());
	        }catch(NumberFormatException nfe){
	            System.err.println("Digite um numero!");
	            return false;
	        }
		} catch (IOException ex) {
			ex.getMessage();
			return false;
		}
		return true;
	}
	
	public static ArrayList<Socket> getClientes() {
		return new ArrayList<Socket>(listaClientes.keySet());
	}
}
