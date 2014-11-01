package com.example.caihsiao.momentintent;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by caihsiao on 10/18/14.
 */
public class MomentLab {
  private ArrayList<Moment> mMoments;
  private MomentIntentJSONSerializer mSerializer;

  private static final String TAG = "MomentLab";
  private static final String FILENAME = "moments.json";

  private static MomentLab sMomentLab;
  private Context mAppContext;

  public static MomentLab getInstance(Context c) {
    if (sMomentLab == null) {
      sMomentLab = new MomentLab(c.getApplicationContext());
    }
    return sMomentLab;
  }

  public ArrayList<Moment> getMoments() {
    return mMoments;
  }

  public void addMoment(Moment moment) {
      mMoments.add(moment);
  }

  public Moment getMoment(UUID id) {
    for (Moment moment : mMoments) {
      if (moment.getId().equals(id)) {
        return moment;
      }
    }
    return null;
  }

  public boolean saveMoments() {
      try {
          mSerializer.saveMoments(mMoments);
          // Log.d(TAG, "Moments saved to file");
          return true;
      } catch (Exception e) {
          Log.e(TAG, "Error saving moments: ", e);
          return false;
      }
  }

  public void deleteMoment(Moment moment) {
      mMoments.remove(moment);
  }

  private MomentLab(Context appContext) {
    mAppContext = appContext;
    mSerializer = new MomentIntentJSONSerializer(mAppContext, FILENAME);

    // mMoments = new ArrayList<Moment>();
    try {
        mMoments = mSerializer.loadMoments();
    } catch (Exception e) {
        mMoments = new ArrayList<Moment>();
        Log.e(TAG, "Error loading moments: ", e);
    }

//    for (int i = 0; i < 100; ++i) {
//      Moment moment = new Moment();
//      moment.setTitle("Moment #" + i);
//      moment.setPublic(i % 2 == 0);
//      mMoments.add(moment);
//    }
  }
}
