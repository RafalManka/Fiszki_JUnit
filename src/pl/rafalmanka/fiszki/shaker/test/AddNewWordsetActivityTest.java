package pl.rafalmanka.fiszki.shaker.test;

import java.util.ArrayList;

import pl.rafalmanka.fiszki.shaker.model.DatabaseHandler;
import pl.rafalmanka.fiszki.shaker.view.AddNewWordsetActivity;
import pl.rafalmanka.fiszki.shaker.view.ChooseLocalSetActivity;
import pl.rafalmanka.fiszki.shaker.view.MainActivity;
import pl.rafalmanka.fiszki.shaker.view.StartingPointActivity;
import android.database.Cursor;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jayway.android.robotium.solo.Solo;

public class AddNewWordsetActivityTest extends
		ActivityInstrumentationTestCase2<AddNewWordsetActivity> {

	private Solo mSolo;
	private String mWordsetName = "one_string_name";
	private static final String TAG = AddNewWordsetActivityTest.class
			.getSimpleName();

	public AddNewWordsetActivityTest() {
		super(AddNewWordsetActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mSolo = new Solo(getInstrumentation(), getActivity());
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
		int numberOfWordsToInsert = 2;// number of words to input

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
		mSolo.enterText(editText, mWordsetName);

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

		DatabaseHandler dbHandler = new DatabaseHandler(
				mSolo.getCurrentActivity());
		for (int i = 0; i < numberOfWordsToInsert; i++) {
			Cursor dbWordId = dbHandler.getWord(word + i);
			assertEquals(1, dbWordId.getCount());
			dbWordId.close();
			Log.d(TAG, "word is present only one time");

			Cursor dbTranslationId = dbHandler.getWord(translation + i);
			assertEquals(1, dbTranslationId.getCount());
			Log.d(TAG, "translation is present only one time");
			dbTranslationId.close();

			Cursor dbWordTranslationRelationId = dbHandler
					.getWordTranslationRelation(word + i, translation + i);
			assertEquals(1, dbWordTranslationRelationId.getCount());
			Log.d(TAG,
					"relationship between word and translation is present only once");
			dbWordTranslationRelationId.close();

			Cursor dbWordWordsetRelationId = dbHandler.getWordWordsetRelation(
					word + i, mWordsetName);
			Log.d(TAG, "dbWordWordsetRelationId.getCount(): "
					+ dbWordWordsetRelationId.getCount());
			assertEquals(1, dbWordWordsetRelationId.getCount());
			Log.d(TAG,
					"relationship between word and wordset is present only once");
			dbWordWordsetRelationId.close();

		}

		Cursor cursor = dbHandler.getlanguageFromWordset(mWordsetName, "");
		assertEquals(1, cursor.getCount());
		cursor.close();

		cursor = dbHandler.getWordsetByName(mWordsetName);
		assertEquals(1, cursor.getCount());
		cursor.close();

		mSolo.assertCurrentActivity("you should be viewing flipcard",
				MainActivity.class);
		
		
		testDeleteWordset();
		
		
	}


	public void testDeleteWordset() {
		ImageButton imageButton = (ImageButton) mSolo.getCurrentActivity()
				.findViewById(
						pl.rafalmanka.fiszki.shaker.R.id.imageButton_titlebar);
		mSolo.clickOnView(imageButton);
		mSolo.assertCurrentActivity("youre in starting point activity",
				StartingPointActivity.class);

		imageButton = (ImageButton) mSolo.getCurrentActivity().findViewById(
				pl.rafalmanka.fiszki.shaker.R.id.imageButton_manage_sets);
		mSolo.clickOnView(imageButton);
		mSolo.assertCurrentActivity("you should be in manage sets activity",
				ChooseLocalSetActivity.class);

		// mSolo.waitForText(mWordsetName);
		assertTrue(mSolo.searchText(mWordsetName));

		// get the list view
		ArrayList<ListView> listViews = mSolo.getCurrentViews(ListView.class);

		ListView lv = listViews.get(0);
		LinearLayout ll = (LinearLayout) lv.getChildAt(1);
		LinearLayout lll = (LinearLayout) ll.getChildAt(1);
		Button deleteButton = (Button) lll.getChildAt(1);
		mSolo.clickOnView(deleteButton);
		//mSolo.waitForText(pl.rafalmanka.fiszki.shaker.R.string.no);
		mSolo.clickOnButton(mSolo
				.getCurrentActivity()
				.getApplicationContext()
				.getResources()
				.getString(pl.rafalmanka.fiszki.shaker.R.string.yes));
		mSolo.assertCurrentActivity(MainActivity.class.getSimpleName(), MainActivity.class);

	}
}
