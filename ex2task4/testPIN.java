import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

class testPIN {
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
	 * Access	trycounter>0	pin==securePin	Result
	 * A		try				pin				T/F
	 * 
	 *  
	 *  
	 */
	
	
	
	/*
	 * A		try				pin				T/F
	 * F		F				F				F
	 * 
	 */
	@Test
	void testFFF() throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, NoSuchFieldException, InvocationTargetException {
		testPIN test = new testPIN();
		
		
		//access = false
		 access.set(pin, false);
		 assertFalse((boolean)access.get(pin));
		 
		 //trycounter > 0 = false
		 trycount.set(pin, 0);
		 assertFalse((int)trycount.get(pin) > 0);
		 
		 
		 //pin == securePin = false
		 int notPin = 111;
		 assertFalse(notPin == (int)securepin.getInt(pin));
		 
		 
		 checkpin.invoke(pin, notPin);
		 
		 
		 
		 assertFalse((boolean)access.get(pin));
		
		 
		 
	}
	
	@Test
	void testFTT() throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, NoSuchFieldException, InvocationTargetException {
		testPIN test = new testPIN();
		
		
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
	
	
	

}
