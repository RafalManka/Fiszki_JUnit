package pl.rafalmanka.fiszki.shaker.test;

import pl.rafalmanka.fiszki.shaker.model.Word;
import android.test.AndroidTestCase;
import junit.framework.TestCase;

public class WordTest extends AndroidTestCase {

	
	public void testNoArgumentConstructor(){
		Word w = new Word(); // passing string to constructor
		assertNotNull(w); // checking if object have been creted
	}
	
	public void testOneArgumentConstructor() {
		String expected = "!@@#$%^&*()_+|}{:\">?<>,./;'][=-0987`~'"; // assigning some random string 
		Word w = new Word(expected); // passing string to constructor
		assertNotNull(w); // checking if object have been creted
		String actual = w.getWord(); // fetching string
		assertEquals(expected, actual); // checking if strings are equal
	}

}
