package com.exampel.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Play game activity that runs the game.
 */

public class PlayGameActivity extends AppCompatActivity {

    private TextView usedLettersView;

    private EditText userInput;
    private ImageView hangmanImage;
    private int[] hangmanImageId;
    private Toast toast;
    private Hangman hangman;
    private TextView mysteryWordView;
    private TextView guessesLeftView;
    private String input;

    public static final String RESULT_MESSAGE = "result";
    public static final String RESULT_GUESSES_LEFT = "guesses_left";
    public static final String RESULT_CORRECT_WORD = "the_word";
    public static final String guessesLeftState = "guessesLeftState";
    public static final String badLettersState = "badLettersState";
    public static final String hiddenWordState = "hiddenWordState";
    public static final String realWordState = "realWordState";
    public static final String visibleState = "visibleState";
    public static final String hangmanImageState = "hangmanImageState";


    /**
     * Sets up the game and handles the user interface
     * @param savedInstanceState
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        Resources res = getResources();

        String[] allWords = res.getStringArray(R.array.all_words);

        hangmanImageId = new int[11];

        for (int i = 0; i < 11; i++) {
            hangmanImageId[i] = res.getIdentifier(getString(R.string.image_name) + i, "drawable", getPackageName());
        }

        if(savedInstanceState == null)
            hangman = new Hangman(allWords);
        else
            onRestoreInstanceState(savedInstanceState);


        Button guessButton = (Button) findViewById(R.id.button_guess);

        getMysteryWordView().setText(hangman.getHiddenWord());
        getGuessesLeftView().setText(hangman.getTriesLeft()+" "+getString(R.string.guesses_left));

        guessButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                input = getUserInput().getText().toString();
                char guess = input.charAt(0);
                guess = Character.toUpperCase(guess);

                handleGuess(guess);

            }
        });

    }

    /**
     * Handles the result
     * Starts a new activity when player has either won or lost.
     */
    public void showResult() {
        Intent intent = new Intent(this, ResultActivity.class);

        String resultText = "";

        if (hangman.hasWon())
            resultText = getString(R.string.you_won);
        else
            resultText = getString(R.string.you_lost_text);

        int guessesLeftResult = hangman.getTriesLeft();
        String correctWord = hangman.getRealWord();

        intent.putExtra(RESULT_MESSAGE, resultText);
        intent.putExtra(RESULT_CORRECT_WORD, correctWord);
        intent.putExtra(RESULT_GUESSES_LEFT, String.valueOf(guessesLeftResult));

        startActivity(intent);

    }

    /**
     * Handels customer input
     * @param c is a user input
     */
    public void handleGuess(char c) {

        try {

            if (hangman.hasUsedLetter(c) || input.length() > 1 || !hangman.isLetter(input)) {

                Context context = getApplicationContext();
                String text = getString(R.string.letter_used);

                if(input.length() > 1)
                    text = getString(R.string.one_character_toast);
                else if (!hangman.isLetter(input))
                    text = getString(R.string.only_char_text);

                int duration = Toast.LENGTH_SHORT;
                toast = Toast.makeText(context, text, duration);
                toast.show();

            } else {

                hangman.guess(c);

                getUserInput().setText("");
                getGuessesLeftView().setText(hangman.getTriesLeft() + " " + getString(R.string.guesses_left));

                getHangmanImage().setImageResource(hangmanImageId[hangman.getTriesLeft()]);

                hangman.getHiddenWord();
                getMysteryWordView().setText(hangman.getHiddenWord());
                getUsedLettersView().setText(hangman.getBadLettersUsed());

                if (hangman.hasWon() || hangman.hasLost()) {
                    showResult();
                }
            }
        } catch (StringIndexOutOfBoundsException e) {

            Context context = getApplicationContext();
            String text = getString(R.string.no_character_found);
            int duration = Toast.LENGTH_SHORT;
            toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }


    /**
     * Inflates the menu and adds items to the action bar if it is present
     *
     * @param menu
     * @return true if we want to make action bar present
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.play_game_menu, menu);
        return true;
    }

    /**
     * Handels actionbar icon clicks
     * @param item is the icon that user clicked on
     * @return returns true if user has clicked on one of the icons
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = true;

        int id = item.getItemId();

        switch(id) {
            case R.id.info:
                // User chose the "About" item, show the app settings UI...
                onClickMenuShowAboutActivity(item);
                break;
            default:
                // If we got here, the user's actin was not recognized.
                // Invoke the superclass to handle it.
                handled = super.onOptionsItemSelected(item);
        }
        return handled;
    }

    private void onClickMenuShowAboutActivity(MenuItem item) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(guessesLeftState, hangman.getTriesLeft());
        outState.putString(badLettersState, hangman.getBadLettersUsed());
        outState.putString(realWordState, hangman.getRealWord());
        outState.putString(hiddenWordState, hangman.getHiddenWord());
        outState.putBooleanArray(visibleState, hangman.getVisible());
        outState.putIntArray(hangmanImageState, hangmanImageId);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        int guesses = savedInstanceState.getInt(guessesLeftState);
        String badLetters = savedInstanceState.getString(badLettersState);
        String mysteryWord = savedInstanceState.getString(realWordState);
        String hiddenWord = savedInstanceState.getString(hiddenWordState);
        boolean[] visible = savedInstanceState.getBooleanArray(visibleState);


        getUsedLettersView().setText(badLetters);
        getHangmanImage().setImageResource(hangmanImageId[guesses]);

        hangman = new Hangman(mysteryWord, badLetters, guesses, hiddenWord.toCharArray(), visible);

    }


    /**
     *
     * @return mystery word vied
     */


    public EditText getUserInput() {
        if(userInput == null)
            userInput = (EditText) findViewById(R.id.userInput);

        return userInput;
    }


    /**
     * Returns the textView where the hidden word is displayed.
     * @return
     */

    public TextView getMysteryWordView() {
        if(mysteryWordView == null)
            mysteryWordView = (TextView) findViewById(R.id.word);
        return mysteryWordView;
    }

    /**
     * @return Returns the textView where the amout of guesses left is displayed.
     */
    public TextView getGuessesLeftView() {
        if(guessesLeftView == null)
            guessesLeftView = (TextView) findViewById(R.id.guessesLeftView);
        return guessesLeftView;
    }
    /**
     * @return Returns the textView where the used letters is displayed.
     */
    public TextView getUsedLettersView() {
        if(usedLettersView == null)
            usedLettersView = (TextView) findViewById(R.id.used_characters);

        return usedLettersView;
    }

    /**
     * @return Returns the image view where the hangman images are shown.
     */
    public ImageView getHangmanImage() {
        if(hangmanImage == null)
            hangmanImage = (ImageView) findViewById(R.id.hangman);
        return hangmanImage;
    }
}
