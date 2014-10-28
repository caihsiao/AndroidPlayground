package com.example.caihsiao.momentintent;

import org.json.JSONException;
import org.json.JSONObject;

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

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_DATE = "date";
    private static final String JSON_PUBLIC = "public";

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

    @Override
    public String toString() {
    return mTitle;
  }

    public Moment() {
        // Generate unique identifier.
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Moment(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        mTitle = json.getString(JSON_TITLE);
        mDate = new Date(json.getLong(JSON_DATE));
        mPublic = json.getBoolean(JSON_PUBLIC);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_DATE, mDate.getTime());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_PUBLIC, mPublic);
        return json;
    }
}
