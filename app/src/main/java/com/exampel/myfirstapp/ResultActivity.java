package com.exampel.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backToMainMenu(view);

            }
        });

        Intent intent = getIntent();

        String resultText = intent.getStringExtra(PlayGameActivity.RESULT_MESSAGE);
        String guessesLeft = intent.getStringExtra(PlayGameActivity.RESULT_GUESSES_LEFT);
        String word = intent.getStringExtra(PlayGameActivity.RESULT_CORRECT_WORD);

        TextView guesses = (TextView) findViewById(R.id.guesses_left_result);

        guesses.setText(getString(R.string.amount_of_guesses_left_result)+" "+guessesLeft);

        TextView correctWord = (TextView) findViewById(R.id.word_result);
        correctWord.setText(getString(R.string.the_word_text)+" "+word);


        TextView result = (TextView) findViewById(R.id.final_result);

        result.setText(resultText);


    }

    /**
     * Inflates the menu and adds items to the action bar if it is present
     * @param menu
     * @return true if we want to make action bar menu present
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Called when the user taps the back button.
     * @param view is the button user clicked on.
     */
    public void backToMainMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
            case R.id.action_info:
                // User chose the "About" item, show the app settings UI...
                onClickMenuShowAboutActivity(item);
                break;
            case R.id.action_play:
                // User chose the "Play" item, show the app play UI...
                onClickMenuShowPlayGameActivity(item);
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

    private void onClickMenuShowPlayGameActivity(MenuItem item) {
        Intent intent = new Intent(this, PlayGameActivity.class);
        startActivity(intent);

    }
}
