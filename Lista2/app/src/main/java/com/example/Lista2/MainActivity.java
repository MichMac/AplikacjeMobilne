package com.example.Lista2;

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
    private boolean[] mQuestionAnswered = new boolean[mQuestionBank.length];
    private int mCurrentIndex = 0;
    private int mCorrectAnswers = 0;
    private int mAnswers = 0;

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
            mAnswers++;
            NextQuestion();
            updateQuestion();
            IsDone();
            }

        });

        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            checkAnswer(false);
            mAnswers++;
            NextQuestion();
            updateQuestion();
            IsDone();
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
        ButtonStatus();
    }

    private void NextQuestion()
    {

        //mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        if (mCurrentIndex == mQuestionBank.length - 1 ){
            mCurrentIndex = 0;
        }
        else {
            mCurrentIndex += 1;
        }

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
            mCorrectAnswers++;
        }
        else {
            messageResId = R.string.incorrect_toast;

        }
        mQuestionBank[mCurrentIndex].setAnswered(true);
        //Odpowiada za blokade pytania na które się udzieliło odpowiedzi.
        Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 90);
        toast.show();
    }
    private void ButtonStatus(){
        if (mQuestionBank[mCurrentIndex].isAnswered()) {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        }
        else{
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }

    }
    private void IsDone(){

        if (mQuestionBank.length == mAnswers){
            Result();
        }

    }
    private void Result(){
        int AllQestions = mQuestionBank.length;
        String text = "Ukończyłeś quiz, zdobyłeś  " + mCorrectAnswers + " na " + AllQestions ;
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
