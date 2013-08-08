package pl.rafalmanka.fiszki.shaker.test;

import pl.rafalmanka.fiszki.shaker.model.DatabaseHandler;
import pl.rafalmanka.fiszki.shaker.view.AddNewWordsetActivity;
import pl.rafalmanka.fiszki.shaker.view.SplashScreenActivity;
import pl.rafalmanka.fiszki.shaker.view.StartingPointActivity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

public class SplashScreenActivityTest extends
		ActivityInstrumentationTestCase2<SplashScreenActivity> {

	private Solo mSolo;
	private static final String TAG = SplashScreenActivityTest.class
			.getSimpleName();

	public SplashScreenActivityTest() {
		super(SplashScreenActivity.class);
	}
	
	public SplashScreenActivityTest(String name) {
		super(SplashScreenActivity.class);
		setName(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mSolo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	public void tearDown() throws Exception {
		mSolo.finishOpenedActivities();
	}

	public void testIfDatabaseWasCreated() {
		ActivityMonitor monitor = getInstrumentation().addMonitor(
				StartingPointActivity.class.getName(), null, false);
		assertNotNull(monitor);
		
		DatabaseHandler db = new DatabaseHandler(mSolo.getCurrentActivity().getApplicationContext());
		assertTrue( db.getRandom().getWord() instanceof String );
	}

}
