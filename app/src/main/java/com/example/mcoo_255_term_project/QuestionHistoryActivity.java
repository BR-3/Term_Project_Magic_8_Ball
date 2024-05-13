package com.example.mcoo_255_term_project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import com.example.mcoo_255_term_project.databinding.ActivityQuestionHistoryBinding;

import models.Magic8Ball;

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