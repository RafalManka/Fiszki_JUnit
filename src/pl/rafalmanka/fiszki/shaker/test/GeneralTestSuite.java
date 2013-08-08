package pl.rafalmanka.fiszki.shaker.test;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class GeneralTestSuite extends TestCase {



	public static TestSuite suite() {
		TestSuite t = new TestSuite();
		for(int i=0;i<10;i++){
			
			
			t.addTest(new DatabaseHandlerTest("testRemoveDb")); // remove db and start from scratch
			t.addTest(new SplashScreenActivityTest("testIfDatabaseWasCreated"));
			t.addTest(new AddNewWordsetActivityTest(
					"testIfNewWordsetIsInsertedProperly"));
			t.addTest(new AddNewWordsetActivityTest("testDeleteWordset"));
			
		
		}
		return t;
	}



}
