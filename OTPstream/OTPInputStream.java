package OTPstream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class OTPInputStream  extends InputStream{
	InputStream message;
	InputStream key;
	
	boolean Encrypt = true;
	
	
	public OTPInputStream(InputStream message, InputStream key) {
		this.message = message;
		this.key = key;
	}
	
	
	public void SetEncrypt(boolean bool) {
		Encrypt = bool;
	}
	public boolean Encrypt() {
		return Encrypt;
	}
	

	
	
	//encrypt / decrypt by value of alphabetical order
	@Override
	public int read() throws IOException {
		
		int mes = message.read();
		int ke = key.read();
		if(mes == -1 || ke == -1) return -1;
		mes -=65;
		ke -=65;
		
		
		if(Encrypt) mes = (mes + ke)%26;
		else{
			 mes = mes - ke;
			 mes = mes % 26;
			 if (mes < 0) //in case of negative modulo result
			 {
			     mes += 26;
			 }
		}
		return mes + 65;
	
	}
	
	
	
	//encrypt / decrypt by the byte using XOR
	public int readXOR() throws IOException{
		int mes = message.read();
		int ke = key.read();
		if(mes == -1 || ke == -1) return -1;
		
		mes = (mes ^ ke);
		return mes;
	}

	
	
	
public static void main(String[] args) {
	
	/*
	 * Example with capital
	 */
	
	System.out.println("Capital letters");
	//message and key
	String me = "SECRET";
	String ny = "IJMFDD";
	
	System.out.println("Message: " + me);
	System.out.println("Key: " + ny);
	
	
	//InputStreams
	InputStream ism = new ByteArrayInputStream(me.getBytes(StandardCharsets.UTF_8));
	InputStream isk = new ByteArrayInputStream(ny.getBytes(StandardCharsets.UTF_8));
	
	
	OTPInputStream otp = new OTPInputStream(ism, isk);
	
	
	String encRes = "";
	String decRes = "";
	
	try {
		int letter = otp.read();
		while(letter != -1) {
			encRes += (char)letter;
			letter = otp.read();
		}
		
		//new message inputstream
		otp.message = new ByteArrayInputStream(encRes.getBytes(StandardCharsets.UTF_8));
		
		
		otp.key.reset();
		
		
		//Set configuration to decryption
		otp.SetEncrypt(false);
		letter = otp.read();
		while(letter != -1) {
			decRes += (char)letter;
			letter = otp.read();
		}
		
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	System.out.println("Encrypted message: ");
	System.out.println(encRes);
	System.out.println("Decrypted encryption: ");
	System.out.println(decRes);
	
	
	
	
	
	
	
	/*
	 * Example with arbitrary data using XOR
	 */
	
	System.out.println("\n\n\nXOR with arbitrary data, displayed in bits by byte ");
	
	
	byte[] message = {123,23}; 
	byte[] key = {55,22};
	
	
	
	System.out.println("\nMessage: ");
	printBytes(message);
	
	System.out.println("\nKey: ");
	printBytes(key);
	
	byte[] encResXOR = new byte[message.length];
	byte[] decResXOR = new byte[key.length];
	
	OTPInputStream otpXOR = new OTPInputStream(new ByteArrayInputStream(message), new ByteArrayInputStream(key));
	
	try {
		int bite = otpXOR.readXOR();
		int place = 0;
		while(bite != -1){
			encResXOR[place] = (byte) bite;
			bite = otpXOR.readXOR();
			place++;
		}
		
		//refresh inputstreams
		otpXOR.message = new ByteArrayInputStream(encResXOR);
		otpXOR.key.reset();
		
		bite = otpXOR.readXOR();
		place = 0;
		while(bite != -1){
			decResXOR[place] = (byte) bite;
			bite = otpXOR.readXOR();
			place++;
		}
		
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	System.out.println("\nEncrypt: ");
	printBytes(encResXOR);
	System.out.println("\nDecrypt:");
	printBytes(decResXOR);
	
	}



	public static void printBytes(byte[] arr){
		
		for (int i = 0; i < arr.length; i++) {
			System.out.print(Integer.toString(arr[i],2) + " ");
		}
		
	}
}
