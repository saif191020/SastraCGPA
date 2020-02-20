package com.sba.sastracgpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CgpaActivity extends AppCompatActivity {

    private Spinner nos;
    private LinearLayout ll[] = new LinearLayout[10];
    private EditText c[] =new EditText[10];
    private EditText s[] =new EditText[10];
    private TextView mycgpa ;

    public static final String my_pref_key ="sba_data";
    public static final String my_pref_sems="NO_OF_SEMS";
    public static final String my_pref_crds[]={"c1","c2","c3","c4","c5","c6","c7","c8","c9","c10"};
    public static final String my_pref_sgpa[]={"s1","s2","s3","s4","s5","s6","s7","s8","s9","s10"};
    public static final String my_pref_cgpa="current_cgpa";
    public static final String my_pref_currentcrdt="current_credit";

    public static final String LOGTAG ="com.sba.MyLog";

    private SharedPreferences sharedPreferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgpa);

        Declaration();

        sharedPreferences= getSharedPreferences(my_pref_key, Context.MODE_PRIVATE);
        checkFirstRunSet();




        nos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int selectedItemIndex, long l) {
                for (int i = 0; i < 10; i++)
                {
                    if(i<selectedItemIndex)
                    {
                        ll[i].setVisibility(View.VISIBLE);
                    }else
                    {
                        c[i].setText("");
                        s[i].setText("");
                        ll[i].setVisibility(View.GONE);
                    }

                }
                //SharedPreferences.Editor editor = sharedPreferences.edit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void Declaration() {

        nos = findViewById(R.id.sc_nos);

        ll[0]=findViewById(R.id.linearLayout1);
        ll[1]=findViewById(R.id.linearLayout2);
        ll[2]=findViewById(R.id.linearLayout3);
        ll[3]=findViewById(R.id.linearLayout4);
        ll[4]=findViewById(R.id.linearLayout5);
        ll[5]=findViewById(R.id.linearLayout6);
        ll[6]=findViewById(R.id.linearLayout7);
        ll[7]=findViewById(R.id.linearLayout8);
        ll[8]=findViewById(R.id.linearLayout9);
        ll[9]=findViewById(R.id.linearLayout10);

        c[0]=findViewById(R.id.etc_c1);
        c[1]=findViewById(R.id.etc_c2);
        c[2]=findViewById(R.id.etc_c3);
        c[3]=findViewById(R.id.etc_c4);
        c[4]=findViewById(R.id.etc_c5);
        c[5]=findViewById(R.id.etc_c6);
        c[6]=findViewById(R.id.etc_c7);
        c[7]=findViewById(R.id.etc_c8);
        c[8]=findViewById(R.id.etc_c9);
        c[9]=findViewById(R.id.etc_c10);

        s[0]=findViewById(R.id.etc_s1);
        s[1]=findViewById(R.id.etc_s2);
        s[2]=findViewById(R.id.etc_s3);
        s[3]=findViewById(R.id.etc_s4);
        s[4]=findViewById(R.id.etc_s5);
        s[5]=findViewById(R.id.etc_s6);
        s[6]=findViewById(R.id.etc_s7);
        s[7]=findViewById(R.id.etc_s8);
        s[8]=findViewById(R.id.etc_s9);
        s[9]=findViewById(R.id.etc_s10);

        mycgpa =findViewById(R.id.tv_current_cgpa);



    }

    public void checkFirstRunSet()
    {
        if(sharedPreferences.contains(my_pref_sems))
        {
            int sms=Integer.parseInt(sharedPreferences.getString(my_pref_sems,""));
            nos.setSelection(sms);
            Log.v(LOGTAG,"SEMS FOUND :"+sms);

            for(int i=0;i<sms;i++)
            {
                c[i].setText(sharedPreferences.getString(my_pref_crds[i],""));
                s[i].setText(sharedPreferences.getString(my_pref_sgpa[i],""));
            }
            if(sharedPreferences.contains(my_pref_cgpa))
            {
                Log.v(LOGTAG,"CGPA FOUND ");

                String myString ="CGPA : " + sharedPreferences.getString(my_pref_cgpa,"");
                mycgpa.setText(myString);
            }

        }
    }
    public void onSave(View view)
    {
        int sems=Integer.parseInt(nos.getSelectedItem().toString());
        boolean valid =true;
        SharedPreferences.Editor editor =sharedPreferences.edit();
        for(int i=0;i<sems;i++)
        {
            if(c[i].getText().toString().equals("") || s[i].getText().toString().equals("")|| s[i].getText().toString().equals("."))
            {
                valid =false;
                break;
            }
        }
        
        if(valid) {
            editor.putString(my_pref_sems, nos.getSelectedItem().toString());
            Log.v(LOGTAG, "Sems =" + sems);
            for (int i = 0; i < 10; i++) {
                if (i < sems) {
                    editor.putString(my_pref_crds[i], c[i].getText().toString());

                    editor.putString(my_pref_sgpa[i], s[i].getText().toString());

                    Log.v(LOGTAG, c[i].getText().toString() + " " + s[i].getText().toString());

                } else {
                    editor.putString(my_pref_crds[i], "");

                    editor.putString(my_pref_sgpa[i], "");
                }

            }


            if (sems != 0) {
                Double n = 0.0, d = 0.0;
                int dd=0;
                for (int j = 0; j < Integer.parseInt(nos.getSelectedItem().toString()); j++) {
                    n += Integer.parseInt(c[j].getText().toString()) * Double.parseDouble(s[j].getText().toString());
                    d += Integer.parseInt(c[j].getText().toString());
                    dd += Integer.parseInt(c[j].getText().toString());
                }
                String cgString = String.format("%.4f",n / d);
                Toast.makeText(this, "SAVED! Current CGPA : "+ cgString, Toast.LENGTH_SHORT).show();
                editor.putString(my_pref_cgpa, cgString);
                editor.putString(my_pref_currentcrdt, String.valueOf(dd));

                Log.v(LOGTAG,"CGPA SAVED");
            }else
            {
                Toast.makeText(this, "SAVED !", Toast.LENGTH_SHORT).show();
            }
            editor.commit();
            finish();
        }
        else 
        {
            Toast.makeText(this, "Enter All The Details", Toast.LENGTH_SHORT).show();
        }
    }


}
