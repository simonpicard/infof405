import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Random;


public class Util {
	public static KeyPair LoadKeyPair(String pub, String priv, String algorithm)
			throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException, CertificateException {
		// Read Public Key.
		File filePublicKey = new File(pub);
		FileInputStream fis = new FileInputStream(pub);
		byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
		fis.read(encodedPublicKey);
		fis.close();
 
		// Read Private Key.
		File filePrivateKey = new File(priv);
		fis = new FileInputStream(priv);
		byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
		fis.read(encodedPrivateKey);
		fis.close();
 
		// Generate KeyPair.
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate certificate = (X509Certificate)cf.generateCertificate(new ByteArrayInputStream(encodedPublicKey));
		PublicKey publicKey = certificate.getPublicKey();
		
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		
		
		//X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
		//		encodedPublicKey);
		//PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
 
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
				encodedPrivateKey);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
 
		return new KeyPair(publicKey, privateKey);
	}
	
	public static byte[] generateSK(){
		byte[] b = new byte[16];
		new SecureRandom().nextBytes(b);
		return b;
	}
	
	public static byte[] generateR(){
		byte[] b = new byte[4];
		new SecureRandom().nextBytes(b);
		return b;
	}
	
	public static byte[] concatBytes(byte[] a, byte[] b){
		byte[] c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static byte[] getIV(){
		byte[] IV = {-111, 4, 126, -60, -29, -29, -86, 27, -125, -92, 73, 42, -45, 64, -121, 79};
		return IV;
	}
}
