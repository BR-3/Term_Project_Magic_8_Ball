package com.example.mcoo_255_term_project;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;
import static lib.Utils.showInfoDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import com.example.mcoo_255_term_project.databinding.ActivityMainBinding;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

import lib.Utils;

public class MainActivity extends AppCompatActivity {

    private TextView mAnswer, mThinkingText;
    private String mQuestionAnswer;
    private TextInputEditText mUserInput;
    private ActivityMainBinding binding;
    private Snackbar mSnackBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        Utils.setNightModeOnOffFromPreferenceValue(getApplicationContext(), getString(R.string.night_mode_key));
        mSnackBar =
                Snackbar.make(findViewById(android.R.id.content), "Hello",
                        Snackbar.LENGTH_LONG);
        setupToolbar();
        setupFAB();
        setupFields();
    }

    private void setupFAB() {
        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // validate that a question was asked
                if(mUserInput.getText().toString() == "") { // makethis work
                    Toast.makeText(getApplicationContext(),
                            R.string.error_message_no_question,
                            Toast.LENGTH_SHORT).show();
                } else {
                    mQuestionAnswer = Magic8Ball.answerQuestion();
                    // insert logic to make it blink on and off
                    mThinkingText.setText("Thinking...");
                    mAnswer.setText(mQuestionAnswer);
                    mSnackBar.setText(Magic8Ball.getQuestionCount() + "questions asked.");
                    mSnackBar.show();
                }
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFields() {
        mAnswer = findViewById(R.id.answer_text);
        mThinkingText = findViewById(R.id.thinking_text);
        mUserInput = findViewById(R.id.user_input);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        int itemId = item.getItemId();
        if (itemId == R.id.action_history) {
            TODO:// have another activitythat shows previous questions and ansewers (in scrollview)
            return true;
        } else if (itemId == R.id.action_delete_history) {
            // delete all history, alert on snackbar that they were eleted
            return true;
        } else if (itemId == R.id.action_settings) {
            showSettings();
            return true;
        } else if (itemId == R.id.action_about) {
            showAbout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAbout() {
        dismissSnackBarIfShown();
        showInfoDialog(MainActivity.this, "About Magic 8 Ball",
                "A fun answer to any question you have!\n" +
                        "\nAndroid game by BR.\nMCOO 255 Term Project");
    }
    ActivityResultLauncher<Intent> settingsLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Handle result (optional: update UI based on changed preferences)
                }
            });

    private void showSettings() {
        dismissSnackBarIfShown();
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        settingsLauncher.launch(intent);
    }

    private void restoreOrSetFromPreferences_AllAppAndGameSettings() {
        getDefaultSharedPreferences(this);
    }
    private void dismissSnackBarIfShown() {
        if (mSnackBar.isShown()) {
            mSnackBar.dismiss();
        }
    }
}
