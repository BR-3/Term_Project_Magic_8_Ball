package com.example.mcoo_255_term_project;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;
import static lib.Utils.showInfoDialog;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;


import com.example.mcoo_255_term_project.databinding.ActivityMainBinding;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.os.PersistableBundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import models.Magic8Ball;
import lib.Utils;

public class MainActivity extends AppCompatActivity {

    private TextView mAnswer, mQuestionCountOutput;
    private String mQuestionAnswer;
    private TextInputEditText mUserInput;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        Utils.setNightModeOnOffFromPreferenceValue(getApplicationContext(), getString(R.string.night_mode_key));
        setupToolbar();
        setupFAB();
        setupFields();


    }

    private void setupFAB() {
        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        mAnswer.setVisibility(View.VISIBLE);
                    }
                };

                // validate that a question was asked
                String userQuestion = mUserInput.getText().toString();
                if(userQuestion.isEmpty()) { // make this work
                    Toast.makeText(getApplicationContext(),
                            R.string.error_message_no_question,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Magic8Ball.saveQuestion(userQuestion);
                    mAnswer.setVisibility(View.INVISIBLE);
                    mQuestionAnswer = Magic8Ball.answerQuestion();
                    // toast pop up that says thinking
                    Toast.makeText(getApplicationContext(),
                            R.string.thinking,
                            Toast.LENGTH_SHORT).show();
                    mAnswer.setText(mQuestionAnswer);
                    mAnswer.postDelayed(runnable, 3000);
                    mQuestionCountOutput.setText(Magic8Ball.getQuestionCount() + " questions asked.");
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
        mUserInput = findViewById(R.id.user_input);
        mQuestionCountOutput = findViewById(R.id.questions_asked);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(Magic8Ball.getQuestionCount() >0) {
            mQuestionCountOutput.setText(Magic8Ball.getQuestionCount() + " questions asked.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_history) {
            showHistory();
            return true;
        } else
        if (itemId == R.id.action_delete_count) {
            deleteQuestionCount();
            return true;
        } else
            if (itemId == R.id.action_settings) {
            showSettings();
            return true;
        } else if (itemId == R.id.action_about) {
            showAbout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showHistory() {
        Intent intent = new Intent(getApplicationContext(), QuestionHistoryActivity.class);
        startActivity(intent);
    }

    private void deleteQuestionCount() {
        Magic8Ball.deleteSavedQuestions();
        mQuestionCountOutput.setText("");
    }

    private void showAbout() {
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
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        settingsLauncher.launch(intent);
    }

    private void restoreOrSetFromPreferences_AllAppAndGameSettings() {
        getDefaultSharedPreferences(this);
    }
}
