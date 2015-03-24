package com.aneeshjoshi.criminalintent;

import android.content.Context;
import android.util.Log;

import com.aneeshjoshi.criminalintent.models.Crime;

import java.util.ArrayList;
import java.util.UUID;

public class CrimeLab {
    public static final String TAG = "CrimeLab";
    public static final String FILENAME = "crimes.json";
    private CriminalIntentJSONSerializer mSerializer;


    private static CrimeLab sCrimeLab;
    private Context mAppContext;
    private ArrayList<Crime> mCrimes = new ArrayList<>();



    private CrimeLab(Context appContext){
        mAppContext = appContext;
        mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILENAME);
        try {
            mCrimes = mSerializer.loadCrimes();
        } catch (Exception e){
            mCrimes = new ArrayList<>();
            Log.e(TAG, "Error loading crimes", e);
        }
    }

    public static CrimeLab get(Context c){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for(Crime c : mCrimes){
            if(c.getId().equals(id)){
                return c;
            }
        }
        return null;
    }

    public void addCrime(Crime c){
        mCrimes.add(c);
    }

    public boolean saveCrimes(){
        try{
            mSerializer.saveCrimes(mCrimes);
            Log.d(TAG, "Crimes saved to file");
            return true;
        } catch (Exception e){
            Log.e(TAG, "Error saving crimes", e);
            return false;
        }
    }

    public void deleteCrime(Crime c){
        mCrimes.remove(c);
    }


}
