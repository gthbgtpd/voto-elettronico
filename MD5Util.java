import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Util {
	public static String encrypt(String msg) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(msg.getBytes());
			return String.format("%032x", new BigInteger(1, m.digest()));
		} catch (Exception e) {
			return null;
		}
	}
}
