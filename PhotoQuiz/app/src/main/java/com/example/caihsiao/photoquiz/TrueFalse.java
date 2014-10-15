package com.example.caihsiao.photoquiz;

/**
 * Created by caihsiao on 10/11/14.
 */
public class TrueFalse {
  private int mQuestion;
  private boolean mTrueQuestion;

  public int getQuestion() {
    return mQuestion;
  }

  public void setQuestion(int question) {
    mQuestion = question;
  }

  public boolean isTrueQuestion() {
    return mTrueQuestion;
  }

  public void setTrueQuestion(boolean trueQuestion) {
    mTrueQuestion = trueQuestion;
  }

  public TrueFalse(int question, boolean trueQuestion) {
    mQuestion = question;
    mTrueQuestion = trueQuestion;
  }

}
