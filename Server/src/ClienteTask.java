import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClienteTask implements Runnable {
    private final Socket clienteSocket;
    ArrayList<PrintStream> listaClientes;

    public ClienteTask(Socket clientSocket) {
        this.clienteSocket = clientSocket;
    }
    
    @Override
    public void run() {
        try {
        	Scanner in = new Scanner(clienteSocket.getInputStream());
            
        	while (in.hasNextLine()) {
        		broadcast(in.nextLine());
            }
            
            in.close();
            clienteSocket.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void broadcast(String msg) {
    	listaClientes = Server.getClientes();
		
    	for (PrintStream out_cli : listaClientes) {
    		out_cli.println(msg);
		}
    }
}