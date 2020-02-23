import java.util.Arrays;


public class getPass {

	public static void main(String[] args) {
		/*
		 * Determine password length
		 */
		Secret sec = new Secret();
		int passLen = 0;
		String pass = "";
		for (int i = 1; i < 50; i++) {
			pass += "1";
			try {
				sec.verifyPassword(pass);
				passLen = i;
				break;
			}
			catch(IllegalArgumentException e) {	
				System.out.println("It failed");
			}
		}
		System.out.println("Length of pass is " + passLen);
		
		char[] passArr = new char[passLen];
		Arrays.fill(passArr, '0');
		
		/*
		 * start time
		 */
		long time = System.currentTimeMillis();
		sec.verifyPassword(new String(passArr));
		time = System.currentTimeMillis() - time;
		

		
		
		for (int i = 0; i < passArr.length; i++) {
			for (int j = 0; j < 10; j++) {
				
				passArr[i] = Character.forDigit(j, 10);
				String testPass = new String(passArr);
				
				long tempTime = System.currentTimeMillis();
				sec.verifyPassword(testPass);
				tempTime = System.currentTimeMillis() - tempTime;
				
				
				if(tempTime - time < -7){
					passArr[i] = Character.forDigit(--j, 10);
					break;
				}
				else if(tempTime - time > 7){
					time = tempTime;
					break;
				}
			}
		}
		
		System.out.println("pass is " + new String(passArr));
		System.out.println(sec.getSecret());
		
		/*
		 *  Password: 1655540751432153
		 *	Secret: 6299DA67DB8474B907E380912F34F337FE3DF89E
		 * 
		 */
		
		
		
	}

}
