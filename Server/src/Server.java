import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket servidor = new ServerSocket(12345);
		System.out.println("Porta 12345 aberta!");
		
		Socket cliente = servidor.accept();
		System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
		
		Scanner in = new Scanner(cliente.getInputStream());
		
		while (in.hasNextLine()) { 
			System.out.println(in.nextLine());
		}
		
		in.close();
		servidor.close();
		cliente.close();
		
		System.out.println("O servidor terminou de executar!");
	}
}
