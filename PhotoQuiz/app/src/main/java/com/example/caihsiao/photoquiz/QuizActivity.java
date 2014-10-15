package com.example.caihsiao.photoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends Activity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private Boolean mIsCheater = false;
    public static final String TAG = "QuizActivity";
    public static final String KEY_INDEX = "index";
    public static final String IS_CHEATER = "cheat";

    private TrueFalse[] mQuestionBank = new TrueFalse[] {
      new TrueFalse(R.string.question_0, true),
      new TrueFalse(R.string.question_1, true),
      new TrueFalse(R.string.question_2, true),
      new TrueFalse(R.string.question_3, true),
      new TrueFalse(R.string.question_4, true),
      new TrueFalse(R.string.question_5, false),
    };

    private int mCurrentIndex;

    private void checkAnswer(boolean userPressedTrue) {
      int messageId = 0;
      if (mIsCheater) {
        messageId = R.string.cheat_toast;
      } else {
        messageId = (userPressedTrue == mQuestionBank[mCurrentIndex].isTrueQuestion()) ?
                R.string.correct_toast : R.string.incorrect_toast;
      }
      Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
          mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
          mIsCheater = savedInstanceState.getBoolean(IS_CHEATER);
        }

        Log.d(TAG, "on create start!");

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getQuestion());
        // Handle True and False buttons.
        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            checkAnswer(true);
          }
        });
        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            checkAnswer(false);
          }
        });
        // Handle Next button.
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            mIsCheater = false;
            mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getQuestion());
          }
        });
        // Handle Previous button.
        mPrevButton = (ImageButton)findViewById(R.id.previous_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            mCurrentIndex = (mCurrentIndex + mQuestionBank.length - 1) % mQuestionBank.length;
            mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getQuestion());
          }
        });
        // Handle Next directly by pressing on the text view.
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getQuestion());
          }
        });
        // Handle Cheat button.
        Button cheatButton = (Button)findViewById(R.id.cheat_button);
        cheatButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
            boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
            intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
            startActivityForResult(intent, 0);
          }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (data == null) {
        return;
      } else {
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
      }
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
      super.onSaveInstanceState(saveInstanceState);
      Log.i(TAG, "Save instance state.");
      saveInstanceState.putInt(KEY_INDEX, mCurrentIndex);
      saveInstanceState.putBoolean(IS_CHEATER, mIsCheater);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
