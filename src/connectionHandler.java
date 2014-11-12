import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class connectionHandler {
	
	public static void main(String[] arg) {
		
		/*InetAddress LocaleAdresse ;
		InetAddress ServeurAdresse;

		try {

			LocaleAdresse = InetAddress.getLocalHost();
			//System.out.println("L'adresse locale est : "+LocaleAdresse ); 
			
			ServeurAdresse= InetAddress.getByName("www.siteduzero.com");
                        System.out.println("L'adresse du serveur du site du zéro est : "+ServeurAdresse);
		
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		}*/
		
		
	}
	
	public static void server(){
		ServerSocket socketserver  ;
		Socket socketduserveur ;
		BufferedReader in;
		PrintWriter out;
		
		try {
		
			socketserver = new ServerSocket(2009);
			System.out.println("Le serveur est à l'écoute du port "+socketserver.getLocalPort());
			socketduserveur = socketserver.accept(); 
		    System.out.println("Un zéro s'est connecté");
		    
			out = new PrintWriter(socketduserveur.getOutputStream());
			
		    out.println("Vous êtes connecté zéro !");
		    out.flush();
		                
		    socketduserveur.close();
		    socketserver.close();
		        
		}catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void client(){
		
		
		Socket socket;
		BufferedReader in;
		PrintWriter out;

		try {
		
			socket = new Socket(InetAddress.getLocalHost(),2009);	
		        System.out.println("Demande de connexion");

		        in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
		        String message_distant = in.readLine();
		        System.out.println(message_distant);
		        
		        socket.close();
		       
		}catch (UnknownHostException e) {
			
			e.printStackTrace();
		}catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

}