package com.sba.sastracgpa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TargetCgpaActivity extends AppCompatActivity {

    private TextView cur__cg, cur__cr,req__sq;
    private EditText total__cr,tar__cg;
    private Spinner total__sems;

    public static final String my_pref_key ="sba_data";
    public static final String my_pref_sems="NO_OF_SEMS";
    public static final String my_pref_cgpa="current_cgpa";
    public static final String my_pref_currentcrdt="current_credit";
    public static final String LOGTAG ="com.sba.MyLog";

    public static final int tot_s[]={8,9};

    int total_sems,total_cr,comp_sems;
    double cur_cg,cur_cr, tar_cg,req_sg;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_cgpa);
        Declaration();



        if(!sharedPreferences.contains(my_pref_sems))
        {
            Toast.makeText(this, "Please Save The CGPA and Come AGAIN :)", Toast.LENGTH_LONG).show();
            Log.e(LOGTAG,"no sems\n");
            finish();
            return ;
        }else if(Integer.parseInt(sharedPreferences.getString(my_pref_sems,""))==0)
        {
            String currentCg = "Current CGPA : " + 0.0;
            cur_cg = 0.0;
            String currentCredit = "Current Credit :" + 0;
            cur_cr = 0;
            cur__cr.setText(currentCredit);
            cur__cg.setText(currentCg);
            Log.v(LOGTAG, cur_cg + cur_cr+ " ");
        } else {
            Log.v(LOGTAG, sharedPreferences.getString(my_pref_sems, "") + " ");

            String currentCg = "Current CGPA : " + sharedPreferences.getString(my_pref_cgpa, "");
            cur_cg = Double.parseDouble(sharedPreferences.getString(my_pref_cgpa, ""));
            String currentCredit = "Current Credit :" + sharedPreferences.getString(my_pref_currentcrdt, "");
            cur_cr = Integer.parseInt(sharedPreferences.getString(my_pref_currentcrdt, ""));
            cur__cr.setText(currentCredit);
            cur__cg.setText(currentCg);
            Log.v(LOGTAG, cur_cg + cur_cr+ " ");
        }

        comp_sems =Integer.parseInt(sharedPreferences.getString(my_pref_sems,""));
        total__sems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int selectedItemIndex, long l) {
                total_sems =tot_s[selectedItemIndex];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void Declaration()
    {
        Toolbar toolbar =findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        sharedPreferences =getSharedPreferences(my_pref_key, Context.MODE_PRIVATE);

        cur__cg =findViewById(R.id.target2_currentcg);
        cur__cr =findViewById(R.id.target2_currentcrd);
        req__sq =findViewById(R.id.target2_requiredcg);
        total__cr =findViewById(R.id.target2_et_nxtcrd);
        tar__cg =findViewById(R.id.target2_cg);
        total__sems  =findViewById(R.id.target2_tot_spinner_sems);

    }

    public void predict(View view)
    {
        Boolean valid =false;
        if(!total__cr.getText().toString().equals("") && !tar__cg.getText().toString().equals("")&& !tar__cg.getText().toString().equals(".")  )
            valid=true;
        if(valid)
        {
            total_cr=Integer.parseInt(total__cr.getText().toString());
            tar_cg=Double.parseDouble(tar__cg.getText().toString());
            double x=
                    (
                       (double)
                            ((double)(tar_cg*total_cr) - (double)(cur_cg*cur_cr))
                                                /
                                    ((double)(total_cr-cur_cr))
                    );
            Log.i(LOGTAG,total_cr+" "+x+" "+tar_cg+" "+cur_cr+" "+cur_cg+" "+total_sems+" "+comp_sems);
            String reqCgPa = "Required SGPA : " + String.format("%.4f",x);
            req__sq.setText(reqCgPa);
        }else
            Toast.makeText(this, "Please Fill The Required Details", Toast.LENGTH_SHORT).show();
    }

}
