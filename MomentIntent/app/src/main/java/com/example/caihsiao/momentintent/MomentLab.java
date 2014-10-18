package com.example.caihsiao.momentintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by caihsiao on 10/18/14.
 */
public class MomentLab {
  private ArrayList<Moment> mMoments;

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

  public Moment getMoment(UUID id) {
    for (Moment moment : mMoments) {
      if (moment.getId().equals(id)) {
        return moment;
      }
    }
    return null;
  }

  private MomentLab(Context appContext) {
    mAppContext = appContext;

    mMoments = new ArrayList<Moment>();
    for (int i = 0; i < 100; ++i) {
      Moment moment = new Moment();
      moment.setTitle("Moment #" + i);
      moment.setPublic(i % 2 == 0);
      mMoments.add(moment);
    }
  }
}
