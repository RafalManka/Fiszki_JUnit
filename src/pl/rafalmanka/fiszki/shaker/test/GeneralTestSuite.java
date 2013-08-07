package pl.rafalmanka.fiszki.shaker.test;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class GeneralTestSuite extends TestCase {

		public static TestSuite suite() {
			TestSuite t = new TestSuite("AllMyTests");
			
			
			AddNewWordsetActivityTest a = new AddNewWordsetActivityTest();
			t.addTest(AddNewWordsetActivityTest.testDeleteWordset());
			return t; 
		}

	

	
}
