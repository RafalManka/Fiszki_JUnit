package pl.rafalmanka.fiszki.shaker.test;

import pl.rafalmanka.fiszki.shaker.model.DatabaseHandler;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

public class DatabaseHandlerTest extends AndroidTestCase {

	public static final String TAG = DatabaseHandlerTest.class.getSimpleName();
	public DatabaseHandlerTest() {
	}
	
	public DatabaseHandlerTest(String name) {
		setName(name);
	}

	public void testRemoveDb(){
		DatabaseHandler.removeDb( getContext() );
	}
	

}
