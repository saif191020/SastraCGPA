package com.sba.sastracgpa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class TargetActivity extends AppCompatActivity {

    private TextView CC, CCG, RCG;
    private EditText NC, target;
    /*        CC     =   Current_crd
              NC     =   nxtSem_crdt
              CCG    =   currentCG
              RCG    =    requiredCG
              target = TARGETCG
    */

    public static final String my_pref_key = "sba_data";
    public static final String my_pref_sems = "NO_OF_SEMS";
    public static final String my_pref_cgpa = "current_cgpa";
    public static final String my_pref_currentcrdt = "current_credit";
    public static final String LOGTAG = "com.sba.MyLog";

    private SharedPreferences sharedPreferences;

    int ccredit;
    Double curCGPA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        Declaration();


        if (!sharedPreferences.contains(my_pref_sems)) {
            Toast.makeText(this, "Please Save The CGPA and Come AGAIN :)", Toast.LENGTH_LONG).show();
            killActivity();
        } else if (Integer.parseInt(sharedPreferences.getString(my_pref_sems, "")) == 0) {
            Toast.makeText(this, "Set CGPA for atleast 1 Sem", Toast.LENGTH_LONG).show();
            killActivity();
            Log.v(LOGTAG, "IN HERE NOT SUPPOSED TO DISPAY");
        } else {
            Log.v(LOGTAG, sharedPreferences.getString(my_pref_sems, "") + " ");

            String currentCg = "Current CGPA : " + sharedPreferences.getString(my_pref_cgpa, "");
            curCGPA = Double.parseDouble(sharedPreferences.getString(my_pref_cgpa, ""));
            String currentCredit = "Current Credit :" + sharedPreferences.getString(my_pref_currentcrdt, "");
            ccredit = Integer.parseInt(sharedPreferences.getString(my_pref_currentcrdt, ""));
            CC.setText(currentCredit);
            CCG.setText(currentCg);
            Log.v(LOGTAG, curCGPA + ccredit + " ");
        }
    }

    public void Declaration() {
        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        sharedPreferences = getSharedPreferences(my_pref_key, Context.MODE_PRIVATE);
        CC = findViewById(R.id.target_currentcrd);
        CCG = findViewById(R.id.target_currentcg);
        RCG = findViewById(R.id.target_requiredcg);
        NC = findViewById(R.id.target_et_nxtcrd);
        target = findViewById(R.id.target_cg);

    }

    public void predict(View view) {
        Boolean valid = false;
        if (!NC.getText().toString().equals("") && !target.getText().toString().equals("") && !target.getText().toString().equals("."))
            valid = true;
        if (valid) {
            Double tar = Double.parseDouble(target.getText().toString());
            int nextCrdt = Integer.parseInt(NC.getText().toString());
            Double X;
            X = (
                    (Double)

                            ((Double) (tar * (ccredit + nextCrdt)) - (Double) ((ccredit * curCGPA)))
                            /
                            (nextCrdt)
            );
            String reqCgPa = "Required SGPA : " + String.format("%.4f", X);
            RCG.setText(reqCgPa);
        } else
            Toast.makeText(this, "Please Fill The Required Details", Toast.LENGTH_SHORT).show();
    }

    public void killActivity() {
        finish();
    }

}
