package com.example.caihsiao.momentintent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by caihsiao on 10/27/14.
 */
public class MomentIntentJSONSerializer {
    private Context mContext;
    private String mFileName;

    public MomentIntentJSONSerializer(Context context, String fileName) {
        mContext = context;
        this.mFileName = fileName;
    }

    public void saveMoments(ArrayList<Moment> moments) throws JSONException, IOException {
        JSONArray array = new JSONArray();
        for (Moment m : moments) {
            array.put(m.toJSON());
        }

        // Write to file.
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public ArrayList<Moment> loadMoments() throws IOException, FileNotFoundException {
        ArrayList<Moment> moments = new ArrayList<Moment>();
        BufferedReader reader = null;
        try {
            InputStream in = mContext.openFileInput(mFileName);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < array.length(); ++i) {
                moments.add(new Moment(array.getJSONObject(i)));
            }
        } catch (JSONException e) {
            // Ignore, this will happen for the first time loading.
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return moments;
    }
}
