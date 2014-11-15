import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;




public class Main {

	public static void main(String[] args) {
		
		//Fenetre mw = new Fenetre();
		/*int t = (int) (System.currentTimeMillis() / 1000L);
		System.out.println(t);
		Date d = new Date((long)t*1000);
		String DATE_FORMAT_NOW = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String stringDate = sdf.format(d );
		System.out.println(stringDate);*/
		
		/*String teststring = "test1234!!";
		
		byte[] test = teststring.getBytes();
		
		byte[] test2 = new byte[test.length];
		
		ByteArrayInputStream bais = new ByteArrayInputStream(test);
		
		bais.read(test2, 0, test.length);
		
		System.out.println(new String(test2));
		
		System.out.println(Util.bytesToHex(test));
		
		
		
		System.out.println(Util.bytesToHex(SHA3.hash(test)));*/
		
		/*try {
			byte[] aliceIP = InetAddress.getLocalHost().getAddress();
			System.out.println(Util.bytesToHex(aliceIP)+" "+aliceIP.length);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] r = "r".getBytes();
		byte[] timestamp = "timestamp".getBytes();
		System.out.println(Util.bytesToHex(r)+" "+r.length);
		System.out.println(Util.bytesToHex(timestamp)+" "+timestamp.length);*/
		
		
		/*try {
			KeyPair kp = Util.LoadKeyPair(".","RSA");
			String teststring = "test1234!!";
			
			System.out.println("RSA :");
			
			byte[] test = teststring.getBytes();
			System.out.println("Original message : " + new String(test));
			byte[] encr = RSA.encrypt(test, kp.getPublic());
			System.out.println("Encrypted message : " + new String(encr));
			byte[] decr = RSA.decrypt(encr, kp.getPrivate());
			System.out.println("Decrypted message : " + new String(decr));

			System.out.println("\nAES :");

			String encryptionKey = "0123456789abcdef";
			SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
			
			System.out.println("Original message : " + new String(test));
			encr = AES.encrypt(test, key);
			System.out.println("Encrypted message : " + new String(encr));
			decr = AES.decrypt(encr, key);
			System.out.println("Decrypted message : " + new String(decr));

			System.out.println("\nSHA3 :");
			SHA3.hash(test);
			
			System.out.println("\nRSA Signature :");
			
			System.out.println("Original signature : " + new String(test));
			encr = RSA.generateSignature(test, kp.getPrivate());
			System.out.println("Encrypted signature : " + new String(encr));
			boolean res = RSA.verifySignature(encr, kp.getPublic(), test);
			System.out.println("Is signature ok ? " + res);
			
			System.out.println("\nSession key generation :");
			System.out.println(Util.bytesToHex(Util.generateSK()));
			System.out.println(Util.bytesToHex(Util.generateSK()));
			System.out.println(Util.bytesToHex(Util.generateSK()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
		if (args.length == 1){
			Bob bob = new Bob();
		}
		else{
			Alice alice = new Alice();
		}
	}

}
