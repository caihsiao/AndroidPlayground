package com.example.caihsiao.momentintent;

import java.util.UUID;

/**
 * Created by caihsiao on 10/16/14.
 */
public class Moment {
  private UUID mId;
  private String mTitle;

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
  }
}
