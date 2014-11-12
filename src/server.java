import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class server {
	
	private ServerSocket connectionSocket  ;
	private Socket socketServer ;
	private BufferedReader in;
	private PrintWriter out;
	
	public void server(){
		
		try {
		
			connectionSocket = new ServerSocket(2009);
			
			//System.out.println("Le serveur est à l'écoute du port "+socketserver.getLocalPort());
			
			socketServer = connectionSocket.accept(); 

		    in = new BufferedReader (new InputStreamReader (socketServer.getInputStream()));
			out = new PrintWriter(socketServer.getOutputStream());
		                
			socketServer.close();
		    connectionSocket.close();
		        
		}catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	private void send(String msg){
	    out.println(msg);
	    out.flush();
	}

	private void receive(){
		String msg;
		try {
			msg = in.readLine();
		    System.out.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
