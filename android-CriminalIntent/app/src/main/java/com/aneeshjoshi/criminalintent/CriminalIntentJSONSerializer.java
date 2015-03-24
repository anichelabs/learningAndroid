package com.aneeshjoshi.criminalintent;

import android.content.Context;

import com.aneeshjoshi.criminalintent.models.Crime;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CriminalIntentJSONSerializer {
    private Context mContext;
    private String mFilename;

    public CriminalIntentJSONSerializer(Context c, String f){
        mContext = c;
        mFilename = f;
    }

    public void saveCrimes(ArrayList<Crime> crimes) throws JSONException, IOException {
        String json = new Gson().toJson(crimes);
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(json);
        } finally {
            if (writer != null){
                writer.close();
            }
        }
    }

    public ArrayList<Crime> loadCrimes() throws IOException, JSONException {
        ArrayList<Crime> crimes = new ArrayList<>();
        BufferedReader reader = null;
         try{
             //Open and read the file into a StringBuilder
             InputStream in = mContext.openFileInput(mFilename);
             reader = new BufferedReader(new InputStreamReader(in));
             StringBuilder jsonString = new StringBuilder();
             String line = null;
             while((line = reader.readLine()) != null){
                 //Line breaks are ommitted & irrelevant
                 jsonString.append(line);
             }

             Type listType = new TypeToken<ArrayList<Crime>>(){}.getType();
             crimes = new Gson().fromJson(jsonString.toString(), listType);
         } catch (FileNotFoundException fe){
             //do nothing
         } finally {
             if(reader != null){
                 reader.close();
             }
         }
        return crimes;

    }

}
