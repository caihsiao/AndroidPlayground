package com.example.caihsiao.momentintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by caihsiao on 10/16/14.
 */
public class Moment {
  private UUID mId;
  private String mTitle;
  private Date mDate;
  private boolean mPublic;

  public Date getDate() {
    return mDate;
  }

  public void setDate(Date date) {
    mDate = date;
  }

  public boolean isPublic() {
    return mPublic;
  }

  public void setPublic(boolean aPublic) {
    mPublic = aPublic;
  }

  public UUID getId() {
    return mId;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public Moment() {
    // Generate unique identifier.
    mId = UUID.randomUUID();
    mDate = new Date();
  }
}
