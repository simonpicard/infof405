
import java.io.IOException;


public class SHA3 {
	public static byte[] hash(byte[] data){
		Keccak keccak = new Keccak(64);
		keccak.update(data, 0, data.length);
		byte[] res = keccak.digest();
		return res;
	}

}
