import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
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
import java.nio.ByteBuffer;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
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
	private String filePath;
	private String pubPath;
	private MainFrame aliceFrame;
	
	public Alice(String pub, String priv, String ip, int port, String fp, MainFrame frame){

		filePath = fp;
		pubPath = pub;
		aliceFrame = frame;

		try {
			KeyPair kp = Util.LoadKeyPair(pub, priv,"RSA");
			publicKey = kp.getPublic();
			privateKey = kp.getPrivate();
		
			socketClient = new Socket(ip, port);	

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
		} catch (CertificateException e) {
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
	
	
	private void protocol() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, CertificateException{
		//step 0 : public key

		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		
		FileInputStream fis = new FileInputStream(pubPath);
	    X509Certificate cert = (X509Certificate) cf.generateCertificate(fis);
	    fis.close();
		
		sendBytes(cert.getEncoded());
		aliceFrame.jlog.setText("Public key sent");
		byte[] rawBobPublicKey = readBytes();
		aliceFrame.jlog.setText(aliceFrame.jlog.getText()+"\n"+"Public key received");

		X509Certificate bobCertificate = (X509Certificate)cf.generateCertificate(new ByteArrayInputStream(rawBobPublicKey));
		PublicKey bobPublicKey = bobCertificate.getPublicKey();

		long originalTS = System.currentTimeMillis();
		
		//step 1
		byte[] aliceIP = InetAddress.getLocalHost().getAddress();
		byte[] bobIP = socketClient.getInetAddress().getAddress();
		
		byte[] rawSessionKey = Util.generateSK();
		SecretKeySpec sessionKey = new SecretKeySpec(rawSessionKey, "AES");
		byte[] r = Util.generateR();
		ByteBuffer b = ByteBuffer.allocate(8);
		b.putLong(originalTS);
		byte[] timestamp = b.array();
		byte[] msg = Util.concatBytes(aliceIP, bobIP);
		msg = Util.concatBytes(msg, rawSessionKey);
		msg = Util.concatBytes(msg, r);
		msg = Util.concatBytes(msg, timestamp);
		byte[] encryptedMsg = RSA.encrypt(msg, bobPublicKey);
		
		sendBytes(encryptedMsg);
		aliceFrame.jlog.setText(aliceFrame.jlog.getText()+"\n"+"Session key sent");
		
		//step 2
		File file = new File(filePath);
	    long length = file.length();
	    if (length > Integer.MAX_VALUE) {
			aliceFrame.jlog.setText(aliceFrame.jlog.getText()+"\n"+"File is to large");
	        return;
	    }
	    byte[] bytes = new byte[(int) length];
	    fis = new FileInputStream(file);
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
		aliceFrame.jlog.setText(aliceFrame.jlog.getText()+"\n"+"File sent");
		
		fis.close();
		bis.close();
		
		//step 3
		msg = Util.concatBytes(aliceIP, bobIP);
		msg = Util.concatBytes(msg, encryptedMsg);
		msg = Util.concatBytes(msg, r);
		msg = Util.concatBytes(msg, timestamp);
		
		byte[] signature = RSA.generateSignature(msg, privateKey);
		
		sendBytes(signature);
		aliceFrame.jlog.setText(aliceFrame.jlog.getText()+"\n"+"Signature sent");
		
		//step 4
		byte[] hash = Util.concatBytes(bobIP, aliceIP);
		hash = Util.concatBytes(hash, fileByte);
		hash = Util.concatBytes(hash, r);
		hash = Util.concatBytes(hash, timestamp);
		hash = SHA3.hash(hash);
		
		msg = readBytes();
		
		if (Arrays.equals(msg,  hash)){
			aliceFrame.jlog.setText(aliceFrame.jlog.getText()+"\n"+"File succesfully transmitted");
		}
		else{
			aliceFrame.jlog.setText(aliceFrame.jlog.getText()+"\n"+"File transmitted is corrupt");
		}
		
	}
}
