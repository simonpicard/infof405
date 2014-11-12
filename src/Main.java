import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.spec.SecretKeySpec;

import SHA3.sha3sum;



public class Main {

	public static void main(String[] args) {
		
		try {
			KeyPair kp = KeyLoader.LoadKeyPair(".","RSA");
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
			String[] argv = {".\\SHA3test.txt"};
			sha3sum.run("sha3sum", argv);

			System.out.println("\nRSA Signature :");
			
			System.out.println("Original signature : " + new String(test));
			encr = RSA.generateSignature(test, kp.getPrivate());
			System.out.println("Encrypted signature : " + new String(encr));
			boolean res = RSA.verifySignature(encr, kp.getPublic(), test);
			System.out.println("Is signature ok ? " + res);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*if (args.length == 1){
			connectionHandler.server();
		}
		else{
			connectionHandler.client();
		}*/
	}

}
