import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class client {
	
	private Socket socketClient;
	private BufferedReader in;
	private PrintWriter out;
	
	public void client(){

		try {
		
			socketClient = new Socket(InetAddress.getLocalHost(),2009);	

		    in = new BufferedReader (new InputStreamReader (socketClient.getInputStream()));
			out = new PrintWriter(socketClient.getOutputStream());
		    
		    socketClient.close();
		    
		}catch (UnknownHostException e) {
			e.printStackTrace();
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
