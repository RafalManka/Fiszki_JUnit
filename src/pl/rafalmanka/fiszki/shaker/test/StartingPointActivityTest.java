package pl.rafalmanka.fiszki.shaker.test;

import pl.rafalmanka.fiszki.shaker.model.DatabaseHandler;
import pl.rafalmanka.fiszki.shaker.view.AddNewWordsetActivity;
import pl.rafalmanka.fiszki.shaker.view.LanguageListActivity;
import pl.rafalmanka.fiszki.shaker.view.MainActivity;
import pl.rafalmanka.fiszki.shaker.view.SettingsActivity;
import pl.rafalmanka.fiszki.shaker.view.StartingPointActivity;
import pl.rafalmanka.fiszki.shaker.view.TopicsListActivity;
import pl.rafalmanka.fiszki.shaker.view.WordsListActivity;
import android.database.Cursor;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jayway.android.robotium.solo.Solo;

public class StartingPointActivityTest extends
		ActivityInstrumentationTestCase2<StartingPointActivity> {

	private Solo mSolo;
	private static final String TAG = StartingPointActivityTest.class.getSimpleName();

	public StartingPointActivityTest() {
		super(StartingPointActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mSolo = new Solo(getInstrumentation(), getActivity());
	}

	public void testIfButtonsPoinToTheRightDestination() {
		mSolo.assertCurrentActivity("check on sterting point activity",
				StartingPointActivity.class);

		// first button - flipcard
		ImageButton buttonview = (ImageButton) mSolo
				.getCurrentActivity()
				.findViewById(
						pl.rafalmanka.fiszki.shaker.R.id.imageButton_go_to_flipcard); // getting
																						// the
																						// view
		mSolo.clickOnView(buttonview); // clicking
		mSolo.assertCurrentActivity("flipcard activity", MainActivity.class); // assertion

		mSolo.goBack(); // going back to previous activity

		buttonview = (ImageButton) mSolo.getCurrentActivity().findViewById(
				pl.rafalmanka.fiszki.shaker.R.id.imageButton_go_to_settings);
		mSolo.clickOnView(buttonview);
		mSolo.assertCurrentActivity("youre in settings", SettingsActivity.class);

		mSolo.goBack();

		buttonview = (ImageButton) mSolo.getCurrentActivity().findViewById(
				pl.rafalmanka.fiszki.shaker.R.id.imageButton_download_wordset);
		mSolo.clickOnView(buttonview);
		mSolo.assertCurrentActivity("youre in language list",
				LanguageListActivity.class);

		mSolo.clickOnText("English");
		mSolo.assertCurrentActivity("youre in wordsets list",
				TopicsListActivity.class);

		mSolo.clickOnText("house");
		mSolo.assertCurrentActivity("youre in word list for single wordset",
				WordsListActivity.class);

		mSolo.clickOnText(mSolo.getCurrentActivity().getString(
				pl.rafalmanka.fiszki.shaker.R.string.submit));
		mSolo.assertCurrentActivity("youre just saved a wordset",
				MainActivity.class);

	}

	
}
