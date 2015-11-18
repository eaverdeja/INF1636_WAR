import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
	static ArrayList<PrintStream> listaClientes = null;
	
	public static void main(String[] args) throws IOException {
		
		//Wait for customers
		Runnable serverTask = new Runnable() {
			@Override
			public void run() {
				Socket cliente = null;
				listaClientes = new ArrayList<>();
				try{
					ServerSocket servidor = new ServerSocket(12345);
					System.out.println("Porta 12345 aberta!");
					while(true) {
						cliente = servidor.accept();
						System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
						listaClientes.add(new PrintStream(cliente.getOutputStream()));
						new Thread(new ClienteTask(cliente)).start();
					}
						
				} catch(IOException e) {
                    System.err.println("Unable to process client request");
                    e.printStackTrace();
				}	
			}
		};
		
		Thread serverThread = new Thread(serverTask);
		serverThread.start();
	}
	
	public static ArrayList<PrintStream> getClientes() {
		return listaClientes;
	}
}
