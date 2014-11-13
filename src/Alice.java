import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;


public class Alice {
	
	private Socket socketClient;
	private InputStream in;
	private DataInputStream dis;
	private OutputStream out;
	private DataOutputStream dos;
	private PublicKey publicKey;
	private PrivateKey privateKey;
	
	public Alice(){

		
		System.out.println("I am Alice");

		try {
			KeyPair kp = Util.LoadKeyPair("./alice","RSA");
			publicKey = kp.getPublic();
			privateKey = kp.getPrivate();
		
			socketClient = new Socket(InetAddress.getLocalHost(),2009);	

		    in = socketClient.getInputStream();
		    dis = new DataInputStream(in);
		    out = socketClient.getOutputStream(); 
		    dos = new DataOutputStream(out);
		    
		    protocol();
		    
		    socketClient.close();
		    
		}catch (UnknownHostException e) {
			e.printStackTrace();
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
	
	public void sendBytes(byte[] myByteArray){
	    sendBytes(myByteArray, 0, myByteArray.length);
	}

	public void sendBytes(byte[] myByteArray, int start, int len){

	    try {
			dos.writeInt(len);
	        dos.write(myByteArray, start, len);
	        dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] readBytes(){
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
		//step 0 : public key
		sendBytes(publicKey.getEncoded());
		byte[] rawBobPublicKey = readBytes();
		PublicKey bobPublicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(rawBobPublicKey));
		
		//step 1
		byte[] aliceIP = InetAddress.getLocalHost().getAddress();
		byte[] bobIP = socketClient.getInetAddress().getAddress();
		byte[] rawSessionKey = Util.generateSK();
		SecretKeySpec sessionKey = new SecretKeySpec(rawSessionKey, "AES");
		byte[] r = "r".getBytes();
		byte[] timestamp = "timestamp".getBytes();
		byte[] msg = Util.concatBytes(aliceIP, bobIP);
		msg = Util.concatBytes(msg, rawSessionKey);
		msg = Util.concatBytes(msg, r);
		msg = Util.concatBytes(msg, timestamp);
		byte[] encryptedMsg = RSA.encrypt(msg, bobPublicKey);
		
		sendBytes(encryptedMsg);
		
		//step 2
		File file = new File(".\\testfile.txt");
	    long length = file.length();
	    if (length > Integer.MAX_VALUE) {
	        System.out.println("File is too large.");
	        return;
	    }
	    byte[] bytes = new byte[(int) length];
	    FileInputStream fis = new FileInputStream(file);
	    BufferedInputStream bis = new BufferedInputStream(fis);
	    int count = bis.read(bytes);
	    byte[] fileByte = bytes;
		
		while ((count = bis.read(bytes)) > 0) {
			fileByte = Util.concatBytes(fileByte, bytes);
	    }

		msg = Util.concatBytes(aliceIP, bobIP);
		msg = Util.concatBytes(msg, fileByte);
		msg = Util.concatBytes(msg, r);
		msg = Util.concatBytes(msg, timestamp);
		encryptedMsg = AES.encrypt(msg, sessionKey);
		
		sendBytes(encryptedMsg);
		
		fis.close();
		bis.close();
		
		//step 3
		byte[] hash = Util.concatBytes(aliceIP, bobIP);
		hash = Util.concatBytes(hash, encryptedMsg);
		hash = Util.concatBytes(hash, r);
		hash = Util.concatBytes(hash, timestamp);
		hash = SHA3.hash(hash);
		
		byte[] signature = RSA.generateSignature(hash, privateKey);
		
		sendBytes(signature);
		
		//step 4
	    
		hash = Util.concatBytes(bobIP, aliceIP);
		hash = Util.concatBytes(hash, fileByte);
		hash = Util.concatBytes(hash, r);
		hash = Util.concatBytes(hash, timestamp);
		hash = SHA3.hash(hash);
		
		msg = readBytes();
		
		if (Arrays.equals(msg,  hash)){
			System.out.println("File succesfully transmitted");
		}
		else{
			System.out.println("File transmitted is corrupt");
		}
		
	}
}
