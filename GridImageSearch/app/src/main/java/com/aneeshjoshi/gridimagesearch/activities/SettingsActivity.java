package com.aneeshjoshi.gridimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.aneeshjoshi.gridimagesearch.R;
import com.aneeshjoshi.gridimagesearch.models.Setting;
import com.google.common.base.Strings;

public class SettingsActivity extends ActionBarActivity {

    public static final String INTENT_PARAM_SETTING = "setting";
    Spinner spnrImageSize;
    Spinner spnrImageColor;
    Spinner spnrImageType;
    EditText etSiteFilter;

    Button btnSave;

    private Setting mSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mSetting = (Setting) getIntent().getSerializableExtra(INTENT_PARAM_SETTING);
        setupViews();

    }

    private void setupViews() {
        etSiteFilter = (EditText) findViewById(R.id.et_setting_site);
        btnSave = (Button) findViewById(R.id.btn_save_settings);
        spnrImageSize= (Spinner) findViewById(R.id.spnr_image_size);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.image_size_choices, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spnrImageSize.setAdapter(sizeAdapter);


        spnrImageColor= (Spinner) findViewById(R.id.spnr_image_color);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(this,
                R.array.image_color_choices, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spnrImageColor.setAdapter(colorAdapter);


        spnrImageType= (Spinner) findViewById(R.id.spnr_image_type);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.image_type_choices, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spnrImageType.setAdapter(typeAdapter);

        if(!Strings.isNullOrEmpty(mSetting.getSize())){
            spnrImageSize.setSelection(mSetting.getSizePos());
        }
        if(!Strings.isNullOrEmpty(mSetting.getColor())){
            spnrImageColor.setSelection(mSetting.getColorPos());
        }
        if(!Strings.isNullOrEmpty(mSetting.getType())){
            spnrImageType.setSelection(mSetting.getTypePos());
        }
        if(!Strings.isNullOrEmpty(mSetting.getSite())){
            etSiteFilter.setText(mSetting.getSite());
        }

        spnrImageSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSetting.setSize((String) spnrImageSize.getSelectedItem());
                mSetting.setSizePos(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do nothing
            }
        });

        spnrImageColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSetting.setColor((String) spnrImageColor.getSelectedItem());
                mSetting.setColorPos(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do Nothing
            }
        });

        spnrImageType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSetting.setType((String) spnrImageType.getSelectedItem());
                mSetting.setTypePos(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do Nothing
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Editable siteText = etSiteFilter.getText();
//                if(siteText != null) {
                mSetting.setSite(etSiteFilter.getText().toString());
//                }
                setResult();
            }
        });


    }

    private void setResult() {
        Intent intent = new Intent();
        intent.putExtra(INTENT_PARAM_SETTING, mSetting);
        setResult(RESULT_OK, intent);
        finish();
    }
}
