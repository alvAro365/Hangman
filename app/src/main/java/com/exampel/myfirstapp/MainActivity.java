package com.exampel.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Main activity class that extends AppCompatActivyt
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
    }

    /**
     * Called when the user taps the About button.
     * Starts a new acitivity called AboutActivity.
     * @param view is the button user clicked on.
     */
    public void seeInfo(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);


    }

    /**
     * Called when the user taps the Play Game button.
     * Starts a new Activity called PlayGameActivity.
     * @param view is the button user clicked on.
     */

    public void playGame(View view) {
        Intent intent = new Intent(this, PlayGameActivity.class);
        startActivity(intent);

    }

    /**
     * Inflates the menu and adds items to the action bar if it is present
     * @param menu
     * @return true if we want to make action bar present
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
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
            case R.id.action_info:
                // User chose the "About" item, show the app settings UI...
                onClickMenuShowAboutActivity(item);
                break;
            case R.id.action_play:
                // User chose the "Play" item, show the app play UI...
                onClickMenuShowPlayGameActivity(item);
                break;
            case R.id.exit:
                onClickMenuExit(item);
                break;
            default:
                // If we got here, the user's actin was not recognized.
                // Invoke the superclass to handle it.
               handled = super.onOptionsItemSelected(item);
        }
        return handled;
    }

    private void onClickMenuExit(MenuItem item) {
        finish();
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
