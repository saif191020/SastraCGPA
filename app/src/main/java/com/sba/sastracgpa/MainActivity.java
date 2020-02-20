package com.sba.sastracgpa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Spinner nos;
    private Spinner[] cr = new Spinner[9];
    private Spinner[] gr = new Spinner[9];
    private TextView sgpa,cgpa;
    private int x;





    public static final String my_pref_key ="sba_data";
    public static final String my_pref_sems="NO_OF_SEMS";
    public static final String[] my_pref_crds = {"c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10"};
    public static final String[] my_pref_sgpa = {"s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "s10"};
    public static final String my_pref_cgpa="current_cgpa";

    private SharedPreferences sharedPreferences ;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Declaration();
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences= getSharedPreferences(my_pref_key, Context.MODE_PRIVATE);

        nos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int m, long l) {
                    x=Integer.parseInt(String.valueOf(nos.getSelectedItem()));
                for(int i=1;i<=8;i++)
                {
                    if(i<=x)
                    {
                        cr[i].setVisibility(View.VISIBLE);
                        gr[i].setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        cr[i].setVisibility(View.GONE);
                        gr[i].setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });//no of sems

        if(!sharedPreferences.contains(my_pref_sems)) {
            builder.setMessage("Looks like your first time here.\nPlease go to the CGPA tab and set ur CGPA of previous SEMS.\n(Set to 0 if no previous Sems)");
            builder.setTitle("Welcome To CGPA Calculator");
            builder.setCancelable(false);
            builder.setNeutralButton("Go Now!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent ini =new Intent(MainActivity.this,CgpaActivity.class);
                    startActivity(ini);
                }
            });
            AlertDialog alert =builder.create();
            alert.show();
        } //To display Welcome meg

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    } //inflate toolbar

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if(id==R.id.menu_cgpa)
        {
            Intent i =new Intent(this,CgpaActivity.class);
            startActivity(i);
        }
        else if(id==R.id.Graph)
        {
            Intent i =new Intent(MainActivity.this,GraphActivity.class);
            startActivity(i);
        }else if(id== R.id.Target)
        {
            Intent intent =new Intent(MainActivity.this,TargetActivity.class);
            startActivity(intent);
        } else if(id== R.id.News)
        {
            Intent intent =new Intent(MainActivity.this,NewsAndEvents.class);
            startActivity(intent);
        }
        return true;

    } //What Toolbar dose

    private void Declaration(){
        nos = findViewById(R.id.s_nos);

        //cr dec
        cr[1]=findViewById(R.id.c1);
        cr[2]=findViewById(R.id.c2);
        cr[3]=findViewById(R.id.c3);
        cr[4]=findViewById(R.id.c4);
        cr[5]=findViewById(R.id.c5);
        cr[6]=findViewById(R.id.c6);
        cr[7]=findViewById(R.id.c7);
        cr[8]=findViewById(R.id.c8);
        //Grade dec
        gr[1]=findViewById(R.id.g1);
        gr[2]=findViewById(R.id.g2);
        gr[3]=findViewById(R.id.g3);
        gr[4]=findViewById(R.id.g4);
        gr[5]=findViewById(R.id.g5);
        gr[6]=findViewById(R.id.g6);
        gr[7]=findViewById(R.id.g7);
        gr[8]=findViewById(R.id.g8);


        sgpa =findViewById(R.id.sgpa);
        cgpa =findViewById(R.id.cgpa);

        builder =new AlertDialog.Builder(this);

    } //Declare Ui elements


    public void calculate(View view){
        Double N=0.0,D=0.0;
        String c,g;
        boolean valid=true;
        Double sg;
        GradeConverter gc =new GradeConverter();
        for(int i =1; i<=x; i++)
        {
                  c=String.valueOf(cr[i].getSelectedItem());
                  g=String.valueOf(gr[i].getSelectedItem());
                  if(c.equals("-") || g.equals("-"))
                  {
                      Toast.makeText(this, "Enter Valid Details", Toast.LENGTH_LONG).show();
                      valid=false;
                      break;
                  }
                  else
                  {
                      D+=Integer.parseInt(c);
                      N+= (Integer.parseInt(c) * gc.convert(g));
                  }

        }
        if(valid)
        {
            sg= N/D;


            sgpa.setText("SGPA : "+ String.format("%.4f",sg));

            if(sharedPreferences.contains(my_pref_sems))
            {
                Double Nu=0.0,De=0.0;
                int sems =Integer.parseInt(sharedPreferences.getString(my_pref_sems,""));
                for(int j=0;j<sems;j++)
                {
                    Nu+=( Integer.parseInt(sharedPreferences.getString(my_pref_crds[j],"")) * Double.parseDouble(sharedPreferences.getString(my_pref_sgpa[j],""))) ;
                    De+= Integer.parseInt(sharedPreferences.getString(my_pref_crds[j],""));
                }
                Nu+=N;
                De+=D;
                cgpa.setText("CGPA : " + String.format("%.4f",Nu/De));
            }else
                cgpa.setText("CGPA : " + String.format("%.4f",sg));
        }
    } //Dose The calculations



}
