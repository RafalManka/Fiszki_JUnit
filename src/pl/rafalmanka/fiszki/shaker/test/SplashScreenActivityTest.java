package pl.rafalmanka.fiszki.shaker.test;

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

	protected void setUp() throws Exception {
		super.setUp();
		mSolo = new Solo(getInstrumentation(), getActivity());
	}

	public void testIfDatabaseWasCreated() {
		ActivityMonitor monitor = getInstrumentation().addMonitor(StartingPointActivity.class.getName(), null, false);
		assertNotNull(monitor);
	}

}
