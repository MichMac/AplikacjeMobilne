package com.example.Lista4;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mBackButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private TextView mVersionTextView;
    private static Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_stolica,true),
            new Question(R.string.question_stolica2,false),
            new Question(R.string.question_szczyt,true),
            new Question(R.string.question_rzeka,true)
    };
    
    private int mCurrentIndex = 0;
    private int mCorrectAnswers = 0;
    private int mAnswers = 0;
    private int CheaterMaxTokens = 3;
    private int CheaterTokens;
    private String version = "API level: " + Integer.valueOf(android.os.Build.VERSION.SDK);
    private boolean mIsCheater;
    private static final String TAG = "MainActivity";
    private static final String KEY_ARRAY = "my_array";
    private static final String KEY_INDEX = "index";
    private static final String KEY_TOKEN = "index";
    private static final int REQUEST_CODE_CHEAT =0;
    private static final String EXTRA_IS_CHEATER = "cheater";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null)
        {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mQuestionBank = (Question[]) savedInstanceState.getSerializable(KEY_ARRAY);
            mIsCheater = savedInstanceState.getBoolean(EXTRA_IS_CHEATER);
            CheaterTokens = savedInstanceState.getInt(KEY_TOKEN);
        }
        mVersionTextView = (TextView) findViewById(R.id.version_text_view);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mTrueButton = (Button) findViewById(R.id.b_true);
        mFalseButton = (Button) findViewById(R.id.b_false);
        mNextButton = (ImageButton) findViewById(R.id.b_next);
        mBackButton = (ImageButton) findViewById(R.id.b_back);
        mCheatButton = (Button) findViewById(R.id.cheat_button);

        mVersionTextView.setText(version);

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheaterTokens >= CheaterMaxTokens) {
                mCheatButton.setEnabled(false);
                //mVersionTextView.setText(String.valueOf(CheaterTokens));
                }
                else {
                    //mVersionTextView.setText(String.valueOf(CheaterTokens));
                    boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                    Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                    startActivityForResult(intent, REQUEST_CODE_CHEAT);
                }
            }
        });

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
                mIsCheater =false;
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
        if (mIsCheater) {
            messageResId = R.string.judgment_toast;}
            else{
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mCorrectAnswers++;
            } else {
                messageResId = R.string.incorrect_toast;
            }
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putSerializable(KEY_ARRAY, mQuestionBank);
        savedInstanceState.putBoolean(EXTRA_IS_CHEATER, mIsCheater);
        savedInstanceState.putInt(KEY_TOKEN, CheaterTokens);

    }
    @Override
    protected  void onActivityResult (int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            CheaterTokens = CheatActivity.CheaterTokensCounter(data);
        }

    }
}
