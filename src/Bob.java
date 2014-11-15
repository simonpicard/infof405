import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;


public class Bob {
	
	private ServerSocket connectionSocket  ;
	private Socket socketServer ;
	private InputStream in;
	private DataInputStream dis;
	private OutputStream out;
	private DataOutputStream dos;
	private PublicKey publicKey;
	private PrivateKey privateKey;
	
	public Bob(){
		
		System.out.println("I am Bob");
		
		try {
			KeyPair kp = Util.LoadKeyPair("./alice","RSA");
			publicKey = kp.getPublic();
			privateKey = kp.getPrivate();
		
			connectionSocket = new ServerSocket(1337);
			
			//System.out.println("Le serveur est à l'écoute du port "+socketserver.getLocalPort());
			
			socketServer = connectionSocket.accept(); 

		    in = socketServer.getInputStream();
		    dis = new DataInputStream(in);
		    out = socketServer.getOutputStream(); 
		    dos = new DataOutputStream(out);
		    
		    protocol();
		                
			socketServer.close();
		    connectionSocket.close();
		        
		}catch (IOException e) {
			
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendBytes(byte[] myByteArray){
	    sendBytes(myByteArray, 0, myByteArray.length);
	}

	private void sendBytes(byte[] myByteArray, int start, int len){

	    try {
			dos.writeInt(len);
	        dos.write(myByteArray, start, len);
	        dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private byte[] readBytes(){
		byte[] data = null;
	    int len;
		try {
			len = dis.readInt();
		    data = new byte[len];
		    if (len > 0) {
		        dis.readFully(data);
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return data;
	}
	
	private void protocol() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException{
		//step 0 public key
		byte[] rawAlicePublicKey = readBytes();
		PublicKey alicePublicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(rawAlicePublicKey));
		sendBytes(publicKey.getEncoded());
		
		//step 1
		byte[] bobIP = InetAddress.getLocalHost().getAddress();
		byte[] aliceIP = socketServer.getInetAddress().getAddress();
		
		byte[] cryptedMsg = readBytes();
		byte[] msg = RSA.decrypt(cryptedMsg, privateKey);
		
		if (!checkIdentifiers(Arrays.copyOfRange(msg, 0, 8))){
			System.out.println("Bad identifiers");
			return;
		}
		
		byte[] rawSessionKey = Arrays.copyOfRange(msg, 8, 24);
		SecretKeySpec sessionKey = new SecretKeySpec(rawSessionKey, "AES");
		byte[] r = Arrays.copyOfRange(msg, 24, 28);
		byte[] timestamp = Arrays.copyOfRange(msg, 28, 36);
		
		if(!(checkTimeStamp(timestamp))){
			System.out.println("Expired timestamp");
			return;
		}
		
		//step2
		cryptedMsg = readBytes();
		msg = AES.decrypt(cryptedMsg, sessionKey);
		
		if (!checkIdentifiers(Arrays.copyOfRange(msg, 0, 8))){
			System.out.println("Bad identifiers");
			return;
		}
		FileOutputStream fos = new FileOutputStream(".\\rcvfile.jpg");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        
		byte[] file = Arrays.copyOfRange(msg, 8, msg.length-12);

        bos.write(file, 0, msg.length-8-12);
        bos.flush();
        fos.close();
        bos.close();
		
		r = Arrays.copyOfRange(msg, msg.length-12, msg.length-8);
		timestamp = Arrays.copyOfRange(msg, msg.length-8,msg.length);
		
		if(!(checkTimeStamp(timestamp))){
			System.out.println("Expired timestamp");
			return;
		}
		
		//step 3
		byte[] hash = Util.concatBytes(aliceIP, bobIP);
		hash = Util.concatBytes(hash, cryptedMsg);
		hash = Util.concatBytes(hash, r);
		hash = Util.concatBytes(hash, timestamp);
		hash = SHA3.hash(hash);
		
		cryptedMsg = readBytes();
		
		if (RSA.verifySignature(cryptedMsg, alicePublicKey, hash)){
			System.out.println("Signature ok");
		}
		else{
			System.out.println("Bad signature");
			return;
		}
		
		//step 4
		hash = Util.concatBytes(bobIP, aliceIP);
		hash = Util.concatBytes(hash, file);
		hash = Util.concatBytes(hash, r);
		hash = Util.concatBytes(hash, timestamp);
		hash = SHA3.hash(hash);
		
		sendBytes(hash);
	}
	
	private boolean checkTimeStamp(byte[] timestamp){
		ByteBuffer wrapped = ByteBuffer.wrap(timestamp);
		long ts = wrapped.getLong();
		System.out.println(ts);
		Date d = new Date(ts);
		Date now = new Date();
		d.setMinutes(d.getMinutes()+5);
		return now.before(d);
	}
	
	private boolean checkIdentifiers(byte[] msg) throws UnknownHostException{
		
		byte[] rAliceIP = Arrays.copyOfRange(msg, 0, 4);
		byte[] rBobIP = Arrays.copyOfRange(msg, 4, 8);

		return (Arrays.equals(rAliceIP, socketServer.getInetAddress().getAddress()) && Arrays.equals(rBobIP, InetAddress.getLocalHost().getAddress()));
	}
	
}
