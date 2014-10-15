package com.example.caihsiao.photoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class CheatActivity extends Activity {

    public static final String EXTRA_ANSWER_IS_TRUE =
            "com.example.caihsiao.photoquiz.answer_is_true";

    public static final String EXTRA_ANSWER_SHOWN =
            "com.example.caihsiao.photoquiz.answer_shown";

    private boolean mIsAnswerShown;

    private void answerShown(boolean isAnswerShown) {
      Intent data = new Intent();
      data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
      setResult(RESULT_OK, data);
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
      super.onSaveInstanceState(saveInstanceState);
      saveInstanceState.putBoolean(QuizActivity.IS_CHEATER, mIsAnswerShown);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mIsAnswerShown = false;
        if (savedInstanceState != null) {
          mIsAnswerShown = savedInstanceState.getBoolean(QuizActivity.IS_CHEATER);
          if (mIsAnswerShown) {
            TextView answerView = (TextView) findViewById(R.id.answer_text);
            boolean answer_is_true = getIntent().getBooleanExtra(
                    EXTRA_ANSWER_IS_TRUE, false);
            answerView.setText(answer_is_true ? R.string.true_button : R.string.false_button);
          }
        }
        answerShown(mIsAnswerShown);
        // Handle button show answers.
        Button showAnswer = (Button)findViewById(R.id.show_answer);
        showAnswer.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            TextView answerView = (TextView)findViewById(R.id.answer_text);
            boolean answer_is_true = getIntent().getBooleanExtra(
                    EXTRA_ANSWER_IS_TRUE, false);
            answerView.setText(answer_is_true ? R.string.true_button : R.string.false_button);
            mIsAnswerShown = true;
            answerShown(mIsAnswerShown);
          }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cheat, menu);
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
