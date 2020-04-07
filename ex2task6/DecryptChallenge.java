package OTPstream;

import java.io.ByteArrayInputStream;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/*
 * A solution to https://challenges.re/50/
 * Written by Elias Josse and Amanda Nerborg
 * 
 */


public class DecryptChallenge {

	public static void main(String[] args) throws IOException {
		
		File file = new File("mfc100.dll.encrypted");  //File encrypted with XOR using 4-byte key
		File source = new File("mfc100.dll");		   //Potential source
		
		
		
		
		/*
		 * Determine if the potential source is true source by 1) comparing size and 2) making frequenzy analysis
		 */
		
		
		/*
		 * Comparing size
		 */
		System.out.println("Are the files of the same size?");
		System.out.println(file.length()==source.length() ? "Yes" : "No");
		
		
		
		
		
		
		byte[] filByte = Files.readAllBytes(file.toPath());
		byte[] srcfilByte = Files.readAllBytes(source.toPath());

		ByteArrayInputStream encStream = new ByteArrayInputStream(filByte);
		ByteArrayInputStream srcStream = new ByteArrayInputStream(srcfilByte);
		
		
		
		/*
		 * A frequenzy analysis can be made for every fourth byte in a portion of both of the files. Every fourth byte since
		 * the key is repeating as such. The distribution over the amount of times a char is mentioned
		 * for both files can be compared. If these are same it very likely that the source is the true source.
		 * 
		 */
		
		HashMap<Integer, Integer> freqSource = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> freqEnc = new HashMap<Integer, Integer>();
		for (int i = 0; i < 128; i++) {
			Integer byteSource = srcStream.read();
			Integer byteEnc = encStream.read();
			
			if(freqEnc.get(byteEnc) == null){
				freqEnc.put(byteEnc, 1);
			}
			else{
				int count = freqEnc.get(byteEnc);
				freqEnc.put(byteEnc, ++count);
				
			}
			
			if(freqSource.get(byteSource) == null){
				freqSource.put(byteSource, 1);
			}
			else{
				int count = freqSource.get(byteSource);
				freqSource.put(byteSource, ++count);
				
			}
			
			
			srcStream.read();
			encStream.read();
			
			srcStream.read();
			encStream.read();
			
			srcStream.read();
			encStream.read();
			
		}
		System.out.println("Occuring chars for encrypted file");
		System.out.println(freqEnc.toString() + "\n");
		System.out.println("Occuring chars for potential source file");
		System.out.println(freqSource.toString() + "\n");
		
		System.out.println("Occuring char counts");
		ArrayList list = new ArrayList(freqEnc.values());
		ArrayList list2 = new ArrayList(freqSource.values());
		Collections.sort(list);
		Collections.sort(list2);
		
		System.out.println(list.toString());
		System.out.println(list2.toString());
		
		System.out.println("Is the distribution same?");
		System.out.println(list.equals(list2) ? " Yes" : "No");
		
		
		
		encStream.reset();
		srcStream.reset();
		
		
		
		

		
		/*
		 * Compare first 4-bytes by XOR. Result is the key.
		 */
		byte[] key = new byte[4];
		
		int res = encStream.read() ^ srcStream.read();
		key[0] = (byte) res;
		
		res = encStream.read() ^ srcStream.read();
		key[1] = (byte) res;
		
		res = encStream.read() ^ srcStream.read();
		key[2] = (byte) res;
		
		res = encStream.read() ^ srcStream.read();
		key[3] = (byte) res;
		
		
		encStream.reset();
		srcStream.reset();
		
		
		System.out.println("Key:");
		for (int j = 0; j < key.length; j++) {
			System.out.print(String.format("%8s", Integer.toBinaryString(key[j] & 0xFF)).replace(' ', '0') + " ");
		}
	
		
		System.out.println("\n\nUsing key to decrypt..");
		
		// Write result in new file mfc100.dll.decprypted
		File resu = new File("mfc100.dll.decprypted"); 
		FileOutputStream ostrm = new FileOutputStream(resu);

		byte[] buff = new byte[4];
		while( encStream.available() > 0){
			buff[0] = (byte) ((byte) encStream.read() ^ key[0]);
			buff[1] = (byte) ((byte) encStream.read() ^ key[1]);
			buff[2] = (byte) ((byte) encStream.read() ^ key[2]);
			buff[3] = (byte) ((byte) encStream.read() ^ key[3]);
			ostrm.write(buff);
		}
		ostrm.close();
	}

}
