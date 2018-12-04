package com.example.Lista4;

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
    private TextView mVersionTextView;
    private Button mShowAnswerButton;
    private boolean mAnswerIsTrue;
    public static int CheaterToken;
    private String version = "API level: " + Integer.valueOf(android.os.Build.VERSION.SDK);
    private static final String TAG = "CheatActivity";
    private static final String EXTRA__ANSWER_SHOWN = "com.example.Lista4.answer_shown";
    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.Lista4.answer_is_true";
    private static final String EXTRA_CHEATER_TOKEN = "com.example.Lista4.cheater_tokens";
    private static final String KEY_TOKENS = "tokens";

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }
    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA__ANSWER_SHOWN, false);
    }
    public static int CheaterTokensCounter(Intent result){
        return result.getIntExtra(EXTRA_CHEATER_TOKEN,0);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);


    mCheatTextView = (TextView) findViewById(R.id.cheat_text_view) ;
    mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
    mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
    mVersionTextView = (TextView) findViewById(R.id.version_text_view2);


    mVersionTextView.setText(version);

    mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (mAnswerIsTrue){
                mCheatTextView.setText(R.string.true_button);
            }
            else {
                mCheatTextView.setText(R.string.false_button);
            }
            mShowAnswerButton.setEnabled(false);
            CheaterToken++;
            setAnswerShownResult(true,CheaterToken);
            //mVersionTextView.setText(String.valueOf(CheaterToken));
        }
    });

    }
    private void setAnswerShownResult(boolean isAnswerShown, int cheaterToken){
        Intent data = new Intent();
        data.putExtra(EXTRA__ANSWER_SHOWN, isAnswerShown);
        data.putExtra(EXTRA_CHEATER_TOKEN, cheaterToken);
        setResult(RESULT_OK, data);
    }


}
