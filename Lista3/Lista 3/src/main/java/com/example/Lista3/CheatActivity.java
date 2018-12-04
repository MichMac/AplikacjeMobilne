package com.example.Lista3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private TextView mCheatTextView;
    private Button mShowAnswerButton;
    private static final String EXTRA__ANSWER_SHOWN = "com.example.Lista3.answer_shown";
    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.Lista3.answer_is_true";
    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }
    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA__ANSWER_SHOWN, false);
    }
    private boolean mAnswerIsTrue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);


    mCheatTextView = (TextView) findViewById(R.id.cheat_text_view) ;
    mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
    mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);


    mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mAnswerIsTrue){
                mCheatTextView.setText(R.string.true_button);
            }
            else {
                mCheatTextView.setText(R.string.false_button);
            }
            setAnswerShownResult(true);
        }
    });
    }
    private void setAnswerShownResult (boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA__ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);

    }
}
