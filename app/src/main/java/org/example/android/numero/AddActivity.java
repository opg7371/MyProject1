package org.example.android.numero;

import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by harshgupta on 17/09/16.
 */
public class AddActivity extends FragmentActivity {

    private final String LOG_TAG=AddActivity.class.getSimpleName();
    private TextView mNameTextView, mEmailTextView, mPhoneTextView;
    private Button nButton;
    private ContentResolver mContentResolver;
    static final int DIALOG_ID=0;
    private EditText nname,ncount,ndescription,ndate;
    private String nreply;
    private long ncreatedtime;
    private Cursor mCursor;
    public static final String[] MONTHS = {"JANUARY", "FEBUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULLY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);

        ImageButton dateselection = (ImageButton) findViewById(R.id.imageButton1);
        dateselection.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        DialogFragment newFragment = new DatePickerFragment();
                        newFragment.show(getFragmentManager().beginTransaction(), "datePicker");

                    }
                }
        );


//        getActionBar().setDisplayHomeAsUpEnabled(true);

        nname=(EditText) findViewById(R.id.categoryname);

        ndescription=(EditText) findViewById(R.id.description);

        ncount=(EditText) findViewById(R.id.initialcount);
        ncount.setText("0", TextView.BufferType.EDITABLE);

        ndate=(EditText) findViewById(R.id.date);
        presentdate(ndate);
        ndate.setKeyListener(null);

        nreply="Something goes wrong try again";
//
        mContentResolver = AddActivity.this.getContentResolver();

        ncreatedtime= TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

        nButton=(Button) findViewById(R.id.saveButton);
        nButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValid()){
                    ContentValues values=new ContentValues();
                    values.put(NumeroContract.NumeroColumns.NUMERO_CATEGORY, nname.getText().toString().toLowerCase());
                    values.put(NumeroContract.NumeroColumns.NUMERO_DESCRIPTION,ndescription.getText().toString());
                    values.put(NumeroContract.NumeroColumns.NUMERO_COUNT,Integer.valueOf(ncount.getText().toString()));
                    values.put(NumeroContract.NumeroColumns.NUMERO_DATE, ndate.getText().toString());
                    values.put(NumeroContract.NumeroColumns.NUMERO_CREATEDDATE,ncreatedtime);
                    values.put(NumeroContract.NumeroColumns.NUMERO_UPDATEDDATE,ncreatedtime);
                    Uri returned= mContentResolver.insert(NumeroContract.URI_TABLE,values);
                    Log.d(LOG_TAG,"record id returned is "+ returned.toString());
                    Intent intent = new Intent(AddActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),nreply, Toast.LENGTH_LONG).show();
                }
            }
        });

        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
    }






    private boolean isValid(){
        if(nname.getText().toString().length() == 0 ||
                ncount.getText().toString().length() == 0||
                ndescription.getText().toString().length() == 0||
                ndate.getText().toString().length()==0){
            nreply="please ensure you have entered data in All Fields.";
            return false;
        }else{
            String[] projection = {BaseColumns._ID,
                    NumeroContract.NumeroColumns.NUMERO_CATEGORY};
            String selection = NumeroContract.NumeroColumns.NUMERO_CATEGORY + " = '" + nname.getText().toString().toLowerCase() + "'";
            mCursor = mContentResolver.query(NumeroContract.URI_TABLE, projection,selection, null, null);
            if(mCursor!=null){
                if(mCursor.getCount()>0){
                    nreply="This Category Exists. Change Category Name";
                    return false;
                }else{
                    return true;
                }
            }else{
                return true;
            }

        }
    }

    private boolean someDataEntered(){
        if(mNameTextView.getText().toString().length()>0 ||
            mPhoneTextView.getText().toString().length()>0||
                mEmailTextView.getText().toString().length()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(AddActivity.this,MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finish();
    }

    public void addDate(String mdate){
        ndate.setText(mdate);
    }

    public void presentdate(EditText ndate){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        String month_x=MONTHS[month];
        int day = c.get(Calendar.DAY_OF_MONTH);

        if(day<10){
            ndate.setText("0"+day + "-" + month_x + "-" + year);
        }else{
            ndate.setText(day + "-" + month_x + "-" + year);
        }
    }


}
