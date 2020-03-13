import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;


/*
 * Test "checkPin" method of "PIN.java" according to MC/DC using reflection.
 * Written by Elias Josse and Amanda Nerborg
 */

public class testPIN {
	PIN pin;
	Class cls;
	Method checkpin;
	Field securepin;
	Field trycount;
	Field access;
	
	
	@SuppressWarnings("unchecked")
	public testPIN() throws NoSuchMethodException, SecurityException, NoSuchFieldException {
		pin = new PIN(123);
		cls  = pin.getClass();
		checkpin = cls.getDeclaredMethod("checkPin",int.class);
		trycount = cls.getDeclaredField("tryCounter");
		securepin = cls.getDeclaredField("securePin");
		access = cls.getDeclaredField("access");
		
		securepin.setAccessible(true);
		trycount.setAccessible(true);
		access.setAccessible(true);
	}
	
	
	/*
	 * Access	     &&     (  trycounter>0	   ||     pin==securePin	)     Result
	 * False/True			False/True				False/True			      =Access post method call
	 * 
	 * MC/DC cases:
	 * F   T   F   /F
	 * F   T   T   /T
	 * T   T   F   /T
	 * F   F   T   /F
	 * 
	 * case relation:
	 * F T F -> T F T  / F->T change "Access" to "true" changes outcome
	 * F T F -> F T T  / F->T change "pin==securePin" to "true" changes outcome
	 * F F T -> F T T  / F->T change "tryCounter > 0" to "true" changes outcome
	 * 
	 */
	
	
	
	/*
	 * Access  trycounter>0	    pin==securePi    Result
	 * F		T			     F				 F 
	 */
	@Test
	public void testFTF() throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, NoSuchFieldException, InvocationTargetException {
		//access = false
		 access.set(pin, false);
		 assertFalse((boolean)access.get(pin));
		 
		 //trycounter > 0 = true
		 trycount.set(pin, 1);
		 assertTrue((int)trycount.get(pin) > 0);
		 
		 //pin == securePin = false
		 int notPin = 111;
		 assertFalse(notPin == (int)securepin.getInt(pin));
		 
		 
		 checkpin.invoke(pin, notPin);
		 assertFalse((boolean)access.get(pin)); 
	}
	
	
	/*
	 * Access  trycounter>0	    pin==securePi    Result
	 * F		T			     T				 T 
	 */
	
	@Test
	public void testFTT() throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, NoSuchFieldException, InvocationTargetException {
		//access = false
		 access.set(pin, false);
		 assertFalse((boolean)access.get(pin));
		 
		 //trycounter > 0 = true
		 trycount.set(pin, 2);
		 assertTrue((int)trycount.get(pin) > 0);
		 
		 
		 //pin == securePin = true
		 int correctPin = 123;
		 assertTrue(correctPin == (int)securepin.getInt(pin));
		 
		 
		 checkpin.invoke(pin, correctPin);
		 assertTrue((boolean)access.get(pin));
	}
	
	
	
	/*
	 * Access  trycounter>0	    pin==securePi    Result
	 * T		T			     F				 T 
	 */
	@Test
	public void testTTF() throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, NoSuchFieldException, InvocationTargetException {
		//access = true
		 access.set(pin, true);
		 assertTrue((boolean)access.get(pin));
		 
		 //trycounter > 0 = true
		 trycount.set(pin, 2);
		 assertTrue((int)trycount.get(pin) > 0);
		 
		 
		 //pin == securePin = false
		 int notPin = 111;
		 assertFalse(notPin == (int)securepin.getInt(pin));
		 
		 
		 checkpin.invoke(pin, notPin);
		 assertTrue((boolean)access.get(pin));
	}
	
	
	/*
	 * Access  trycounter>0	    pin==securePi    Result
	 * F		F			     T				 F 
	 */
	
	@Test
	public void testFFT() throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, NoSuchFieldException, InvocationTargetException {
		//access = false
		 access.set(pin, false);
		 assertFalse((boolean)access.get(pin));
		 
		 //trycounter > 0 = false
		 trycount.set(pin, 0);
		 assertFalse((int)trycount.get(pin) > 0);
		 
		 
		 //pin == securePin = true
		 int correctPin = 123;
		 assertTrue(correctPin == (int)securepin.getInt(pin));
		 
		 
		 checkpin.invoke(pin, correctPin);
		 assertFalse((boolean)access.get(pin));
	}

}
