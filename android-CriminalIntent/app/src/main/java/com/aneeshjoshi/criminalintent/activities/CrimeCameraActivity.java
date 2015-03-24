package com.aneeshjoshi.criminalintent.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.aneeshjoshi.criminalintent.fragments.CrimeCameraFragment;

public class CrimeCameraActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new CrimeCameraFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }
}
