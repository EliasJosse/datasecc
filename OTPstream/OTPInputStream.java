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
	

	//Encrypt
	@Override
	public int read() throws IOException {
		int mes = message.read();
		int ke = key.read();
		if(mes == -1 || ke == -1) return -1;
		
		mes = ((mes + ke)-130)%26;
		return mes + 65;
	
	}
	
	public int readXOR() throws IOException{
		int mes = message.read();
		int ke = key.read();
		if(mes == -1 || ke == -1) return -1;
		
		mes = (mes-65) ^ (ke - 65);
		return mes + 65;
	}

	
	
	
public static void main(String[] args) {
	String me = "ABC";
	String ny = "BBB";
	OTPInputStream str = new OTPInputStream(new ByteArrayInputStream(me.getBytes(StandardCharsets.UTF_8)), new ByteArrayInputStream(ny.getBytes(StandardCharsets.UTF_8) ));
	System.out.println("hmm");
	try {
		int letter = str.read();
		while(letter != -1) {
			System.out.println((char)letter);
			letter = str.readXOR();
		}
			
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	
//	Scanner in = new Scanner(System.in);   
//    String s = in.nextLine(); 
//    System.out.println("You entered string "+s);
		
	}
}
