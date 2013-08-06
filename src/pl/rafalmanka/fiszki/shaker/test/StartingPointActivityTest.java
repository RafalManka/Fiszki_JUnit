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

	/**
	 * here I'm trying to test weather insertion of new word-set is performed
	 * properly. I enter activity, insert new word-set with couple of new random
	 * words and then check weather those words are 1. present in database under
	 * correct indexes 2. check if those words are inserted only once 3. if no
	 * other errors do not occur 4. Delete the wordset 5. test if no remainings
	 * of previous wordset can be found in database
	 */
	public void testIfNewWordsetIsInsertedProperly() {
		int numberOfWordsToInsert = 2;// i set 5 as number of words to input
		String wordsetName = "one_string_name";

		ImageButton imageButton = (ImageButton) mSolo.getCurrentActivity()
				.findViewById(
						pl.rafalmanka.fiszki.shaker.R.id.imageButton_titlebar);
		mSolo.clickOnView(imageButton);
		mSolo.assertCurrentActivity("youre in starting point activity",
				StartingPointActivity.class);

		imageButton = (ImageButton) mSolo.getCurrentActivity().findViewById(
				pl.rafalmanka.fiszki.shaker.R.id.imageButton_add_wordset);
		mSolo.clickOnView(imageButton);
		mSolo.assertCurrentActivity("youre add new wordset activity",
				AddNewWordsetActivity.class);

		// typing into textfields some information about the wordset
		EditText editText = (EditText) mSolo
				.getCurrentActivity()
				.findViewById(
						pl.rafalmanka.fiszki.shaker.R.id.editText_add_new_dictionary_enter_title);
		mSolo.enterText(editText, wordsetName);

		EditText editText_word = (EditText) mSolo
				.getCurrentActivity()
				.findViewById(
						pl.rafalmanka.fiszki.shaker.R.id.editText_add_new_dictionary_add_new_word);
		EditText editText_translaion = (EditText) mSolo
				.getCurrentActivity()
				.findViewById(
						pl.rafalmanka.fiszki.shaker.R.id.editText_add_new_dictionary_add_new_translation);

		Button button = (Button) mSolo
				.getCurrentActivity()
				.findViewById(
						pl.rafalmanka.fiszki.shaker.R.id.button_add_new_dictionary_add_another);

		String word = "some word_";
		String translation = "some translation_";
		
		for (int i = 0; i < numberOfWordsToInsert; i++) {
			mSolo.enterText(editText_word, word + i);
			mSolo.enterText(editText_translaion, translation + i);
			mSolo.clickOnView(button);
		}

		button = (Button) mSolo
				.getCurrentActivity()
				.findViewById(
						pl.rafalmanka.fiszki.shaker.R.id.button_add_new_dictionary_submit_set);

		mSolo.clickOnView(button);
		
		DatabaseHandler dbHandler = new DatabaseHandler(mSolo.getCurrentActivity());
		for (int i=0;i<numberOfWordsToInsert;i++){			
			Cursor dbWordId = dbHandler.getWord(word+i);
			assertEquals(1, dbWordId.getCount());
			dbWordId.close();
			Log.d(TAG , "word is present only one time");
			
			Cursor dbTranslationId = dbHandler.getWord(translation+i);
			assertEquals(1, dbTranslationId.getCount());
			Log.d(TAG, "translation is present only one time");
			dbTranslationId.close();		
			
			Cursor dbWordTranslationRelationId = dbHandler.getWordTranslationRelation(word+i, translation+i);
			assertEquals(1, dbWordTranslationRelationId.getCount());
			Log.d(TAG, "relationship between word and translation is present only once");
			dbWordTranslationRelationId.close();
			
			Cursor dbWordWordsetRelationId = dbHandler.getWordWordsetRelation(word+i, wordsetName);
			assertEquals(1, dbWordWordsetRelationId.getCount());
			Log.d(TAG, "relationship between word and wordset is present only once");
			dbWordWordsetRelationId.close();
			
		}
		Cursor cursor = dbHandler.getlanguageFromWordset(wordsetName, "");
		assertEquals(1, cursor.getCount());
		cursor.close();
		
		cursor = dbHandler.getWordsetByName(wordsetName);
		assertEquals(1, cursor.getCount());
		cursor.close();
		
		
		
		
		mSolo.assertCurrentActivity("you should be viewing flipcard",
				MainActivity.class);

		imageButton = (ImageButton) mSolo.getCurrentActivity().findViewById(
				pl.rafalmanka.fiszki.shaker.R.id.imageButton_titlebar);
		mSolo.clickOnView(imageButton);
		mSolo.assertCurrentActivity("youre in starting point activity",
				StartingPointActivity.class);

	}
}
