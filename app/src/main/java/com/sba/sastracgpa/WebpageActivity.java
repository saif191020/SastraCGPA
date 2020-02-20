package com.sba.sastracgpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class WebpageActivity extends AppCompatActivity {

    public static final String LOGTAG ="com.sba.MyLog";
    WebView webView;
    Elements ele;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);

         webView = new WebView(this);

        setContentView(webView);
        Log.i(LOGTAG,"into the Activity");
        if(getIntent().hasExtra("DATAS_"))
        {

            webView.loadUrl(getIntent().getStringExtra("DATAS_"));

            //Toast.makeText(this, getIntent().getStringExtra("DATAS_"), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplication(), "FAILED", Toast.LENGTH_SHORT).show();
            finish();
        }
       // new CONNECT().execute();

    }

   /* class CONNECT extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            if(getIntent().hasExtra("DATAS_"))
            {

                //webView.loadUrl(getIntent().getStringExtra("DATAS_"));
                try {
                    Log.i(LOGTAG,"Found Link");
                    final Document document = Jsoup.connect(getIntent().getStringExtra("DATAS_")).get();
                    Log.i(LOGTAG,"Connected To"+getIntent().getStringExtra("DATAS_"));
                     ele = document.select("div.item-page");
                    Log.i(LOGTAG,"Selected");
                   // Toast.makeText(getApplication(), ele.html(), Toast.LENGTH_SHORT).show();


                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                //Toast.makeText(this, getIntent().getStringExtra("DATAS_"), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplication(), "FAILED", Toast.LENGTH_SHORT).show();
                finish();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            webView.loadDataWithBaseURL(null,ele.html(), "text/html", "UTF-8",null);
            Log.i(LOGTAG,"Done" +ele.html());
        }
    }
*/
}
