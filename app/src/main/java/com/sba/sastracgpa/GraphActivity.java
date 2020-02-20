package com.sba.sastracgpa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    public static final String my_pref_key ="sba_data";
    public static final String my_pref_sems="NO_OF_SEMS";
    public static final String[] my_pref_crds = {"c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10"};
    public static final String[] my_pref_sgpa = {"s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "s10"};
    private SharedPreferences sharedPreferences ;





    int nos;
    AnyChartView anyChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Declaration();

        List<DataEntry> data = new ArrayList<>();
        for (int i = 0; i < nos; i++) {
            data.add(new ValueDataEntry(String.valueOf(i + 1), Double.parseDouble(sharedPreferences.getString(my_pref_sgpa[i], ""))));
        }

        Cartesian cartesian = AnyChart.column();


        cartesian.yScale().maximum(10d);
        Column column = cartesian.column(data);
        cartesian.xAxis(0).title("Sem");

        cartesian.yAxis(0).title("SGPA");
        cartesian.animation(true);
        anyChartView.setChart(cartesian);





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void Declaration(){
        Toolbar toolbar =findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        sharedPreferences = getSharedPreferences(my_pref_key, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(my_pref_sems) && Integer.parseInt(sharedPreferences.getString(my_pref_sems,""))!=0)
        {
            nos= Integer.parseInt(sharedPreferences.getString(my_pref_sems,""));
        }else
        {
            Toast.makeText(this, "Go to the CGPA TAB and Update Sems", Toast.LENGTH_LONG).show();
            finish();
        }

        anyChartView =findViewById(R.id.anyChartView);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

    }
}
