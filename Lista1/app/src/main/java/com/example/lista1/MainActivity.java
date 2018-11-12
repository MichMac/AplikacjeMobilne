package com.example.lista1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mBackButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_stolica,true),
            new Question(R.string.question_stolica2,false),
            new Question(R.string.question_szczyt,true),
            new Question(R.string.question_rzeka,true)
    };
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mTrueButton = (Button) findViewById(R.id.b_true);
        mFalseButton = (Button) findViewById(R.id.b_false);
        mNextButton = (ImageButton) findViewById(R.id.b_next);
        mBackButton = (ImageButton) findViewById(R.id.b_back);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            checkAnswer (true);
            NextQuestion();
            updateQuestion();

            }

        });

        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            checkAnswer(false);
            NextQuestion();
            updateQuestion();
            }
        });

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextQuestion();
                updateQuestion();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                NextQuestion();
                updateQuestion();
            }

        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackQuestion();
                updateQuestion();
            }
        });

        updateQuestion();
    }
    private void updateQuestion()

    {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void NextQuestion()
    {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
    }

    private void BackQuestion()
    {
        if (mCurrentIndex <= 0) {
            mCurrentIndex = (mQuestionBank.length - 1);
        }
        else {
            mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
        }
    }


    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        }
        else {
            messageResId = R.string.incorrect_toast;

        }
        Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 90);
        toast.show();
    }


}
