import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class RSA {
	public static byte[] encrypt(byte[] data, Key pubKey) {
		byte[] cipherData = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			cipherData = cipher.doFinal(data);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cipherData;
	}
	

	public static byte[] decrypt(byte[] data, Key privKey) {
		byte[] cipherData = null;
	    try {
			Cipher cipher = Cipher.getInstance("RSA");
		    cipher.init(Cipher.DECRYPT_MODE, privKey);
			cipherData = cipher.doFinal(data);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cipherData;
	}
	
	public static byte[] generateSignature(byte[] data, PrivateKey privKey) {
	    byte[] hash = SHA3.hash(data);
	    return RSA.encrypt(hash, privKey);
	    /*
	    Signature signature;
	    byte[] sigBytes = null;
		try {
			signature = Signature.getInstance("SHA1withRSA");

		    signature.initSign(privKey);
		    signature.update(data);

		    sigBytes = signature.sign();
		    
		    
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return sigBytes;*/
	}
	
	public static boolean verifySignature(byte[] data, PublicKey pubKey, byte[] msg) {
	    byte[] hash = SHA3.hash(msg);
	    return Arrays.equals(RSA.decrypt(data, pubKey),hash);/*
	    Signature signature;
	    boolean res = false;
		try {
			signature = Signature.getInstance("SHA1withRSA");

		    signature.initVerify(pubKey);
		    signature.update(msg);
		    res = signature.verify(data);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;*/
	}
}
