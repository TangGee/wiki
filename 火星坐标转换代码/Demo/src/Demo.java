import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Demo {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("md5");
		
		String password = "14e1b600b1fd579f47433b88e8d85291";
		byte[] result = digest.digest(password.getBytes());
		StringBuffer sb = new StringBuffer();
		for(byte b : result){
			int number = b&0xff;//╪сян
			String str = Integer.toHexString(number);
			if(str.length()==1){
				sb.append("0");
			}
			sb.append(str);
		}
		System.out.println(sb.toString());
	}
}
