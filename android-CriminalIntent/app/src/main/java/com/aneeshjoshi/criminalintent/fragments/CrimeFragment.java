package com.aneeshjoshi.criminalintent.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.aneeshjoshi.criminalintent.CrimeLab;
import com.aneeshjoshi.criminalintent.R;
import com.aneeshjoshi.criminalintent.activities.CrimeCameraActivity;
import com.aneeshjoshi.criminalintent.models.Crime;

import java.util.Date;
import java.util.UUID;

import static android.provider.ContactsContract.CommonDataKinds.Phone;

public class CrimeFragment extends Fragment {
    public static final String EXTRA_CRIME_ID = "com.aneeshjoshi.criminalintent.crime_id";
    public static final int REQUEST_DATE = 0;
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_CALL_PHONE =  2;

    private static final String DIALOG_DATE = "date";

    private static final String TAG = "CrimeFragment";


    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private ImageButton mPhotoButton;
    private Button mSuspectButton;
    private Callbacks mCallbacks;
    private Button mCallButton;

    public interface Callbacks{
        void onCrimeUpdated(Crime crime);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mCrime = new Crime();
        UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        if(NavUtils.getParentActivityName(getActivity()) != null){
            ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
                mCallbacks.onCrimeUpdated(mCrime);
                getActivity().setTitle(mCrime.getTitle());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //This space is intentionally blank
            }

            @Override
            public void afterTextChanged(Editable s) {
                //This space is intentionally blank
            }
        });

        mDateButton = (Button) v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });


        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
                mCallbacks.onCrimeUpdated(mCrime);
            }
        });

        mPhotoButton = (ImageButton) v.findViewById(R.id.crime_imageButton);
        mPhotoButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CrimeCameraActivity.class);
                startActivity(i);
            }
        });

        PackageManager pm = getActivity().getPackageManager();
        boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) ||
                pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT) ||
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD &&
                        Camera.getNumberOfCameras() > 0);


        if(!hasACamera){
            mPhotoButton.setEnabled(false);
        }

        Button reportButton = (Button) v.findViewById(R.id.crime_reportButton);
        reportButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
                i = Intent.createChooser(i, getString(R.string.send_report));
                startActivity(i);
            }
        });

        mSuspectButton = (Button) v.findViewById(R.id.crime_suspectButton);
        mSuspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACT);
            }
        });


        mCallButton = (Button) v.findViewById(R.id.crime_callButton);
        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCrime.getPhoneNumber() != null) {
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse("tel: " + mCrime.getPhoneNumber()));
                    startActivityForResult(i, REQUEST_CALL_PHONE);
                } else {
                    Toast.makeText(CrimeFragment.this.getActivity(), "No phone number provided", Toast.LENGTH_SHORT);
                }
            }
        });

        if(mCrime.getSuspect() != null){
            mSuspectButton.setText(mCrime.getSuspect());
            mCallButton.setText("Call " + mCrime.getPhoneNumber());
        }

        return v;
    }

    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }


    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK) return;

        if(requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            mCallbacks.onCrimeUpdated(mCrime);
            updateDate();
        } else if(requestCode == REQUEST_CONTACT){
            Uri contactUri = data.getData();
            //Specify which fields you want your query to return values for.
            String [] queryFields = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts.HAS_PHONE_NUMBER,
                    ContactsContract.Contacts._ID
            };
            //Perform your query - the contactUri is like a where clause.
            Cursor c = getActivity().getContentResolver().query(contactUri, queryFields,
                    null, null, null);

            //Double-check that you actually got results.
            if(c.getCount() == 0){
                c.close();
                return;
            }

            //Pull out hte first column of the first row of data - suspect name
            c.moveToFirst();
            String suspect = c.getString(0);
            boolean suspectHasPhone = c.getInt(1) == 0 ? false : true;
            long suspectContactId = c.getLong(2);
            mCrime.setSuspect(suspect);
            if(suspectHasPhone){
                Cursor phoneCursor = getActivity().getContentResolver().query(Phone.CONTENT_URI,
                        null,
                        Phone.CONTACT_ID +  " = " + suspectContactId, null ,null);
                while (phoneCursor.moveToNext() && mCrime.getPhoneNumber() == null) {
                    String number = phoneCursor.getString(phoneCursor.getColumnIndex(Phone.NUMBER));
                    int type = phoneCursor.getInt(phoneCursor.getColumnIndex(Phone.TYPE));
                    switch (type) {
                        case Phone.TYPE_MAIN:
                        case Phone.TYPE_MOBILE:
                        case Phone.TYPE_WORK:
                            mCrime.setPhoneNumber(number);
                            break;
                    }
                }
                phoneCursor.close();
            }

            mCallbacks.onCrimeUpdated(mCrime);
            mSuspectButton.setText(suspect);
            mCallButton.setText("Call " + mCrime.getPhoneNumber());
            c.close();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                if(NavUtils.getParentActivityName(getActivity()) != null){
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

   public String getCrimeReport(){
        String solvedString;
        if(mCrime.isSolved()){
            solvedString = getString(R.string.crime_report_solved);
        } else {
            solvedString = getString(R.string.crime_report_unsolved);
        }

        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, mCrime.getDate()).toString();

        String suspect = mCrime.getSuspect();
        if(suspect == null){
            suspect = getString(R.string.crime_report_no_suspect);
        } else {
            suspect = getString(R.string.crime_report_suspect, suspect);
        }
        String report = getString(R.string.crime_report, mCrime.getTitle(), dateString, solvedString, suspect);

        return report;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
}
