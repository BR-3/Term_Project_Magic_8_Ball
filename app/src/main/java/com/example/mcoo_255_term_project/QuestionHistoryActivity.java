package com.example.mcoo_255_term_project;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mcoo_255_term_project.databinding.ActivityQuestionHistoryBinding;

public class QuestionHistoryActivity extends AppCompatActivity {

    private ActivityQuestionHistoryBinding binding;
    public TextView mQuestionHistoryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolbar();

        setSupportActionBar(binding.toolbar);

        setupQuestionHistory();

        setupFAB();
    }

    private void setupToolbar() {
        binding = ActivityQuestionHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void setupFAB() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setupQuestionHistory() {
        mQuestionHistoryTextView = findViewById(R.id.question_history);
        mQuestionHistoryTextView.setText(Magic8Ball.getSavedQuestions());
    }

}