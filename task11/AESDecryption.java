package q11;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class AESDecryption {

	public static void main(String[] args) throws InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {

		System.out.println(AESDecryption.decrypt("0123456789abcdef","0000000000000000", "1ff4ec7cef0e00d81b2d55a4bfdad4ba"));
//		System.out.println(AESDecryption.decrypt("0011223344556677","0123456789abcdef", "9e4816cc13810b8424d788fbcd4b006b31bf45f5f9191072820ae0a545500c966cf22afda1002466a78b7e4ddf02587f"));
	}

	
	public static String decrypt(String ke,String ivv, String enc) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		
		
		byte[] encr = DatatypeConverter.parseHexBinary(enc);
		//byte[] key = DatatypeConverter.parseHexBinary(ke);
		//byte[] iv = DatatypeConverter.parseHexBinary(ivv);
		IvParameterSpec ivspec = new IvParameterSpec(ivv.getBytes()); 
		
		SecretKeySpec AesKey = new SecretKeySpec(ke.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, AesKey,ivspec);
		//cipher.update(encr);
		byte[] dec = cipher.doFinal(encr);
		
		return new String(dec, "UTF8");
	}
}
